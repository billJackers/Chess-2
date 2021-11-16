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
        System.out.println("it is " + currentTurn.name() + "'s turn");
    }

    public void deselectCurrent() {
        this.previouslySelected.setState(Square.ActionStates.NONE);
        this.previouslySelected = null;  // cut the reference
    }

    public void attemptMove(Square selected) {

        // Checks if legal move
        boolean legal = false;
        if (previouslySelected.getPiece().getLegalMoves().contains(selected)) legal = true;

        // obnoxiously long if statement that checks if a set side is moving their respective piece
        if (legal) {
            if ((currentTurn == PlayerTurn.PLAYER_BLUE && previouslySelected.getPiece().side == Piece.Sides.BLUE) || (currentTurn == PlayerTurn.PLAYER_RED && previouslySelected.getPiece().side == Piece.Sides.RED)) {
                // Square sq = previouslySelected; // DEBUG STUFF
                // System.out.println(sq.getPiece() + " old pos: " + sq.getRank() + " " + sq.getFile() + " new pos: " + selected.getRank() + " " + selected.getFile());
                selected.setPiece(previouslySelected.getPiece());
                previouslySelected.clearPiece();
                deselectCurrent();
                swapTurns();
            }
        } else {
            System.out.println("Illegal move!");
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
          Square squareSelected = board.getSquareClicked(e.getX(), e.getY());
          if (previouslySelected != null && previouslySelected.hasPiece()) {  // if the player has previously selected a piece to move
              if (squareSelected == previouslySelected) {  // if the newly selected Square is the same as the previous one
                  deselectCurrent();
                  return;
              }
              attemptMove(squareSelected);
          }
          else if (squareSelected.getPiece() != null) {  // if we are selecting a new piece to move
              if (squareSelected.getPiece() instanceof Knight) {  // temporary proof of concept legalMoves code
                  for (Square sq : squareSelected.getPiece().getLegalMoves()) {
                      sq.setState(Square.ActionStates.LEGAL_MOVE);
                  }
              }
              squareSelected.setState(Square.ActionStates.PLAYER_SELECTED);
              previouslySelected = squareSelected;
          }

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
