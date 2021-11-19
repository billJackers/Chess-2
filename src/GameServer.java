import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer {

    private final int PORT = 42069;
    // https://www.geeksforgeeks.org/socket-programming-in-java/

    public GameServer () {
        JFrame gameWindow = new JFrame("Chess 2");
        gameWindow.setLocationRelativeTo(null);

        Socket connectionToClient = getServerSocket();
        Board board = new Board(); // our board, also our gameloop
        StatsDisplay stats = new StatsDisplay(board);  // stats displayer panel

        gameWindow.add(stats, BorderLayout.NORTH); // creates the stats JPanel to display the games statistics above the board panel
        gameWindow.add(board, BorderLayout.SOUTH); // creates the board JPanel to draw on. This also initializes the game loop

        gameWindow.setSize(board.getPreferredSize()); // Set the size of the window based on the size of the board

        gameWindow.setResizable(false); // don't allow the user to resize the window
        gameWindow.pack(); // pack() should be called after setResizable() to avoid issues on some platforms

        gameWindow.setVisible(true);
        gameWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private Socket getServerSocket() {
        Socket socket;
        ServerSocket server;

        // starts server and waits for a connection
        try {
            server = new ServerSocket(PORT);
            System.out.println("Server started");
            System.out.println("Waiting for a client ...");
            socket = server.accept();  // waits until client accepts
            System.out.println("Client accepted");
            return socket;
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
        return null;
    }
}
