import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.Socket;

public class GameClient {

    private static class DisplayConnectionFailed extends JPanel {  // for displaying initial "waiting" text on screen
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawString("Unable to connect to server", 10, 35);
        }
    }

    private final String LOCAL_ADDR = "127.0.0.1";
    private final int PORT = 42069;
    // https://www.geeksforgeeks.org/socket-programming-in-java/

    public GameClient () {
        JFrame gameWindow = new JFrame("Chess 2 (Client)");
        gameWindow.setLocationRelativeTo(null);

        Socket connectionToServer = getClientSocket();
        if (connectionToServer == null) {  // client didnt connect
            DisplayConnectionFailed failed = new DisplayConnectionFailed();
            gameWindow.add(failed);
            gameWindow.setPreferredSize(new Dimension(190, 100)); // Set the size of the window based on the size of the board
            failed.repaint();
        } else {  // client connected to server
            // our board, also our gameloop
            Board board = new Board(connectionToServer, Sides.RED);  // the client is the RED side
            StatsDisplay stats = new StatsDisplay(board);  // stats displayer panel

            gameWindow.add(stats, BorderLayout.NORTH); // creates the stats JPanel to display the games statistics above the board panel
            gameWindow.add(board, BorderLayout.SOUTH); // creates the board JPanel to draw on. This also initializes the game loop

            gameWindow.setSize(board.getPreferredSize()); // Set the size of the window based on the size of the board
        }

        gameWindow.setResizable(false); // don't allow the user to resize the window
        gameWindow.pack(); // pack() should be called after setResizable() to avoid issues on some platforms

        gameWindow.setVisible(true);
        gameWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private Socket getClientSocket() {
        Socket socket;
        // starts server and waits for a connection
        try {
            socket = new Socket(LOCAL_ADDR, PORT);
            System.out.println("Connected");
            return socket;
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
        return null;
    }
}
