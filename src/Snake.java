import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Snake {
    private final double MOVE_AMT = 40;
    private String direction;
    private int score;
    private BufferedImage body;
    private int xCord;
    private int yCord;
    private int count;
    private class Block {
        int x;
        int y;

        Block(int x, int y){
            this.x = x;
            this.y =y;
        }
        public int getX(){
            return x;
        }
        public int getY(){
            return y;
        }

        public void setX(int n){
            x = n;
        }

        public void setY(int y) {
            this.y = y;
        }
    }
    public Snake(int x, int y, String leftImg) {
        direction = "right";
        xCord = x;
        yCord = y;
        score = 0;
        count = 0;
        try {
            body = ImageIO.read(new File(leftImg));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    public int getxCord() {
        return (int) xCord;
    }

    public int getyCord() {
        return (int) yCord;
    }

    public int getScore() {
        return score;
    }
    public String getDirection() {return direction;}
    public int getCount() {
        return count;
    }

    public void setScore(int num) {score = num;}

    public void faceDirection(String direction) {
        this.direction = direction;
    }

    public void move() {
        count++;
        if (count == 230) {
            if (direction.equals("up")) {
                yCord += MOVE_AMT;
            }
            if (direction.equals("down")) {
                yCord -= MOVE_AMT;
            }
            if (direction.equals("left")) {
                xCord -= MOVE_AMT;
            }
            if (direction.equals("right")) {
                xCord += MOVE_AMT;
            }
            count = 0;
        }
    }

    public BufferedImage getPlayerImage() {
        return body;
    }

    public void eatFruit() {
        score++;
    }

    public Rectangle playerRect() {
        int imageHeight = getPlayerImage().getHeight();
        int imageWidth = getPlayerImage().getWidth();
        Rectangle rect = new Rectangle(xCord, yCord, imageWidth, imageHeight);
        return rect;
    }
}
