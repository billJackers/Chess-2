import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.LinkedList;
import java.util.Objects;

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
        LinkedList<Square> legalMoves = new LinkedList<>();

        int rank = this.curSquare.getRank();
        int file = this.curSquare.getFile();

        int rankSize = board.getRankSize();
        int fileSize = board.getFileSize();

        Square[] b = board.getBoard();

        switch (this.side) {
            case RED:
                if (!wasMoved) {
                    if (!b[rank+(fileSize*2)].isOccupied()) {
                        legalMoves.add(b[rank+(fileSize*2)]);
                    }
                }
                if (rank+1 < rankSize) {
                    if (!b[rank+fileSize].isOccupied()) {
                        legalMoves.add(b[rank+fileSize]);
                    }
                }
                if (rank+1 < rankSize && file+1 < fileSize) {
                    if (b[rank+fileSize+1].isOccupied()) {
                        legalMoves.add(b[rank+fileSize+1]);
                    }
                }
                if (rank+1 < rankSize && file-1 > 0) {
                    if (b[rank+fileSize-1].isOccupied()) {
                        legalMoves.add(b[rank+fileSize-1]);
                    }
                }
                break;
            case BLUE:
                if (!wasMoved) {
                    if (!b[rank-(fileSize*2)].isOccupied()) {
                        legalMoves.add(b[rank-(fileSize*2)]);
                    }
                }
                if (rank-1 >= 0) {
                    if (!b[rank-fileSize].isOccupied()) {
                        legalMoves.add(b[rank-fileSize]);
                    }
                }
                if (rank-1 >= 0 && file+1 < fileSize) {
                    if (b[rank-fileSize+1].isOccupied()) {
                        legalMoves.add(b[rank-fileSize+1]);
                    }
                }
                if (rank-1 >= 0 && file-1 >= 0) {
                    if (b[rank-fileSize-1].isOccupied()) {
                        legalMoves.add(b[rank-fileSize-1]);
                    }
                }
                break;
        }

        return legalMoves;

    }

}
