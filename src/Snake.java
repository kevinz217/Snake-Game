import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Snake {
    private final double MOVE_AMT = 40;
    private String direction;
    private int score;
    private BufferedImage right;
    private BufferedImage left;
    private int xCoord;
    private int yCoord;
    private BufferedImage up;
    private BufferedImage down;
    private int count;
    public Snake(String leftImg, String rightImg, String name) {
        direction = "right";
        xCoord = 40; // starting position is (50, 435), right on top of ground
        yCoord = 400;
        score = 0;
        count = 0;
        try {
            left = ImageIO.read(new File(leftImg));
            right = ImageIO.read(new File(rightImg));
            // temp images
            up = ImageIO.read(new File(rightImg));
            down = ImageIO.read(new File(rightImg));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public int getxCoord() {
        return (int) xCoord;
    }

    public int getyCoord() {
        return (int) yCoord;
    }

    public int getScore() {
        return score;
    }
    public String getDirection() {return direction;}
    public int getCount() {
        return count;
    }

    public void faceDirection(String direction) {
        this.direction = direction;
    }

    public void move() {
        count++;
        if (count == 220) {
            if (direction.equals("up")) {
                yCoord += MOVE_AMT;
            }
            if (direction.equals("down")) {
                yCoord -= MOVE_AMT;
            }
            if (direction.equals("left")) {
                xCoord -= MOVE_AMT;
            }
            if (direction.equals("right")) {
                xCoord += MOVE_AMT;
            }
            count = 0;
        }
    }

    public BufferedImage getPlayerImage() {
        if (direction.equals("up")) {
            return up;
        }
        if (direction.equals("down")) {
            return down;
        }
        if (direction.equals("left")) {
            return left;
        }
        if (direction.equals("right")) {
            return right;
        }
        return null;
    }

    public void eatFruit() {
        score++;
        // add code for length
    }
    public Rectangle playerRect() {
        int imageHeight = getPlayerImage().getHeight();
        int imageWidth = getPlayerImage().getWidth();
        Rectangle rect = new Rectangle( xCoord, yCoord, imageWidth, imageHeight);
        return rect;
    }
}
