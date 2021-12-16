import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

public class GameWindow {

    public GameWindow(Settings settings) {

        JFrame gameWindow = new JFrame("Giga Chess");
        gameWindow.setLocationRelativeTo(null);

        String FEN = "rbbrqkrbbr/socnggncos/pppppppppp/X/X/X/X/PPPPPPPPPP/SOCNGGNCOS/RBBRQKRBBR";

        PlayerController controller = new PlayerController(settings);  // PlayerController to handle mouse input
        Board board = new Board(settings, controller, FEN);  // creates the board JPanel to draw on
        StatsDisplay stats = new StatsDisplay(board, settings);  // stats displayer JPanel

        // adding JPanels to JFrame
        gameWindow.add(stats, BorderLayout.NORTH);
        gameWindow.add(board, BorderLayout.SOUTH);

        gameWindow.setSize(board.getPreferredSize()); // Set the size of the window based on the size of the board
        gameWindow.setResizable(false); // don't allow the user to resize the window
        gameWindow.pack(); // pack() should be called after setResizable() to avoid issues on some platforms
        gameWindow.setVisible(true);
        gameWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

}