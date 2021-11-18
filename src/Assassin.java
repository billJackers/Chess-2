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

        int column = pos % 10;
        int row = pos / 10;

        int[] allMoves = {20, 9, 11, 2};  // [+] OR [-] these values RELATIVE TO OUR CURRENT INDEX gives us possible moves for the assassin
        for (int relativeMove : allMoves) {
            // if the relative moves are within the bounds of the board and the position is able to be captured, then add to legalMoves
            if (pos + relativeMove < 100 && this.canCapture(b[pos + relativeMove]) && acceptableRow(row,(pos + relativeMove) / 10) && acceptableColumn(column,(pos + relativeMove) % 10)) {
                legalMoves.add(b[pos + relativeMove]);
            }
            if (pos - relativeMove >= 0 && this.canCapture(b[pos - relativeMove]) && acceptableRow(row,(pos - relativeMove) / 10) && acceptableColumn(column,(pos - relativeMove) % 10)) {
                legalMoves.add(b[pos - relativeMove]);
            }
        }
        return legalMoves;
    }

    public boolean acceptableRow(int row, int proposedRow) {
        int maxRow = row + 2;
        int minRow = row - 2;
            if (proposedRow <= maxRow && proposedRow >= minRow) {
                return true;
            }
        return false;
    }

    public boolean acceptableColumn(int column, int proposedColumn) {
        int maxColumn = column + 2;
        int minColumn = column - 2;
        if (proposedColumn <= maxColumn && proposedColumn >= minColumn) {
            return true;
        }
        return false;
    }

    @Override
    public List<Square> getTargets() {
        return null;
    }
}
