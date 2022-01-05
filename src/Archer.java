import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Archer extends Piece {

    private static final String IMAGES_ARCHER_BLUE = "images/barcher.png";
    private static final String IMAGES_ARCHER_RED = "images/rarcher.png";

    public Archer(Sides side, int size, Square initSquare, Settings settings) {
        super(side, size, initSquare, settings);
        switch (side) {
            case BLUE -> this.image = getImageByFile(IMAGES_ARCHER_BLUE);
            case RED -> this.image = getImageByFile(IMAGES_ARCHER_RED);
        }
        this.materialValue = 5;
    }

    public List<Square> getLegalMoves(Board board) {
        return super.getKingLegalMoves(board);
    }

    public String getName() { return "Archer"; }

    @Override
    public String getFENValue() {
        return "c";
    }

    @Override
    public List<Square> getTargets(Board board) {
        List<Square> targets = new ArrayList<>();

        int rank = this.parentSquare.getRank();
        int file = this.parentSquare.getFile();

        Square[] b = board.getBoard();

        int pos = (file*10) + rank;

        // Northwest
        if (pos % 10 > 1 && pos >= 20 && b[pos-22].hasPiece() && (!b[pos-11].hasPiece()) && this.canCapture(b[pos-22])) targets.add(b[pos-22]);
        // Northeast
        if (pos % 10 < 8 && pos >= 20 && b[pos-18].hasPiece() && (!b[pos-9].hasPiece()) && this.canCapture(b[pos-18])) targets.add(b[pos-18]);
        // Southwest
        if (pos % 10 > 1 && pos < 80 && b[pos+18].hasPiece() && (!b[pos+9].hasPiece()) && this.canCapture(b[pos+18])) targets.add(b[pos+18]);
        // Southeast
        if (pos % 10 < 8 && pos < 80 && b[pos+22].hasPiece() && (!b[pos+11].hasPiece()) && this.canCapture(b[pos+22])) targets.add(b[pos+22]);

        return targets;
    }

}