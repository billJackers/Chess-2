import java.util.LinkedList;
import java.util.List;

public class Knight extends Piece {

    public Knight(boolean white, Square startSquare, String file) {
        super(white, startSquare, file);
    }

    @Override
    public List<Square> getLegalMoves(Board b) {
        LinkedList<Square> legalMoves = new LinkedList<Square>();
        Square[][] board = b.getSquareArray();

        int x = this.getPosition().getXCoordinate();
        int y = this.getPosition().getYCoordinate();

        for (int i = 2; i > -3; i--) {
            for (int j = 2; j > -3; j--) {
                if (Math.abs(i) == 2 ^ Math.abs(j) == 2) {
                    if (i != 0 && j != 0) {
                        try {
                            legalMoves.add(board[y+j][x+i]);
                        } catch (ArrayIndexOutOfBoundsException e) {
                            continue;
                        }
                    }
                }
            }
        }

        return legalMoves;

    }

}
