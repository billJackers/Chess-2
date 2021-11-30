import javax.swing.*;
import java.awt.*;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
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

public class StartMenu {

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
        startMenu.setTitle("Chess 2");
        startMenu.setSize(375, 300);
        startMenu.setLocationRelativeTo(null);
        startMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startMenu.setResizable(false);

        // For dividing the screen into two sides: the singleplayer and multiplayer sections
        String backgroundPath = "images/menuscreen.png";
        MenuPanel menuLayout = new MenuPanel(backgroundPath);
        menuLayout.setLayout(new GridLayout(1, 2));

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
        quickStartBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int h = Integer.parseInt((String) hours.getSelectedItem());
                int m = Integer.parseInt((String) minutes.getSelectedItem());
                int s = Integer.parseInt((String) seconds.getSelectedItem());
                int i = Integer.parseInt((String) increment.getSelectedItem());
                new GameWindow(h, m, s, i);

            }
        });

        // multiplayer section
        JPanel multiPlayerMenu = new JPanel();
        multiPlayerMenu.setOpaque(false);
        JLabel multiPlayerHeader = new JLabel("Multiplayer");
        multiPlayerMenu.setLayout(new FlowLayout(FlowLayout.CENTER));
        Button serverBtn = new Button("Host a local game");
        Button clientBtn = new Button("Join a local game");
        serverBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GameServer();
            }
        });
        clientBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GameClient();
            }
        });
        multiPlayerMenu.add(multiPlayerHeader);
        multiPlayerMenu.add(serverBtn);
        multiPlayerMenu.add(clientBtn);

        // adding the menus together
        menuLayout.add(singlePlayerMenu);
        menuLayout.add(multiPlayerMenu);
        menuLayout.setVisible(true);
        startMenu.add(menuLayout, BorderLayout.CENTER);
        startMenu.setVisible(true);
    }

    class MenuPanel extends JPanel {
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

}