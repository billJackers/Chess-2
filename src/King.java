import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class King extends Piece {

    public King(boolean isWhite, Square startSquare, String file) {
        super(isWhite, startSquare, file);
    }

    @Override
    public List<Square> getLegalMoves(Board b) {

        LinkedList<Square> legalMoves = new LinkedList<Square>();
        Square[][] board = b.getSquareArray();

        int x = this.getPosition().getXCoordinate();
        int y = this.getPosition().getYCoordinate();

        for (int i = 1; i > -2; i--) {
            for (int j = 1; j > -2; j--) {
                try{
                    if (!board[y+j][x+i].isOccupied() || board[y+j][x+i].getOccupyingPiece().white() != this.white()) {
                        legalMoves.add(board[y+j][x+i]);
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    continue;
                }
            }
        }

        return legalMoves;

    }

}
