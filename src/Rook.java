import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {

    private static final String IMAGES_ROOK_BLUE = "images/brook.png";
    private static final String IMAGES_ROOK_RED = "images/rrook.png";

    public Rook(Sides side, int size, Square initSquare) {
        super(side, size, initSquare);
        switch (side) {
            case BLUE -> this.image = getImageByFile(IMAGES_ROOK_BLUE);
            case RED -> this.image = getImageByFile(IMAGES_ROOK_RED);
        }
    }

    public List<Square> getLegalMoves() {
        return getRookLegalMoves();
    }

    @Override
    public List<Square> getTargets() {
        return null;
    }

}
