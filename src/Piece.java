import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import javax.imageio.ImageIO;

public abstract class Piece {

    protected static Board board;
    public static void setBoard(Board board) {  // the board should only be set once in the board class constructor
        Piece.board = board;
    }

    public enum Sides {
        BLUE,
        RED
    }

    protected Sides side;
    protected int size;
    protected Image image;
    protected Square parentSquare;

    public Piece(Sides side, int size, Square initialSquare) {
        this.side = side;
        this.size = size;
        this.parentSquare = initialSquare;
    }

    protected Image getImageByFile(String file) {  // get our image based on a file name
        Image sprite = null;
        try {
            sprite = ImageIO.read(Objects.requireNonNull(getClass().getResource(file)));
            sprite = sprite.getScaledInstance(size, size, Image.SCALE_DEFAULT); // scale the image based on game configurations
        } catch (IOException e) {
            System.out.println("File not found: " + e.getMessage());
        }
        return sprite;
    }

    public void setParentSquare(Square newParent) {
        this.parentSquare = newParent;
    }

    public void draw(Graphics g, int x, int y) {
        g.drawImage(this.image, x, y, null);
    }  // draws image at location

    public abstract List<Square> getLegalMoves();

    protected boolean canCapture(Square target) {
        return target.getPiece() == null || this.side != target.getPiece().side;
    }

    // checks if the square is on either side edge
    // left edge is -1, right edge is 1, neither is 0
    public int onEdge(Square target) {
        if (target.getRank() == 0) return -1;
        if (target.getRank() == 9) return 1;
        return 0;
    }

    // checks if the square is on the top or bottom edge
    // top edge is -1, bottom edge is 1, neither is 0
    public int onEnd(Square target) {
        if (target.getFile() == 0) return -1;
        if (target.getFile() == 9) return 1;
        return 0;
    }
    // public boolean move(Square moveTo);
}
