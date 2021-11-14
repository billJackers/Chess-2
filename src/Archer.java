import java.util.LinkedList;
import java.util.List;

public class Archer extends Piece {

    private static final String IMAGES_ARCHER_BLUE = "images/wpawn.png";
    private static final String IMAGES_ARCHER_RED = "images/bpawn.png";

    public Archer(Sides side, int size) {
        super(side, size);
        switch (side) {
            case BLUE -> this.image = getImageByFile(IMAGES_ARCHER_BLUE);
            case RED -> this.image = getImageByFile(IMAGES_ARCHER_RED);
        }
    }

    public List<Square> getLegalMoves(Board b) {
        return null;
    }

}