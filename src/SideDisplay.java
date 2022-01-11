import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SideDisplay extends JPanel {

    private final int HEIGHT;
    private final int WIDTH;
    private final Board board;
    private Settings settings;

    public SideDisplay(Board board, Settings settings) {

        this.board = board;
        this.settings = settings;

        this.HEIGHT = board.getHeight() + board.getHeight() / 10;
        this.WIDTH = board.getWidth() / 3;

        this.setPreferredSize(new Dimension(WIDTH, HEIGHT)); // dimensions based on the size of the board
        this.setMaximumSize(this.getPreferredSize());

        JButton lolBtn = new JButton("lol");
        lolBtn.addActionListener(e -> {
            Sound lol = new Sound("src/sounds/lol.wav");
            lol.play();
        });

        this.add(lolBtn);

        this.setVisible(true);

    }

    public int getHeight() {
        return HEIGHT;
    }

    public int getWidth() {
        return WIDTH;
    }
}
