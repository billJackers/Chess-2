import java.util.List;
import java.util.ArrayList;

public class Pawn extends Piece {

    private static final String IMAGES_PAWN_BLUE = "images/wpawn.png";
    private static final String IMAGES_PAWN_RED = "images/bpawn.png";

    private boolean wasMoved;

    public Pawn(Sides side, int size, Square initSquare) {
        super(side, size, initSquare);
        wasMoved = false;
        switch (side) {
            case BLUE -> this.image = getImageByFile(IMAGES_PAWN_BLUE);
            case RED -> this.image = getImageByFile(IMAGES_PAWN_RED);
        }
    }

    @Override
    public List<Square> getLegalMoves() {
        ArrayList<Square> legalMoves = new ArrayList<>();

        int rank = this.parentSquare.getRank();
        int file = this.parentSquare.getFile();

        Square[] b = board.getBoard();

        int pos = (10*rank) + file;

        switch (this.side) {
            case BLUE -> {
                if (!wasMoved) {
                    if (!b[pos + (20)].hasPiece()) {
                        legalMoves.add(b[pos + (20)]);
                    }
                }
                if (rank+1 < 10) {
                    if (!b[pos + 10].hasPiece()) {
                        legalMoves.add(b[pos + 10]);
                    }
                }
                if (rank + 1 < 10 && file + 1 < 10) {
                    if (b[pos + 11].hasPiece()) {
                        if (this.canCapture(b[pos + 10 + 1])) legalMoves.add(b[pos + 10 + 1]);
                    }
                }
                if (rank + 1 < 10 && file - 1 > 0) {
                    if (b[pos + 10 - 1].hasPiece()) {
                        if (this.canCapture(b[pos + 10 - 1])) legalMoves.add(b[pos + 10 - 1]);
                    }
                }
            }
            case RED -> {
                if (!wasMoved) {
                    if (!b[pos - (20)].hasPiece()) {
                        legalMoves.add(b[pos - (20)]);
                    }
                }
                if (rank - 1 >= 0) {
                    if (!b[pos - 10].hasPiece()) {
                        legalMoves.add(b[pos - 10]);
                    }
                }
                if (rank - 1 >= 0 && file + 1 < 10) {
                    if (b[pos - 10 + 1].hasPiece()) {
                        if (this.canCapture(b[pos - 10 + 1])) legalMoves.add(b[pos - 10 + 1]);
                    }
                }
                if (rank - 1 >= 0 && file - 1 >= 0) {
                    if (b[pos - 10 - 1].hasPiece()) {
                        if (this.canCapture(b[pos - 10 - 1])) legalMoves.add(b[pos - 10 - 1]);
                    }
                }
            }
        }

        return legalMoves;

    }

}
