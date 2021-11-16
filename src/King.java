import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class King extends Piece {

    private static final String IMAGES_KING_BLUE = "images/bking.png";
    private static final String IMAGES_KING_RED = "images/rking.png";

    public King(Sides side, int size, Square initSquare) {
        super(side, size, initSquare);
        switch (side) {
            case BLUE -> this.image = getImageByFile(IMAGES_KING_BLUE);
            case RED -> this.image = getImageByFile(IMAGES_KING_RED);
        }
    }

    public List<Square> getLegalMoves() {
        ArrayList<Square> legalMoves = new ArrayList<>();

        int rank = this.parentSquare.getRank();
        int file = this.parentSquare.getFile();
        int fileSize = board.getFileSize();
        int indexOfPiece = (file*fileSize) + rank;  // the index of this piece in the board[]

        Square[] b = board.getBoard();

        int[] allMoves = {1, 9, 10, 11};  // [+] OR [-] these values RELATIVE TO OUR CURRENT INDEX gives us possible moves for the King
        for (int relativeMove : allMoves) {
            // if the relative moves are within the bounds of the board and the position is capturable, then add to legalMoves
            if (indexOfPiece + relativeMove < 100 && this.canCapture(b[indexOfPiece + relativeMove])) legalMoves.add(b[indexOfPiece + relativeMove]);
            if (indexOfPiece - relativeMove >= 0 && this.canCapture(b[indexOfPiece - relativeMove])) legalMoves.add(b[indexOfPiece - relativeMove]);
        }
        return legalMoves;
    }


}
