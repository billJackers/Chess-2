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
        JFrame gameWindow = new JFrame("Giga Chess (Client)");
        gameWindow.setLocationRelativeTo(null);

        Socket connectionToServer = getClientSocket();

        if (connectionToServer == null) {  // client didnt connect
            DisplayConnectionFailed failed = new DisplayConnectionFailed();
            gameWindow.add(failed);
            gameWindow.setPreferredSize(new Dimension(190, 100)); // Set the size of the window based on the size of the board
            failed.repaint();

        } else {  // client connected to server
            String FEN = "rbbrqkrbbr/socnggncos/pppppppppp/X/X/X/X/PPPPPPPPPP/SOCNGGNCOS/RBBRQKRBBR";
            Board board = new Board(new Settings("Gigachess", true, "Original", false), FEN);
            board.flipBoard();
            board.pause();  // begin as paused since it is the server's turn first
            board.getController().setClocks(0, 10, 0, 5);  // setting the clocks statically
            StatsDisplay stats = new StatsDisplay(board, 0, 10, 0);  // stats displayer panel

            ConnectionHandler connectionHandler = new ConnectionHandler(connectionToServer, board, Sides.RED);  // the client is the RED side

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
            System.out.println(i.getMessage());
        }
        return null;
    }
}
