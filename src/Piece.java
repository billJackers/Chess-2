import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.*;
import javax.imageio.ImageIO;

public abstract class Piece {

    protected Sides side;
    protected int size;
    protected Image image;
    protected Square parentSquare;
    protected boolean enPassantable;
    protected boolean wasMoved;

    public Piece(Sides side, int size, Square initialSquare) {
        this.side = side;
        this.size = size;
        this.parentSquare = initialSquare;
        this.enPassantable = false;
    }

    protected Image getImageByFile(String file) {  // get our image based on a file name
        Image sprite = null;
        try {
            sprite = ImageIO.read(Objects.requireNonNull(getClass().getResource(file)));
            sprite = sprite.getScaledInstance(size, size, Image.SCALE_DEFAULT); // scale the image based on game configurations
        } catch (IOException e) {
            System.out.println("File not found: " + e.getMessage());
        }
        return sprite;
    }

    public void setParentSquare(Square newParent) {
        this.parentSquare = newParent;
    }

    public void draw(Graphics g, int x, int y) {
        g.drawImage(this.image, x, y, null);
    }  // draws image at location

    public abstract List<Square> getLegalMoves(Board board);

    public abstract String getName();

    // This is for the bombers and archers
    public abstract List<Square> getTargets(Board board);

    protected boolean canCapture(Square target) {
        if (!target.hasPiece() || (this.side != target.getPiece().side && !(target.getPiece() instanceof RoyalGuard))) {
            return true;
        } else if (target.hasPiece() && target.getPiece() instanceof RoyalGuard && (this.side != target.getPiece().side)) {
            if (this instanceof Assassin) return true;
            switch (this.side) {
                case BLUE -> {
                    if (this.parentSquare.getFile() > target.getFile()) return true;
                }
                case RED -> {
                    if (this.parentSquare.getFile() < target.getFile()) return true;
                }
            }
        }
        return false;
    }

    public Sides getSide() {
        return this.side;
    }

    // checks if the square is on either side edge
    // left edge is -1, right edge is 1, neither is 0
    public int onEdge(Square target) {
        if (target.getRank() == 0) return -1;
        if (target.getRank() == 9) return 1;
        return 0;
    }

    // checks if the square is on the top or bottom edge
    // top edge is -1, bottom edge is 1, neither is 0
    public int onEnd(Square target) {
        if (target.getFile() == 0) return -1;
        if (target.getFile() == 9) return 1;
        return 0;
    }

    public List<Square> getBishopLegalMoves(Board board) {
        List<Square> legalMoves = new ArrayList<>();

        int rank = this.parentSquare.getRank();
        int file = this.parentSquare.getFile();

        Square[] b = board.getBoard();

        int pos = (file*10) + rank;

        int temp = 1;

        // up and to the left
        if (pos >= 10 && pos % 10 != 0) {
            while ((pos-temp-(temp*10)) % 10 != 9 && pos-temp-(temp*10) >= 0) {
                if (!b[pos-temp-(temp*10)].hasPiece()) legalMoves.add(b[pos-temp-(temp*10)]);
                else if (this.canCapture(b[pos-temp-(temp*10)])) {
                    legalMoves.add(b[pos-temp-(temp*10)]);
                    break;
                } else break;

                temp++;
            }
        }

        // up and to the right
        temp = 1;
        if (pos >= 10 && pos % 10 != 9) {
            while ((pos+temp-(temp*10)) % 10 != 0 && pos+temp-(temp*10) >= 0) {
                if (!b[pos+temp-(temp*10)].hasPiece()) legalMoves.add(b[pos+temp-(temp*10)]);
                else if (this.canCapture(b[pos+temp-(temp*10)])) {
                    legalMoves.add(b[pos+temp-(temp*10)]);
                    break;
                } else break;

                temp++;
            }
        }

        // Down and to the left
        temp = 1;
        if (pos < 90 && pos % 10 != 0) {
            while ((pos-temp+(temp*10)) % 10 != 9 && pos-temp+(temp*10) < 100) {
                if (!b[pos-temp+(temp*10)].hasPiece()) legalMoves.add(b[pos-temp+(temp*10)]);
                else if (this.canCapture(b[pos-temp+(temp*10)])) {
                    legalMoves.add(b[pos-temp+(temp*10)]);
                    break;
                } else break;

                temp++;
            }
        }

        // Down and to the right
        temp = 1;
        if (pos < 90 && pos % 10 != 9) {
            while ((pos+temp+(temp*10)) % 10 != 0 && pos+temp+(temp*10) < 100) {
                if (!b[pos+temp+(temp*10)].hasPiece()) legalMoves.add(b[pos+temp+(temp*10)]);
                else if (this.canCapture(b[pos+temp+(temp*10)])) {
                    legalMoves.add(b[pos+temp+(temp*10)]);
                    break;
                } else break;

                temp++;
            }
        }

        return legalMoves;
    }

    public List<Square> getRookLegalMoves(Board board) {
        List<Square> legalMoves = new ArrayList<>();

        int rank = this.parentSquare.getRank();
        int file = this.parentSquare.getFile();

        int rankSize = board.getRankSize();
        int fileSize = board.getFileSize();

        Square[] b = board.getBoard();

        int pos = (file*10) + rank;

        // Horizontal

        int temp = 1;

        if (pos % 10 != 9) {
            while ((pos+temp) % 10 != 0) {
                if (!b[pos+temp].hasPiece()) {
                    legalMoves.add(b[pos+temp]);
                }
                if (b[pos+temp].hasPiece() && canCapture(b[pos+temp])) {
                    legalMoves.add(b[pos+temp]);
                    break;
                }
                if (b[pos+temp].hasPiece() && !canCapture(b[pos+temp])) {
                    break;
                }
                temp++;
            }
        }
        temp = 1;
        if (pos % 10 != 0) {
            while ((pos-temp) % 10 != 9 && (pos-temp) >= 0) {
                if (!b[pos-temp].hasPiece()) {
                    legalMoves.add(b[pos-temp]);
                }
                if (b[pos-temp].hasPiece() && canCapture(b[pos-temp])) {
                    legalMoves.add(b[pos-temp]);
                    break;
                }
                if (b[pos-temp].hasPiece() && !canCapture(b[pos-temp])) {
                    break;
                }
                temp++;
            }
        }

        // Vertical
        temp = 1;
        if (pos < 90) {
            while (pos+(temp*fileSize) < 100) {
                if (!b[pos+(temp*fileSize)].hasPiece()) {
                    legalMoves.add(b[pos+(temp*fileSize)]);
                }
                if (b[pos+(temp*fileSize)].hasPiece() && canCapture(b[pos+(temp*fileSize)])) {
                    legalMoves.add(b[pos+(temp*fileSize)]);
                    break;
                }
                if (b[pos+(temp*fileSize)].hasPiece() && !canCapture(b[pos+(temp*fileSize)])) {
                    break;
                }
                temp++;
            }
        }

        temp = 1;
        if (pos >= 10) {
            while (pos-(temp*fileSize) >= 0) {
                if (!b[pos-(temp*fileSize)].hasPiece()) {
                    legalMoves.add(b[pos-(temp*fileSize)]);
                }
                if (b[pos-(temp*fileSize)].hasPiece() && canCapture(b[pos-(temp*fileSize)])) {
                    legalMoves.add(b[pos-(temp*fileSize)]);
                    break;
                }
                if (b[pos-(temp*fileSize)].hasPiece() && !canCapture(b[pos-(temp*fileSize)])) {
                    break;
                }
                temp++;
            }
        }

        return legalMoves;
    }

    public List<Square> getKingLegalMoves(Board board) {
        ArrayList<Square> legalMoves = new ArrayList<>();

        int rank = this.parentSquare.getRank();
        int file = this.parentSquare.getFile();
        int fileSize = board.getFileSize();
        int indexOfPiece = (file*fileSize) + rank;  // the index of this piece in the board[]

        Square[] b = board.getBoard();

        int[] allMoves = {10, 9, 11, 1};  // [+] OR [-] these values RELATIVE TO OUR CURRENT INDEX gives us possible moves for the knight
        for (int relativeMove : allMoves) {
            // if the relative moves are within the bounds of the board and the position is capturable, then add to legalMoves
            if (indexOfPiece + relativeMove < 100 && Math.abs(((indexOfPiece + relativeMove)%10)-(indexOfPiece%10)) <= 1 && this.canCapture(b[indexOfPiece + relativeMove])) legalMoves.add(b[indexOfPiece + relativeMove]);
            if (indexOfPiece - relativeMove >= 0  && Math.abs(((indexOfPiece - relativeMove)%10)-(indexOfPiece%10)) <= 1 && this.canCapture(b[indexOfPiece - relativeMove])) legalMoves.add(b[indexOfPiece - relativeMove]);
        }
        return legalMoves;
    }

    public boolean isEnPassantable() {
        return this.enPassantable;
    }

    public void setEnPassantable(boolean b) {
        this.enPassantable = b;
    }

    public boolean getWasMoved() {
        return wasMoved;
    }

    public void setToMoved() {
        this.wasMoved = true;
    }

//    public List<Square> getPawnAttacks(Sides s) {
//        if (!(this instanceof Pawn)) return new ArrayList<>();
//
//        List<Square> attacks = new ArrayList<>();
//
//        int rank = this.parentSquare.getRank();
//        int file = this.parentSquare.getFile();
//
//        Square[] b = board.getBoard();
//
//        int pos = (file*10) + rank;
//
//        switch (s) {
//            case BLUE -> {
//                if (pos % 10 != 0) attacks.add(b[pos+9]);
//                if (pos % 10 != 9) attacks.add(b[pos+11]);
//            }
//            case RED -> {
//                if (pos % 10 != 0) attacks.add(b[pos-11]);
//                if (pos % 10 != 9) attacks.add(b[pos-9]);
//            }
//        }
//
//        return attacks;
//
//    }
    // public boolean move(Square moveTo);
}