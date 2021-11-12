import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javax.imageio.ImageIO;

public abstract class Piece {
    private final boolean isWhite;
    private Square currentSquare;
    private BufferedImage img;

    public Piece(boolean isWhite, Square startSquare, String file) {
        this.isWhite = isWhite;
        this.currentSquare = startSquare;

        try {
            this.img = ImageIO.read(Objects.requireNonNull(getClass().getResource(file)));
        } catch (IOException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    public boolean move(Square moveTo) {
        Piece occupying = moveTo.getOccupyingPiece();
        if (occupying != null) {
            if (occupying.white() == this.isWhite) return false;
            else moveTo.capturePiece(this);
        }

        currentSquare.removePiece();
        this.currentSquare = moveTo;
        currentSquare.placePiece(this);
        return true;
    }

    public Square getCurrentSquare() {
        return currentSquare;
    }

    public void setPosition(Square s) {
        this.currentSquare = s;
    }

    public boolean white() {
        return this.isWhite;
    }

    public Image getImage() {
        return img;
    }

    public void draw(Graphics g) {
        int x = currentSquare.getXCoordinate();
        int y = currentSquare.getYCoordinate();

        g.drawImage(this.img, x, y, null);
    }

    public Square getPosition() {
        return currentSquare;
    }

    public int[] getLinearOccupations(Square[][] board, int x, int y) {
        int lastYabove = 0;
        int lastXright = 7;
        int lastYbelow = 7;
        int lastXleft = 0;

        for (int i = 0; i < y; i++) {
            if (board[i][x].isOccupied()) {
                if (board[i][x].getOccupyingPiece().white() != this.isWhite) {
                    lastYabove = i;
                } else lastYabove = i + 1;
            }
        }

        for (int i = 7; i > y; i--) {
            if (board[i][x].isOccupied()) {
                if (board[i][x].getOccupyingPiece().white() != this.isWhite) {
                    lastYbelow = i;
                } else lastYbelow = i - 1;
            }
        }

        for (int i = 0; i < x; i++) {
            if (board[y][i].isOccupied()) {
                if (board[y][i].getOccupyingPiece().white() != this.isWhite) {
                    lastXleft = i;
                } else lastXleft = i + 1;
            }
        }

        for (int i = 7; i > x; i--) {
            if (board[y][i].isOccupied()) {
                if (board[y][i].getOccupyingPiece().white() != this.isWhite) {
                    lastXright = i;
                } else lastXright = i - 1;
            }
        }

        return new int[]{lastYabove, lastYbelow, lastXleft, lastXright};
    }

    public List<Square> getDiagonalOccupations(Square[][] board, int x, int y) {
        LinkedList<Square> diagOccup = new LinkedList<Square>();

        int xNW = x - 1;
        int xSW = x - 1;
        int xNE = x + 1;
        int xSE = x + 1;
        int yNW = y - 1;
        int ySW = y + 1;
        int yNE = y - 1;
        int ySE = y + 1;

        while (xNW >= 0 && yNW >= 0) {
            if (board[yNW][xNW].isOccupied()) {
                if (board[yNW][xNW].getOccupyingPiece().white() == this.isWhite) {
                    break;
                } else {
                    diagOccup.add(board[yNW][xNW]);
                    break;
                }
            } else {
                diagOccup.add(board[yNW][xNW]);
                yNW--;
                xNW--;
            }
        }

        while (xSW >= 0 && ySW < 10) {
            if (board[ySW][xSW].isOccupied()) {
                if (board[ySW][xSW].getOccupyingPiece().white() == this.isWhite) {
                    break;
                } else {
                    diagOccup.add(board[ySW][xSW]);
                    break;
                }
            } else {
                diagOccup.add(board[ySW][xSW]);
                ySW++;
                xSW--;
            }
        }

        while (xSE < 10 && ySE < 8) {
            if (board[ySE][xSE].isOccupied()) {
                if (board[ySE][xSE].getOccupyingPiece().white() == this.isWhite) {
                    break;
                } else {
                    diagOccup.add(board[ySE][xSE]);
                    break;
                }
            } else {
                diagOccup.add(board[ySE][xSE]);
                ySE++;
                xSE++;
            }
        }

        while (xNE < 10 && yNE >= 0) {
            if (board[yNE][xNE].isOccupied()) {
                if (board[yNE][xNE].getOccupyingPiece().white() == this.isWhite) {
                    break;
                } else {
                    diagOccup.add(board[yNE][xNE]);
                    break;
                }
            } else {
                diagOccup.add(board[yNE][xNE]);
                yNE--;
                xNE++;
            }
        }

        return diagOccup;
    }

    public abstract List<Square> getLegalMoves(Board b);

}
