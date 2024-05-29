import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Fruit {
    private int xCoord;
    private int yCoord;
    private BufferedImage image;

    public Fruit(int x, int y, String fruitImg) {
        xCoord = x;
        yCoord = y;
        try {
            image = ImageIO.read(new File(fruitImg));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public int getxCoord() {
        return xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public BufferedImage getImage() {
        return image;
    }

    // we use a "bounding Rectangle" for detecting collision
    public Rectangle coinRect() {
        int imageHeight = getImage().getHeight();
        int imageWidth = getImage().getWidth();
        Rectangle rect = new Rectangle(xCoord, yCoord, imageWidth, imageHeight);
        return rect;
    }
}
