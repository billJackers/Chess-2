import java.awt.Color;
import java.awt.Graphics;

import javax.swing.*;



@SuppressWarnings("serial")
public class Square extends JComponent {

    enum Sides {
        BLUE,
        RED
    }

    private Piece piece;
    private final int rank;
    private final int file;
    private final int size;
    private final Sides side;

    public Square(int rank, int file, int size) {
        this.rank = rank; // "x"
        this.file = file; // "y"
        this.size = size; // width/height
        if (this.rank % 2 == 0 && this.file % 2 == 0 || this.rank % 2 == 1 && this.file % 2 == 1)  // colors the background of the board
            this.side = Sides.BLUE;
        else
            this.side = Sides.RED;
    }

    // getters
    public Piece getPiece() {
        return this.piece;
    }
    public int getX() {
        return this.rank * size;
    } // actual x position on window
    public int getY() {
        return this.file * size;
    } // actual y position on window

    public Piece setPiece(Piece newPiece) {  // set a piece and return the old piece
        Piece oldPiece = this.piece;
        this.piece = newPiece;
        return oldPiece;
    }

    public boolean isOccupied() {
        return (this.piece != null);
    }

    public void draw(Graphics g) {  // draws the background square and the piece (if piece exists)

        switch (this.side) {  // draw the background square color
            case BLUE -> g.setColor(new Color(102, 173, 255));
            case RED -> g.setColor(new Color(181, 74, 74));
        }
        // System.out.println(this.getX() + " " + this.getY());
        g.fillRect(this.getX(), this.getY(), size, size);

        if(this.piece != null) {  // if there's a piece, draw the piece over the background
            piece.draw(g, this.getX(), this.getY());
        }

    }

}
