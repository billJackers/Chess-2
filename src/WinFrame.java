import javax.swing.*;
import java.awt.*;

public class WinFrame {
    private JFrame winFrame;
    private JLabel winLabel;

    public WinFrame(Sides side) {
        winFrame = new JFrame("Game over");
        winFrame.setLayout(new BorderLayout(20, 20));
        winFrame.setSize(200, 200);
        this.winLabel = new JLabel(side + " has won!");
        winFrame.add(winLabel);
        winFrame.setVisible(true);
        winFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

}
