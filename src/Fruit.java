import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Fruit {
    private int xCord;
    private int yCord;
    private BufferedImage image;

    public Fruit(int x, int y, String fruitImg) {
        xCord = x;
        yCord = y;
        try {
            image = ImageIO.read(new File(fruitImg));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public int getxCord() {
        return xCord;
    }

    public int getyCord() {
        return yCord;
    }
    public void setxCord(int newX) {
        xCord = newX;
    }

    public void setyCord(int newY) {
        yCord = newY;
    }

    public BufferedImage getImage() {
        return image;
    }

    // we use a "bounding Rectangle" for detecting collision
    public Rectangle coinRect() {
        int imageHeight = getImage().getHeight();
        int imageWidth = getImage().getWidth();
        Rectangle rect = new Rectangle(xCord, yCord, imageWidth, imageHeight);
        return rect;
    }
}
