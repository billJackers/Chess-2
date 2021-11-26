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

    private class MenuLayout extends JPanel {
        private Image bg;
        public MenuLayout(Image background){
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
        startMenu.setLayout(new BorderLayout(10, 5));
        startMenu.setTitle("Chess 2");
        startMenu.setSize(300, 300);
        startMenu.setLocationRelativeTo(null);
        startMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startMenu.setResizable(false);

        // For dividing the screen into two sides: the singleplayer and multiplayer sections
        Image background;
        String backgroundPath = "images/menuscreen.png";
        try {
            BufferedImage bg = ImageIO.read(Objects.requireNonNull(getClass().getResource(backgroundPath)));
            background = bg.getScaledInstance(300, 300, Image.SCALE_DEFAULT); // scale the image based on game configurations
        } catch (IOException e) {
            System.out.println("File not found: " + e.getMessage());
            return;
        }
        MenuLayout menuLayout = new MenuLayout(background);
        menuLayout.setLayout(new GridLayout());

        // singleplayer section
        JPanel singlePlayerMenu = new JPanel();
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
        final JComboBox<String> seconds = new JComboBox<>(minSecInts);
        final JComboBox<String> minutes = new JComboBox<>(minSecInts);
        final JComboBox<String> hours = new JComboBox<>(hourInts);
        Box timerSettings = Box.createHorizontalBox();
        hours.setMaximumSize(hours.getPreferredSize());
        minutes.setMaximumSize(minutes.getPreferredSize());
        minutes.setSelectedIndex(10);  // defaults to 10 minutes
        seconds.setMaximumSize(minutes.getPreferredSize());
        timerSettings.add(hours);
        timerSettings.add(minutes);
        timerSettings.add(seconds);
        singlePlayerMenu.add(singlePlayerHeader);
        singlePlayerMenu.add(timerSettings);
        singlePlayerMenu.add(quickStartBtn);
        quickStartBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int h = Integer.parseInt((String) hours.getSelectedItem());
                int m = Integer.parseInt((String) minutes.getSelectedItem());
                int s = Integer.parseInt((String) seconds.getSelectedItem());
                new GameWindow(h, m, s);
            }
        });

        // multiplayer section
        JPanel multiPlayerMenu = new JPanel();
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
        startMenu.add(menuLayout);
        startMenu.setVisible(true);
    }
}