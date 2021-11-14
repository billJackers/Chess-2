import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.LinkedList;
import java.util.Objects;

public class Pawn extends Piece {

    private static final String IMAGES_PAWN_BLUE = "images/wpawn.png";
    private static final String IMAGES_PAWN_RED = "images/bpawn.png";

    public Pawn(Sides side, int size) {
        super(side, size);
        switch (side) {
            case BLUE -> this.image = getImageByFile(IMAGES_PAWN_BLUE);
            case RED -> this.image = getImageByFile(IMAGES_PAWN_RED);
        }
    }

    public List<Square> getLegalMoves(Board b) {
        return null;
    }

}
