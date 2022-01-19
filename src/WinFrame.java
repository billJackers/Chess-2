import javax.swing.*;
import java.awt.*;

public class WinFrame {
    private JLabel winnerLabel;
    public WinFrame() {

    }

    // 1 for blue, 0 for draw, -1 for red
    public void makeWinFrame(int result) {
        JFrame winFrame = new JFrame();
        if (result == 1) winnerLabel = new JLabel("BLUE has won! Good job!");
        else if (result == -1) winnerLabel = new JLabel("RED has won! Good job!");
        else winnerLabel = new JLabel("The game has resulted in a DRAW.");
        winnerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        winnerLabel.setVerticalAlignment(SwingConstants.CENTER);
        winFrame.setLocationRelativeTo(null);
        winFrame.add(winnerLabel);
        winFrame.setVisible(true);
        winFrame.setSize(300, 300);
        winFrame.setBackground(Color.pink);
        winFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

}
