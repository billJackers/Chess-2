import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ConnectionHandler extends Thread {

    // multiplayer stuff
    private final Socket connection;
    private BufferedReader dataInput;
    private BufferedWriter dataOutput;

    private final PlayerController controller;
    private final Sides playerSide;

    public ConnectionHandler (Socket connection, PlayerController controller, Sides playerSide) {
        // initializing multiplayer stuff
        this.connection = connection;
        try {
            dataInput = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            dataOutput = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
        } catch (IOException IOe) {
            dataInput = null;
            dataOutput = null;
        }
        // the PlayerController and the player's side
        this.controller = controller;
        controller.setConnectionHandler(this);
        this.playerSide = playerSide;
        start();
    }

    public void send() {
        try {
            System.out.println("sending");
            dataOutput.write("hello\r\n");  // write thing then flush output
            dataOutput.flush();
        } catch (IOException IOe) {
            System.out.println("burh");
        }

    }

    public void run() {
        String line = "";
        while (!line.equals("end")) {
            if (!(playerSide == controller.getCurrentTurn())) {  // if it is not our turn then we receive data
                try {
                    System.out.println(playerSide.toString() + " attempting to read");
                    line = dataInput.readLine();
                    System.out.println(line + " received!");

                } catch (IOException IOe) {
                    System.out.println(IOe);
                }
            }
        }
        System.out.println("ran");
    }
}
