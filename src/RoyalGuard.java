import java.util.ArrayList;
import java.util.List;

public class RoyalGuard extends Piece {

    private static final String IMAGES_ROYAL_GUARD_BLUE = "images/pieceImages/broyalguard.png";
    private static final String IMAGES_ROYAL_GUARD_RED = "images/pieceImages/rroyalguard.png";

    public RoyalGuard(Sides side, int size, Square initSquare, Settings settings) {
        super(side, size, initSquare, settings);
        switch (side) {
            case BLUE -> this.image = getImageByFile(IMAGES_ROYAL_GUARD_BLUE);
            case RED -> this.image = getImageByFile(IMAGES_ROYAL_GUARD_RED);
        }
        this.materialValue = 2;
    }

    public String getName() { return "Royal Guard"; }

    @Override
    public String getFENValue() {
        return "g";
    }

    public List<Square> getLegalMoves(Board board) {
        ArrayList<Square> legalMoves = new ArrayList<>(this.getKingLegalMoves(board));
        legalMoves.removeIf(Square::hasPiece);
        return legalMoves;
    }

    @Override
    public List<Square> getTargets(Board board) {
        return null;
    }

}