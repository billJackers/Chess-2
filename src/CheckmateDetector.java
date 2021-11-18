import java.util.List;
import java.util.ArrayList;

public class CheckmateDetector {

    private Board b;

    private List<Piece> bPieces;
    private List<Piece> rPieces;

    private Piece bKing;
    private Piece rKing;

    private List<Square> rLegalMoves;
    private List<Square> bLegalMoves;

    public CheckmateDetector(Board b, Piece bk, Piece rk) {
        this.b = b;
        this.bKing = bk;
        this.rKing = rk;

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
                    }
                    case RED -> {
                        rLegalMoves.addAll(p.getLegalMoves());
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

}
