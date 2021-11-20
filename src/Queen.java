import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Queen extends Piece {

    private static final String IMAGES_QUEEN_BLUE = "images/bqueen.png";
    private static final String IMAGES_QUEEN_RED = "images/rqueen.png";

    public Queen(Sides side, int size, Square initSquare) {
        super(side, size, initSquare);
        switch (side) {
            case BLUE -> this.image = getImageByFile(IMAGES_QUEEN_BLUE);
            case RED -> this.image = getImageByFile(IMAGES_QUEEN_RED);
        }
    }

    public List<Square> getLegalMoves(Board board) {
        List<Square> legalMoves = new ArrayList<>();
        legalMoves.addAll(getBishopLegalMoves(board));
        legalMoves.addAll(getRookLegalMoves(board));
        return legalMoves;
    }

    @Override
    public List<Square> getTargets(Board board) {
        return null;
    }

}
