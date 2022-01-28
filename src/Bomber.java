import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Bomber extends Piece {

    private static final String IMAGES_BOMBER_BLUE = "images/pieceImages/bbomber.png";
    private static final String IMAGES_BOMBER_RED = "images/pieceImages/rbomber.png";

    public Bomber(Sides side, int size, Square initSquare, Settings settings) {
        super(side, size, initSquare, settings);
        switch (side) {
            case BLUE -> this.image = getImageByFile(IMAGES_BOMBER_BLUE);
            case RED -> this.image = getImageByFile(IMAGES_BOMBER_RED);
        }
        this.materialValue = 2.5f;
    }

    public String getName() { return "Bomber"; }

    @Override
    public String getFENValue() {
        return "o";
    }

    public List<Square> getLegalMoves(Board board) {
        ArrayList<Square> legalMoves = new ArrayList<>();

        int rank = this.parentSquare.getRank();
        int file = this.parentSquare.getFile();

        Square[] b = board.getBoard();

        int pos = (file*10) + rank;

        switch (this.side) {
            case BLUE -> {
                try {
                    if (!b[pos + 10].hasPiece() || this.canCapture(b[pos + 10])) legalMoves.add(b[pos + 10]);
                    if (pos + 10 < 100 && !b[pos + 10].hasPiece()) {
                        legalMoves.add(b[pos + 10]);
                    }
                    if (pos % 10 != 9 && pos + 10 < 100 && this.canCapture(b[pos + 11])) {
                        legalMoves.add(b[pos + 11]);
                    }

                    if (pos % 10 != 0 && pos + 9 < 100 && this.canCapture(b[pos + 9])) {
                        legalMoves.add(b[pos + 9]);
                    }
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Your bomber is stuck lol");
                }

            }

            case RED -> {
                try {
                    if (!b[pos - 10].hasPiece() || this.canCapture(b[pos - 10])) legalMoves.add(b[pos - 10]);
                    if (pos - 10 > 0 && !b[pos - 10].hasPiece()) {
                        legalMoves.add(b[pos - 10]);
                    }
                    if (pos % 10 != 9 && pos - 10 > 0 && this.canCapture(b[pos - 9])) {
                        legalMoves.add(b[pos - 9]);
                    }

                    if (pos % 10 != 0 && pos - 11 > 0 && this.canCapture(b[pos - 11])) {
                        legalMoves.add(b[pos - 11]);
                    }
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Your bomber is stuck lol");
                }
            }
        }

        return legalMoves;
    }

    @Override
    public List<Square> getTargets(Board board) {
        ArrayList<Square> targets = new ArrayList<>();

        int rank = this.parentSquare.getRank();
        int file = this.parentSquare.getFile();

        Square[] b = board.getBoard();

        int pos = (file*10) + rank;

        int[] bombTargets = {-11, -10, -9,-1, 1, 9, 10, 11};
        for (int target : bombTargets) {
            if (pos + target < 100 && pos + target >= 0 && b[pos + target].hasPiece() && Math.abs((pos % 10) - ((pos + target) % 10)) <= 1) {
                targets.add(b[pos + target]);
            }
        }
        return targets;
    }

    public void runOnDeath(Board board, Piece captor) {
        super.runOnDeath(board, captor);
        // Bomber explosion
        if (!(captor instanceof Assassin)) {
            explode(board);
        }
        board.repaint();
    }

    public void explode(Board board) {
        List<Square> targets = getTargets(board);
        parentSquare.clearPiece();  // first clear the detonated bomber
        for (Square target : targets) {
            if (target.hasPiece())
                target.getPiece().runOnDeath(board, this);
            target.clearPiece();  // then clear the targets
        }
    }

    private static class AnimationThread extends SwingWorker<Void, Void> {
        private static final Image explosionGif;
        private final Board board;

        static {

//                File file = new File("src/images/pogexplosion.gif");
//                FileInputStream fs = new FileInputStream(file);
            //explosionGif = new ImageIcon("src/images/pogexplosion.gif").getImage();
            explosionGif = Toolkit.getDefaultToolkit().getImage("src/images/pogexplosion.gif");

        }

        public AnimationThread (Board board) {
            this.board = board;
        }

        @Override
        protected Void doInBackground() throws Exception {
            Graphics g = board.getGraphics();
            for (int i = 0; i < 20; i++){
                //board.repaint();
                System.out.println("Drawn Image " + i);
                g.drawImage(explosionGif, 10, 10, null);
                Thread.sleep(100);
            }

            return null;
        }
    }

//    public void doAttackAnimation(){
//        new Thread(()-> {
//            setIcon("Your Attack Animation");
//            sleep(durationOfAnimation);
//            setIcon("default Animation");
//        }).start();
//    }

}
