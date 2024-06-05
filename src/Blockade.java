import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Blockade {
    private int xCord;
    private int yCord;
    private BufferedImage image;

    public Blockade(int x, int y, String idkWhat) {
        xCord = x;
        yCord = y;
        try {
            image = ImageIO.read(new File(idkWhat));
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
            int asd = xCord + 120;
            Rectangle rect = new Rectangle(asd, yCord, 160, 40);
            return rect;
        } else {
            int imageHeight = getImage().getHeight();
            int imageWidth = getImage().getWidth();
            Rectangle rect = new Rectangle(xCord, yCord, imageWidth, imageHeight);
            return rect;
        }
    }
}
