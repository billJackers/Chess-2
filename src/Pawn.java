import java.util.List;
import java.util.ArrayList;

public class Pawn extends Piece {

    private static final String IMAGES_PAWN_BLUE = "images/wpawn.png";
    private static final String IMAGES_PAWN_RED = "images/bpawn.png";

    private boolean wasMoved;

    public Pawn(Sides side, int size, Square initSquare) {
        super(side, size, initSquare);
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

        int rankSize = board.getRankSize();
        int fileSize = board.getFileSize();

        Square[] b = board.getBoard();

        int pos = (fileSize*rank) + file;

        switch (this.side) {
            case RED:
                if (!wasMoved) {
                    if (!b[pos+(fileSize*2)].hasPiece()) {
                        legalMoves.add(b[pos+(fileSize*2)]);
                    }
                }
                if (rank+1 < rankSize) {
                    if (!b[pos+fileSize].hasPiece()) {
                        legalMoves.add(b[pos+fileSize]);
                    }
                }
                if (rank+1 < rankSize && file+1 < fileSize) {
                    if (b[pos+fileSize+1].hasPiece()) {
                        if (this.canCapture(b[pos+fileSize+1])) legalMoves.add(b[pos+fileSize+1]);
                    }
                }
                if (rank+1 < rankSize && file-1 > 0) {
                    if (b[pos+fileSize-1].hasPiece()) {
                        if (this.canCapture(b[pos+fileSize-1])) legalMoves.add(b[pos+fileSize-1]);
                    }
                }
                break;
            case BLUE:
                if (!wasMoved) {
                    if (!b[pos-(fileSize*2)].hasPiece()) {
                        legalMoves.add(b[pos-(fileSize*2)]);
                    }
                }
                if (rank-1 >= 0) {
                    if (!b[pos-fileSize].hasPiece()) {
                        legalMoves.add(b[pos-fileSize]);
                    }
                }
                if (rank-1 >= 0 && file+1 < fileSize) {
                    if (b[pos-fileSize+1].hasPiece()) {
                        if (this.canCapture(b[pos-fileSize+1])) legalMoves.add(b[pos-fileSize+1]);
                    }
                }
                if (rank-1 >= 0 && file-1 >= 0) {
                    if (b[pos-fileSize-1].hasPiece()) {
                        if (this.canCapture(b[pos-fileSize-1])) legalMoves.add(b[pos-fileSize-1]);
                    }
                }
                break;
        }

        return legalMoves;

    }

}
