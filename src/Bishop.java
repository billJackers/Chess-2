import java.util.List;

public class Bishop extends Piece {

    public Bishop(boolean white, Square initSq, String img_file) {
        super(white, initSq, img_file);
    }

    @Override
    public List<Square> getLegalMoves(Board b) {
        Square[][] board = b.getSquareArray();
        int x = this.getPosition().getXCoordinate();
        int y = this.getPosition().getYCoordinate();

        return getDiagonalOccupations(board, x, y);
    }
}