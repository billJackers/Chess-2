import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ConnectionHandler extends Thread {

    // multiplayer stuff
    private final Socket connection;
    private BufferedReader dataInput;
    private BufferedWriter dataOutput;

    private final Board board;
    private final PlayerController controller;
    private final Sides playerSide;

    public ConnectionHandler (Socket connection, PlayerController controller, Sides playerSide, Board board) {
        // initializing multiplayer stuff
        this.connection = connection;
        try {
            dataInput = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            dataOutput = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
        } catch (IOException IOe) {
            dataInput = null;
            dataOutput = null;
        }
        this.board = board;
        // the PlayerController and the player's side
        this.controller = controller;
        controller.setConnectionHandler(this);
        this.playerSide = playerSide;
        start();
    }

    public void send(String data) {
        try {
            dataOutput.write(data + "\r\n");  // write thing then flush output
            dataOutput.flush();
        } catch (IOException IOe) {
            System.out.println("error sending data");
        }

    }

    public int[] parseMove(String data) {
        String[] values = data.split(" ");
        int[] moves = new int[4];
        for (int positionIndex = 0; positionIndex < values.length; positionIndex++) {
            moves[positionIndex] = Integer.parseInt(values[positionIndex]);
        }
        return moves;
    }

    public void updateOpponentMove(int[] moves) {
        Square fromSquare = board.getSquareClicked(moves[0], moves[1]);
        Square toSquare = board.getSquareClicked(moves[2], moves[3]);
        controller.move(fromSquare, toSquare);
        controller.swapTurns();
    }

    public void run() {
        String line = "";
        while (!line.equals("end")) {
            if (!(playerSide == controller.getCurrentTurn())) {  // if it is not our turn then we receive data
                System.out.println(playerSide + " is listening");
                try {
                    line = dataInput.readLine();
                    updateOpponentMove(parseMove(line));
                    controller.swapTurns();

                } catch (IOException IOe) {
                    System.out.println(IOe);
                }
            }
        }
    }
}
