import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer extends Thread {

    private static class DisplayConnecting extends JPanel {  // for displaying initial "waiting" text on screen
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawString("Waiting for client...", 10, 35);
        }
    }

    private final int PORT = 42069;
    // https://www.geeksforgeeks.org/socket-programming-in-java/

    public GameServer () {
        start();
    }

    public void run() {  // functions are run in a separate thread currently for testing purposes
        JFrame gameWindow = new JFrame("Chess 2 (Server)");
        gameWindow.setLocationRelativeTo(null);

        //  Initial waiting text on screen
        DisplayConnecting waitingScreen = new DisplayConnecting();
        gameWindow.add(waitingScreen);
        gameWindow.setSize(100, 100);
        waitingScreen.repaint();

        // setting settings
        gameWindow.setResizable(false); // don't allow the user to resize the window
        gameWindow.setVisible(true);
        gameWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Try to establish a connection with the client
        Socket connectionToClient = getServerSocket();
        // our board, also our gameloop
        Board board = new Board(connectionToClient, Sides.BLUE);  // the Server is the BLUE side
        StatsDisplay stats = new StatsDisplay(board, 0, 10, 0);  // stats displayer panel

        gameWindow.remove(waitingScreen);  // remove the "waiting for client" panel, as we have connected with the client

        gameWindow.add(stats, BorderLayout.NORTH); // creates the stats JPanel to display the games statistics above the board panel
        gameWindow.add(board, BorderLayout.SOUTH); // creates the board JPanel to draw on. This also initializes the game loop

        gameWindow.setSize(board.getPreferredSize()); // Set the size of the window based on the size of the board

        gameWindow.pack(); // pack() should be called after setResizable() to avoid issues on some platforms
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
