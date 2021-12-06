import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

public class GameWindow {

    public GameWindow(int hours, int minutes, int seconds, int increment, String variant, boolean highlightsOn) {

        JFrame gameWindow = new JFrame("Giga Chess");
        gameWindow.setLocationRelativeTo(null);

        Board board = new Board(variant, highlightsOn); // our board, also our gameloop

        board.getController().setClocks(hours, minutes, seconds, increment);

        StatsDisplay stats = new StatsDisplay(board, hours, minutes, seconds);  // stats displayer panel
        gameWindow.add(stats, BorderLayout.NORTH);

        //gameWindow.add(stats, BorderLayout.NORTH); // creates the stats JPanel to display the games statistics above the board panel
        gameWindow.add(board, BorderLayout.SOUTH); // creates the board JPanel to draw on. This also initializes the game loop

        gameWindow.setSize(board.getPreferredSize()); // Set the size of the window based on the size of the board

        gameWindow.setResizable(false); // don't allow the user to resize the window
        gameWindow.pack(); // pack() should be called after setResizable() to avoid issues on some platforms


        gameWindow.setVisible(true);
        gameWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

}