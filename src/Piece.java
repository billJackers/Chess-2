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
    protected Square curSquare;

    public Piece(Sides side, int size, Square initSquare) {
        this.side = side;
        this.size = size;
        this.curSquare = initSquare;
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

    public void draw(Graphics g, int x, int y) {
        g.drawImage(this.image, x, y, null);
    }  // draws image at location

    public abstract List<Square> getLegalMoves();
    // public boolean move(Square moveTo);
}
