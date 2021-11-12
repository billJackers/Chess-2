import java.util.LinkedList;
import java.util.List;

public class Rook extends Piece {
    
    public Rook(boolean white, Square startSquare, String file) {
        super(white, startSquare, file);
    }

    @Override
    public List<Square> getLegalMoves(Board b) {
        LinkedList<Square> legalMoves = new LinkedList<Square>();
        Square[][] board  = b.getSquareArray();

        int x = this.getPosition().getXCoordinate();
        int y = this.getPosition().getYCoordinate();

        int[] occups = getLinearOccupations(board, x, y);

        for (int i = occups[0]; i <= occups[1]; i++) {
            if (i != y) legalMoves.add(board[i][x]);
        }

        for (int i = occups[2]; i <= occups[3]; i++) {
            if (i != x) legalMoves.add(board[y][i]);
        }

        return legalMoves;

    }
    
}
