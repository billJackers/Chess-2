import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SideDisplay extends JPanel {

    private final int HEIGHT;
    private final int WIDTH;
    private final Board board;
    private Settings settings;
    private PlayerController controller;
    private WinFrame winFrame;

    public SideDisplay(Board board, Settings settings) {

        this.board = board;
        this.settings = settings;
        this.controller = board.getController();
        this.winFrame = new WinFrame();

        this.HEIGHT = board.getHeight() + board.getHeight() / 10;
        this.WIDTH = board.getWidth() / 3;

        this.setPreferredSize(new Dimension(WIDTH, HEIGHT)); // dimensions based on the size of the board
        this.setMaximumSize(this.getPreferredSize());

        // Emote buttons
        JButton lolBtn = new JButton("lol");
        lolBtn.addActionListener(e -> {
            Sound lol = new Sound("src/sounds/lol.wav");
            lol.play();
        });

        JButton bruhBtn = new JButton("bruh");
        bruhBtn.addActionListener(e -> {
            Sound bruh = new Sound("src/sounds/bruh.wav");
            bruh.play();
        });

        JButton painBtn = new JButton("pain");
        painBtn.addActionListener(e -> {
            Sound pain = new Sound("src/sounds/pain.wav");
            pain.play();
        });

        // Game buttons
        JButton undoBtn = new JButton("Undo");
        undoBtn.addActionListener(e -> controller.undoMove());

        JButton drawBtn = new JButton("Offer Draw");
        drawBtn.addActionListener(e -> {
            winFrame.makeWinFrame(0);
            board.pause();
        });

        JButton resignBtn = new JButton("Resign");
        resignBtn.addActionListener(e -> {
            switch (controller.getCurrentTurn()) {
                case RED -> winFrame.makeWinFrame(1);
                case BLUE -> winFrame.makeWinFrame(-1);
            }
            board.pause();
        });

        // Scrollable panel that shows moves
        JPanel movePanelBox = new JPanel();
        JPanel movePanel = new JPanel();
        movePanel.setPreferredSize(new Dimension(this.WIDTH, this.HEIGHT/2));
        movePanel.setLayout(new GridLayout(40, 1));
        for (int i = 0; i < 40; i++) movePanel.add(BorderLayout.CENTER, new JLabel("Move " + i));
        JScrollPane scrollPane = new JScrollPane(movePanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        movePanelBox.add(scrollPane);

        // Add emote buttons
        this.add(lolBtn);
        this.add(bruhBtn);
        this.add(painBtn);

        // Add game buttons
        this.add(undoBtn);
        this.add(drawBtn);
        this.add(resignBtn);

        // Add move box
        this.add(movePanelBox);

        this.setVisible(true);
    }

    public int getHeight() {
        return HEIGHT;
    }

    public int getWidth() {
        return WIDTH;
    }


}
