import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GraphicsPanel extends JPanel implements KeyListener, MouseListener, ActionListener
        {
    private BufferedImage background;
    private Snake snake;
    private boolean[] pressedKeys;
    private ArrayList<Blockade> images;
    private Timer timer;
    private Timer timer2;
    private int time;
    private boolean gameOver;

    public GraphicsPanel(String name) {
        try {
            background = ImageIO.read(new File("src/assets/background.png"));
        } catch (IOException e) {
            System.out.println("No background ! !");
            System.out.println(e.getMessage());
        }
        gameOver = false;
        snake = new Snake("src/assets/test.png", "src/assets/test.png", name);
        images = new ArrayList<>();
        pressedKeys = new boolean[128];
        time = 0;
        timer = new Timer(1000, this); // this Timer will call the actionPerformed interface method every 1000ms = 1 second
        timer2 = new Timer(500, this);
        timer.start();
        timer2.start();
        addKeyListener(this);
        addMouseListener(this);
        setFocusable(true); // this line of code + one below makes this panel active for keylistener events
        requestFocusInWindow(); // see comment above
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);  // just do this
        g.drawImage(background, 0, 0, null);
        // black background
        setBackground(Color.black);
        g.drawImage(snake.getPlayerImage(), snake.getxCoord(), snake.getyCoord(), null);
        Fruit apple = new Fruit(320, 400, "src/assets/apple.png");
        Fruit orange = new Fruit(320, 480, "src/assets/orange.png");
        g.drawImage(apple.getImage(), apple.getxCoord(), apple.getyCoord(), null);
        g.drawImage(orange.getImage(), orange.getxCoord(), orange.getyCoord(), null);

        // this loop does two things:  it draws each Coin that gets placed with mouse clicks,
        // and it also checks if the player has "intersected" (collided with) the Coin, and if so,
        // the score goes up and the Coin is removed from the arraylist
        /*
        for (int i = 0; i < coins.size(); i++) {
            Coin coin = coins.get(i);
            g.drawImage(coin.getImage(), coin.getxCoord(), coin.getyCoord(), null); // draw Coin
            if (player.playerRect().intersects(coin.coinRect())) { // check for collision
                playCoinSound();
                player.collectCoin();
                coins.remove(i);
                i--;
            }
        }
            */
        // draw score
        g.setFont(new Font("Courier New", Font.BOLD, 24));
        g.setColor(Color.WHITE);
        g.drawString("Your Score: " + snake.getScore(), 20, 40);
        g.drawString("Time: " + time, 20, 70);
        g.drawString("Test val: " + snake.getCount(), 20, 100);
        Point mouseP = getMousePosition();
        g.drawRect(mouseP.x, mouseP.y, 40, 40);
        g.fillRect(mouseP.x, mouseP.y, 40, 40);
        Rectangle rectangle = new Rectangle(mouseP.x, mouseP.y, 40, 40);

        // player moves left (A)
        if (pressedKeys[65]) {
            if (!snake.getDirection().equals("right")) {
                snake.faceDirection("left");
            }
        }

        // player moves right (D)
        if (pressedKeys[68]) {
            if (!snake.getDirection().equals("left")) {
                snake.faceDirection("right");
            }
        }

        // player moves up (W)
        if (pressedKeys[87]) {
            if (!snake.getDirection().equals("up")) {
                snake.faceDirection("down");
            }
        }

        // player moves down (S)
        if (pressedKeys[83]) {
            if (!snake.getDirection().equals("down")) {
                snake.faceDirection("up");
            }
        }
        if (!gameOver) {
            snake.move();
            timer.stop();
            timer2.stop();
        }


        for (int i = 0; i < images.size(); i++) {
            Blockade image = images.get(i);
            g.drawImage(image.getImage(), image.getxCoord(), image.getyCoord(), null); // draw leaves
        }

        if (snake.playerRect().intersects(rectangle)) {
            gameOver = true;
        }
    }

    private void playCoinSound() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/assets/coin.wav").getAbsoluteFile());
            Clip coinClip = AudioSystem.getClip();
            coinClip.open(audioInputStream);
            coinClip.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // ----- KeyListener interface methods -----
    public void keyTyped(KeyEvent e) { } // unimplemented

    public void keyPressed(KeyEvent e) {
        // see this for all keycodes: https://stackoverflow.com/questions/15313469/java-keyboard-keycodes-list
        // A = 65, D = 68, S = 83, W = 87, left = 37, up = 38, right = 39, down = 40, space = 32, enter = 10
        int key = e.getKeyCode();
        pressedKeys[key] = true;
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        pressedKeys[key] = false;
    }

    // ----- MouseListener interface methods -----
    public void mouseClicked(MouseEvent e) { }  // unimplemented; if you move your mouse while clicking,
    // this method isn't called, so mouseReleased is best

    public void mousePressed(MouseEvent e) { } // unimplemented

    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {  // left mouse click
            Point mouseClickLocation = e.getPoint();
            for (int i = 0; i < images.size(); i++) {
                Blockade image = images.get(i);
                if (image.aRect().contains(mouseClickLocation)) {
                    images.remove(image);
                    snake.eatFruit();
                }
            }
        }
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
        gameOver = true;
    }

    // ACTIONLISTENER INTERFACE METHODS: used for buttons AND timers!
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == timer) {
            time++;
        }
        if (e.getSource() == timer2) {
            timer2.setDelay(500 - (int) (snake.getScore() * 2.3));
            double random = Math.random();
            //if (random > 0.7) {
                int randomX = (int) (Math.random() * 600);
                int randomY = (int) (Math.random() * 560);
                Blockade newImage = new Blockade(randomX, randomY, "src/assets/download.jpg");
                images.add(newImage);
            //}
        }
    }

}
