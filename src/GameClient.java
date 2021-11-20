import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.Socket;

public class GameClient {

    private final String LOCAL_ADDR = "127.0.0.1";
    private final int PORT = 42069;
    // https://www.geeksforgeeks.org/socket-programming-in-java/

    public GameClient () {
        JFrame gameWindow = new JFrame("Chess 2 (Client)");
        gameWindow.setLocationRelativeTo(null);

        Socket connectionToServer = getClientSocket();
        Board board = new Board(connectionToServer); // our board, also our gameloop
        StatsDisplay stats = new StatsDisplay(board);  // stats displayer panel

        gameWindow.add(stats, BorderLayout.NORTH); // creates the stats JPanel to display the games statistics above the board panel
        gameWindow.add(board, BorderLayout.SOUTH); // creates the board JPanel to draw on. This also initializes the game loop

        gameWindow.setSize(board.getPreferredSize()); // Set the size of the window based on the size of the board

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
