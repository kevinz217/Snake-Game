import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Blockade {
    private int xCoord;
    private int yCoord;
    private BufferedImage image;

    public Blockade(int x, int y, String idkWhat) {
        xCoord = x;
        yCoord = y;
        try {
            image = ImageIO.read(new File(idkWhat));
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

    public Rectangle imgRect() {
        BufferedImage compare = null;
        try {
             compare = ImageIO.read(new File("src/assets/windows8_window.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        if (image.equals(compare))  {
            int asd = xCoord + 120;
            Rectangle rect = new Rectangle(asd, yCoord, 160, 40);
            return rect;
        } else {
            int imageHeight = getImage().getHeight();
            int imageWidth = getImage().getWidth();
            Rectangle rect = new Rectangle(xCoord, yCoord, imageWidth, imageHeight);
            return rect;
        }
    }
}
