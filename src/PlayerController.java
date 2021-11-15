import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PlayerController implements MouseListener {  // handles player inputs

    private enum PlayerTurn {
        PLAYER_BLUE,
        PLAYER_RED
    }
    private Square previouslySelected;
    private PlayerTurn currentTurn;
    private final Board board;

    public PlayerController(Board board) {
        previouslySelected = null;
        currentTurn = PlayerTurn.PLAYER_BLUE;
        this.board = board;
        board.addMouseListener(this); // OOP black magic
    }

    public void swapTurns() {
        if (currentTurn == PlayerTurn.PLAYER_BLUE) currentTurn = PlayerTurn.PLAYER_RED;
        else currentTurn = PlayerTurn.PLAYER_BLUE;
    }

    public void attemptMove(Square selected) {
        // obnoxiously long if statement that checks if the correct player is moving the correct piece
        if (previouslySelected != null && previouslySelected.getPiece() != null && ((currentTurn == PlayerTurn.PLAYER_BLUE && previouslySelected.getPiece().side == Piece.Sides.BLUE) || (currentTurn == PlayerTurn.PLAYER_RED && previouslySelected.getPiece().side == Piece.Sides.RED))) {


            selected.setPiece(this.previouslySelected.getPiece());
            this.previouslySelected.setState(Square.ActionStates.NONE);
            this.previouslySelected.clearPiece();
            this.previouslySelected = null;  // cut the reference
            return;
        }
        selected.setState(Square.ActionStates.PLAYER_SELECTED);
        previouslySelected = selected;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
          Square squareSelected = board.getSquareClicked(e.getX(), e.getY());
          attemptMove(squareSelected);
//        if (this.playerSelected != null && this.playerSelected.getPiece() != null) {  // the player has selected a piece and a new Square to move it to
//            clicked.setPiece(this.playerSelected.getPiece());
//            this.playerSelected.setState(Square.ActionStates.NONE);
//            this.playerSelected.clearPiece();
//            this.playerSelected = null;  // cut the reference
//            return;
//        }
          // if (squareSelected.getPiece() == null) return;
//        this.playerSelected = clicked;
//        clicked.setState(Square.ActionStates.PLAYER_SELECTED);
          //System.out.println("Pressed: " + squareSelected.getPiece());
    }

    @Override
    public void mousePressed(MouseEvent e) {

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
