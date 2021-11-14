import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import javax.swing.*;

public class Board extends JPanel implements ActionListener, MouseListener {

    private static final int SQUARE_SIZE = 50; // length of a square tile
    private static final int FILE_SIZE = 10; // columns
    private static final int RANK_SIZE = 10; // rows

    private final Square[] board;
    private final GameWindow window;

    private static final int DELAY = 25; // delay in ms to update board

    public Board(GameWindow g) {
        // set game window and create a new board
        this.window = g;
        this.board = new Square[100];

        // window size
        this.setPreferredSize(new Dimension(SQUARE_SIZE*RANK_SIZE, SQUARE_SIZE*FILE_SIZE)); // dimensions based on the size of the grid
        this.setMaximumSize(this.getPreferredSize());

        // the Game Loop
        Timer timer = new Timer(DELAY, this);
        timer.start();

        // adding mouse listener
        this.addMouseListener(this);

        initializePieces();
        generateBoardState("rbrbqkbrbr/socnggncos/pppppppppp/X/X/X/X/PPPPPPPPPP/SOCNGGNCOS/RBRBKQBRBR");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // this method is called by the timer every DELAY ms.
        // use this space to update the state of your game or animation
        // before the graphics are redrawn.
        repaint();
    }

    private void initializePieces() {
        int squareCount = 0;
        for (int file = 0; file < FILE_SIZE; file++) {
            for (int rank = 0; rank < RANK_SIZE; rank++) {
                board[squareCount] = new Square(this, rank, file, SQUARE_SIZE, null);
                squareCount++;
            }
        }
    }

    public void paintComponent(Graphics g) {  // draws all the squares in the board
        for (Square sq : this.board) {
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
        for (int c = 0; c < FEN.length(); c++) {
            char curr = FEN.toLowerCase().charAt(c);

            Piece.Sides side;  // if the character is lowercase then the side is blue else red
            if (curr == FEN.charAt(c)) {
                side = Piece.Sides.BLUE;
            }
            else {
                side = Piece.Sides.RED;
            }

            switch (curr) {
                case 'k' -> board[pos].setPiece(new King(side, SQUARE_SIZE));
                case 'q' -> board[pos].setPiece(new Queen(side, SQUARE_SIZE));
                case 'n' -> board[pos].setPiece(new Knight(side, SQUARE_SIZE));
                case 'r' -> board[pos].setPiece(new Rook(side, SQUARE_SIZE));
                case 'b' -> board[pos].setPiece(new Bishop(side, SQUARE_SIZE));
                case 'p' -> board[pos].setPiece(new Pawn(side, SQUARE_SIZE));
                case 'c' -> board[pos].setPiece(new Archer(side, SQUARE_SIZE));
                case 's' -> board[pos].setPiece(new Assassin(side, SQUARE_SIZE));
                case 'o' -> board[pos].setPiece(new Bomber(side, SQUARE_SIZE));
                case 'g' -> board[pos].setPiece(new RoyalGuard(side, SQUARE_SIZE));
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
        int rankPos = mouseX / SQUARE_SIZE;  // integer division leaves us with the correct rank and file
        int filePos = mouseY / SQUARE_SIZE;
        int boardPos = filePos * FILE_SIZE + rankPos;
        return board[boardPos];
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        Square clicked = getSquareClicked(e.getX(), e.getY());
        System.out.println("Pressed: " + clicked.getPiece());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("Released: " + e.getX() + " " + e.getY());
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
