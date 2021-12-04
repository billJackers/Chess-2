import javax.swing.*;
import java.awt.*;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class StartMenu {

    private final int WIDTH= 375;
    private final int HEIGHT = 300;

    private class MenuImage extends JPanel {
        private final Image bg;
        public MenuImage(Image background){
            bg = background;
        }
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(bg, 0, 0, this);
        }
    }

    private JFrame startMenu;

    public StartMenu() {
        initialize();
    }

    public void initialize() {
        startMenu = new JFrame();
        startMenu.setLayout(new BorderLayout());
        startMenu.setTitle("Giga Chess");
        startMenu.setSize(this.WIDTH, this.HEIGHT);
        startMenu.setLocationRelativeTo(null);
        startMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startMenu.setResizable(false);

        // For dividing the screen into two sides: the singleplayer and multiplayer sections
        String backgroundPath = "images/menuscreen.png";
        MenuPanel menuLayout = new MenuPanel(backgroundPath);
        menuLayout.setLayout(new GridLayout(2, 2));

        // singleplayer section
        JPanel singlePlayerMenu = new JPanel();
        singlePlayerMenu.setOpaque(false);
        JLabel singlePlayerHeader = new JLabel("Singleplayer");
        singlePlayerMenu.setLayout(new FlowLayout(FlowLayout.CENTER));
        Button quickStartBtn = new Button("Quick start");
        // Clocks
        final String[] minSecInts = new String[60];
        for (int i = 0; i < 60; i++) {
            if (i < 10) minSecInts[i] = "0" + i;
            else minSecInts[i] = Integer.toString(i);
        }
        final String[] hourInts = new String[24];
        for (int i = 0; i < 24; i++) {
            if (i < 10) hourInts[i] = "0" + i;
            else hourInts[i] = Integer.toString(i);
        }
        final String[] incrementInts = new String[11];
        for (int i = 0; i < 11; i++) {
            if (i < 10) incrementInts[i] = "0" + i;
            else incrementInts[i] = Integer.toString(i);
        }
        final JComboBox<String> seconds = new JComboBox<>(minSecInts);
        final JComboBox<String> minutes = new JComboBox<>(minSecInts);
        final JComboBox<String> hours = new JComboBox<>(hourInts);
        final JComboBox<String> increment = new JComboBox<>(incrementInts);
        Box timerSettings = Box.createHorizontalBox();
        hours.setMaximumSize(hours.getPreferredSize());
        minutes.setMaximumSize(minutes.getPreferredSize());
        minutes.setSelectedIndex(10);  // defaults to 10 minutes
        seconds.setMaximumSize(minutes.getPreferredSize());
        timerSettings.add(hours);
        timerSettings.add(minutes);
        timerSettings.add(seconds);
        timerSettings.add(increment);
        JLabel timerLabels = new JLabel("   hrs      min      sec     +time  ");
        timerLabels.setOpaque(true);
        timerLabels.setBackground(new Color(200, 200, 200));
        timerLabels.setBorder(BorderFactory.createLineBorder(Color.black));
        singlePlayerMenu.add(singlePlayerHeader);
        singlePlayerMenu.add(timerLabels);
        singlePlayerMenu.add(timerSettings);
        singlePlayerMenu.add(quickStartBtn);
        // Weird lambda magic
        quickStartBtn.addActionListener(e -> {
            int h = Integer.parseInt((String) hours.getSelectedItem());
            int m = Integer.parseInt((String) minutes.getSelectedItem());
            int s = Integer.parseInt((String) seconds.getSelectedItem());
            int i = Integer.parseInt((String) increment.getSelectedItem());
            new GameWindow(h, m, s, i);

        });

        // More options
        JPanel moreOptionsPanel = new JPanel();
        moreOptionsPanel.setOpaque(false);

        // Settings
        Button settingsBtn = new Button("Settings");
        moreOptionsPanel.add(settingsBtn);
        moreOptionsPanel.setOpaque(false);

        // Help
        Button helpBtn = new Button("Help");
        moreOptionsPanel.add(helpBtn);

        helpBtn.addActionListener(e -> {
            try {
                showHelp();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        // Creates a dialog box
        settingsBtn.addActionListener(e -> showSettings());

        // multiplayer section
        JPanel multiPlayerMenu = new JPanel();
        multiPlayerMenu.setOpaque(false);
        JLabel multiPlayerHeader = new JLabel("Multiplayer");
        multiPlayerMenu.setLayout(new FlowLayout(FlowLayout.CENTER));
        Button serverBtn = new Button("Host a local game");
        Button clientBtn = new Button("Join a local game");
        serverBtn.addActionListener(e -> new GameServer());
        clientBtn.addActionListener(e -> new GameClient());
        multiPlayerMenu.add(multiPlayerHeader);
        multiPlayerMenu.add(serverBtn);
        multiPlayerMenu.add(clientBtn);

        // adding the menus together
        menuLayout.add(singlePlayerMenu);
        menuLayout.add(multiPlayerMenu);
        menuLayout.add(moreOptionsPanel);
        menuLayout.setVisible(true);
        startMenu.add(menuLayout, BorderLayout.CENTER);
        startMenu.setVisible(true);
    }

    static class MenuPanel extends JPanel {
        private Image backgroundImage;
        public MenuPanel(String img) {
            try {
                BufferedImage bg = ImageIO.read(Objects.requireNonNull(getClass().getResource(img)));
                backgroundImage = bg.getScaledInstance(300, 300, Image.SCALE_DEFAULT); // scale the image based on game configurations
            } catch (IOException e) {
                System.out.println("File not found: " + e.getMessage());
            }
        }
        @Override
        public void paintComponent(Graphics g) {
            g.drawImage(backgroundImage, 0, 0, null);
        }
    }

    // Separate method made for showing settings
    public static void showSettings() {
        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new GridLayout(4, 1, 0, 3));

        JComboBox<String> highlights = new JComboBox<>();
        JComboBox<String> deciseconds = new JComboBox<>();
        JComboBox<String> soundEffects = new JComboBox<>();

        JPanel volumePanel = new JPanel();
        JSlider volumeSlider = new JSlider(0, 100, 0);
        volumePanel.add(new JLabel("Volume"));
        volumePanel.add(volumeSlider);

        String[] highlightSettings = {"Show highlights", "Don't show highlights"};
        String[] decisecondSettings = {"Show deciseconds after clock goes below 20s", "Always show deciseconds", "Don't show at all"};
        String[] soundEffectsSettings = {"Sound effects ON", "Sound effects OFF"};

        // Add strings to JComboBoxes
        for (String str : highlightSettings) highlights.addItem(str);
        for (String str : decisecondSettings) deciseconds.addItem(str);
        for (String str : soundEffectsSettings) soundEffects.addItem(str);

        // Add components to settings panel
        settingsPanel.add(highlights);
        settingsPanel.add(deciseconds);
        settingsPanel.add(soundEffects);
        settingsPanel.add(volumePanel);

        JOptionPane.showMessageDialog(null, settingsPanel, "Settings", JOptionPane.QUESTION_MESSAGE);
    }

    // Help
    public void showHelp() throws IOException {
        JFrame helpFrame = new JFrame("Help");
        helpFrame.setSize(this.WIDTH*5/6, this.HEIGHT*5/6);
        helpFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        helpFrame.setVisible(true);
        helpFrame.setResizable(false);

        // Initialize components/content
        JPanel helpPanel = new JPanel();
        helpPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel helpLbl = new JLabel("Click on a piece to see how it works");
        helpLbl.setFont(new Font("Sans Serif", Font.BOLD, 15));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2,2, 3, 3));

        JButton archerBtn = new JButton("Archer", new ImageIcon(Objects.requireNonNull(getClass().getResource("images/barcher.png"))));
        JButton bomberBtn = new JButton("Bomber", new ImageIcon(Objects.requireNonNull(getClass().getResource("images/bbomber.png"))));
        JButton assassinBtn = new JButton("Assassin", new ImageIcon(Objects.requireNonNull(getClass().getResource("images/bassassin.png"))));
        JButton rgBtn = new JButton("Royal Guard", new ImageIcon(Objects.requireNonNull(getClass().getResource("images/broyalguard.png"))));
        JButton[] helpButtons = {archerBtn, bomberBtn, assassinBtn, rgBtn};

        for (JButton button : helpButtons) {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        showPieceMovementHelp(button.getText());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            button.setMargin(new Insets(2, 2, 2, 2));
            buttonPanel.add(button);
        }

        helpPanel.add(helpLbl);
        helpPanel.add(buttonPanel);
        helpFrame.add(helpPanel);
    }

    // Piece movement help frame
    public void showPieceMovementHelp(String pieceClicked) throws IOException {

        JFrame pieceMovementHelpFrame = new JFrame("Help");
        pieceMovementHelpFrame.setResizable(false);
        pieceMovementHelpFrame.setSize(this.HEIGHT*9/8, this.WIDTH*9/8);

        JPanel pieceMovementHelpPanel = new JPanel();
        pieceMovementHelpPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        Image movementImage = new BufferedImage(this.HEIGHT/2, this.WIDTH/2, 1);
        JLabel movementText;

        if (pieceClicked.equals("Archer")) {
            movementImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("images/gigachad.png")));
        } else if (pieceClicked.equals("Bomber")) {
            movementImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("images/gigachad.png")));
        } else if (pieceClicked.equals("Assassin")) {
            movementImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("images/gigachad.png")));
        } else {
            movementImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("images/gigachad.png")));
        }

        pieceMovementHelpPanel.add(new JLabel(new ImageIcon(movementImage)));
        pieceMovementHelpFrame.add(pieceMovementHelpPanel);
        pieceMovementHelpFrame.setVisible(true);
    }

}