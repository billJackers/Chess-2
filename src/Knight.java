import java.util.LinkedList;
import java.util.List;

public class Knight extends Piece {

    private static final String IMAGES_KNIGHT_BLUE = "images/wknight.png";
    private static final String IMAGES_KNIGHT_RED = "images/bknight.png";

    public Knight(Sides side, int size, Square initSquare) {
        super(side, size, initSquare);
        switch (side) {
            case BLUE -> this.image = getImageByFile(IMAGES_KNIGHT_BLUE);
            case RED -> this.image = getImageByFile(IMAGES_KNIGHT_RED);
        }
    }

    public List<Square> getLegalMoves(Board b) {
        return null;
    }

}
