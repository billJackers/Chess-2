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
        startMenu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        startMenu.setResizable(false);

        JPanel menu = new JPanel();
        menu.setLayout(new FlowLayout(FlowLayout.CENTER));

        startMenu.add(menu);

        menu.setBackground(Color.PINK);

        Button casualBtn = new Button("Play a casual game");
        Button timedBtn = new Button("Play a timed game");
        menu.add(casualBtn);
        menu.add(timedBtn);

        casualBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GameWindow();
            }
        });

        startMenu.setVisible(true);
        menu.setVisible(true);

    }
}
