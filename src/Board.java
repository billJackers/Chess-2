import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import javax.swing.*;

public class Board extends JPanel implements ActionListener {

    private static final int SQUARE_SIZE = 60; // length of a square tile
    private static final int FILE_SIZE = 10; // columns
    private static final int RANK_SIZE = 10; // rows

    private boolean isPaused;

    private final PlayerController controller;

    private final Square[] board = new Square[100];

    private static final int DELAY = 25; // delay in ms to update board

    public Board() {  // single player board
        this(null, null);
    }

    public Board(Socket connection, Sides playerSide) {
        // window size
        this.setPreferredSize(new Dimension(SQUARE_SIZE*RANK_SIZE, SQUARE_SIZE*FILE_SIZE)); // dimensions based on the size of the grid
        this.setMaximumSize(this.getPreferredSize());

        // the Game Loop
        Timer timer = new Timer(DELAY, this);
        timer.start();

        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(true);

        initializeSquares();  // "rbrbqkbrbr/socnggncos/pppppppppp/X/X/X/X/PPPPPPPPPP/SOCNGGNCOS/RBRBQKBRBR"
        generateBoardState("rbrbqkbrbr/socnggncos/pppppppppp/X/X/X/X/PPPPPPPPPP/SOCNGGNCOS/RBRBQKBRBR");

        // PlayerController to handle mouse input
        controller = new PlayerController(this);

        // ConnectionHandler to handle multiplayer sessions
        ConnectionHandler connectionHandler;

        if (connection != null)  // if we are doing multiplayer, instantiate ConnectionHandler
            connectionHandler = new ConnectionHandler(connection, controller, playerSide, this);

        isPaused = false;
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

    @Override
    public void actionPerformed(ActionEvent e) {
        // this method is called by the timer every DELAY ms.
        // use this space to update the state of your game or animation
        // before the graphics are redrawn.
        repaint();
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
        for (int i = 0; i < board.length/2; i++) {
            Square tempSquare = board[99-i];
            Piece tempPiece = tempSquare.getPiece();
            board[99-i] = board[i];
            if (board[i].getPiece() != null)
                board[99-i].setPiece(board[i].getPiece());
            board[i] = tempSquare;
            if (tempPiece != null)
                board[i].setPiece(tempPiece);
        }
    }

    public void paintComponent(Graphics g) {  // draws all the squares in the board
        super.paintComponent(g);
        g.setColor(new Color(225, 209, 163, 255));
        g.fillRect(0, 0, RANK_SIZE*SQUARE_SIZE, FILE_SIZE*SQUARE_SIZE);  // fill background
        for (Square sq : this.board) {  // then draw squares
            sq.draw(g);
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
                case 'k' -> board[pos].setPiece(new King(side, SQUARE_SIZE, board[pos]));
                case 'q' -> board[pos].setPiece(new Queen(side, SQUARE_SIZE, board[pos]));
                case 'n' -> board[pos].setPiece(new Knight(side, SQUARE_SIZE, board[pos]));
                case 'r' -> board[pos].setPiece(new Rook(side, SQUARE_SIZE, board[pos]));
                case 'b' -> board[pos].setPiece(new Bishop(side, SQUARE_SIZE, board[pos]));
                case 'p' -> board[pos].setPiece(new Pawn(side, SQUARE_SIZE, board[pos]));
                case 'c' -> board[pos].setPiece(new Archer(side, SQUARE_SIZE, board[pos]));
                case 's' -> board[pos].setPiece(new Assassin(side, SQUARE_SIZE, board[pos]));
                case 'o' -> board[pos].setPiece(new Bomber(side, SQUARE_SIZE, board[pos]));
                case 'g' -> board[pos].setPiece(new RoyalGuard(side, SQUARE_SIZE, board[pos]));
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
    public Square getSquareClicked(int mouseX, int mouseY) {
        int rank = mouseX / SQUARE_SIZE;  // integer division leaves us with the correct rank and file
        int file = mouseY / SQUARE_SIZE;
        int boardPos = file * FILE_SIZE + rank;  // calculate the position instead of looping through in for loop
        return board[boardPos];
    }
}