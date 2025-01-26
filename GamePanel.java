package snake_game;

import javax.swing.*;

import main.Main1;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 1300;
    static final int SCREEN_HEIGHT = 750;
    static final int UNIT_SIZE = 50;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
    static final int DELAY = 175;

    final int[] x = new int[GAME_UNITS];
    final int[] y = new int[GAME_UNITS];
    int bodyParts = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;
    String userName;
    String snakeColor;
    boolean buttonsDisplayed = false; // Flag to track if buttons are displayed
JFrame fr;
    GamePanel(String userName, String snakeColor,JFrame fr) {
        this.userName = userName;
        this.snakeColor = snakeColor;
        this.fr=fr;
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public GamePanel() {
		// TODO Auto-generated constructor stub
    	
	}

	public void startGame() {
        newApple();
        running = true;
        bodyParts = 6; // Reset body parts on new game
        applesEaten = 0; // Reset score on new game
        direction = 'R'; // Reset direction on new game
        timer = new Timer(DELAY, this);
        timer.start();
        removeAll(); // Ensure no buttons are present before starting the game
        buttonsDisplayed = false; // Reset button display flag
        this.revalidate(); // Refresh layout
        this.repaint(); // Ensure the screen is updated
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (running) {
            // Draw the apple
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            // Draw the snake
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    // Head
                    g.setColor(getSnakeColor());
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    // Body
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }

            // Draw the score box with transparency
            Graphics2D g2d = (Graphics2D) g;
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f)); // Set transparency to 70%
            g2d.setColor(Color.white);
            g2d.fillRect(SCREEN_WIDTH - 220, 10, 210, 50); // Score box dimensions
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f)); // Reset to full opacity
            g.setColor(Color.black);
            g.setFont(new Font("Arial", Font.BOLD, 20)); // Font settings
            g.drawString("Score: " + applesEaten, SCREEN_WIDTH - 200, 40); // Score text position
        } else {
            showGameOver(); // Call to show buttons when game is over
        }
    }

    public Color getSnakeColor() {
        switch (snakeColor.toLowerCase()) {
            case "red":
                return Color.RED;
            case "blue":
                return Color.BLUE;
            case "white":
                return Color.WHITE;
            case "gray":
                return Color.GRAY;
            case "purple":
                return new Color(128, 0, 128); // Custom purple color
            default:
                return Color.GREEN; // Default color if not matched
        }
    }

    public void newApple() {
        appleX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        appleY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }

    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }

    public void checkApple() {
        if ((x[0] == appleX) && (y[0] == appleY)) {
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }

    public void checkCollisions() {
        // Check if head collides with body
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }
        }
        // Check if head touches borders
        if (x[0] < 0 || x[0] >= SCREEN_WIDTH || y[0] < 0 || y[0] >= SCREEN_HEIGHT) {
            running = false;
        }

        if (!running) {
            timer.stop();
        }
    }

    public void showGameOver() {
        if (!buttonsDisplayed) { // Only add buttons if they haven't been displayed yet
            // Draw the restart button
            JButton restartButton = new JButton("Restart");
            restartButton.setBounds((SCREEN_WIDTH / 2) - 100, (SCREEN_HEIGHT / 2) + 10, 200, 50);
            restartButton.setFont(new Font("Arial", Font.BOLD, 20));
            restartButton.addActionListener(e -> restartGame());
            this.add(restartButton);

            // Draw the exit button
            JButton exitButton = new JButton("Exit");
            exitButton.setBounds((SCREEN_WIDTH / 2) - 100, (SCREEN_HEIGHT / 2) + 70, 200, 50);
            exitButton.setFont(new Font("Arial", Font.BOLD, 20));
            exitButton.addActionListener(e -> {
            
            
                fr.setVisible(false);

            	new Main1();
            });
            this.add(exitButton);

            buttonsDisplayed = true; // Set flag to true to indicate buttons are now displayed

            // Refresh the panel to show buttons
            this.revalidate();
            this.repaint();
        }
    }

    public void restartGame() {
        // Reset game state
        bodyParts = 6;
        applesEaten = 0;
        direction = 'R';
        running = true; // Set running to true to start the game

        // Initialize the snake's position
        for (int i = 0; i < bodyParts; i++) {
            x[i] = 0; // Start snake at (0, 0)
            y[i] = 0; // Start snake at (0, 0)
        }

        newApple(); // Generate a new apple
        timer.start(); // Start the timer
        removeAll(); // Remove any buttons before starting the game
        buttonsDisplayed = false; // Reset button display flag
        this.revalidate(); // Refresh layout
        repaint(); // Repaint the panel to reflect changes
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}