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

        LinkedList<Square> legalMoves = new LinkedList<>();

        int rank = this.curSquare.getRank();
        int file = this.curSquare.getFile();

        int rankSize = board.getRankSize();
        int fileSize = board.getFileSize();

        Square[] b = board.getBoard();

        if (rank-2 >= 0 && file-1 >= 0 && this.canCapture(b[rank-(fileSize*2)-1])) {
            legalMoves.add(b[rank-(fileSize*2)-1]);
        }

        if (rank-2 >= 0 && file+1 <= fileSize && this.canCapture(b[rank-(fileSize*2)+1])) {
            legalMoves.add(b[rank-(fileSize*2)+1]);
        }

        if (rank+2 <= rankSize && file-1 >= 0 && this.canCapture(b[rank+(fileSize*2)-1])) {
            legalMoves.add(b[rank+(fileSize*2)-1]);
        }

        if (rank+2 <= rankSize && file+1 <= fileSize && this.canCapture(b[rank+(fileSize*2)+1])) {
            legalMoves.add(b[rank+(fileSize*2)+1]);
        }

        if (rank-1 >= 0 && file-2 >= 0 && this.canCapture(b[rank-fileSize-2])) {
            legalMoves.add(b[rank-fileSize-2]);
        }

        if (rank-1 >= 0 && file+2 <= fileSize && this.canCapture(b[rank-fileSize+2])) {
            legalMoves.add(b[rank-fileSize+2]);
        }

        if (rank+1 <= rankSize && file-2 >= 0 && this.canCapture(b[rank+fileSize-2])) {
            legalMoves.add(b[rank+fileSize-2]);
        }

        if (rank+1 <= rankSize && file+2 <= fileSize && this.canCapture(b[rank+fileSize+2])) {
            legalMoves.add(b[rank+fileSize+2]);
        }

        return legalMoves;
    }

}
