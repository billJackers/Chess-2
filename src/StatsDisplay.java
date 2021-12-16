import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.List;

public class StatsDisplay extends JPanel implements ActionListener {

    private final int HEADER_HEIGHT;
    private final int HEADER_WIDTH;
    private final Board board;
    private Image background;

    private Sides turn;
    private final Font clockFont;

    private boolean timed = true;

    private final WinFrame winFrame;

    private final Timer globalClock;

    public StatsDisplay(Board board, Settings settings, int hours, int minutes, int seconds) {
        this.board = board;
        this.winFrame = new WinFrame();

        // dimensions of this JPanel
        HEADER_HEIGHT = board.getHeight() / 10;  // dimensions relative to game
        HEADER_WIDTH = board.getWidth();
        this.setPreferredSize(new Dimension(HEADER_WIDTH, HEADER_HEIGHT)); // dimensions based on the size of the board
        this.setMaximumSize(this.getPreferredSize());
        this.setLayout(new FlowLayout(0));

        // Undo move button
        if (settings.canRollback()) {
            JButton undoBtn = new JButton("Undo");
            undoBtn.addActionListener(e -> {
                this.board.getController().undoMove();
            });
            this.add(undoBtn);
        }

        if (hours == 0 && minutes == 0 && seconds == 0) timed = false;

        String backgroundPath = "images/display_background.png";
        try {
            BufferedImage bg = ImageIO.read(Objects.requireNonNull(getClass().getResource(backgroundPath)));
            background = bg.getScaledInstance(HEADER_WIDTH, HEADER_HEIGHT, Image.SCALE_DEFAULT); // scale the image based on game configurations
        } catch (IOException e) {
            System.out.println("File not found: " + e.getMessage());
        }

        turn = Sides.BLUE;
        clockFont = new Font("Serif", Font.BOLD, HEADER_WIDTH/30);

        globalClock = new Timer(100, this);
        globalClock.start();
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);  // background color of stats

        // while seemingly random, positions based on the width and height of the screen lets us alter the size of the board maintaining the correct ratios of text positions and sizes
        // drawing new clock states
        g.setFont(clockFont);
        g.setColor(new Color(56, 211, 255, 255));  // set a color before drawing text for text to be that color
        // Clocks should only show if the game is timed
        if (timed) {
            g.drawString(board.getController().getbClock().getTime(), HEADER_WIDTH * 5 / 26, HEADER_HEIGHT / 2);
            g.setColor(new Color(255, 29, 29, 255));
            g.drawString(board.getController().getrClock().getTime(), HEADER_WIDTH * 18 / 26, HEADER_HEIGHT / 2);
        }

        switch (turn) {
            case BLUE -> g.setColor(new Color(56, 211, 255, 218));
            case RED -> g.setColor(new Color(255, 29, 29, 218));
        }

        // Draw captured red pieces (ugly code, should probably clean up)
        for (int i = 0; i < board.getController().getRedPiecesCaptured().size(); i++) {
            Image image = board.getController().getRedPiecesCaptured().get(i).getImage().getScaledInstance(HEADER_HEIGHT/3, HEADER_HEIGHT/3, Image.SCALE_FAST);
            g.drawImage(image, (HEADER_WIDTH*5/26)+(HEADER_WIDTH*i/50), HEADER_HEIGHT*3/5, null);
        }

        // Draw captured blue pieces
        for (int i = 0; i < board.getController().getBluePiecesCaptured().size(); i++) {
            Image image = board.getController().getBluePiecesCaptured().get(i).getImage().getScaledInstance(HEADER_HEIGHT/3, HEADER_HEIGHT/3, Image.SCALE_FAST);
            g.drawImage(image, (HEADER_WIDTH*18/26)+(HEADER_WIDTH*i/50), HEADER_HEIGHT*3/5, null);
        }

        // draws the current turn with a background color
        String formattedTurn = turn.toString().charAt(0) + turn.toString().substring(1).toLowerCase() + "'s turn";  // coverts BLUE to Blue or RED to Red
        g.fillRoundRect(HEADER_WIDTH*11/26, HEADER_HEIGHT*3/12, g.getFontMetrics().stringWidth(formattedTurn), HEADER_WIDTH/30, 10, 10);  // draws the background at relative positions
        g.setColor(Color.BLACK);  // changes the text color back to black
        g.drawString(formattedTurn, HEADER_WIDTH*11/26, HEADER_HEIGHT/2);  // then draws the text on top
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //if (board.isPaused()) return;  // if the game is paused, don't do any actions

        turn = board.getController().getCurrentTurn();
        if (timed) {
            if (board.getController().getbClock().outOfTime() || board.getController().getrClock().outOfTime()) {
                globalClock.stop();
                if (board.getController().getbClock().outOfTime()) winFrame.makeWinFrame(Sides.RED);
                else winFrame.makeWinFrame(Sides.BLUE);
            }
            // determining which clock to decrement
            switch (turn) {
                case BLUE -> board.getController().getbClock().decrement();
                case RED -> board.getController().getrClock().decrement();
            }
        }
        repaint();
    }


}
