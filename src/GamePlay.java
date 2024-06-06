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
    private int[] mousePosX = { 25, 50, 75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400, 425, 450, 475, 500, 525, 550, 575, 600, 625, 650, 675, 700, 725, 750, 775, 800, 825, 850};
    private int[] mousePosY = { 25, 50, 75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400, 425, 450, 475, 500, 525, 550, 575, 600, 625};



    private ImageIcon mouseImage;

    private Random random = new Random();

    private int xpos = random.nextInt(mousePosX.length);
    private int ypos = random.nextInt(mousePosY.length);
    public GamePlay(){
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        timer = new Timer(delay, this);
        timer.restart();



    }

    @Override
    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);

        if(moves == 0){
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

        // initial head position
        headRight = new ImageIcon("src/images/headRight.png");
        headRight.paintIcon(this, graphics, snakeXLength[0],snakeYLength[0]);

        for(int i = 0; i<lengthOfSnake; i++){
            if(i==0 && right){
                headRight = new ImageIcon("src/images/headRight.png");
                headRight.paintIcon(this, graphics, snakeXLength[i], snakeYLength[i]);
            }
            if (i==0 && left) {
                headLeft = new ImageIcon("src/images/headLeft.png");
                headLeft.paintIcon(this, graphics, snakeXLength[i], snakeYLength[i]);
            }
            if(i==0 && up){
                headUp = new ImageIcon("src/images/headUP.png");
                headUp.paintIcon(this, graphics, snakeXLength[i], snakeYLength[i]);
            }
            if(i==0 && down){
                headDown = new ImageIcon("src/images/headDown.png");
                headDown.paintIcon(this, graphics, snakeXLength[i], snakeYLength[i]);
            }

            if(i != 0){
                tail = new ImageIcon("src/images/tail.png");
                tail.paintIcon(this, graphics, snakeXLength[i], snakeYLength[i]);
            }

            mouseImage = new ImageIcon("src/images/mouse.png");
        }


    }


    @Override
    public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub
        //Part4
        timer.restart();
        // move right animation
        if(right)
        {
            for(int n = lengthOfSnake-1; n>=0;n--)
            {
                snakeYLength[n+1] = snakeYLength[n];
            }
            for(int n = lengthOfSnake; n>=0; n--)
            {
                if (n==0)
                {
                    snakeXLength[n] = snakeXLength[n]+25;
                }
                else
                {
                    snakeXLength[n] = snakeXLength[n-1];
                }
                if(snakeXLength[n] >850)
                {
                    snakeXLength[n] = 25;
                }

            }
            repaint();
        }
        // move left animation
        if(left)
        {
            for(int n = lengthOfSnake-1; n>=0;n--)
            {
                snakeYLength[n+1] = snakeYLength[n];
            }
            for(int n = lengthOfSnake; n>=0; n--)
            {
                if (n==0)
                {
                    snakeXLength[n] = snakeXLength[n]-25;
                }
                else
                {
                    snakeXLength[n] = snakeXLength[n-1];
                }
                if(snakeXLength[n] < 25)
                {
                    snakeXLength[n] = 850;
                }

            }
            repaint();

        }
        // move up animation
        if(up)
        {
            for(int n = lengthOfSnake-1; n>=0;n--)
            {
                snakeXLength[n+1] = snakeXLength[n];
            }
            for(int n = lengthOfSnake; n>=0; n--)
            {
                if (n==0)
                {
                    snakeYLength[n] = snakeYLength[n]-25;
                }
                else
                {
                    snakeYLength[n] = snakeYLength[n-1];
                }
                if(snakeYLength[n] < 75)
                {
                    snakeYLength[n] = 625;
                }

            }
            repaint();

        }
        // move down animation
        if(down)
        {
            for(int n = lengthOfSnake-1; n>=0;n--)
            {
                snakeXLength[n+1] = snakeXLength[n];
            }
            for(int n = lengthOfSnake; n>=0; n--)
            {
                if (n==0)
                {
                    snakeYLength[n] = snakeYLength[n]+25;
                }
                else
                {
                    snakeYLength[n] = snakeYLength[n-1];
                }
                if(snakeYLength[n] > 625)
                {
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
        // move right
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            moves++;
            right = true;
            if(!left){
                right = true;
            } else {
              right = false;
              left = true;
            }
            up = false;
            down = false;
        }

        // move left
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            moves++;
            left = true;
            if(!right){
                left = true;
            } else {
                left = false;
                right = true;
            }
            up = false;
            down = false;
        }

        // move up
        if(e.getKeyCode() == KeyEvent.VK_UP){
            moves++;
            up = true;
            if(!down){
                up = true;
            } else {
                up = false;
                down = true;
            }
            left = false;
            right = false;
        }

        // move down
        if(e.getKeyCode() == KeyEvent.VK_DOWN){
            moves++;
            down = true;
            if(!up){
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
