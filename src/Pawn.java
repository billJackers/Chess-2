import java.util.List;
import java.util.ArrayList;

public class Pawn extends Piece {

    private static final String IMAGES_PAWN_BLUE = "images/bpawn.png";
    private static final String IMAGES_PAWN_RED = "images/rpawn.png";

    private boolean wasMoved;

    public Pawn(Sides side, int size, Square initSquare) {
        super(side, size, initSquare);
        wasMoved = false;
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

        Square[] b = board.getBoard();

        int pos = (file*10) + rank;

        switch (this.side) {
            case BLUE -> {
                // Forward moves
                if (!wasMoved && !b[pos+20].hasPiece()) legalMoves.add(b[pos+20]);
                if (pos+10 < 100 && !b[pos+10].hasPiece()) {
                    legalMoves.add(b[pos+10]);
                }

                // Captures
                if (pos % 10 != 9 && pos+10 < 100 && b[pos+11].hasPiece() && this.canCapture(b[pos+11])) {
                    legalMoves.add(b[pos+11]);
                }

                if (pos % 10 != 0 && pos+9 < 100 && b[pos+9].hasPiece() && this.canCapture(b[pos+9])) {
                    legalMoves.add(b[pos+9]);
                }

            }

            case RED -> {
                // Forward moves
                if (!wasMoved && !b[pos-20].hasPiece()) legalMoves.add(b[pos-20]);
                if (pos-10 >= 0 && !b[pos+1].hasPiece()) {
                    legalMoves.add(b[pos-10]);
                }

                // Captures
                if (pos % 10 != 9 && pos-9 >= 0 && b[pos-9].hasPiece() && this.canCapture(b[pos-9])) {
                    legalMoves.add(b[pos-9]);
                }

                if (pos % 10 != 0 && pos-11 >= 0 && b[pos-11].hasPiece() && this.canCapture(b[pos-11])) {
                    legalMoves.add(b[pos-11]);
                }
            }
        }

        return legalMoves;
    }

    public boolean getWasMoved() {
        return wasMoved;
    }

    public void setToMoved() {
        this.wasMoved = true;
    }

}
