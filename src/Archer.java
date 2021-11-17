import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Archer extends Piece {

    private static final String IMAGES_ARCHER_BLUE = "images/barcher.png";
    private static final String IMAGES_ARCHER_RED = "images/rarcher.png";

    public Archer(Sides side, int size, Square initSquare) {
        super(side, size, initSquare);
        switch (side) {
            case BLUE -> this.image = getImageByFile(IMAGES_ARCHER_BLUE);
            case RED -> this.image = getImageByFile(IMAGES_ARCHER_RED);
        }
    }

    public List<Square> getLegalMoves() {
        return super.getKingLegalMoves();
    }

}