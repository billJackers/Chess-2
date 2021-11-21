import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class StatsDisplay extends JPanel implements ActionListener {

    private final int HEADER_HEIGHT;
    private final int HEADER_WIDTH;
    private final Board board;
    private Image background;


    public StatsDisplay(Board board) {
        this.board = board;

        // dimensions of this JPanel
        HEADER_HEIGHT = board.getHeight() / 10;  // dimensions relative to game
        HEADER_WIDTH = board.getWidth();
        this.setPreferredSize(new Dimension(HEADER_WIDTH, HEADER_HEIGHT)); // dimensions based on the size of the board
        this.setMaximumSize(this.getPreferredSize());

        String backgroundPath = "images/display_background.png";
        try {
            BufferedImage bg = ImageIO.read(Objects.requireNonNull(getClass().getResource(backgroundPath)));
            background = bg.getScaledInstance(HEADER_WIDTH, HEADER_HEIGHT, Image.SCALE_DEFAULT); // scale the image based on game configurations
        } catch (IOException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);  // background color of stats
        //would probably draw stats down here

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

}
