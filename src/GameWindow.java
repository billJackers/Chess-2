import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

public class GameWindow {

    private PlayerController controller;

    public GameWindow(Settings settings) {

        JFrame gameWindow = new JFrame("Giga Chess");
        gameWindow.setLocationRelativeTo(null);

        String FEN = "rbbrqkrbbr/socnggncos/pppppppppp/X/X/X/X/PPPPPPPPPP/SOCNGGNCOS/RBBRQKRBBR";

        this.controller = new PlayerController(settings);  // PlayerController to handle mouse input
        Board board = new Board(settings, controller, FEN);  // creates the board JPanel to draw on
        StatsDisplay stats = new StatsDisplay(board, settings);  // stats displayer JPanel
        SideDisplay side = new SideDisplay(board, settings);

        // adding JPanels to JFrame
        gameWindow.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        gameWindow.add(stats, constraints);
        constraints.gridx = 0;
        constraints.gridy = 1;
        gameWindow.add(board, constraints);
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridheight = 2;
        gameWindow.add(side, constraints);

        gameWindow.setSize(gameWindow.getPreferredSize());
        gameWindow.setLocationRelativeTo(null);
        gameWindow.setResizable(false); // don't allow the user to resize the window
        gameWindow.pack(); // pack() should be called after setResizable() to avoid issues on some platforms
        gameWindow.setVisible(true);
        gameWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

}