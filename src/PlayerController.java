import javax.swing.*;
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

    private List<Piece> bluePiecesCaptured;
    private List<Piece> redPiecesCaptured;

    public enum Key {
        SHIFT, ALT, CONTROL, NONE
    }

    private Clock bClock;
    private Clock rClock;
    private int increment;

    public PlayerController(Board board) {
        previouslySelected = null;
        currentTurn = Sides.BLUE;
        this.board = board;
        board.addMouseListener(this); // OOP black magic
        this.currentKey = Key.NONE;
        board.addKeyListener(this);

        bluePiecesCaptured = new ArrayList<>();
        redPiecesCaptured = new ArrayList<>();
    }

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

    public void addToCaptured(Piece piece) {
        switch (piece.getSide()) {
            case BLUE -> bluePiecesCaptured.add(piece);
            case RED -> redPiecesCaptured.add(piece);
        }
    }

    public void move(Square from, Square to) {
        Piece pieceToMove = from.getPiece();
        Piece pieceTaken = to.getPiece();

        pieceToMove.runOnMove(board, to);  // call runOnMove() (calls oldSquare.setPiece(newPiece), moving the piece)
        from.clearPiece(); // clear the old square of the moved piece
        if (pieceTaken != null)
            pieceTaken.runOnDeath(board, pieceToMove);  // call runOnDeath if the captured square had a piece

        // Increment clocks when move occurs
        switch (pieceToMove.getSide()) {
            case BLUE -> bClock.increment(increment);
            case RED -> rClock.increment(increment);
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

                    selected.clearPiece();
                    if (selected.hasPiece())
                        selected.getPiece().runOnDeath(board, pieceToMove);

                    switch (pieceToMove.getSide()) {
                        case BLUE -> bClock.increment(increment);
                        case RED -> rClock.increment(increment);
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
    }

    public List<Piece> getBluePiecesCaptured() {
        return bluePiecesCaptured;
    }

    public List<Piece> getRedPiecesCaptured() {
        return redPiecesCaptured;
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