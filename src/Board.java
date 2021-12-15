import java.awt.*;
import javax.swing.*;

public class Board extends JPanel  {

    private static final int SQUARE_SIZE = 60; // length of a square tile
    private static final int FILE_SIZE = 10; // columns
    private static final int RANK_SIZE = 10; // rows

    private boolean isPaused;
    private boolean isFlipped;

    private final PlayerController controller;

    private final Square[] board = new Square[100];

    private Settings settings;

    public Board(Settings settings, String FEN) {
        // window size
        this.setPreferredSize(new Dimension(SQUARE_SIZE*RANK_SIZE, SQUARE_SIZE*FILE_SIZE)); // dimensions based on the size of the grid
        this.setMaximumSize(this.getPreferredSize());
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(true);
        this.settings = settings;

        //  initially creating the board
        initializeSquares();  // "rbbrqkrbbr/socnggncos/pppppppppp/X/X/X/X/PPPPPPPPPP/SOCNGGNCOS/RBBRQKRBBR"
        generateBoardState(FEN);


        controller = new PlayerController(this, settings);  // PlayerController to handle mouse input
        isPaused = false;
        flipBoard();  // flip the board so Blue is on the bottom (default is red)
    }

    // getters
    @Override //  all i have to say is bruh
    public int getHeight() {
        return SQUARE_SIZE*FILE_SIZE;
    }
    @Override
    public int getWidth() {
        return SQUARE_SIZE*RANK_SIZE;
    }

    public int getSquareSize() { return SQUARE_SIZE; }

    public Square[] getBoard() {
        return board;
    }

    public int getRankSize() {
        return RANK_SIZE;
    }

    public int getFileSize() {
        return FILE_SIZE;
    }

    public PlayerController getController() {
        return controller;
    }

    // pausing events
    public boolean isPaused() { return isPaused; }
    public void pause() { isPaused = true; }
    public void unpause() { isPaused = false; }

    private void initializeSquares() {
        int squareCount = 0;
        for (int file = 0; file < FILE_SIZE; file++) {
            for (int rank = 0; rank < RANK_SIZE; rank++) {
                board[squareCount] = new Square(rank, file, SQUARE_SIZE);
                squareCount++;
            }
        }
    }

    public void flipBoard() {  // flips the sides (does not work though)
        isFlipped = !isFlipped;  // this is based programming fight me
    }

    public void paintComponent(Graphics g) {  // draws all the squares in the board
        super.paintComponent(g);
        g.setColor(new Color(225, 209, 163, 255));
        g.fillRect(0, 0, RANK_SIZE*SQUARE_SIZE, FILE_SIZE*SQUARE_SIZE);  // fill background

        for (Square sq : this.board) {  // then draw squares
            int squareX = sq.getX();
            int squareY = sq.getY();
            if (isFlipped) {  // if the board is flipped, we draw the squares in the opposite spots
                squareX = SQUARE_SIZE*RANK_SIZE - squareX - SQUARE_SIZE;
                squareY = SQUARE_SIZE*FILE_SIZE - squareY - SQUARE_SIZE;
            }
            sq.draw(g, squareX, squareY);
        }
    }

    public void generateBoardState(String FEN) {  // Function that will generate a state of the board based on a modified FEN string
        // Information on FEN strings: http://files.lib.byu.edu/webdev-hire/instructions/
        // For Example, this is the FEN String for the initial start: rbrbqkbrbr/socnggncos/pppppppppp/X/X/X/X/PPPPPPPPPP/SOCNGGNCOS/RBRBKQBRBR
        // FEN String key (lowercase is blue, uppercase is red):
        //King – k
        //Queen – q
        //Knight – n
        //Rook – r
        //Bishop – b
        //Pawn – p
        //Archer – c
        //Assassin – s
        //Bomber – o
        //Royal Guard – g
        int pos = 0;

        for (int c = 0; c < FEN.length(); c++) {  // looping through the characters
            char curr = FEN.toLowerCase().charAt(c);

            Sides side;  // if the character is lowercase then the side is blue else red
            if (curr == FEN.charAt(c)) {
                side = Sides.BLUE;
            }
            else {
                side = Sides.RED;
            }

            switch (curr) {
                case 'k' -> board[pos].setPiece(new King(side, SQUARE_SIZE, board[pos], settings));
                case 'q' -> board[pos].setPiece(new Queen(side, SQUARE_SIZE, board[pos], settings));
                case 'n' -> board[pos].setPiece(new Knight(side, SQUARE_SIZE, board[pos], settings));
                case 'r' -> board[pos].setPiece(new Rook(side, SQUARE_SIZE, board[pos], settings));
                case 'b' -> board[pos].setPiece(new Bishop(side, SQUARE_SIZE, board[pos], settings));
                case 'p' -> board[pos].setPiece(new Pawn(side, SQUARE_SIZE, board[pos], settings));
                case 'c' -> board[pos].setPiece(new Archer(side, SQUARE_SIZE, board[pos], settings));
                case 's' -> board[pos].setPiece(new Assassin(side, SQUARE_SIZE, board[pos], settings));
                case 'o' -> board[pos].setPiece(new Bomber(side, SQUARE_SIZE, board[pos], settings));
                case 'g' -> board[pos].setPiece(new RoyalGuard(side, SQUARE_SIZE, board[pos], settings));
                case 'x' -> pos += 10; // a 1 character way to write "10"
            }
            if (Character.isDigit(curr)) {  // if the character is a digit, then we skip that amount in the board[pos]
                pos += Character.getNumericValue(curr);
            }
            else if (curr != '/' && curr != 'x') { // increment the position whenever the character isnt the newline (/) or has not already been incremented (x == +10)
                pos++;
            }
        }

    }
    public Square getSquare(int rank, int file) {
        int boardPos = file * FILE_SIZE + rank;  // calculate the position instead of looping through in for loop
        return board[boardPos];
    }
    public Square getSquareClicked(int mouseX, int mouseY) {
        int rank = mouseX / SQUARE_SIZE;  // integer division leaves us with the correct rank and file
        int file = mouseY / SQUARE_SIZE;
        if (isFlipped) {  // if the board is flipped then get the inverse square
            rank = 9 - rank;
            file = 9 - file;
        }
        return getSquare(rank, file);
    }

    public String getFEN() {
        String FEN = "";
        // Traverse the array by row
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Square curSquare = board[(row*10) + col];
                if (curSquare.hasPiece()) {
                    switch (curSquare.getPiece().side) {
                        case BLUE -> FEN += curSquare.getPiece().getFENValue();
                        case RED -> FEN += curSquare.getPiece().getFENValue().toUpperCase();
                    }
                } else {
                    FEN += "0";
                }
            }
            if (row != 9) FEN += "/"; // Don't add a '/' for the last row
        }
        return simplifiedFEN(FEN);
    }

    // Helper method for getFEN
    public String simplifiedFEN(String FEN) {
        String newFEN = "";
        int count = 0;

        for (int i = 0; i < FEN.length(); i++) {
            if (FEN.charAt(i) != '0') {
                if (count == 10) newFEN += "X";
                else if (count != 0) newFEN += count;
                count = 0;
                newFEN += FEN.substring(i, i+1);
            } else {
                count++;
            }
        }

        return newFEN;
    }

}