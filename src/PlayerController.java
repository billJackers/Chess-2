import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerController implements MouseListener, KeyListener {  // handles player inputs

    private Square previouslySelected;
    private Sides currentTurn;
    private Board board;
    private List<Square> legalMovesOfSelectedPiece;
    private List<Square> targetsOfSelectedArcher;
    private ConnectionHandler connectionHandler = null;

    private Key currentKey;

    private final List<Piece> bluePiecesCaptured;
    private final List<Piece> redPiecesCaptured;

    public enum Key {
        SHIFT, ALT, CONTROL, NONE
    }

    private Clock bClock;
    private Clock rClock;
    private int increment;

    // Settings
    private final Settings settings;

    // For 3 check mode
    private int blueChecks;
    private int redChecks;

    // All FENS so far
    List<String> allFENS;

    public PlayerController(Board board, Settings settings) {
        previouslySelected = null;
        currentTurn = Sides.BLUE;
        this.board = board;
        board.addMouseListener(this); // OOP black magic
        this.currentKey = Key.NONE;
        board.addKeyListener(this);

        // Initialize settings
        this.settings = settings;

        this.allFENS = new ArrayList<>();
        allFENS.add(board.getFEN());

        blueChecks = 0;
        redChecks = 0;

        bluePiecesCaptured = new ArrayList<>();
        redPiecesCaptured = new ArrayList<>();
    }
    public void setConnectionHandler(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    public void swapTurns() {
        if (currentTurn == Sides.BLUE) currentTurn = Sides.RED;
        else currentTurn = Sides.BLUE;
    }
    public Sides getCurrentTurn() {
        return currentTurn;
    }

    public void selectSquare(Square selected) {  // conditions: square must contain a piece
        selected.setState(Square.ActionStates.PLAYER_SELECTED);  // highlight square selected
        this.previouslySelected = selected;

        legalMovesOfSelectedPiece = selected.getPiece().getLegalMoves(board);

        // Only do this if highlights are on
        if (settings.getHighlightsOn()) {
            for (Square move : legalMovesOfSelectedPiece) {  // highlight all the legalMoves of selected piece
                move.setState(Square.ActionStates.LEGAL_MOVE);
            }
        }

        // Only do this if highlights are on
        if (selected.getPiece() instanceof Archer && settings.getHighlightsOn()) { // if piece is archer
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
    public void unhighlightBoard() {
        for (Square s : board.getBoard()) s.setState(Square.ActionStates.NONE);
    }

    public void addToCaptured(Piece piece) {
        switch (piece.getSide()) {
            case BLUE -> bluePiecesCaptured.add(piece);
            case RED -> redPiecesCaptured.add(piece);
        }
    }
    public List<Piece> getBluePiecesCaptured() {
        return bluePiecesCaptured;
    }
    public List<Piece> getRedPiecesCaptured() {
        return redPiecesCaptured;
    }

    public void attemptMove(Square target) {
        if (previouslySelected != null && previouslySelected.hasPiece()) {  // if we currently have a piece selected to move
            Piece pieceToMove = previouslySelected.getPiece();

            if (isCorrectPlayerMoving()) {  // if the correct player is moving

                if (legalMovesOfSelectedPiece.contains(target) && pieceToMove.canCapture(target)) {  // if it is legal to move to the new location
                    move(previouslySelected, target);  // make the client side moves

                    sendMovesToOpponent(target, false);  // update the opponent (if connection handler exists)
                }

                // Archer fire
                if (pieceToMove instanceof Archer && pieceToMove.getTargets(board).contains(target)) {  // COMMENT YOUR CODE <-----------------------
                    //if (target.getPiece() instanceof Bomber) target.getPiece().explode(board);
                    shoot(previouslySelected, target);  // make the client side moves
                    sendMovesToOpponent(target, true);  // update the opponent (if connection handler exists)
                }

            }
            deselectCurrent();  // clear board states on after this click
        } else {  //
            if (target.hasPiece()) {
                selectSquare(target);
            }
        }
    }
    public void move(Square from, Square to) {
        Piece pieceToMove = from.getPiece();
        Piece pieceTaken = to.getPiece();
        boolean pieceCaptured = to.hasPiece();

        pieceToMove.runOnMove(board, to);  // call runOnMove() (calls oldSquare.setPiece(newPiece), moving the piece)
        from.clearPiece(); // clear the old square of the moved piece
        if (pieceTaken != null)
            pieceTaken.runOnDeath(board, pieceToMove);  // call runOnDeath if the captured square had a piece

        // -------------------- GAME MODES ------------------------------

        // Atomic mode
        if (settings.getVariant().equals("Atomic Gigachess") && pieceCaptured) pieceToMove.nuke(board);

        // Three check mode
        if (settings.getVariant().equals("Three Check Gigachess")) {
            WinFrame winFrame = new WinFrame();
            switch (this.currentTurn) {
                case BLUE -> {
                    for (Square square : pieceToMove.getLegalMoves(board)) {
                        if (square.hasPiece() && square.getPiece() instanceof King) blueChecks++;
                        System.out.println(blueChecks);
                    }
                    if (blueChecks == 3) winFrame.makeWinFrame(Sides.BLUE);
                }
                case RED -> {
                    for (Square square : pieceToMove.getLegalMoves(board)) {
                        if (square.hasPiece() && square.getPiece() instanceof King) redChecks++;
                    }
                    if (redChecks == 3) winFrame.makeWinFrame(Sides.RED);
                }
            }
        }

        // ------------------ END OF GAME MODES -------------------------
        // What other game modes should we add?

        // Increment clocks when move occurs
        switch (currentTurn) {
            case BLUE -> bClock.increment(increment);
            case RED -> rClock.increment(increment);
        }
        swapTurns();
        allFENS.add(board.getFEN());
        System.out.println(allFENS.get(allFENS.size()-1));

        board.repaint();
    }
    public void shoot(Square from, Square to) {
        // archer shot does not move archer, so we don't call move() we just clear the target
        Piece pieceTaken = to.getPiece();  // get the piece before we take it
        to.clearPiece();  // clear the piece (it is now yeeted from the square it was on)
        if (pieceTaken != null)
            pieceTaken.runOnDeath(board, from.getPiece());  // if there was a piece on the square before we cleared it then we run its death function

        // Increment clocks when move occurs
        switch (currentTurn) {
            case BLUE -> bClock.increment(increment);
            case RED -> rClock.increment(increment);
        }
        swapTurns();
    }

    public void sendMovesToOpponent(Square target, boolean isArcherShot) {  // updates the opponent's board if running client/server
        if (connectionHandler != null) {
            String moveType = "m";
            if (isArcherShot)
                moveType = "a";
            String moves = previouslySelected.getRank() + " " + previouslySelected.getFile() + " " + target.getRank() + " " + target.getFile() + " " + moveType;  // Sends the file and rank of the selected piece and the target square
            connectionHandler.send(moves);
            board.pause();  // pause the board since turn has been made, the board will unpause when the opponent makes a move
        }
    }

    public boolean isCorrectPlayerMoving() {  // checks whether it is the correct player's turn
        return (currentTurn == Sides.BLUE && previouslySelected.getPiece().getSide() == Sides.BLUE) || (currentTurn == Sides.RED && previouslySelected.getPiece().getSide() == Sides.RED);
    }

    public void undoMove() {
        // Don't undo if there aren't any moves yet
        if (allFENS.size() == 1) return;

        allFENS.remove(allFENS.size()-1);

        // Clear past board
        for (Square square : board.getBoard()) if (square.hasPiece()) square.clearPiece();
        // Generate new board state
        board.generateBoardState(allFENS.get(allFENS.size()-1));
        board.repaint();
        swapTurns();
    }

    // Initialize clocks
    public void setClocks(int hours, int minutes, int seconds, int increment) {
        this.bClock = new Clock(hours, minutes, seconds);
        this.rClock = new Clock(hours, minutes, seconds);
        this.increment = increment;
    }
    public Clock getbClock() {
        return bClock;
    }
    public Clock getrClock() {
        return rClock;
    }


    // input-catching interface methods
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {

        if (board.isPaused()) return;  // if the game is paused, disregard mouse events


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
        board.repaint();
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