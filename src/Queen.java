import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece {

    private static final String IMAGES_QUEEN_BLUE = "images/pieceImages/bqueen.png";
    private static final String IMAGES_QUEEN_RED = "images/pieceImages/rqueen.png";

    public Queen(Sides side, int size, Square initSquare, Settings settings) {
        super(side, size, initSquare, settings);
        switch (side) {
            case BLUE -> this.image = getImageByFile(IMAGES_QUEEN_BLUE);
            case RED -> this.image = getImageByFile(IMAGES_QUEEN_RED);
        }
        this.materialValue = 9;
    }

    @Override
    public String getFENValue() {
        return "q";
    }

    public String getName() { return "Queen"; }

    public List<Square> getLegalMoves(Board board) {
        List<Square> legalMoves = new ArrayList<>();
        legalMoves.addAll(getBishopLegalMoves(board));
        legalMoves.addAll(getRookLegalMoves(board));
        return legalMoves;
    }

    @Override
    public List<Square> getTargets(Board board) {
        return null;
    }

}
