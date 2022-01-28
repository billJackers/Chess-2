import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class SideDisplay extends JPanel {

    private final int HEIGHT;
    private final int WIDTH;
    private final Board board;
    private Settings settings;
    private final PlayerController controller;
    private final WinFrame winFrame;

    public static final DefaultListModel<String> movesDisplay = new DefaultListModel<>();  // this is horrible but there is no better way of doing this with the current codebase

    public SideDisplay(Board board, Settings settings) {

        this.setLayout(new FlowLayout());

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
        undoBtn.addActionListener(e -> {
            controller.undoMove();
            if (movesDisplay.size() > 0) movesDisplay.remove(movesDisplay.size()-1);
        });

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
        JPanel movePanelBox = new JPanel();  // First make the panel to hold the scrollable elements
        // movesDisplay is a static variable defined above: DefaultListModel<String> movesDisplay = new DefaultListModel<>();
        JList<String> list = new JList<>(movesDisplay);  // create a JList. By adding items to movesDisplay, it will update the JList

        JScrollPane scroller = new JScrollPane();  // Scrollable interface
        scroller.setViewportView(list);  // Add the JList to the scrollable interface
        list.setLayoutOrientation(JList.VERTICAL);  // we do a little formatting
        movePanelBox.add(scroller);  // add the scroller to our JPanel

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
