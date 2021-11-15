import java.util.List;

public class Bishop extends Piece {

    private static final String IMAGES_BISHOP_BLUE = "images/bbishop.png";
    private static final String IMAGES_BISHOP_RED = "images/rbishop.png";

    public Bishop(Sides side, int size, Square initSquare) {
        super(side, size, initSquare);
        switch (side) {
            case BLUE -> this.image = getImageByFile(IMAGES_BISHOP_BLUE);
            case RED -> this.image = getImageByFile(IMAGES_BISHOP_RED);
        }
    }

    public List<Square> getLegalMoves(Board b) {
        return null;
    }
}