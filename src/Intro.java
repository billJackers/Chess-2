import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Intro implements ActionListener {

    private JFrame introFrame;
    private Timer timer;
    private int secondsElapsed;

    private JPanel textPanel;

    private final int WIDTH = 500;
    private final int HEIGHT = 500;

    public Intro() {
        // Timer for disposing the frame after a certain number of seconds has elapsed
        this.timer = new Timer(1000, this);
        this.secondsElapsed = 0;

        timer.start();

        initializeFrame();
    }

    public void initializeFrame() {
        // Initialize JFrame
        introFrame = new JFrame("Giga chess");
        introFrame.setLocationRelativeTo(null);
        introFrame.setSize(WIDTH, HEIGHT);
        introFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        introFrame.setResizable(false);
        introFrame.setVisible(true);

        // Initialize JPanels
        JPanel skipPanel = new JPanel();
        skipPanel.setSize(WIDTH, 30);
        skipPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        skipPanel.setBackground(Color.black);
        JButton skipBtn = new JButton("Skip");
        skipBtn.addActionListener(e -> {
            timer.stop();
            introFrame.dispose();
            new StartMenu();
        });
        skipBtn.setForeground(Color.white);
        skipBtn.setBackground(Color.black);
        skipPanel.add(skipBtn);

        textPanel = new JPanel();
        textPanel.setBackground(Color.black);
        textPanel.setSize(WIDTH, 200);

        JLabel titleLbl = new JLabel("");
        titleLbl.setForeground(Color.white);
        titleLbl.setFont(new Font("Sans Serif", Font.BOLD, 40));
        textPanel.add(titleLbl);

        // GIGA CHESS pops up after 2 seconds, while "BROUGHT TO YOU BY" pops up in 4 seconds, and "THE NERDS" pops up in 6 seconds
        ActionListener taskPerformer = e -> {
            titleLbl.setText("<html><body>GIGA CHESS</body></html>");
            Sound boom = new Sound("src/sounds/vine-boom.wav");
            boom.play();
        };
        Timer textTimer = new Timer(2000, taskPerformer);
        textTimer.setRepeats(false);
        textTimer.start();

        taskPerformer = e -> {
            titleLbl.setText("<html><body>GIGA CHESS<br>BROUGHT TO YOU BY:</body></html>");
            Sound boom = new Sound("src/sounds/vine-boom.wav");
            boom.play();
        };
        textTimer = new Timer(4000, taskPerformer);
        textTimer.setRepeats(false);
        textTimer.start();

        taskPerformer = e -> {
            titleLbl.setText("<html><body>GIGA CHESS<br>BROUGHT TO YOU BY:<br>THE NERDS</body></html>");
            Sound boom = new Sound("src/sounds/vine-boom.wav");
            boom.play();
        };
        textTimer = new Timer(6000, taskPerformer);
        textTimer.setRepeats(false);
        textTimer.start();

        textPanel.setLayout(new GridBagLayout());

        introFrame.add(skipPanel);
        introFrame.add(textPanel);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        secondsElapsed++;
        System.out.println(secondsElapsed);
        // Intro closes after 10 seconds
        if (secondsElapsed == 10) {
            timer.stop();
            introFrame.dispose();
            new StartMenu();
        }
    }
}
