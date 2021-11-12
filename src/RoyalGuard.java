import java.util.LinkedList;
import java.util.List;

public class RoyalGuard extends Piece {

    public RoyalGuard(boolean white, Square startSquare, String file) {
        super(white, startSquare, file);
    }

    @Override
    public List<Square> getLegalMoves(Board b) {
        LinkedList<Square> legalMoves = new LinkedList<Square>();

        return legalMoves;
    }

}