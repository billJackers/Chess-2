import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class GameWindow {

    public Clock blueClock;
    public Clock redClock;

    private Timer timer;

    private Board board;
    private Sides turn;

    public GameWindow(int hours, int minutes, int seconds) {

        JFrame gameWindow = new JFrame("Chess 2");
        gameWindow.setLocationRelativeTo(null);

        board = new Board(); // our board, also our gameloop

        redClock = new Clock(hours, minutes, seconds);
        blueClock = new Clock(hours, minutes, seconds);

        turn = Sides.BLUE;

        //StatsDisplay stats = new StatsDisplay(board);  // stats displayer panel

        JPanel statsPanel = statsDisplayPanel(hours, minutes, seconds);
        statsPanel.setSize(statsPanel.getPreferredSize());
        gameWindow.add(statsPanel, BorderLayout.NORTH);


        //gameWindow.add(stats, BorderLayout.NORTH); // creates the stats JPanel to display the games statistics above the board panel
        gameWindow.add(board, BorderLayout.SOUTH); // creates the board JPanel to draw on. This also initializes the game loop

        gameWindow.setSize(board.getPreferredSize()); // Set the size of the window based on the size of the board

        gameWindow.setResizable(false); // don't allow the user to resize the window
        gameWindow.pack(); // pack() should be called after setResizable() to avoid issues on some platforms

        gameWindow.setVisible(true);
        gameWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }

    private JPanel statsDisplayPanel(final int h, final int m, final int s) {
        JPanel stats = new JPanel();
        stats.setLayout(new GridLayout(3, 3, 0, 5));

        JLabel turnLabel = new JLabel(turn + " to move");
        turnLabel.setHorizontalAlignment(JLabel.CENTER);
        turnLabel.setVerticalAlignment(JLabel.CENTER);

        JLabel b = new JLabel("Blue");
        JLabel r = new JLabel("Red");

        r.setHorizontalAlignment(JLabel.CENTER);
        r.setVerticalAlignment(JLabel.CENTER);
        b.setHorizontalAlignment(JLabel.CENTER);
        b.setVerticalAlignment(JLabel.CENTER);

        r.setSize(r.getMinimumSize());
        b.setSize(b.getMinimumSize());

        stats.add(r);
        stats.add(b);

        // Clocks
        final JLabel bTime = new JLabel(blueClock.getTime());
        final JLabel rTime = new JLabel(redClock.getTime());

        bTime.setHorizontalAlignment(JLabel.CENTER);
        bTime.setVerticalAlignment(JLabel.CENTER);
        rTime.setHorizontalAlignment(JLabel.CENTER);
        rTime.setVerticalAlignment(JLabel.CENTER);

        if (!(h == 0 && m == 0 && s == 0)) {
            timer = new Timer(1000, null);
            timer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (turn.equals("Blue")) {
                        blueClock.decrement();
                        bTime.setText(blueClock.getTime());

                        if (blueClock.outOfTime()) {
                            timer.stop();
                            System.out.println("Red has won on time!");
                        }
                    } else {
                        redClock.decrement();
                        rTime.setText(redClock.getTime());
                        if (redClock.outOfTime()) {
                            timer.stop();
                            System.out.println("Blue has won on time!");
                        }
                    }
                    timer.start();
                    System.out.println(bTime.getText());
                }
            });
        } else {
            bTime.setText("Untimed game");
            rTime.setText("Untimed game");
        }

        stats.add(bTime);
        stats.add(rTime);

        stats.add(turnLabel);

        stats.setPreferredSize(stats.getMinimumSize());

        return stats;
    }

}