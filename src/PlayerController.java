import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlayerController implements MouseListener, KeyListener {  // handles player inputs

    private Square previouslySelected;
    private Sides currentTurn;
    private final Board board;
    private List<Square> legalMovesOfSelectedPiece;
    private List<Square> targetsOfSelectedArcher;
    private ConnectionHandler connectionHandler = null;

    private Key currentKey;

    private List<Piece> bPiecesCaptured;
    private List<Piece> rPiecesCaptured;

    public enum Key {
        SHIFT, ALT, CONTROL, NONE
    }

    private boolean isPaused = false;

    private final WinFrame winFrame;

    private Clock bClock;
    private Clock rClock;
    private int increment;

    public PlayerController(Board board) {
        previouslySelected = null;
        currentTurn = Sides.BLUE;
        this.board = board;
        board.addMouseListener(this); // OOP black magic
        winFrame = new WinFrame();
        this.currentKey = Key.NONE;
        board.addKeyListener(this);

        bPiecesCaptured = new ArrayList<>();
        rPiecesCaptured = new ArrayList<>();
    }

    public boolean isPaused() { return isPaused; }

    public void setConnectionHandler(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    public Sides getCurrentTurn() {
        return currentTurn;
    }

    public void swapTurns() {
        if (currentTurn == Sides.BLUE) currentTurn = Sides.RED;
        else currentTurn = Sides.BLUE;
    }


    public void selectSquare(Square selected) {  // conditions: square must contain a piece
        selected.setState(Square.ActionStates.PLAYER_SELECTED);  // highlight square selected
        this.previouslySelected = selected;
        legalMovesOfSelectedPiece = selected.getPiece().getLegalMoves(board);
        for (Square move : legalMovesOfSelectedPiece) {  // highlight all the legalMoves of selected piece
            move.setState(Square.ActionStates.LEGAL_MOVE);
        }
        if (selected.getPiece() instanceof Archer) { // if piece is archer
            targetsOfSelectedArcher = selected.getPiece().getTargets(board);
            for (Square shot : targetsOfSelectedArcher) { // highlight all targets
                shot.setState(Square.ActionStates.ARCHER_SHOT);
            }
        }
    }
    public void deselectCurrent() {
        this.previouslySelected.setState(Square.ActionStates.NONE);  // unhighlight square highlighted
        for (Square move : legalMovesOfSelectedPiece) {  // remove all legalMove highlights
            move.setState(Square.ActionStates.NONE);
        }
        if (targetsOfSelectedArcher != null) { // if piece is archer
            for (Square shot : targetsOfSelectedArcher) { // unhighlight all archer shot squares
                shot.setState(Square.ActionStates.NONE);
            }
        }
        this.previouslySelected = null;  // cut the references
        this.legalMovesOfSelectedPiece = null;
    }

    public void move(Square from, Square to) {
        Piece pieceToMove = from.getPiece();

        // Increment clocks when move occurs
        switch (pieceToMove.getSide()) {
            case BLUE -> bClock.increment(increment);
            case RED -> rClock.increment(increment);
        }

        boolean enPassantMove = false;

        if (pieceToMove instanceof Pawn) {
            if (!to.hasPiece()) enPassantMove = true;
        }

        // Winframe occurs when King blows up
        if (to.hasPiece() && to.getPiece() instanceof Bomber) {
            boolean rKingBlown = false;
            boolean bKingBlown = false;
            for (Square square : to.getPiece().getTargets(board)) {
                if (square.hasPiece() && square.getPiece() instanceof King) {
                    switch (square.getPiece().side) {
                        case BLUE -> {
                            bKingBlown = true;
                        }
                        case RED -> {
                            rKingBlown = true;
                        }
                    }
                }
            }
            if (rKingBlown && bKingBlown) {
                System.out.println("Draw");
            } else if (rKingBlown) {
                winFrame.makeWinFrame(Sides.BLUE);
            } else if (bKingBlown) {
                winFrame.makeWinFrame(Sides.RED);
            }
        }

        from.clearPiece();

        // King taken
        if (to.hasPiece() && to.getPiece() instanceof King) {
            System.out.println(pieceToMove.side + " has won!");
            winFrame.makeWinFrame(pieceToMove.side);
        }

        // Bomber explosion
        if (to.getPiece() instanceof Bomber && !(pieceToMove instanceof Assassin)) {
            // Add piece that took bomber to captured pieces list
            switch (pieceToMove.side) {
                case BLUE -> bPiecesCaptured.add(pieceToMove);
                case RED -> rPiecesCaptured.add(pieceToMove);
            }
            explode(to);
        } else {
            // Add piece to captured list
            if (to.hasPiece()) {
                switch (to.getPiece().side) {
                    case BLUE -> bPiecesCaptured.add(to.getPiece());
                    case RED -> rPiecesCaptured.add(to.getPiece());
                }
            }
            to.setPiece(pieceToMove);
        }

        if (to.getPiece() instanceof Pawn) {
            to.getPiece().setToMoved();
        }

        // En Passant
        if (enPassantMove) {
            switch (pieceToMove.side) {
                case BLUE -> board.getBoard()[(to.getFile() * 10) + to.getRank() - 10].clearPiece();
                case RED -> board.getBoard()[(to.getFile() * 10) + to.getRank() + 10].clearPiece();
            }
        }

        switch (pieceToMove.side) {
            case BLUE -> resetEnPassants(Sides.BLUE);
            case RED -> resetEnPassants(Sides.RED);
        }

        // Helper if statement for en passanting
        if (pieceToMove instanceof Pawn) {
            int fromPos = (from.getFile() * 10) + from.getRank();
            int toPos = (to.getFile() * 10) + to.getRank();
            if (Math.abs(toPos-fromPos) == 20) to.getPiece().setEnPassantable(true);
        }

        // Pawn promotions
        if (to.getPiece() instanceof Pawn && !isPaused) {
            switch (to.getFile()) {
                case 0 -> doPromotionEvent(to, Sides.RED);   // Red has made it to blue side
                case 9 -> doPromotionEvent(to, Sides.BLUE);  // Blue has made it to red side
            }
        }

    }

    public void attemptMove(Square selected) {
        if (previouslySelected != null && previouslySelected.hasPiece()) {  // if we currently have a piece selected to move
            Piece pieceToMove = previouslySelected.getPiece();

            if (isCorrectPlayerMoving()) {  // if the correct player is moving

                if (legalMovesOfSelectedPiece.contains(selected) && pieceToMove.canCapture(selected)) {  // if it is legal to move to the new location
                    move(previouslySelected, selected);
                    if (connectionHandler != null) {
                        String moves = previouslySelected.getX() + " " + previouslySelected.getY() + " " + selected.getX() + " " + selected.getY();  // coords movedto and movedfrom
                        connectionHandler.send(moves);
                    }
                    swapTurns();  // swap the turns on a successful move
                }

                // Archer fire
                if (pieceToMove instanceof Archer && pieceToMove.getTargets(board).contains(selected)) {  // COMMENT YOUR CODE <-----------------------
                    if (selected.getPiece() instanceof Bomber) explode(selected);
                    else {
                        // King shot
                        if (selected.getPiece() instanceof King) {
                            switch (selected.getPiece().side) {
                                case BLUE -> winFrame.makeWinFrame(Sides.RED);
                                case RED -> winFrame.makeWinFrame(Sides.BLUE);
                            }
                        }

                        // Add piece to captured list
                        switch (selected.getPiece().side) {
                            case BLUE -> bPiecesCaptured.add(selected.getPiece());
                            case RED -> rPiecesCaptured.add(selected.getPiece());
                        }

                        selected.clearPiece();
                        switch (pieceToMove.getSide()) {
                            case BLUE -> bClock.increment(increment);
                            case RED -> rClock.increment(increment);
                        }
                    }
                    swapTurns();
                }
            }
            deselectCurrent();  // clear board states on after this click
        } else {  //
            if (selected.hasPiece()) {
                selectSquare(selected);
            }
        }
    }

    public boolean isCorrectPlayerMoving() {  // checks whether it is the correct player's turn
        return (currentTurn == Sides.BLUE && previouslySelected.getPiece().getSide() == Sides.BLUE) || (currentTurn == Sides.RED && previouslySelected.getPiece().getSide() == Sides.RED);
    }

    public void explode(Square s) {
        List<Square> targets = s.getPiece().getTargets(board);
        for (Square target : targets) {
            // Add exploded pieces to captured list
            switch (target.getPiece().side) {
                case BLUE -> bPiecesCaptured.add(target.getPiece());
                case RED -> rPiecesCaptured.add(target.getPiece());
            }
            target.clearPiece();
        }
        // Add bomber to captured pieces list
        switch (s.getPiece().side) {
            case BLUE -> bPiecesCaptured.add(s.getPiece());
            case RED -> rPiecesCaptured.add(s.getPiece());
        }
        s.clearPiece();
    }

    public void resetEnPassants(Sides side) {
        for (Square square : board.getBoard()) {
            if (square.hasPiece()) {
                switch (side) {
                    case BLUE -> {
                        if (square.getPiece().side == Sides.BLUE) square.getPiece().setEnPassantable(false);
                    }
                    case RED -> {
                        if (square.getPiece().side == Sides.RED) square.getPiece().setEnPassantable(false);
                    }
                }
            }
        }
    }

    public void doPromotionEvent(Square parent, Sides side) {
        isPaused = true;  // pause the game while doing promotions
        JComboBox<String> popupMenu = new JComboBox<>();
        String[] pieces = {
                "Queen",
                "Rook",
                "Bishop",
                "Knight",
                "Royal Guard",
                "Archer",
                "Assassin",
        };
        for (String piece : pieces) {
            popupMenu.addItem(piece);
        }
        JOptionPane.showMessageDialog(null, popupMenu, "Promote piece", JOptionPane.QUESTION_MESSAGE);
        String selected = (String) popupMenu.getSelectedItem();
        switch (Objects.requireNonNull(selected)) {
            case "Queen" -> parent.setPiece(new Queen(side, board.getSquareSize(), parent));
            case "Rook" -> parent.setPiece(new Rook(side, board.getSquareSize(), parent));
            case "Bishop" -> parent.setPiece(new Bishop(side, board.getSquareSize(), parent));
            case "Knight" -> parent.setPiece(new Knight(side, board.getSquareSize(), parent));
            case "Royal Guard" -> parent.setPiece(new RoyalGuard(side, board.getSquareSize(), parent));
            case "Archer" -> parent.setPiece(new Archer(side, board.getSquareSize(), parent));
            case "Assassin" -> parent.setPiece(new Assassin(side, board.getSquareSize(), parent));
        }
        isPaused = false;
    }

    public Clock getbClock() {
        return bClock;
    }

    public Clock getrClock() {
        return rClock;
    }

    // Initialize clocks
    public void setClocks(int hours, int minutes, int seconds, int increment) {
        this.bClock = new Clock(hours, minutes, seconds);
        this.rClock = new Clock(hours, minutes, seconds);
        this.increment = increment;
    }

    public void unhighlightBoard() {
        for (Square s : board.getBoard()) s.setState(Square.ActionStates.NONE);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

        if (isPaused()) return;  // if the game is paused, disregard mouse events

        Square squareSelected = board.getSquareClicked(e.getX(), e.getY());
        if (SwingUtilities.isLeftMouseButton(e)) {
            unhighlightBoard();
            attemptMove(squareSelected);
        } else if (SwingUtilities.isRightMouseButton(e)) {  // for highlighting squares
            if (previouslySelected != null) deselectCurrent();
            if (squareSelected.getState() != Square.ActionStates.NONE)
                squareSelected.setState(Square.ActionStates.NONE);
            else {
                switch (currentKey) {
                    case NONE -> squareSelected.setState(Square.ActionStates.HIGHLIGHTED1);
                    case SHIFT -> squareSelected.setState(Square.ActionStates.HIGHLIGHTED2);
                    case ALT -> squareSelected.setState(Square.ActionStates.HIGHLIGHTED3);
                    case CONTROL -> squareSelected.setState(Square.ActionStates.HIGHLIGHTED4);
                }
            }
        }
    }

    public List<Piece> getbPiecesCaptured() {
        return bPiecesCaptured;
    }

    public List<Piece> getrPiecesCaptured() {
        return rPiecesCaptured;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }


    // Sets currentKey
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.isShiftDown()) currentKey = Key.SHIFT;
        else if (e.isAltDown()) currentKey = Key.ALT;
        else if (e.isControlDown()) currentKey = Key.CONTROL;
        else currentKey = Key.NONE;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        currentKey = Key.NONE;
    }
}