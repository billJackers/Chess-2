import java.util.List;

public class Bishop extends Piece {

    private static final String IMAGES_BISHOP_BLUE = "images/wbishop.png";
    private static final String IMAGES_BISHOP_RED = "images/bbishop.png";

    public Bishop(Sides side, int size) {
        super(side, size);
        switch (side) {
            case BLUE -> this.image = getImageByFile(IMAGES_BISHOP_BLUE);
            case RED -> this.image = getImageByFile(IMAGES_BISHOP_RED);
        }
    }

    public List<Square> getLegalMoves(Board b) {
        return null;
    }
}