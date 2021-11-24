import javax.swing.*;
import java.awt.*;

public class WinFrame {

    public WinFrame() {

    }

    public void makeWinFrame(Sides winner) {
        JFrame winFrame = new JFrame();
        JLabel winnerLabel = new JLabel(winner + " has won!!! Well played.");
        winnerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        winnerLabel.setVerticalAlignment(SwingConstants.CENTER);
        winFrame.add(winnerLabel);
        winFrame.setVisible(true);
        winFrame.setSize(300, 300);
        winFrame.setBackground(Color.pink);
        winFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

}
