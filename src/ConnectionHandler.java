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

    private class Data {
        private final int[] moves;
        private final char moveType;
        public Data (String data) {
            this.moves = parseMove(data);  // and int array of positions. For example, [1, 2, 4, 5] this means that a piece on square(x=1, y=2) has taken square(x=4, y=5)
            this.moveType = data.split(" ")[4].charAt(0);  // whether the move is a typical move or an archer shot ('m' = normal move, 'a' = archer shot)
        }
        private int[] parseMove(String data) {
            String[] values = data.split(" ");
            int[] moves = new int[4];
            for (int positionIndex = 0; positionIndex < 4; positionIndex++) {
                moves[positionIndex] = Integer.parseInt(values[positionIndex]);
            }
            return moves;
        }
        public Square getInitialSquare () {
            return board.getSquare(moves[0], moves[1]);
        }
        public Square getFinalSquare () {
            return board.getSquare(moves[2], moves[3]);
        }
        public char getMoveType () {
            return moveType;
        }
    }

    public ConnectionHandler (Socket connection, Board board, Sides playerSide) {
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
        this.controller = board.getController();
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

    public void updateOpponentMove(Data data) {
        Square fromSquare = data.getInitialSquare();
        Square toSquare = data.getFinalSquare();
        switch (data.getMoveType()) {
            case 'm' -> controller.move(fromSquare, toSquare);
            case 'a' -> controller.shoot(fromSquare, toSquare);
        }
        board.unpause();
    }

    public void run() {
        String line = "";
        if (playerSide == controller.getCurrentTurn())
            System.out.println(playerSide + " is sending");
        while (!line.equals("end")) {

            System.out.println(playerSide + " is sending");  // connections only work if this line is run??  (perhaps weird stuff happening with java threads?)

            if (!(playerSide == controller.getCurrentTurn())) {  // if it is not our turn then we receive data
                System.out.println(playerSide + " is listening");
                try {
                    line = dataInput.readLine();
                    System.out.println("got data: " + line);
                    Data data = new Data(line);
                    updateOpponentMove(data);
                    System.out.println(playerSide + " is sending");

                } catch (IOException IOe) {
                    System.out.println(IOe.getMessage());
                }
            }
        }
    }
}
