import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ComputerOpponent {

    class MoveListener extends PlayerController {
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

        private final Square from;
        private final Square to;
        private final Type type;
        private final int score;

        private enum Type {
            PIECE_MOVE,
            ARCHER_SHOT
        }

        public Move (Square from, Square to, Type moveType, int score) {
            this.from = from;
            this.to = to;
            this.type = moveType;
            this.score = score;
        }

        public void play() {
            switch (type){
                case PIECE_MOVE -> moveListener.move(from, to);
                case ARCHER_SHOT -> moveListener.shoot(from, to);
            }
        }

        public int getScore() {
            return score;
        }
    }

    private final MoveListener moveListener;
    private final Board board;

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
        runEvaluatedMove();
    }

    private static final HashMap<String, Integer> pieceValues = new HashMap<>();
    static {  // why cant there be a better way to initialize lists/hashmaps java you prick
        pieceValues.put("King", 9001);  // its over 9000
        pieceValues.put("Queen", 100);
        pieceValues.put("Rook", 50);
        pieceValues.put("Archer", 50);
        pieceValues.put("Bishop", 30);
        pieceValues.put("Knight", 30);
        pieceValues.put("Assassin", 20);
        pieceValues.put("Pawn", 10);
        pieceValues.put("Royal Guard", 5);
        pieceValues.put("Bomber", 5);
    }

    private int evaluate(Square from, Square to) {
        int score = 0;
        if (to.hasPiece()) {
            Piece enemy = to.getPiece();
            score += pieceValues.get(enemy.getName());
        }
        return score;
    }

    public void runEvaluatedMove() {
        ArrayList<Move> bestMoves = new ArrayList<>();  // arraylist of the highest scoring moves
        bestMoves.add(new Move(null, null, null, Integer.MIN_VALUE));

        for (Square from : board.getBoard()) {  // iterate through the board
            if (from.hasPiece() && from.getPiece().getSide() == Sides.RED) {  // get the square of every playable piece
                for (Square to : from.getPiece().getLegalMoves(board)) {  // get the legalMoves of the playable piece

                    int moveEval = evaluate(from, to);
                    if (moveEval > bestMoves.get(0).getScore()) {
                        bestMoves.clear();
                        bestMoves.add(new Move(from, to, Move.Type.PIECE_MOVE, moveEval));
                    }
                    else if (moveEval == bestMoves.get(0).getScore()) {
                        bestMoves.add(new Move(from, to, Move.Type.PIECE_MOVE, moveEval));
                    }
                }

                if (from.getPiece() instanceof Archer) {  // if the piece is an archer, we also need to get the shots
                    for (Square shots : from.getPiece().getTargets(board)) {  // get the legalMoves of the playable piece
                        int moveEval = evaluate(from, shots);
                        if (moveEval > bestMoves.get(0).getScore()) {
                            bestMoves.clear();
                            bestMoves.add(new Move(from, shots, Move.Type.ARCHER_SHOT, moveEval));
                        }
                        else if (moveEval == bestMoves.get(0).getScore()) {
                            bestMoves.add(new Move(from, shots, Move.Type.ARCHER_SHOT, moveEval));
                        }
                    }
                }

            }
        }
        Move randBestMove = bestMoves.get((int) ((Math.random() * (bestMoves.size()-1))));
        randBestMove.play();
    }

    public void runRandomMove() {
        ArrayList<Move> allPossibleMoves = new ArrayList<>();
        for (Square from : board.getBoard()) {  // iterate through the board
            if (from.hasPiece() && from.getPiece().getSide() == Sides.RED) {  // get the square of every playable piece

                for (Square to : from.getPiece().getLegalMoves(board)) {  // get the legalMoves of the playable piece
                    allPossibleMoves.add(new Move(from, to, Move.Type.PIECE_MOVE, 0));  // add each legalMove to allPossibleMoves
                }

                if (from.getPiece() instanceof Archer) {  // if the piece is an archer, we also need to get the shots
                    for (Square shots : from.getPiece().getTargets(board)) {  // get the legalMoves of the playable piece
                        allPossibleMoves.add(new Move(from, shots, Move.Type.ARCHER_SHOT, 0));  // add each Archer shot to allPossibleMoves
                    }
                }

            }
        }
        Move randMove = allPossibleMoves.get((int) ((Math.random() * (allPossibleMoves.size()-1))));
        randMove.play();
    }
}
