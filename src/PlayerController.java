import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class PlayerController implements MouseListener {  // handles player inputs

    private enum PlayerTurn {
        PLAYER_BLUE,
        PLAYER_RED
    }
    private Square previouslySelected;
    private PlayerTurn currentTurn;
    private Board board;
    private List<Square> legalMovesOfSelectedPiece;

    private CheckmateDetector cmd;

    public PlayerController(Board board) {
        previouslySelected = null;
        currentTurn = PlayerTurn.PLAYER_BLUE;
        this.board = board;
        board.addMouseListener(this); // OOP black magic
    }


    public void swapTurns() {
        if (currentTurn == PlayerTurn.PLAYER_BLUE) currentTurn = PlayerTurn.PLAYER_RED;
        else currentTurn = PlayerTurn.PLAYER_BLUE;
        System.out.println("it is " + currentTurn.name() + "'s turn");
    }


    public void selectSquare(Square selected) {  // conditions: square must contain a piece
        selected.setState(Square.ActionStates.PLAYER_SELECTED);  // highlight square selected
        this.previouslySelected = selected;
        legalMovesOfSelectedPiece = selected.getPiece().getLegalMoves();
        for (Square move : legalMovesOfSelectedPiece) {
            move.setState(Square.ActionStates.LEGAL_MOVE);
        }
    }
    public void deselectCurrent() {
        this.previouslySelected.setState(Square.ActionStates.NONE);  // unhighlight square highlighted
        for (Square move : legalMovesOfSelectedPiece) {  // remove all legalMove highlights
            move.setState(Square.ActionStates.NONE);
        }
        this.previouslySelected = null;  // cut the references
        this.legalMovesOfSelectedPiece = null;
    }

    public void move(Square from, Square to) {
        Piece pieceToMove = from.getPiece();
        from.clearPiece();

        // Bomber explosion
        if (to.getPiece() instanceof Bomber && !(pieceToMove instanceof Assassin)) {
            explode(to);
        } else to.setPiece(pieceToMove);

        if (to.getPiece() instanceof Pawn) {
            ((Pawn) to.getPiece()).setToMoved();
        }

        // Pawn promotions
        if (to.getPiece() instanceof Pawn) {
            switch (to.getPiece().side) {
                case BLUE -> {
                    if (to.getFile() == 9) to.setPiece(new Queen(Piece.Sides.BLUE, 50, to));
                }
                case RED -> {
                    if (to.getFile() == 0) to.setPiece(new Queen(Piece.Sides.RED, 50, to));
                }
            }
        }
        cmd.updateLegalMoves();
    }

    public void attemptMove(Square selected) {
        if (previouslySelected != null && previouslySelected.hasPiece()) {  // if we currently have a piece selected to move
            Piece pieceToMove = previouslySelected.getPiece();

            if (isCorrectPlayerMoving()) {  // if the correct player is moving
                if (legalMovesOfSelectedPiece.contains(selected) && pieceToMove.canCapture(selected)) {  // if it is legal to move to the new location
                    move(previouslySelected, selected);
                    swapTurns();  // swap the turns on a successful move
                }
                if (pieceToMove instanceof Archer && pieceToMove.getTargets().contains(selected)) {
                    if (selected.getPiece() instanceof Bomber) explode(selected);
                    else selected.clearPiece();
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
        return (currentTurn == PlayerTurn.PLAYER_BLUE && previouslySelected.getPiece().getSide() == Piece.Sides.BLUE) || (currentTurn == PlayerTurn.PLAYER_RED && previouslySelected.getPiece().getSide() == Piece.Sides.RED);
    }

    public void explode(Square s) {
        List<Square> targets = s.getPiece().getTargets();
        for (Square target : targets) target.clearPiece();
        s.clearPiece();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        Square squareSelected = board.getSquareClicked(e.getX(), e.getY());
        attemptMove(squareSelected);
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
}
