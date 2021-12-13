import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Knight extends Piece {

    private static final String IMAGES_KNIGHT_BLUE = "images/bknight.png";
    private static final String IMAGES_KNIGHT_RED = "images/rknight.png";

    public Knight(Sides side, int size, Square initSquare, Settings settings) {
        super(side, size, initSquare, settings);
        switch (side) {
            case BLUE -> this.image = getImageByFile(IMAGES_KNIGHT_BLUE);
            case RED -> this.image = getImageByFile(IMAGES_KNIGHT_RED);
        }
    }

    public String getName() { return "Knight"; }

    @Override
    public String getFENValue() {
        return "n";
    }

    public List<Square> getLegalMoves(Board board) {

        ArrayList<Square> legalMoves = new ArrayList<>();

        int rank = this.parentSquare.getRank();
        int file = this.parentSquare.getFile();
        int fileSize = board.getFileSize();
        int indexOfPiece = (file*fileSize) + rank;  // the index of this piece in the board[]

        Square[] b = board.getBoard();

        int[] allMoves = {21, 19, 12, 8};  // [+] OR [-] these values RELATIVE TO OUR CURRENT INDEX gives us possible moves for the knight
        for (int relativeMove : allMoves) {
            // if the relative moves are within the bounds of the board and the position is capturable, then add to legalMoves
            if (indexOfPiece + relativeMove < 100 && Math.abs(((indexOfPiece + relativeMove)%10)-(indexOfPiece%10)) <= 2 && this.canCapture(b[indexOfPiece + relativeMove])) legalMoves.add(b[indexOfPiece + relativeMove]);
            if (indexOfPiece - relativeMove >= 0  && Math.abs(((indexOfPiece - relativeMove)%10)-(indexOfPiece%10)) <= 2 && this.canCapture(b[indexOfPiece - relativeMove])) legalMoves.add(b[indexOfPiece - relativeMove]);
        }
        return legalMoves;
    }

    @Override
    public List<Square> getTargets(Board board) {
        return null;
    }

}
