import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class Intro implements ActionListener {

    private JFrame introFrame;
    private Timer timer;
    private int secondsElapsed;

    private JPanel textPanel;

    private final int WIDTH = 500;
    private final int HEIGHT = 500;

    private static boolean skipped = false;

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
        introFrame.setLocationRelativeTo(null);

        // Initialize JPanels
        JPanel skipPanel = new JPanel();
        skipPanel.setSize(WIDTH, 30);
        skipPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        skipPanel.setBackground(Color.black);
        JButton skipBtn = new JButton("Skip");
        skipBtn.addActionListener(e -> {
            timer.stop();
            introFrame.dispose();
            skipped = true;
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
        titleLbl.setVisible(false);

        // Gif
        JLabel gifLabel = new JLabel("");
        textPanel.add(gifLabel);

        // GIGA CHESS pops up after 2 seconds, while "BROUGHT TO YOU BY" pops up in 4 seconds, and "THE NERDS" pops up in 6 seconds
        ActionListener taskPerformer = e -> {
            titleLbl.setText("<html><body>GIGA CHESS</body></html>");
            Sound boom = new Sound("src/sounds/vine-boom.wav");
            if (!skipped) boom.play();
        };
        Timer textTimer = new Timer(2000, taskPerformer);
        textTimer.setRepeats(false);
        textTimer.start();

        taskPerformer = e -> {
            titleLbl.setText("<html><body>GIGA CHESS<br>BROUGHT TO YOU BY:</body></html>");
            Sound boom = new Sound("src/sounds/vine-boom.wav");
            if (!skipped) boom.play();
        };
        textTimer = new Timer(4000, taskPerformer);
        textTimer.setRepeats(false);
        textTimer.start();

        taskPerformer = e -> {
            titleLbl.setText("<html><body>GIGA CHESS<br>BROUGHT TO YOU BY:<br>THE NERDS</body></html>");
            Sound boom = new Sound("src/sounds/vine-boom.wav");
            if (!skipped) boom.play();
        };
        textTimer = new Timer(6000, taskPerformer);
        textTimer.setRepeats(false);
        textTimer.start();

        taskPerformer = e -> {
            Icon imgIcon = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("images/rock.gif")));
            gifLabel.setIcon(imgIcon);
            Sound boom = new Sound("src/sounds/vine-boom.wav");
            if (!skipped) boom.play();
        };
        textTimer = new Timer(8000, taskPerformer);
        textTimer.setRepeats(false);
        textTimer.start();

        taskPerformer = e -> {
            Sound boom = new Sound("src/sounds/vine-boom.wav");
            if (!skipped) boom.play();
        };
        textTimer = new Timer(10500, taskPerformer);
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
        if (secondsElapsed == 12) {
            timer.stop();
            introFrame.dispose();
            new StartMenu();
        }
    }
}
