import java.util.ArrayList;
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

    public List<Square> getLegalMoves() {
        List<Square> legalMoves = new ArrayList<>();

        int rank = this.parentSquare.getRank();
        int file = this.parentSquare.getFile();

        Square[] b = board.getBoard();

        int pos = (file*10) + rank;

        int temp = 1;

        // up and to the left
        if (pos >= 10 && pos % 10 != 0) {
            while ((pos-temp-(temp*10)) % 10 != 9 && pos-temp-(temp*10) >= 0) {
                if (!b[pos-temp-(temp*10)].hasPiece()) legalMoves.add(b[pos-temp-(temp*10)]);
                else if (this.canCapture(b[pos-temp-(temp*10)])) {
                    legalMoves.add(b[pos-temp-(temp*10)]);
                    break;
                } else break;

                temp++;
            }
        }

        // up and to the right
        temp = 1;
        if (pos >= 10 && pos % 10 != 9) {
            while ((pos+temp-(temp*10)) % 10 != 0 && pos+temp-(temp*10) >= 0) {
                if (!b[pos+temp-(temp*10)].hasPiece()) legalMoves.add(b[pos+temp-(temp*10)]);
                else if (this.canCapture(b[pos+temp-(temp*10)])) {
                    legalMoves.add(b[pos+temp-(temp*10)]);
                    break;
                } else break;

                temp++;
            }
        }

        // Down and to the left
        temp = 1;
        if (pos < 90 && pos % 10 != 0) {
            while ((pos-temp+(temp*10)) % 10 != 9 && pos-temp+(temp*10) < 100) {
                if (!b[pos-temp+(temp*10)].hasPiece()) legalMoves.add(b[pos-temp+(temp*10)]);
                else if (this.canCapture(b[pos-temp+(temp*10)])) {
                    legalMoves.add(b[pos-temp+(temp*10)]);
                    break;
                } else break;

                temp++;
            }
        }

        // Down and to the right
        temp = 1;
        if (pos < 90 && pos % 10 != 9) {
            while ((pos+temp+(temp*10)) % 10 != 0 && pos+temp+(temp*10) < 100) {
                if (!b[pos+temp+(temp*10)].hasPiece()) legalMoves.add(b[pos+temp+(temp*10)]);
                else if (this.canCapture(b[pos+temp+(temp*10)])) {
                    legalMoves.add(b[pos+temp+(temp*10)]);
                    break;
                } else break;

                temp++;
            }
        }

        return legalMoves;
    }
}