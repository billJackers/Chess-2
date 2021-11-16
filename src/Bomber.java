import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Bomber extends Piece {

    private static final String IMAGES_BOMBER_BLUE = "images/bbomber.png";
    private static final String IMAGES_BOMBER_RED = "images/rbomber.png";

    private boolean wasMoved;

    public Bomber(Sides side, int size, Square initSquare) {
        super(side, size, initSquare);
        switch (side) {
            case BLUE -> this.image = getImageByFile(IMAGES_BOMBER_BLUE);
            case RED -> this.image = getImageByFile(IMAGES_BOMBER_RED);
        }
    }

    public List<Square> getLegalMoves() {

        ArrayList<Square> legalMoves = new ArrayList<>();

        int rank = this.parentSquare.getRank();
        int file = this.parentSquare.getFile();

        int rankSize = board.getRankSize();
        int fileSize = board.getFileSize();

        Square[] b = board.getBoard();


        int pos = (fileSize*rank) + file;
        /*
        switch (this.side) {

            case RED:
                if (!wasMoved) {
                    if (!b[pos+(fileSize*2)].isOccupied()) {
                        legalMoves.add(b[pos+(fileSize*2)]);
                    }
                }
                if (rank+1 < rankSize) {
                    if (!b[pos+fileSize].isOccupied()) {
                        legalMoves.add(b[pos+fileSize]);
                    }
                }
                if (rank+1 < rankSize && file+1 < fileSize) {
                    if (b[pos+fileSize+1].isOccupied()) {
                        if (this.canCapture(b[pos+fileSize+1])) legalMoves.add(b[pos+fileSize+1]);
                    }
                }
                if (rank+1 < rankSize && file-1 > 0) {
                    if (b[pos+fileSize-1].isOccupied()) {
                        if (this.canCapture(b[pos+fileSize-1])) legalMoves.add(b[pos+fileSize-1]);
                    }
                }
                break;
            case BLUE:
                if (!wasMoved) {
                    if (!b[pos-(fileSize*2)].isOccupied()) {
                        legalMoves.add(b[pos-(fileSize*2)]);
                    }
                }
                if (rank-1 >= 0) {
                    if (!b[pos-fileSize].isOccupied()) {
                        legalMoves.add(b[pos-fileSize]);
                    }
                }
                if (rank-1 >= 0 && file+1 < fileSize) {
                    if (b[pos-fileSize+1].isOccupied()) {
                        if (this.canCapture(b[pos-fileSize+1])) legalMoves.add(b[pos-fileSize+1]);
                    }
                }
                if (rank-1 >= 0 && file-1 >= 0) {
                    if (b[pos-fileSize-1].isOccupied()) {
                        if (this.canCapture(b[pos-fileSize-1])) legalMoves.add(b[pos-fileSize-1]);
                    }
                }
                break;
        }
        */
        return legalMoves;
    }

}
