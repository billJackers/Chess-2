import java.awt.BorderLayout;
import javax.swing.*;

public class GameWindow {

    private JLabel turn = new JLabel("Blue to move");

    public GameWindow() {

        JFrame gameWindow = new JFrame("Chess 2");
        gameWindow.setLocationRelativeTo(null);

        Board board = new Board(); // our board, also our gameloop
        StatsDisplay stats = new StatsDisplay(board);  // stats displayer panel


        stats.add(turn);

        gameWindow.add(stats, BorderLayout.NORTH); // creates the stats JPanel to display the games statistics above the board panel
        gameWindow.add(board, BorderLayout.SOUTH); // creates the board JPanel to draw on. This also initializes the game loop

        gameWindow.setSize(board.getPreferredSize()); // Set the size of the window based on the size of the board

        gameWindow.setResizable(false); // don't allow the user to resize the window
        gameWindow.pack(); // pack() should be called after setResizable() to avoid issues on some platforms

        gameWindow.setVisible(true);
        gameWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }
}
