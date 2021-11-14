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

public class Board extends JPanel implements ActionListener, MouseListener, MouseMotionListener {

    private static final int SQUARE_SIZE = 80; // length of a square tile
    private static final int FILE_SIZE = 10; // columns
    private static final int RANK_SIZE = 10; // rows

    private final Square[] board;
    private final GameWindow window;

    private static final int DELAY = 25; // delay in ms to update board

    public Board(GameWindow g) {
        // set game window and create a new board
        this.window = g;
        this.board = new Square[100];

        // add a mouse event listener
        this.addMouseListener(this);

        // window size
        this.setPreferredSize(new Dimension(SQUARE_SIZE*RANK_SIZE, SQUARE_SIZE*FILE_SIZE)); // dimensions based on the size of the grid
        this.setMaximumSize(this.getPreferredSize());

        // the Game Loop
        Timer timer = new Timer(DELAY, this);
        timer.start();

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
            switch (curr) {  // there's probably a cleaner way to do this
                case 'k':
                    if (FEN.charAt(c) == 'k') board[pos].setPiece(new King(Piece.Sides.BLUE, SQUARE_SIZE)); // if the character is lowercase then the side is blue
                    else board[pos].setPiece(new King(Piece.Sides.RED, SQUARE_SIZE)); // else red
                    break;
                case 'q':
                    if (FEN.charAt(c) == 'q') board[pos].setPiece(new Queen(Piece.Sides.BLUE, SQUARE_SIZE));
                    else board[pos].setPiece(new Queen(Piece.Sides.RED, SQUARE_SIZE));
                    break;
                case 'n':
                    if (FEN.charAt(c) == 'n') board[pos].setPiece(new Knight(Piece.Sides.BLUE, SQUARE_SIZE));
                    else board[pos].setPiece(new Knight(Piece.Sides.RED, SQUARE_SIZE));
                    break;
                case 'r':
                    if (FEN.charAt(c) == 'r') board[pos].setPiece(new Rook(Piece.Sides.BLUE, SQUARE_SIZE));
                    else board[pos].setPiece(new Rook(Piece.Sides.RED, SQUARE_SIZE));
                    break;
                case 'b':
                    if (FEN.charAt(c) == 'b') board[pos].setPiece(new Bishop(Piece.Sides.BLUE, SQUARE_SIZE));
                    else board[pos].setPiece(new Bishop(Piece.Sides.RED, SQUARE_SIZE));
                    break;
                case 'p':
                    if (FEN.charAt(c) == 'p') board[pos].setPiece(new Pawn(Piece.Sides.BLUE, SQUARE_SIZE));
                    else board[pos].setPiece(new Pawn(Piece.Sides.RED, SQUARE_SIZE));
                    break;
                case 'c':
                    if (FEN.charAt(c) == 'c') board[pos].setPiece(new Archer(Piece.Sides.BLUE, SQUARE_SIZE));
                    else board[pos].setPiece(new Archer(Piece.Sides.RED, SQUARE_SIZE));
                    break;
                case 's':
                    if (FEN.charAt(c) == 's') board[pos].setPiece(new Assassin(Piece.Sides.BLUE, SQUARE_SIZE));
                    else board[pos].setPiece(new Assassin(Piece.Sides.RED, SQUARE_SIZE));
                    break;
                case 'o':
                    if (FEN.charAt(c) == 'o') board[pos].setPiece(new Bomber(Piece.Sides.BLUE, SQUARE_SIZE));
                    else board[pos].setPiece(new Bomber(Piece.Sides.RED, SQUARE_SIZE));
                    break;
                case 'g':
                    if (FEN.charAt(c) == 'g') board[pos].setPiece(new RoyalGuard(Piece.Sides.BLUE, SQUARE_SIZE));
                    else board[pos].setPiece(new RoyalGuard(Piece.Sides.RED, SQUARE_SIZE));
                    break;
                case 'x':  // a 1 character way to write "10"
                    pos += 10;
            }
            System.out.println("curr: " + curr + ", pos: " + pos);
            if (Character.isDigit(curr)) {
                pos += curr;
            }
            else if (curr != '/' && curr != 'x') { // increment the position whenever the character isnt the newline (/) or has not already been incremented (x == +10)
                pos++;
            }
        }

    }

    public void paintComponent(Graphics g) {  // draws all the squares in the board
        for (Square sq : this.board) {
            sq.draw(g);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Square sq = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));
        repaint();
    }


    @Override
    public void mouseDragged(MouseEvent e) {
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
