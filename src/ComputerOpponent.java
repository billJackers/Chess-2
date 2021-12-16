import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ComputerOpponent {

    private class MoveListener extends PlayerController {
        public MoveListener(Settings settings) {
            super(settings);
        }

        public void move(Square from, Square to) {
            super.move(from, to);
            runComputerMove();
        }
    }

    private class Move {  // move class is used to pair "from" and "to" squares

        private Square from;
        private Square to;

        public Move (Square from, Square to) {
            this.from = from;
            this.to = to;
        }

        public void play() {
            moveListener.move(from, to);
        }
    }

    private MoveListener moveListener;
    private Board board;

    public ComputerOpponent(Settings settings) {
        JFrame gameWindow = new JFrame("Giga Chess");
        gameWindow.setLocationRelativeTo(null);

        String FEN = "rbbrqkrbbr/socnggncos/pppppppppp/X/X/X/X/PPPPPPPPPP/SOCNGGNCOS/RBBRQKRBBR";

        moveListener = new MoveListener(settings);  // the playerController but also how we know when the computer should move
        board = new Board(settings, moveListener, FEN);  // creates the board JPanel to draw on
        StatsDisplay stats = new StatsDisplay(board, settings);  // stats displayer JPanel

        // adding JPanels to JFrame
        gameWindow.add(stats, BorderLayout.NORTH);
        gameWindow.add(board, BorderLayout.SOUTH);

        gameWindow.setSize(board.getPreferredSize()); // Set the size of the window based on the size of the board
        gameWindow.setResizable(false); // don't allow the user to resize the window
        gameWindow.pack(); // pack() should be called after setResizable() to avoid issues on some platforms
        gameWindow.setVisible(true);
        gameWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void runComputerMove() {
        if (moveListener.getCurrentTurn() != Sides.RED) return;  // it is the player's move so don't do anything
        runRandomMove();
    }

    public void runRandomMove() {
        ArrayList<Move> allPossibleMoves = new ArrayList<>();
        for (Square from : board.getBoard()) {
            if (from.hasPiece() && from.getPiece().getSide() == Sides.RED) {
                for (Square to : from.getPiece().getLegalMoves(board)) {
                    allPossibleMoves.add(new Move(from, to));
                }
            }
        }

        Move randMove = allPossibleMoves.get((int) ((Math.random() * (allPossibleMoves.size()-1))));
        randMove.play();
    }
}
