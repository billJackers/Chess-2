import java.util.List;

public class Bishop extends Piece {

    private static final String IMAGES_BISHOP_BLUE = "images/pieceImages/bbishop.png";
    private static final String IMAGES_BISHOP_RED = "images/pieceImages/rbishop.png";

    public Bishop(Sides side, int size, Square initSquare, Settings settings) {
        super(side, size, initSquare, settings);
        switch (side) {
            case BLUE -> this.image = getImageByFile(IMAGES_BISHOP_BLUE);
            case RED -> this.image = getImageByFile(IMAGES_BISHOP_RED);
        }
        this.materialValue = 3;
    }

    public String getName() { return "Bishop"; }

    @Override
    public String getFENValue() {
        return "b";
    }

    @Override
    public List<Square> getLegalMoves(Board board) {
        return getBishopLegalMoves(board);
    }

    @Override
    public List<Square> getTargets(Board board) {
        return null;
    }
}