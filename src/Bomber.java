import java.util.LinkedList;
import java.util.List;

public class Bomber extends Piece {

    private static final String IMAGES_BOMBER_BLUE = "images/wbishop.png";
    private static final String IMAGES_BOMBER_RED = "images/bbishop.png";

    public Bomber(Sides side, int size) {
        super(side, size);
        switch (side) {
            case BLUE -> this.image = getImageByFile(IMAGES_BOMBER_BLUE);
            case RED -> this.image = getImageByFile(IMAGES_BOMBER_RED);
        }
    }

    public List<Square> getLegalMoves(Board b) {
        return null;
    }

}
