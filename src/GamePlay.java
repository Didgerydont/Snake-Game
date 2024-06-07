import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GamePlay extends JPanel implements KeyListener, ActionListener {

    // Snake dimensions
    private int[] snakeXLength = new int[750];
    private int[] snakeYLength = new int[750];

    // Movement variables
    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;

    // Snake Images
    private ImageIcon headLeft;
    private ImageIcon headRight;
    private ImageIcon headUp;
    private ImageIcon headDown;
    private ImageIcon tail;

    private Timer timer;
    private int delay = 100;

    private int lengthOfSnake = 3;
    private int moves = 0;
    private int score = 0;

    // Mouse parameters
    private int[] mousePotentialPosX = {25, 50, 75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400, 425, 450, 475, 500, 525, 550, 575, 600, 625, 650, 675, 700, 725, 750, 775, 800, 825, 850};
    private int[] mousePotentialPosY = {75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400, 425, 450, 475, 500, 525, 550, 575, 600, 625};

    private ImageIcon mouseImage;

    private Random random = new Random();

    private int mouseXPosition = random.nextInt(mousePotentialPosX.length);
    private int mouseYPosition = random.nextInt(mousePotentialPosY.length);

    public GamePlay() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        timer = new Timer(delay, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        if (moves == 0) {
            snakeXLength[0] = 100;
            snakeXLength[1] = 75;
            snakeXLength[2] = 50;

            snakeYLength[0] = 100;
            snakeYLength[1] = 100;
            snakeYLength[2] = 100;
        }

        // Display title
        ImageIcon titleImage = new ImageIcon("src/images/title.png");
        titleImage.paintIcon(this, graphics, 25, 5);

        // Display gameplay border
        graphics.setColor(Color.DARK_GRAY);
        graphics.drawRect(24, 74, 851, 577);

        // Display gameplay background
        graphics.setColor(Color.BLACK);
        graphics.fillRect(25, 75, 850, 575);

        // draw score and length
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("arial", Font.BOLD, 14));
        graphics.drawString("Score: " + score, 780, 30);
        graphics.drawString("Length: " + lengthOfSnake, 780, 50);

        // initial head position
        headRight = new ImageIcon("src/images/headRight.png");
        headRight.paintIcon(this, graphics, snakeXLength[0], snakeYLength[0]);

        for (int i = 0; i < lengthOfSnake; i++) {
            if (i == 0 && right) {
                headRight = new ImageIcon("src/images/headRight.png");
                headRight.paintIcon(this, graphics, snakeXLength[i], snakeYLength[i]);
            }
            if (i == 0 && left) {
                headLeft = new ImageIcon("src/images/headLeft.png");
                headLeft.paintIcon(this, graphics, snakeXLength[i], snakeYLength[i]);
            }
            if (i == 0 && up) {
                headUp = new ImageIcon("src/images/headUP.png");
                headUp.paintIcon(this, graphics, snakeXLength[i], snakeYLength[i]);
            }
            if (i == 0 && down) {
                headDown = new ImageIcon("src/images/headDown.png");
                headDown.paintIcon(this, graphics, snakeXLength[i], snakeYLength[i]);
            }
            if (i != 0) {
                tail = new ImageIcon("src/images/tail.png");
                tail.paintIcon(this, graphics, snakeXLength[i], snakeYLength[i]);
            }
        }

        // Draw the mouse
        mouseImage = new ImageIcon("src/images/mouse.png");
        mouseImage.paintIcon(this, graphics, mousePotentialPosX[mouseXPosition], mousePotentialPosY[mouseYPosition]);

        // Check if the snake eats the mouse
        if (mousePotentialPosX[mouseXPosition] == snakeXLength[0] && mousePotentialPosY[mouseYPosition] == snakeYLength[0]) {
            score += 5;
            lengthOfSnake++;
            mouseXPosition = random.nextInt(mousePotentialPosX.length);
            mouseYPosition = random.nextInt(mousePotentialPosY.length);
        }

        // Game restart
        for (int i = 1; i < lengthOfSnake; i++) {
            if (snakeXLength[i] == snakeXLength[0] && snakeYLength[i] == snakeYLength[0]) {
                right = false;
                left = false;
                up = false;
                down = false;

                graphics.setColor(Color.RED);
                graphics.setFont(new Font("arial", Font.BOLD, 40));
                graphics.drawString("Game Over! Score: " + score, 250, 300);

                graphics.setColor(Color.WHITE);
                graphics.setFont(new Font("arial", Font.BOLD, 20));
                graphics.drawString("Press Enter to Restart", 350, 340);
            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        timer.start();
        // move right animation
        if (right) {
            for (int n = lengthOfSnake - 1; n >= 0; n--) {
                snakeYLength[n + 1] = snakeYLength[n];
            }
            for (int n = lengthOfSnake; n >= 0; n--) {
                if (n == 0) {
                    snakeXLength[n] = snakeXLength[n] + 25;
                } else {
                    snakeXLength[n] = snakeXLength[n - 1];
                }
                if (snakeXLength[n] > 850) {
                    snakeXLength[n] = 25;
                }
            }
            repaint();
        }
        // move left animation
        if (left) {
            for (int n = lengthOfSnake - 1; n >= 0; n--) {
                snakeYLength[n + 1] = snakeYLength[n];
            }
            for (int n = lengthOfSnake; n >= 0; n--) {
                if (n == 0) {
                    snakeXLength[n] = snakeXLength[n] - 25;
                } else {
                    snakeXLength[n] = snakeXLength[n - 1];
                }
                if (snakeXLength[n] < 25) {
                    snakeXLength[n] = 850;
                }
            }
            repaint();
        }
        // move up animation
        if (up) {
            for (int n = lengthOfSnake - 1; n >= 0; n--) {
                snakeXLength[n + 1] = snakeXLength[n];
            }
            for (int n = lengthOfSnake; n >= 0; n--) {
                if (n == 0) {
                    snakeYLength[n] = snakeYLength[n] - 25;
                } else {
                    snakeYLength[n] = snakeYLength[n - 1];
                }
                if (snakeYLength[n] < 75) {
                    snakeYLength[n] = 625;
                }
            }
            repaint();
        }
        // move down animation
        if (down) {
            for (int n = lengthOfSnake - 1; n >= 0; n--) {
                snakeXLength[n + 1] = snakeXLength[n];
            }
            for (int n = lengthOfSnake; n >= 0; n--) {
                if (n == 0) {
                    snakeYLength[n] = snakeYLength[n] + 25;
                } else {
                    snakeYLength[n] = snakeYLength[n - 1];
                }
                if (snakeYLength[n] > 625) {
                    snakeYLength[n] = 75;
                }
            }
            repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            moves = 0;
            score = 0;
            lengthOfSnake = 3;
            repaint();
        }

        // move right
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            moves++;
            right = true;
            if (!left) {
                right = true;
            } else {
                right = false;
                left = true;
            }
            up = false;
            down = false;
        }

        // move left
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            moves++;
            left = true;
            if (!right) {
                left = true;
            } else {
                left = false;
                right = true;
            }
            up = false;
            down = false;
        }

        // move up
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            moves++;
            up = true;
            if (!down) {
                up = true;
            } else {
                up = false;
                down = true;
            }
            left = false;
            right = false;
        }

        // move down
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            moves++;
            down = true;
            if (!up) {
                down = true;
            } else {
                down = false;
                up = true;
            }
            left = false;
            right = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
