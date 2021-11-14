import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;

public class GameWindow {

    public GameWindow() {

        JFrame gameWindow = new JFrame("Chess 2");
        gameWindow.setLocationRelativeTo(null);
        Board board = new Board(); // our board, also our gameloop

        gameWindow.add(board); // create the JPanel to draw on. This also initializes the game loop
        gameWindow.setSize(board.getPreferredSize()); // Set the size of the window based on the size of the board

        gameWindow.setResizable(false); // don't allow the user to resize the window
        gameWindow.pack(); // pack() should be called after setResizable() to avoid issues on some platforms

        gameWindow.setVisible(true);
        gameWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }
}
