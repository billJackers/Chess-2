import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Archer extends Piece {

    private static final String IMAGES_ARCHER_BLUE = "images/barcher.png";
    private static final String IMAGES_ARCHER_RED = "images/rarcher.png";

    public Archer(Sides side, int size, Square initSquare) {
        super(side, size, initSquare);
        switch (side) {
            case BLUE -> this.image = getImageByFile(IMAGES_ARCHER_BLUE);
            case RED -> this.image = getImageByFile(IMAGES_ARCHER_RED);
        }
    }

    public List<Square> getLegalMoves() {
        return super.getKingLegalMoves();
    }

    public List<Square> getTargets() {
        List<Square> targets = new ArrayList<>();

        int rank = this.parentSquare.getRank();
        int file = this.parentSquare.getFile();

        Square[] b = board.getBoard();

        int pos = (file*10) + rank;

        // Northwest
        if (pos % 10 > 1 && pos >= 20 && b[pos-22].hasPiece() && this.canCapture(b[pos-22])) targets.add(b[pos-22]);
        // Northeast
        if (pos % 10 < 8 && pos >= 20 && b[pos-18].hasPiece() && this.canCapture(b[pos-18])) targets.add(b[pos-18]);
        // Southwest
        if (pos % 10 > 1 && pos < 80 && b[pos+18].hasPiece() && this.canCapture(b[pos+18])) targets.add(b[pos+18]);
        // Southeast
        if (pos % 10 < 8 && pos < 80 && b[pos+22].hasPiece() && this.canCapture(b[pos+22])) targets.add(b[pos+22]);

        return targets;
    }

}