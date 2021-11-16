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

        int rankSize = board.getRankSize();
        int fileSize = board.getFileSize();

        Square[] b = board.getBoard();

        int pos = (fileSize*rank) + file;

        int temp = 1;

        // up and to the left
        if (rank != 0 && file != 0) {
            while (pos-(temp*fileSize)-temp % fileSize >= 0 && pos-(temp*fileSize)-temp >= 0 ) {
                if (!b[pos-(temp*fileSize)-temp].hasPiece()) {
                    legalMoves.add(b[pos-(temp*fileSize)-temp]);
                }
                if (b[pos-(temp*fileSize)-temp].hasPiece()) {
                    if (this.canCapture(b[pos-(temp*fileSize)-temp])) {
                        legalMoves.add(b[pos-(temp*fileSize)-temp]);
                    }
                    break;
                }
                temp++;
            }
        }

        // up and to the right
        if (rank != 0 && file < fileSize) {
            while (pos-(temp*fileSize)+temp+1 % fileSize != 0 && pos-(temp*fileSize)+temp >= 0 ) {
                if (!b[pos-(temp*fileSize)+temp].hasPiece()) {
                    legalMoves.add(b[pos-(temp*fileSize)+temp]);
                }
                if (b[pos-(temp*fileSize)+temp].hasPiece()) {
                    if (this.canCapture(b[pos-(temp*fileSize)+temp])) {
                        legalMoves.add(b[pos-(temp*fileSize)+temp]);
                    }
                    break;
                }
                temp++;
            }
        }

        return legalMoves;
    }
}