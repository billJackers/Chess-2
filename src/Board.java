import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;
import java.util.List;
import javax.swing.*;

public class Board extends JPanel implements MouseListener, MouseMotionListener {

    private static final String IMAGES_WBISHOP_PNG = "images/wbishop.png";
    private static final String IMAGES_BBISHOP_PNG = "images/bbishop.png";
    private static final String IMAGES_WKNIGHT_PNG = "images/wknight.png";
    private static final String IMAGES_BKNIGHT_PNG = "images/bknight.png";
    private static final String IMAGES_WROOK_PNG = "images/wrook.png";
    private static final String IMAGES_BROOK_PNG = "images/brook.png";
    private static final String IMAGES_WKING_PNG = "images/wking.png";
    private static final String IMAGES_BKING_PNG = "images/bking.png";
    private static final String IMAGES_BQUEEN_PNG = "images/bqueen.png";
    private static final String IMAGES_WQUEEN_PNG = "images/wqueen.png";
    private static final String IMAGES_WPAWN_PNG = "images/wpawn.png";
    private static final String IMAGES_BPAWN_PNG = "images/bpawn.png";


    private final Square[][] board;

    private final GameWindow g;
    public final LinkedList<Piece> Bpieces;
    public final LinkedList<Piece> Wpieces;
    public List<Square> movable;

    private boolean whiteTurn;

    private Piece curPiece;
    private int curX;
    private int curY;

    private CheckmateDetector cmd;

    public Board(GameWindow g) {
        this.g = g;
        this.board = new Square[10][10];
        Bpieces = new LinkedList<Piece>();
        Wpieces = new LinkedList<Piece>();
        setLayout(new GridLayout(10, 10, 0, 0));

        this.addMouseListener(this);
        this.addMouseListener(this);

        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                int xMod = x % 2;
                int yMod = y % 2;

                if ((xMod == 0 && yMod == 0) || (xMod == 1 && yMod == 1)) {
                    board[x][y] = new Square(this, false, y, x);
                } else {
                    board[x][y] = new Square(this, true, y, x);
                }
                this.add(board[x][y]);
            }
        }

        initializePieces();

        this.setPreferredSize(new Dimension(600, 600));
        this.setMaximumSize(this.getPreferredSize());
        this.setMaximumSize(this.getPreferredSize());
        this.setSize(new Dimension(600, 600));

        whiteTurn = true;

    }

    private void initializePieces() {
        // Place white pawns
        for (int x = 0; x < 10; x++) {
            board[7][x].placePiece(new Pawn(true, board[7][x], IMAGES_WPAWN_PNG));
        }

        // Place black pawns
        for (int x = 0; x < 10; x++) {
            board[2][x].placePiece(new Pawn(false, board[2][x], IMAGES_BPAWN_PNG));
        }

        // Place rooks
        board[0][0].placePiece(new Rook(false, board[0][0], IMAGES_BROOK_PNG));
        board[0][9].placePiece(new Rook(false, board[0][9], IMAGES_BROOK_PNG));
        board[9][0].placePiece(new Rook(true, board[9][0], IMAGES_WROOK_PNG));
        board[9][9].placePiece(new Rook(false, board[9][9], IMAGES_WROOK_PNG));

        // Place bishops
        board[0][1].placePiece(new Bishop(false, board[0][1], IMAGES_BBISHOP_PNG));
        board[0][3].placePiece(new Bishop(false, board[0][3], IMAGES_BBISHOP_PNG));
        board[0][6].placePiece(new Bishop(false, board[0][6], IMAGES_BBISHOP_PNG));
        board[0][8].placePiece(new Bishop(false, board[0][8], IMAGES_BBISHOP_PNG));
        board[9][1].placePiece(new Bishop(true, board[9][1], IMAGES_WBISHOP_PNG));
        board[9][3].placePiece(new Bishop(true, board[9][3], IMAGES_WBISHOP_PNG));
        board[9][6].placePiece(new Bishop(true, board[9][6], IMAGES_WBISHOP_PNG));
        board[9][8].placePiece(new Bishop(true, board[9][8], IMAGES_WBISHOP_PNG));

        // Place kings on board
        King bk = new King(false, board[0][4], IMAGES_BKING_PNG);
        King wk = new King(true, board[9][4], IMAGES_WKING_PNG);

        cmd = new CheckmateDetector(this, Wpieces, Bpieces, wk, bk);
    }

    @Override
    public void paintComponent(Graphics g) {
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                Square s = board[x][y];
                s.paintComponent(g);
            }
        }

        if (curPiece != null) {
            if ((curPiece.white() && whiteTurn) || (!curPiece.white() && !whiteTurn)) {
                final Image i = curPiece.getImage();
                g.drawImage(i, curX, curY, null);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        curX = e.getX();
        curY = e.getY();
        Square s = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));

        if (s.isOccupied()) {
            curPiece = s.getOccupyingPiece();
            if (!curPiece.white() && whiteTurn) {
                return;
            }
            if (curPiece.white() && !whiteTurn) {
                return;
            }
            s.setDisplay(false);
        }
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Square sq = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));

        if (curPiece != null) {
            if (!curPiece.white() && whiteTurn)
                return;
            if (curPiece.white() && !whiteTurn)
                return;

            List<Square> legalMoves = curPiece.getLegalMoves(this);
            movable = cmd.getAllowableSquares(whiteTurn);

            if (legalMoves.contains(sq) && movable.contains(sq)
                    && cmd.testMove(curPiece, sq)) {
                sq.setDisplay(true);
                curPiece.move(sq);
                cmd.update();

                if (cmd.blackCheckMated()) {
                    curPiece = null;
                    repaint();
                    this.removeMouseListener(this);
                    this.removeMouseMotionListener(this);
                    g.checkmateOccurred(0);
                } else if (cmd.whiteCheckMated()) {
                    curPiece = null;
                    repaint();
                    this.removeMouseListener(this);
                    this.removeMouseMotionListener(this);
                    g.checkmateOccurred(1);
                } else {
                    curPiece = null;
                    whiteTurn = !whiteTurn;
                    movable = cmd.getAllowableSquares(whiteTurn);
                }

            } else {
                curPiece.getPosition().setDisplay(true);
                curPiece = null;
            }
        }

        repaint();
    }

    public Square[][] getSquareArray() {
        return board;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        curX = e.getX() - 24;
        curY = e.getY() - 24;

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
