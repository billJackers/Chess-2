import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;

public class GameWindow {

    private JFrame gameWindow;
    private Board board;

    private Timer timer;
    public Clock blackClock;
    public Clock whiteClock;

    public GameWindow() {

        gameWindow = new JFrame("Chess 2");
        gameWindow.setLocationRelativeTo(null);
        gameWindow.setLayout(new BorderLayout(20, 20));

        this.board = new Board(this);

        gameWindow.add(board, BorderLayout.CENTER);
        gameWindow.setSize(board.getPreferredSize());
        gameWindow.setResizable(false);

        gameWindow.setVisible(true);
        gameWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }

    public void checkmateOccurred(int c) {
        // To be implemented
    }
}
