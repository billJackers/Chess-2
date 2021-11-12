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

    public void checkmateOccurred (int c) {
        if (c == 0) {
            if (timer != null) timer.stop();
            int n = JOptionPane.showConfirmDialog(
                    gameWindow,
                    "White wins by checkmate! Set up a new game? \n",
                    "White wins!",
                    JOptionPane.YES_NO_OPTION);

            if (n == JOptionPane.YES_OPTION) {
                SwingUtilities.invokeLater(new StartMenu());
                gameWindow.dispose();
            }
        } else {
            if (timer != null) timer.stop();
            int n = JOptionPane.showConfirmDialog(
                    gameWindow,
                    "Black wins by checkmate! Set up a new game? \n" +
                            "Choosing \"No\" lets you look at the final situation.",
                    "Black wins!",
                    JOptionPane.YES_NO_OPTION);

            if (n == JOptionPane.YES_OPTION) {
                SwingUtilities.invokeLater(new StartMenu());
                gameWindow.dispose();
            }
        }
    }
}
