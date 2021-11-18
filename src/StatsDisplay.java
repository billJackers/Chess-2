import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StatsDisplay extends JPanel {

    private static final int HEADER_HEIGHT = 70;
    private final Board board;

    public StatsDisplay(Board board) {
        this.board = board;

        // dimensions of this JPanel
        this.setPreferredSize(new Dimension(board.getWidth(), HEADER_HEIGHT)); // dimensions based on the size of the board
        this.setMaximumSize(this.getPreferredSize());

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, board.getWidth(), HEADER_HEIGHT);  // background color of stats
        //would probably draw stats down here

    }
}
