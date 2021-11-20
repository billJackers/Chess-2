import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RoyalGuard extends Piece {

    private static final String IMAGES_ROYAL_GUARD_BLUE = "images/broyalguard.png";
    private static final String IMAGES_ROYAL_GUARD_RED = "images/rroyalguard.png";

    public RoyalGuard(Sides side, int size, Square initSquare) {
        super(side, size, initSquare);
        switch (side) {
            case BLUE -> this.image = getImageByFile(IMAGES_ROYAL_GUARD_BLUE);
            case RED -> this.image = getImageByFile(IMAGES_ROYAL_GUARD_RED);
        }
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