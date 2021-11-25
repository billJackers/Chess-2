import java.awt.Color;
import java.awt.Graphics;

import javax.swing.*;

public class Square extends JComponent {

    enum ActionStates {  // If the square currently has a unique state (ActionStates != none), then these will effect the background color instead of Sides
        NONE,
        PLAYER_SELECTED,
        LEGAL_MOVE,
        ARCHER_SHOT,
        HIGHLIGHTED
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
    public int getRank() {
        return rank;
    }
    public int getFile() {
        return file;
    }
    public int getX() {
        return this.rank * size;
    } // actual x position on window
    public int getY() { return this.file * size; } // actual y position on window

    public boolean hasPiece() {
        return this.getPiece() != null;
    }

    public Piece setPiece(Piece newPiece) {  // set a piece and return the old piece
        Piece oldPiece = this.piece;
        this.piece = newPiece;
        this.piece.setParentSquare(this);  // set the parent square of the piece
        return oldPiece;
    }
    public void setState(ActionStates state) { this.state = state; }
    public ActionStates getState() {
        return this.state;
    }
    public void clearPiece() { this.piece = null; }

    public void draw(Graphics g) {  // draws the background square and then the piece (if piece exists)
        if (this.state == ActionStates.NONE) {
            switch (this.side) {  // draw the background square color
                case BLUE -> g.setColor(new Color(225, 209, 163));
                case RED -> g.setColor(new Color(196, 159, 117));
            }
        }
        else {
            switch (this.state) {  // If the tile currently has a unique state (ActionStates != none), then draw the state instead
                case PLAYER_SELECTED -> g.setColor(new Color(255, 229, 79));
                case LEGAL_MOVE -> g.setColor(new Color(255, 205, 79));
                case ARCHER_SHOT -> g.setColor(new Color(255, 93, 23));
                case HIGHLIGHTED -> g.setColor(new Color(200, 100, 100));
            }
        }
        // System.out.println(this.getX() + " " + this.getY());
        int borderOffset = 2;
        int positionOffset = borderOffset/2;
        g.fillRect(this.getX()+positionOffset, this.getY()+positionOffset, size-borderOffset, size-borderOffset);

        if(this.piece != null) {  // if there's a piece, draw the piece over the background
            piece.draw(g, this.getX(), this.getY());
        }

    }

}
