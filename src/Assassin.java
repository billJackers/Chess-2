import java.util.ArrayList;
import java.util.List;

public class Assassin extends Piece {

    private static final String IMAGES_ASSASSIN_BLUE = "images/bassassin.png";
    private static final String IMAGES_ASSASSIN_RED = "images/rassassin.png";

    public Assassin(Sides side, int size, Square initSquare) {
        super(side, size, initSquare);
        switch (side) {
            case BLUE -> this.image = getImageByFile(IMAGES_ASSASSIN_BLUE);
            case RED -> this.image = getImageByFile(IMAGES_ASSASSIN_RED);
        }
    }

    public List<Square> getLegalMoves() {

        ArrayList<Square> legalMoves = new ArrayList<>();

        int rank = this.parentSquare.getRank();
        int file = this.parentSquare.getFile();

        Square[] b = board.getBoard();

        int pos = (file*10) + rank;

        /** Lines 29 to 66 define legal moves
         * Since the movement options for the assassin are symmetrical it applies to blue and red
         */

        int[] allMoves = {20, 9, 11, 2};  // [+] OR [-] these values RELATIVE TO OUR CURRENT INDEX gives us possible moves for the assassin
        for (int relativeMove : allMoves) {
            // if the relative moves are within the bounds of the board and the position is able to be captured, then add to legalMoves
            if (pos + relativeMove < 100 && this.canCapture(b[pos + relativeMove])) legalMoves.add(b[pos + relativeMove]);
            if (pos - relativeMove >= 0 && this.canCapture(b[pos - relativeMove])) legalMoves.add(b[pos - relativeMove]);
        }




        // Moves backwards 2 spaces
        if (!b[pos + 20].hasPiece() && pos + 20 < 100) {
            legalMoves.add(b[pos + 20]);
        }
        // Moves forward 2 spaces
        if (!b[pos - 20].hasPiece() && pos - 20 >= 0) {
            legalMoves.add(b[pos - 20]);
        }

        // Moves to the left 2 spaces
        if (!b[pos - 2].hasPiece() && pos - 2 >= 0) {
            legalMoves.add(b[pos - 2]);
        }

        // Moves to the left 2 spaces
        if (!b[pos + 2].hasPiece() && pos + 2 < 100) {
            legalMoves.add(b[pos + 2]);
        }

        // Moves left one space and up one space (one space diagonally)
        if (!b[pos - 11].hasPiece() && pos - 11 >= 0) {
            legalMoves.add(b[pos - 11]);
        }

        // Moves right one space and down one space (one space diagonally)
        if (!b[pos + 11].hasPiece() && pos + 11 < 100) {
            legalMoves.add(b[pos + 11]);
        }

        // Moves left one space and down one space (one space diagonally)
        if (!b[pos + 9].hasPiece() && pos + 9 < 100) {
            legalMoves.add(b[pos + 9]);
        }

        // Moves right one space and up one space (one space diagonally)
        if (!b[pos - 9].hasPiece() && pos - 9 >= 0) {
            legalMoves.add(b[pos - 9]);
        }

        // Captures
        if (pos % 10 != 9 && pos+10 < 100 && b[pos+11].hasPiece() && this.canCapture(b[pos+11])) {
            legalMoves.add(b[pos+11]);
        }

        if (pos % 10 != 0 && pos+9 < 100 && b[pos+9].hasPiece() && this.canCapture(b[pos+9])) {
            legalMoves.add(b[pos+9]);
        }

    }

}
