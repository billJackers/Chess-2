import java.util.LinkedList;
import java.util.List;

public class Queen extends Piece {

    private static final String IMAGES_QUEEN_BLUE = "images/wqueen.png";
    private static final String IMAGES_QUEEN_RED = "images/bqueen.png";

    public Queen(Sides side, int size) {
        super(side, size);
        switch (side) {
            case BLUE -> this.image = getImageByFile(IMAGES_QUEEN_BLUE);
            case RED -> this.image = getImageByFile(IMAGES_QUEEN_RED);
        }
    }

    public List<Square> getLegalMoves(Board b) {
        return null;
    }

}
