import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Snake {
    private final double MOVE_AMT = 0.4;
    private String direction;
    private int score;
    private BufferedImage right;
    private BufferedImage left;
    private double xCoord;
    private double yCoord;
    private BufferedImage up;
    private BufferedImage down;
    public Snake(String leftImg, String rightImg, String name) {
        direction = "right";
        xCoord = 50; // starting position is (50, 435), right on top of ground
        yCoord = 435;
        score = 0;
        try {
            left = ImageIO.read(new File("src/assets/left.png"));
            right = ImageIO.read(new File("src/assets/right.png"));
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

    public void faceRight() {
        direction = "right";
    }

    public void faceLeft() {
        direction = "left";
    }
    public void faceUp() {
        direction = "up";
    }

    public void faceDown() {
        direction = "down";
    }

    public void move() {
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
    }

    public void eatFruit() {
        score++;
        // add code for length
    }

}
