import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class King extends Piece {

    private static final String IMAGES_KING_BLUE = "images/wking.png";
    private static final String IMAGES_KING_RED = "images/bking.png";

    public King(Sides side, int size, Square initSquare) {
        super(side, size, initSquare);
        switch (side) {
            case BLUE -> this.image = getImageByFile(IMAGES_KING_BLUE);
            case RED -> this.image = getImageByFile(IMAGES_KING_RED);
        }
    }

    public List<Square> getLegalMoves(Board b) {
        return null;
    }

}
