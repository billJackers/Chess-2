import javax.swing.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

public class Pawn extends Piece {

    private static final String IMAGES_PAWN_BLUE = "images/bpawn.png";
    private static final String IMAGES_PAWN_RED = "images/rpawn.png";

    private boolean wasMoved;
    private boolean enPassantable;

    public Pawn(Sides side, int size, Square initSquare, Settings settings) {
        super(side, size, initSquare, settings);
        wasMoved = false;
        switch (side) {
            case BLUE -> this.image = getImageByFile(IMAGES_PAWN_BLUE);
            case RED -> this.image = getImageByFile(IMAGES_PAWN_RED);
        }
    }

    public String getName() { return "Pawn"; }

    @Override
    public String getFENValue() {
        return "p";
    }

    public void setToMoved() {
        this.wasMoved = true;
    }
    public boolean isEnPassantable() {
        return this.enPassantable;
    }
    public void setEnPassantable(boolean b) {
        this.enPassantable = b;
    }

    public void runOnMove(Board board, Square captured) {
        setToMoved();  // the piece has moved

        // En Passant (clean up later)
        if (!captured.hasPiece()) {  // captured will never be null
            switch (side) {
                case BLUE -> board.getBoard()[(captured.getFile() * 10) + captured.getRank() - 10].clearPiece();
                case RED -> board.getBoard()[(captured.getFile() * 10) + captured.getRank() + 10].clearPiece();
            }
        }
        resetEnPassants(board);
        int fromPos = (parentSquare.getFile() * 10) + parentSquare.getRank();
        int toPos = (captured.getFile() * 10) + captured.getRank();
        if (Math.abs(toPos-fromPos) == 20) setEnPassantable(true);


        super.runOnMove(board, captured);  // call so the piece reference gets move to the target square

        // Pawn promotions
        switch (captured.getFile()) {
            case 0 -> doPromotionEvent(board, Sides.RED);   // Red has made it to blue side
            case 9 -> doPromotionEvent(board, Sides.BLUE);  // Blue has made it to red side
        }
        board.repaint();
    }

    public void resetEnPassants(Board board) {
        for (Square square : board.getBoard()) {
            if (square.hasPiece()) {
                switch (side) {
                    case BLUE -> {
                        if (square.getPiece().side == Sides.BLUE && square.getPiece() instanceof Pawn) ((Pawn) square.getPiece()).setEnPassantable(false);
                    }
                    case RED -> {
                        if (square.getPiece().side == Sides.RED && square.getPiece() instanceof Pawn) ((Pawn) square.getPiece()).setEnPassantable(false);
                    }
                }
            }
        }
    }

    public void doPromotionEvent(Board board, Sides side) {
        board.pause();  // pause the game while doing promotions
        JComboBox<String> popupMenu = new JComboBox<>();
        String[] pieces = {
                "Queen",
                "Rook",
                "Bishop",
                "Knight",
                "Royal Guard",
                "Archer",
                "Assassin",
        };
        for (String piece : pieces) {
            popupMenu.addItem(piece);
        }
        JOptionPane.showMessageDialog(null, popupMenu, "Promote piece", JOptionPane.QUESTION_MESSAGE);
        String selected = (String) popupMenu.getSelectedItem();
        switch (Objects.requireNonNull(selected)) {
            case "Queen" -> parentSquare.setPiece(new Queen(side, board.getSquareSize(), parentSquare, settings));
            case "Rook" -> parentSquare.setPiece(new Rook(side, board.getSquareSize(), parentSquare, settings));
            case "Bishop" -> parentSquare.setPiece(new Bishop(side, board.getSquareSize(), parentSquare, settings));
            case "Knight" -> parentSquare.setPiece(new Knight(side, board.getSquareSize(), parentSquare, settings));
            case "Royal Guard" -> parentSquare.setPiece(new RoyalGuard(side, board.getSquareSize(), parentSquare, settings));
            case "Archer" -> parentSquare.setPiece(new Archer(side, board.getSquareSize(), parentSquare, settings));
            case "Assassin" -> parentSquare.setPiece(new Assassin(side, board.getSquareSize(), parentSquare, settings));
        }
        board.unpause();
    }

    @Override
    public List<Square> getLegalMoves(Board board) {
        List<Square> legalMoves = new ArrayList<>();

        int rank = this.parentSquare.getRank();
        int file = this.parentSquare.getFile();

        Square[] b = board.getBoard();

        int pos = (file*10) + rank;

        switch (this.side) {
            case BLUE -> {
                // Forward moves
                if (pos+20 < 100 && !wasMoved && !b[pos+20].hasPiece() && !b[pos+10].hasPiece()) legalMoves.add(b[pos+20]);
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

                // En Passant
                if (pos % 10 != 9 && pos + 10 < 100) {
                    if (b[pos+1].getPiece() instanceof Pawn && ((Pawn) b[pos+1].getPiece()).isEnPassantable()) {
                        legalMoves.add(b[pos+11]);
                    }
                }

                if (pos % 10 != 0 && pos + 9 < 100) {
                    if (b[pos-1].getPiece() instanceof Pawn && ((Pawn) b[pos-1].getPiece()).isEnPassantable()) {
                        legalMoves.add(b[pos+9]);
                    }
                }

            }
            case RED -> {
                try {
                    // Forward moves
                    if (pos - 20 >= 0 && !wasMoved && !b[pos - 20].hasPiece() && !b[pos - 10].hasPiece()) legalMoves.add(b[pos - 20]);
                    if (pos - 10 >= 0 && !b[pos - 10].hasPiece()) {
                        legalMoves.add(b[pos - 10]);
                    }

                    // Captures
                    if (pos % 10 != 9 && pos - 9 >= 0 && b[pos - 9].hasPiece() && this.canCapture(b[pos - 9])) {
                        legalMoves.add(b[pos - 9]);
                    }

                    if (pos % 10 != 0 && pos - 11 >= 0 && b[pos - 11].hasPiece() && this.canCapture(b[pos - 11])) {
                        legalMoves.add(b[pos - 11]);
                    }

                    // En Passant
                    if (pos % 10 != 9 && pos - 9 >= 0) {
                        if (b[pos+1].getPiece() instanceof Pawn && ((Pawn) b[pos+1].getPiece()).isEnPassantable()) {
                            legalMoves.add(b[pos-9]);
                        }
                    }

                    if (pos % 10 != 0 && pos - 11 < 100) {
                        if (b[pos-1].getPiece() instanceof Pawn && ((Pawn) b[pos-1].getPiece()).isEnPassantable()) {
                            legalMoves.add(b[pos-11]);
                        }
                    }

                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        return legalMoves;
    }

    @Override
    public List<Square> getTargets(Board board) {
        return null;
    }

}
