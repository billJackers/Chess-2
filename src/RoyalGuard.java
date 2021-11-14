import java.util.LinkedList;
import java.util.List;

public class RoyalGuard extends Piece {

    private static final String IMAGES_ROYAL_GUARD_BLUE = "images/wrook.png";
    private static final String IMAGES_ROYAL_GUARD_RED = "images/brook.png";

    public RoyalGuard(Sides side, int size) {
        super(side, size);
        switch (side) {
            case BLUE -> this.image = getImageByFile(IMAGES_ROYAL_GUARD_BLUE);
            case RED -> this.image = getImageByFile(IMAGES_ROYAL_GUARD_RED);
        }
    }

    public List<Square> getLegalMoves(Board b) {
        return null;
    }

}