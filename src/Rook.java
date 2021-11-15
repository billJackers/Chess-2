import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {

    private static final String IMAGES_ROOK_BLUE = "images/wrook.png";
    private static final String IMAGES_ROOK_RED = "images/brook.png";

    public Rook(Sides side, int size, Square initSquare) {
        super(side, size, initSquare);
        switch (side) {
            case BLUE -> this.image = getImageByFile(IMAGES_ROOK_BLUE);
            case RED -> this.image = getImageByFile(IMAGES_ROOK_RED);
        }
    }

    public List<Square> getLegalMoves() {
        List<Square> legalMoves = new ArrayList<>();

        int rank = this.parentSquare.getRank();
        int file = this.parentSquare.getFile();

        int rankSize = board.getRankSize();
        int fileSize = board.getFileSize();

        Square[] b = board.getBoard();

        int pos = (fileSize*rank) + file;

        // Horizontal
        int temp = 1;
        if (file != fileSize-1) {
            while (temp+pos+1 % 10 != 0) {
                if (!b[pos+temp].isOccupied()) {
                    legalMoves.add(b[pos+temp]);
                }
                if (b[pos+temp].isOccupied() && canCapture(b[pos+temp])) {
                    legalMoves.add(b[pos+temp]);
                    break;
                }
                if (b[pos+temp].isOccupied() && !canCapture(b[pos+temp])) {
                    break;
                }
                temp++;
            }
        }
        temp = 1;
        if (file != 0) {
            while (pos-temp % 10 != 0) {
                if (!b[pos-temp].isOccupied()) {
                    legalMoves.add(b[pos-temp]);
                }
                if (b[pos-temp].isOccupied() && canCapture(b[pos-temp])) {
                    legalMoves.add(b[pos-temp]);
                    break;
                }
                if (b[pos-temp].isOccupied() && !canCapture(b[pos-temp])) {
                    break;
                }
                temp++;
            }
        }

        // Vertical
        temp = 1;
        if (rank != rankSize-1) {
            while (pos+(temp*10) < b.length) {
                if (!b[pos+(temp*10)].isOccupied()) {
                    legalMoves.add(b[pos+(temp*10)]);
                }
                if (b[pos+(temp*10)].isOccupied() && canCapture(b[pos+(temp*10)])) {
                    legalMoves.add(b[pos+(temp*10)]);
                    break;
                }
                if (b[pos+(temp*10)].isOccupied() && !canCapture(b[pos+(temp*10)])) {
                    break;
                }
                temp++;
            }
        }

        temp = 1;
        if (rank != 0) {
            while (pos-(temp*10) >= 0) {
                if (!b[pos-(temp*10)].isOccupied()) {
                    legalMoves.add(b[pos-(temp*10)]);
                }
                if (b[pos-(temp*10)].isOccupied() && canCapture(b[pos-(temp*10)])) {
                    legalMoves.add(b[pos-(temp*10)]);
                    break;
                }
                if (b[pos-(temp*10)].isOccupied() && !canCapture(b[pos-(temp*10)])) {
                    break;
                }
                temp++;
            }
        }

        return legalMoves;
    }
    
}
