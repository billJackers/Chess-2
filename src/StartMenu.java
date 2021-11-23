import javax.swing.*;
import java.awt.*;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        JPanel menu = new JPanel();
        menu.setLayout(new FlowLayout(FlowLayout.CENTER));

        startMenu.add(menu);

        menu.setBackground(Color.PINK);

        Button quickStartBtn = new Button("Quick start");

        // server mode
        Button serverBtn = new Button("Host a local game");
        Button clientBtn = new Button("Join a local game");

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
        seconds.setMaximumSize(minutes.getPreferredSize());

        timerSettings.add(hours);
        timerSettings.add(minutes);
        timerSettings.add(seconds);

        menu.add(timerSettings);

        menu.add(quickStartBtn);
        menu.add(serverBtn);
        menu.add(clientBtn);

        quickStartBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int h = Integer.parseInt((String) hours.getSelectedItem());
                int m = Integer.parseInt((String) minutes.getSelectedItem());
                int s = Integer.parseInt((String) seconds.getSelectedItem());

                System.out.println("Source: " + e.getSource());

                new GameWindow(h, m, s);
            }
        });

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

        startMenu.setVisible(true);
        menu.setVisible(true);

    }
}