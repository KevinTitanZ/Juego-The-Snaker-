package juego;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Snake extends JPanel {

    private static final int SIZE = 20;
    private static final int WIDTH = 400;
    private static final int HEIGHT = 400;

    private int appleX;
    private int appleY;
    int[] x;
    int[] y;
    int length;
    private int direction;
    private int borderThickness=5;
    private Color borderColor =Color.blue;
    
    private int obstacleX;
    private int obstacleY;

    private boolean upPressed;
    private boolean downPressed;
    private boolean leftPressed;
    private boolean rightPressed;

    public Snake() {
        x = new int[WIDTH * HEIGHT];
        y = new int[WIDTH * HEIGHT];
        length = 1;
        direction = KeyEvent.VK_RIGHT;

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);      ///COLOR DE FONDO
       //setBackground(new Color(128, 125, 125));    //COLOR BLANCO
       
        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                processKey(e.getKeyCode(), true);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                processKey(e.getKeyCode(), false);
            }
        });

        placeApple();
    }

    public void processKey(int keyCode, boolean pressed) {
        if (keyCode == KeyEvent.VK_UP)
            upPressed = pressed;
        else if (keyCode == KeyEvent.VK_DOWN)
            downPressed = pressed;
        else if (keyCode == KeyEvent.VK_LEFT)
            leftPressed = pressed;
        else if (keyCode == KeyEvent.VK_RIGHT)
            rightPressed = pressed;
    }

    public void move() {
        if (upPressed && direction != KeyEvent.VK_DOWN)
            direction = KeyEvent.VK_UP;
        else if (downPressed && direction != KeyEvent.VK_UP)
            direction = KeyEvent.VK_DOWN;
        else if (leftPressed && direction != KeyEvent.VK_RIGHT)
            direction = KeyEvent.VK_LEFT;
        else if (rightPressed && direction != KeyEvent.VK_LEFT)
            direction = KeyEvent.VK_RIGHT;

        for (int i = length - 1; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        if (direction == KeyEvent.VK_UP)
            y[0] -= SIZE;
        else if (direction == KeyEvent.VK_DOWN)
            y[0] += SIZE;
        else if (direction == KeyEvent.VK_LEFT)
            x[0] -= SIZE;
        else if (direction == KeyEvent.VK_RIGHT)
            x[0] += SIZE;
    }


    public boolean checkCollision() {
        if (x[0] < 0 || x[0] >= WIDTH || y[0] < 0 || y[0] >= HEIGHT) {
            return true;
        }

        for (int i = length; i > 0; i--) {
            if (x[0] == x[i] && y[0] == y[i]) {
                return true;
            }
        }

        return false;
    }

    public boolean checkAppleCollision() {
        return x[0] == appleX && y[0] == appleY;
    }

    public void grow() {
        length++;
    }

    
    //metodo para que aparezla la manzana
    public void placeApple() {
        boolean overlap;
        do {
            overlap = false;
            appleX = (int) (Math.random() * (WIDTH / SIZE)) * SIZE;
            appleY = (int) (Math.random() * (HEIGHT / SIZE)) * SIZE;
            for (int i = 0; i < length; i++) {
                if (x[i] == appleX && y[i] == appleY) {
                    overlap = true;
                    break;
                }
            }
        } while (overlap);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.RED);
        g.fillOval(appleX, appleY, SIZE, SIZE);

        g.setColor(Color.GREEN);
        for (int i = 0; i < length; i++) {
            g.fillRect(x[i], y[i], SIZE, SIZE);
        }

        g.setColor(Color.WHITE);
        for (int i = 0; i < WIDTH; i += SIZE) {
            g.drawLine(i, 0, i, HEIGHT - 1);
        }
        for (int i = 0; i < HEIGHT; i += SIZE) {
            g.drawLine(0, i, WIDTH - 1, i);
        }

        // Dibujar obstaculo
        g.setColor(Color.BLUE);//color de obstaculo
        g.fillRect(obstacleX, obstacleY, SIZE, SIZE);
    }
    private void placeObstacle() {
        obstacleX = 0;
        obstacleY = 0;
    }
}

    

