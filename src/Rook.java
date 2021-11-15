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

        int rank = this.curSquare.getRank();
        int file = this.curSquare.getFile();

        int rankSize = board.getRankSize();
        int fileSize = board.getFileSize();

        Square[] b = board.getBoard();

        // Horizontal
        int tempFile = file;

        if (file != fileSize) {
            while (file <= fileSize) {

            }
        }

        return legalMoves;
    }
    
}
