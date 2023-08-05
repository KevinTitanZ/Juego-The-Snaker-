package juego;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class SnakeGame extends JFrame {

    private static final int SIZE = 20;
    private static final int WIDTH = 400;
    private static final int HEIGHT = 400;

    private Snake snake;
    private Timer timer;
    private int score;

    private JLabel scoreLabel;

    private boolean gameStarted = false;

    private Nodo raiz;

    // Variables de obstaculo
    private int obstacleX;
    private int obstacleY;

    public SnakeGame() {
        snake = new Snake();
        timer = new Timer(100, new GameLoop());
        score = 0;

        setTitle("Snake Game");
        setSize(416, 458);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));

        getContentPane().add(scoreLabel, BorderLayout.NORTH);
        getContentPane().add(snake, BorderLayout.WEST);
        GridBagLayout gbl_snake = new GridBagLayout();
        gbl_snake.columnWidths = new int[]{0};
        gbl_snake.rowHeights = new int[]{0};
        gbl_snake.columnWeights = new double[]{Double.MIN_VALUE};
        gbl_snake.rowWeights = new double[]{Double.MIN_VALUE};
        snake.setLayout(gbl_snake);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                snake.processKey(e.getKeyCode(), true);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                snake.processKey(e.getKeyCode(), false);
            }
        });

        raiz = cargarPuntajes();
        placeApple();
        placeObstacle();

        showMenu();
    }

    private void handleMenuOption(int option) {
        switch (option) {
            case 0:
                startGame();
                break;
            case 1:
                showScores();
                showMenu();
                break;
            case 2:
                showInorden();
                showMenu();
                break;
            case 3:
                showPreorden();
                showMenu();
                break;
            case 4:
                showPostorden();
                showMenu();
                break;
            case 5:
                System.out.println("Gracias por jugar. ¡Hasta pronto!");
                cargarPuntajes();
                System.exit(0);
                break;
            default:
                System.out.println("Opción inválida. Por favor, seleccione una opción válida.");
        }
    }

    private void showInorden() {
        System.out.println("Recorrido Inorden:");
        inorden(raiz);
    }
    
    private void inorden(Nodo r) {
        if (r != null) {
            inorden(r.izdo);
            System.out.println(r.playerName + ": " + r.getScore());
            inorden(r.dcho);
        }
    }
    
    

    private void showPreorden() {
        System.out.println("Recorrido Preorden:");
        preorden(raiz);
    }

    private void preorden(Nodo r) {
        if (r != null) {
            System.out.println(r.playerName + ": " + r.getScore());
     
            
            
            
            
            
            
            
            preorden(r.izdo);
            preorden(r.dcho);
        }
    }

    private void showPostorden() {
        System.out.println("Recorrido Postorden:");
        postorden(raiz);
    }

    private void postorden(Nodo r) {
        if (r != null) {
            postorden(r.izdo);
            postorden(r.dcho);
            System.out.println(r.playerName + ": " + r.getScore());
        }
    }
    

    private Nodo cargarPuntajes() {
        Nodo raiz = null;
        try {
            Scanner scanner = new Scanner(new FileReader("scores.txt"));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                String playerName = parts[0];
                int score = Integer.parseInt(parts[1]);
                raiz = insert(raiz, score, playerName);
            }
            scanner.close();
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de puntuaciones.");
        }
        return raiz;
    }

    private Nodo insert(Nodo nodo, int score, String playerName) {
        if (nodo == null) {
            return new Nodo(playerName, score);
        }

        if (score < nodo.getScore()) {
            nodo.izdo = insert(nodo.izdo, score, playerName);
        } else {
            nodo.dcho = insert(nodo.dcho, score, playerName);
        }

        return nodo;
    }

    private void showScores() {
        System.out.println("Puntuaciones:");
        try {
            Scanner scanner = new Scanner(new FileReader("scores.txt"));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                System.out.println(line);
            }
            scanner.close();
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de puntuaciones.");
        }
    }

    private void placeApple() {
        snake.placeApple();
    }

    private void startGame() {
        if (!gameStarted) {
            gameStarted = true;
            timer.start();
        }
    }

    class GameLoop implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            snake.move();
            if (snake.checkCollision()) {
                gameOver();
            }
            if (snake.checkAppleCollision()) {
                snake.grow();
                score += 10;
                scoreLabel.setText("Score: " + score);
                placeApple();
            }
            snake.repaint();
        }
    }

    public void gameOver() {
        timer.stop();

        String playerName = JOptionPane.showInputDialog(this, "Enter your name:");
        saveScore(playerName, score);

        JOptionPane.showMessageDialog(this, "Game Over\nYour Score: " + score, "Snake Game", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    private void saveScore(String playerName, int score) {
        try {
            FileWriter fileWriter = new FileWriter("scores.txt", true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(playerName + "," + score);
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showMenu() {
        Object[] options = {"Iniciar juego", "Ver puntuaciones", "Inorden", "Preorden", "Postorden", "Salir"};
        int selectedOption = JOptionPane.showOptionDialog(this, "Bienvenido al juego Snake!",
                "Snake Game", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);

        handleMenuOption(selectedOption);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            SnakeGame snakeGame = new SnakeGame();
            snakeGame.setVisible(true);
        });
    }

    private void placeObstacle() {
        boolean overlap;
        do {
            overlap = false;
            obstacleX = (int) (Math.random() * (WIDTH / SIZE)) * SIZE;
            obstacleY = (int) (Math.random() * (HEIGHT / SIZE)) * SIZE;
            for (int i = 0; i < snake.length; i++) {
                if (snake.x[i] == obstacleX && snake.y[i] == obstacleY) {
                    overlap = true;
                    break;
                }
            }
        } while (overlap);
    }
}