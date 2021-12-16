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

        public void shoot(Square from, Square to) {
            super.shoot(from, to);
            runComputerMove();
        }
    }

    private class Move {  // move class is used to pair "from" and "to" squares

        private Square from;
        private Square to;
        private Type type;

        private enum Type {
            PIECE_MOVE,
            ARCHER_SHOT
        }

        public Move (Square from, Square to, Type moveType) {
            this.from = from;
            this.to = to;
            this.type = moveType;
        }

        public void play() {
            switch (type){
                case PIECE_MOVE -> moveListener.move(from, to);
                case ARCHER_SHOT -> moveListener.shoot(from, to);
            }
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
        for (Square from : board.getBoard()) {  // iterate through the board
            if (from.hasPiece() && from.getPiece().getSide() == Sides.RED) {  // get the square of every playable piece

                for (Square to : from.getPiece().getLegalMoves(board)) {  // get the legalMoves of the playable piece
                    allPossibleMoves.add(new Move(from, to, Move.Type.PIECE_MOVE));  // add each legalMove to allPossibleMoves
                }

                if (from.getPiece() instanceof Archer) {  // if the piece is an archer, we also need to get the shots
                    for (Square shots : from.getPiece().getTargets(board)) {  // get the legalMoves of the playable piece
                        allPossibleMoves.add(new Move(from, shots, Move.Type.ARCHER_SHOT));  // add each Archer shot to allPossibleMoves
                    }
                }

            }
        }

        Move randMove = allPossibleMoves.get((int) ((Math.random() * (allPossibleMoves.size()-1))));
        randMove.play();
    }
}
