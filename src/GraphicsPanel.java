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
    private Snake snakeH;
    private ArrayList<Snake> body;
    private boolean[] pressedKeys;
    private ArrayList<Blockade> images;
    private Timer timer;
    private Timer timer2;
    private int time;
    private boolean gameOver;

    private Fruit apple;
    private Fruit orange;

    public GraphicsPanel(String name) {
        try {
            background = ImageIO.read(new File("src/assets/background.png"));
        } catch (IOException e) {
            System.out.println("No background ! !");
            System.out.println(e.getMessage());
        }
        gameOver = false;
        snakeH = new Snake(40, 400, "src/assets/test.png");
        images = new ArrayList<>();
        body = new ArrayList<Snake>();
        pressedKeys = new boolean[128];
        time = 0;
        timer = new Timer(1000, this);
        timer2 = new Timer(500, this);
        timer.start();
        timer2.start();
        apple = new Fruit(40, 240, "src/assets/apple.png");
        orange = new Fruit(240, 40, "src/assets/orange.png");
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
        g.drawImage(snakeH.getPlayerImage(), snakeH.getxCord(), snakeH.getyCord(), null);
        for (int i = 0; i < body.size(); i++) {
            Snake bodyPart = body.get(i);

            g.drawImage(bodyPart.getPlayerImage(), bodyPart.getxCord(), bodyPart.getyCord(), null);
        }
        g.drawImage(apple.getImage(), apple.getxCord(), apple.getyCord(), null);
        g.drawImage(orange.getImage(), orange.getxCord(), orange.getyCord(), null);

        // draws each blockade
        for (int i = 0; i < images.size(); i++) {
            Blockade image = images.get(i);
            g.drawImage(image.getImage(), image.getxCord(), image.getyCord(), null);
        }

        // draw score
        g.setFont(new Font("Courier New", Font.PLAIN, 24));
        g.setColor(Color.WHITE);
        g.drawString("Your Score: " + snakeH.getScore(), 20, 40);
        g.drawString("Time: " + time, 20, 70);
        g.drawString("Test val: " + snakeH.getCount(), 20, 100);
        g.drawString("Timer2 val: " + timer2.getDelay(), 20, 130);
        g.drawString("Total Leaves: " + images.size(), 20, 160);

        // draw mouse box
        Point mouseP = getMousePosition();
        if (mouseP != null) {
            g.fillRect(mouseP.x - 10, mouseP.y - 10, 20, 20);
            Rectangle rectangle = new Rectangle(mouseP.x - 10, mouseP.y - 10, 10, 10);
            // gameover if touches the snake
            if (snakeH.playerRect().intersects(rectangle)) {
                gameOver = true;
            }

            for (int i = 0; i < body.size(); i++) {
                Snake bodyPart = body.get(i);

                if (bodyPart.playerRect().intersects(rectangle)) {
                    gameOver = true;
                }
            }
        }

        // player moves left (A)
        if (pressedKeys[65]) {
            if (!snakeH.getDirection().equals("right")) {
                snakeH.faceDirection("left");
            }
        }

        // player moves right (D)
        if (pressedKeys[68]) {
            if (!snakeH.getDirection().equals("left")) {
                snakeH.faceDirection("right");
            }
        }

        // player moves up (W)
        if (pressedKeys[87]) {
            if (!snakeH.getDirection().equals("up")) {
                snakeH.faceDirection("down");
            }
        }

        // player moves down (S)
        if (pressedKeys[83]) {
            if (!snakeH.getDirection().equals("down")) {
                snakeH.faceDirection("up");
            }
        }

        // fruit eating
        if (snakeH.playerRect().intersects(apple.coinRect())) {
            snakeH.eatFruit();
            Snake part = new Snake(apple.getxCord(), apple.getyCord(), "src/assets/test.png");
            int randomX = (int) (Math.random() * 16);
            int randomY = (int) (Math.random() * 14);
            apple.setxCord(randomX * 40);
            apple.setyCord(randomY * 40);
            body.add(part);
        } else if (snakeH.playerRect().intersects(orange.coinRect())) {
            snakeH.eatFruit();
            Snake part = new Snake(orange.getxCord(), orange.getyCord(), "src/assets/test.png");
            int randomX = (int) (Math.random() * 16);
            int randomY = (int) (Math.random() * 14);
            orange.setxCord(randomX * 40);
            orange.setyCord(randomY * 40);
            body.add(part);
        }

        // draws text when its game over or moves the snake
        if (!gameOver) {
            snakeH.move();
        } else {
            timer.stop();
            timer2.stop();
            g.setFont(new Font("Sans Serif", Font.BOLD, 70));
            g.setColor(Color.RED);
            g.drawString("GAME OVER", 120, 300);
            clearLeaves();
        }

        // game over conditions
        if (snakeH.getxCord() < 0 || snakeH.getyCord() < 0 || snakeH.getxCord() >= 630 || snakeH.getyCord() >= 560) {
            gameOver = true;
        }

        for (int i = 0; i < body.size(); i++) {
            Snake bodyPart = body.get(i);

            if (snakeH.playerRect().intersects(bodyPart.playerRect())) {
                gameOver = true;
            }
        }
    }

    private void clearLeaves() {
        for (int i = images.size() - 1; i >= 0; i--) {
            images.remove(i);
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
        if (key == 69) {
            snakeH.eatFruit();
        }
    }

    // ----- MouseListener interface methods -----
    public void mouseClicked(MouseEvent e) { }  // unimplemented; if you move your mouse while clicking,
    // this method isn't called, so mouseReleased is best

    public void mousePressed(MouseEvent e) { } // unimplemented

    public void mouseReleased(MouseEvent e) {
        // removes blockade on click
        if (e.getButton() == MouseEvent.BUTTON1) {  // left mouse click
            Point mouseClickLocation = e.getPoint();
            for (int i = 0; i < images.size(); i++) {
                Blockade image = images.get(i);
                if (image.imgRect().contains(mouseClickLocation)) {
                    images.remove(image);
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
        // generates the blockades based on the second timer
        if (e.getSource() == timer2) {
            int newDelay = 500 - (int) (snakeH.getScore() * 7.5);
            if (newDelay > 0) {
                timer2.setDelay(newDelay);
            }
            double random = Math.random();
            if (random > 0.65) {
                int randomX = (int) (Math.random() * 600);
                int randomY = (int) (Math.random() * 560);
                double random2 = Math.random();
                if (random2 > 0.5) {
                    Blockade newImage = new Blockade(randomX, randomY, "src/assets/download.jpg");
                    images.add(newImage);
                } else {
                    Blockade newImage = new Blockade(randomX, randomY, "src/assets/download.jpg");
                    images.add(newImage);
                }
            }
        }
    }

}
