import java.awt.Color;
import java.awt.Graphics;

import javax.swing.*;

@SuppressWarnings("serial")
public class Square extends JComponent {
    private Board b;

    private final boolean isDark;
    private Piece occupyingPiece;
    private boolean disPiece;

    private int x;
    private int y;

    public Square(Board b, boolean isDark, int xCoordinate, int yCoordinate) {
        this.b = b;
        this.isDark = isDark;
        this.disPiece = true;
        this.x = xCoordinate;
        this.y = yCoordinate;
    }

    public boolean isDark() {
        return this.isDark;
    }

    public Piece getOccupyingPiece() {
        return occupyingPiece;
    }

    public boolean isOccupied() {
        return (this.occupyingPiece != null);
    }

    public int getXCoordinate() {
        return this.x;
    }

    public int getYCoordinate() {
        return this.y;
    }

    public void setDisplay(boolean v) {
        this.disPiece = v;
    }

    public void placePiece(Piece p) {
        this.occupyingPiece = p;
        p.setPosition(this);
    }

    public Piece removePiece() {
        Piece p = this.occupyingPiece;
        this.occupyingPiece = null;
        return p;
    }

    public void capturePiece(Piece p) {
        Piece capturedPiece = getOccupyingPiece();
        if (p.white()) b.Wpieces.remove(capturedPiece);
        else b.Bpieces.remove(capturedPiece);
        this.occupyingPiece = p;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (this.isDark) {
            g.setColor(new Color(56, 142, 19));
        } else {
            g.setColor(new Color(237, 231, 201));
        }

        g.fillRect(this.getXCoordinate(), this.getYCoordinate(), this.getWidth(), this.getHeight());

        if(occupyingPiece != null && disPiece) {
            occupyingPiece.draw(g);
        }

    }

}
