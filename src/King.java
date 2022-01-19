import java.util.List;

public class King extends Piece {

    private static final String IMAGES_KING_BLUE = "images/bking.png";
    private static final String IMAGES_KING_RED = "images/rking.png";

    public King(Sides side, int size, Square initSquare, Settings settings) {
        super(side, size, initSquare, settings);
        switch (side) {
            case BLUE -> this.image = getImageByFile(IMAGES_KING_BLUE);
            case RED -> this.image = getImageByFile(IMAGES_KING_RED);
        }
        this.materialValue = Float.MAX_VALUE;
    }

    public String getName() { return "King"; }

    @Override
    public String getFENValue() {
        return "k";
    }

    public void runOnDeath(Board board, Piece captor) {  // THE KING IS DEAD LMAO
        WinFrame winFrame = new WinFrame();
        int winner = 0;
        switch (side) {
            case RED -> winner = 1;
            case BLUE -> winner = -1;
        }
        winFrame.makeWinFrame(winner);
        board.pause();  // pause the game on king's death
        super.runOnDeath(board, captor);
    }

    public List<Square> getLegalMoves(Board board) {
        return getKingLegalMoves(board);
    }

    @Override
    public List<Square> getTargets(Board board) {
        return null;
    }

}
