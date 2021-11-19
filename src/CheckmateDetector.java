import java.util.List;
import java.util.ArrayList;

public class CheckmateDetector {

    private Board b;

    private Piece bKing;
    private Piece rKing;

    private List<Square> rLegalMoves;
    private List<Square> bLegalMoves;

    public CheckmateDetector(Board b) {
        this.b = b;
        for (Square square : b.getBoard()) {
            if (square.hasPiece() && square.getPiece() instanceof King) {
                switch (square.getPiece().side) {
                    case BLUE: bKing = square.getPiece();
                    case RED: rKing = square.getPiece();
                }
            }
        }

        rLegalMoves = new ArrayList<>();
        bLegalMoves = new ArrayList<>();
        updateLegalMoves();
    }

    public void updateLegalMoves() {
        Square[] squares = b.getBoard();
        for (Square square : squares) {
            if (square.hasPiece()) {
                Piece p = square.getPiece();
                switch (p.side) {
                    case BLUE -> {
                        bLegalMoves.addAll(p.getLegalMoves());
                        if (p instanceof Archer) bLegalMoves.addAll(p.getTargets());
                        if (p instanceof Pawn) bLegalMoves.addAll(p.getPawnAttacks(Piece.Sides.BLUE));
                    }
                    case RED -> {
                        rLegalMoves.addAll(p.getLegalMoves());
                        if (p instanceof Archer) rLegalMoves.addAll(p.getTargets());
                        if (p instanceof Pawn) rLegalMoves.addAll(p.getPawnAttacks(Piece.Sides.RED));
                    }
                }
            }
        }

        System.out.println((bKing.parentSquare.getFile()));
    }

    public boolean blueInCheck() {
        return rLegalMoves.contains(bKing.parentSquare);
    }

    public boolean redInCheck() {
        return bLegalMoves.contains(rKing.parentSquare);
    }

    public List<Square> getRLegalMoves() {
        return rLegalMoves;
    }

    public List<Square> getBLegalMoves() {
        return bLegalMoves;
    }

}
