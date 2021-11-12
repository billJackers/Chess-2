import java.util.List;
import java.util.LinkedList;

public class Pawn extends Piece {

    private boolean wasMoved;
    public Pawn(boolean isWhite, Square startSquare, String file) {
        super(isWhite, startSquare, file);
    }

    @Override
    public boolean move(Square moveTo) {
        boolean b = super.move(moveTo);
        wasMoved = true;
        return b;
    }

    @Override
    public List<Square> getLegalMoves(Board b) {
        LinkedList<Square> legalMoves = new LinkedList<>();
        Square[][] board = b.getSquareArray();
        int x = this.getPosition().getXCoordinate();
        int y = this.getPosition().getYCoordinate();
        boolean isWhite = this.white();

        if (!isWhite) {
            if (!wasMoved) {
                if (!board[y+2][x].isOccupied()) {
                    legalMoves.add(board[y+2][x]);
                }
            }

            if (y+1 < 10) {
                if(!board[y+1][x].isOccupied()) {
                    legalMoves.add(board[y+1][x]);
                }
            }

            if (x+1 < 10 && y+1 < 10) {
                if (board[y+1][x+1].isOccupied()) {
                    legalMoves.add(board[y+1][x+1]);
                }
            }

            if (x-1 >= 0 && y+1 < 10) {
                if (board[y+1][x-1].isOccupied()) {
                    legalMoves.add(board[y+1][x-1]);
                }
            }

        }

        if (isWhite) {
            if (!wasMoved) {
                if (!board[y-2][x].isOccupied()) {
                    legalMoves.add(board[y-2][x]);
                }
            }

            if (y-1 >= 0) {
                if (!board[y-1][x].isOccupied()) {
                    legalMoves.add(board[y-1][x]);
                }
            }

            if (x+1 < 10 && y-1 >= 0) {
                if (board[y-1][x+1].isOccupied()) {
                    legalMoves.add(board[y-1][x+1]);
                }
            }

            if (x-1 >= 0 && y-1 >= 0) {
                if (board[y-1][x-1].isOccupied()) {
                    legalMoves.add(board[y-1][x-1]);
                }
            }
        }

        return legalMoves;
    }

}
