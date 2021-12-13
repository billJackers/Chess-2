import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {

    private static final String IMAGES_ROOK_BLUE = "images/brook.png";
    private static final String IMAGES_ROOK_RED = "images/rrook.png";

    public Rook(Sides side, int size, Square initSquare, Settings settings) {
        super(side, size, initSquare, settings);
        switch (side) {
            case BLUE -> this.image = getImageByFile(IMAGES_ROOK_BLUE);
            case RED -> this.image = getImageByFile(IMAGES_ROOK_RED);
        }
    }

    public String getName() { return "Rook"; }

    @Override
    public String getFENValue() {
        return "r";
    }

    public List<Square> getLegalMoves(Board board) {
        return getRookLegalMoves(board);
    }

    @Override
    public List<Square> getTargets(Board board) {
        return null;
    }

}
