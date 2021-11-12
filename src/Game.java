import javax.swing.*;

public class Game {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                StartMenu frame = new StartMenu();
            }
        });
    }
}
