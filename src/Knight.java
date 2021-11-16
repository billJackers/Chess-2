import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Knight extends Piece {

    private static final String IMAGES_KNIGHT_BLUE = "images/wknight.png";
    private static final String IMAGES_KNIGHT_RED = "images/bknight.png";

    public Knight(Sides side, int size, Square initSquare) {
        super(side, size, initSquare);
        switch (side) {
            case BLUE -> this.image = getImageByFile(IMAGES_KNIGHT_BLUE);
            case RED -> this.image = getImageByFile(IMAGES_KNIGHT_RED);
        }
    }

    public List<Square> getLegalMoves() {

        ArrayList<Square> legalMoves = new ArrayList<>();

        int rank = this.parentSquare.getRank();
        int file = this.parentSquare.getFile();
        int fileSize = board.getFileSize();
        int pos = (file*fileSize) + rank;  // the index of this piece

        Square[] b = board.getBoard();

        int[] positions = {21, 19, 12, 8};  // +- these indexes give us knight's legalMoves
        for (int currentPos : positions) {
            if (pos + currentPos < 100 && this.canCapture(b[pos + currentPos])) legalMoves.add(b[pos + currentPos]);
            if (pos - currentPos >= 0 && this.canCapture(b[pos - currentPos])) legalMoves.add(b[pos - currentPos]);
        }

        return legalMoves;
    }

}
