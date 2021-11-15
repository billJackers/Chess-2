import java.awt.Color;
import java.awt.Graphics;

import javax.swing.*;



public class Square extends JComponent {

    private enum Sides {  // Dictates the background color of a Square
        BLUE,
        RED
    }

    enum ActionStates {
        NONE,
        PLAYER_SELECTED,
        LEGAL_MOVE
    }

    private Piece piece;
    private final int rank;
    private final int file;
    private final int size;
    private final Sides side;
    private ActionStates state;

    public Square(int rank, int file, int size) {
        this.rank = rank; // "x"
        this.file = file; // "y"
        this.size = size; // width/height
        if (this.rank % 2 == 0 && this.file % 2 == 0 || this.rank % 2 == 1 && this.file % 2 == 1)  // colors the background of the board
            this.side = Sides.BLUE;
        else
            this.side = Sides.RED;
        this.state = ActionStates.NONE;  // changes the background color of the board based on game Events
    }

    // getters
    public Piece getPiece() {
        return this.piece;
    }
    public int getX() {
        return this.rank * size;
    } // actual x position on window
    public int getY() { return this.file * size; } // actual y position on window

    public int getRank() {
        return rank;
    }

    public int getFile() {
        return file;
    }

    public boolean isOccupied() {
        return this.getPiece() == null;
    }

    public Piece setPiece(Piece newPiece) {  // set a piece and return the old piece
        Piece oldPiece = this.piece;
        this.piece = newPiece;
        return oldPiece;
    }
    public void setState(ActionStates state) { this.state = state; }
    public void clearPiece() { this.piece = null; }

    public void draw(Graphics g) {  // draws the background square and the piece (if piece exists)
        if (this.state == ActionStates.NONE) {  // finding the rect's color
            switch (this.side) {  // draw the background square color
                case BLUE -> g.setColor(new Color(225, 209, 163));
                case RED -> g.setColor(new Color(196, 159, 117));
            }
        }
        else {
            switch (this.state) {  // If the tile currently has a unique state (ActionStates != none), then draw the state instead
                case PLAYER_SELECTED -> g.setColor(new Color(255, 229, 79));
                case LEGAL_MOVE -> g.setColor(new Color(255, 205, 79));
            }
        }
        // System.out.println(this.getX() + " " + this.getY());
        g.fillRect(this.getX(), this.getY(), size, size);

        if(this.piece != null) {  // if there's a piece, draw the piece over the background
            piece.draw(g, this.getX(), this.getY());
        }

    }

}
