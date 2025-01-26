package spaceinvaders;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class App extends JPanel implements ActionListener, KeyListener {
    // Board settings
    int tileSize = 32;
    int rows = 16;
    int columns = 16;

    int boardWidth = tileSize * columns;
    int boardHeight = tileSize * rows;

    // Images for ship and aliens
    Image shipImg;
    Image alienImg;
    Image alienCyanImg;
    Image alienMagentaImg;
    Image alienYellowImg;
    ArrayList<Image> alienImgArray;

    // Ship
    class Block {
        int x;
        int y;
        int width;
        int height;
        Image img;
        boolean alive = true; // Used for aliens
        boolean used = false; // Used for bullets

        Block(int x, int y, int width, int height, Image img) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.img = img;
        }
    }

    // Ship settings
    int shipWidth = tileSize * 2;
    int shipHeight = tileSize;
    int shipX = tileSize * columns / 2 - tileSize;
    int shipY = tileSize * rows - tileSize * 2;
    int shipVelocityX = 6; // Ship moving speed
    Block ship;

    // Aliens
    ArrayList<Block> alienArray;
    int alienWidth = tileSize * 2;
    int alienHeight = tileSize;
    int alienX = tileSize;
    int alienY = tileSize;

    int alienRows = 2;
    int alienColumns = 3;
    int alienCount = 0; // Number of aliens to defeat
    int alienVelocityX = 1; // Alien moving speed

    // Bullets
    ArrayList<Block> bulletArray;
    int bulletWidth = tileSize / 8;
    int bulletHeight = tileSize / 2;
    int bulletVelocityY = -10; // Bullet moving speed

    Timer gameLoop;
    boolean gameOver = false;
    int score = 0, point = 0;

    // Buttons
    JButton restartButton;
    JButton exitButton;

    // Constructor
    public App() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(Color.black);
        setFocusable(true);
        addKeyListener(this);

        // Load images
        shipImg = new ImageIcon(getClass().getResource("ship.png")).getImage();
        alienImg = new ImageIcon(getClass().getResource("alien.png")).getImage();
        alienCyanImg = new ImageIcon(getClass().getResource("alien-cyan.png")).getImage();
        alienMagentaImg = new ImageIcon(getClass().getResource("alien-magenta.png")).getImage();
        alienYellowImg = new ImageIcon(getClass().getResource("alien-yellow.png")).getImage();

        alienImgArray = new ArrayList<>();
        alienImgArray.add(alienImg);
        alienImgArray.add(alienCyanImg);
        alienImgArray.add(alienMagentaImg);
        alienImgArray.add(alienYellowImg);

        ship = new Block(shipX, shipY, shipWidth, shipHeight, shipImg);
        alienArray = new ArrayList<>();
        bulletArray = new ArrayList<>();

        // Game timer
        gameLoop = new Timer(1000 / 60, this);
        createAliens();
        gameLoop.start();

        // Create buttons
        restartButton = new JButton("Restart");
        exitButton = new JButton("Exit");

        // Set button actions
        restartButton.addActionListener(e -> restartGame());
        exitButton.addActionListener(e -> System.exit(0));

        // Initially hide the buttons
        restartButton.setVisible(false);
        exitButton.setVisible(false);

        // Add buttons to the panel
        this.setLayout(null); // Use null layout for precise positioning
        restartButton.setBounds(boardWidth / 2 - 100, boardHeight - 50, 80, 30); // Positioning
        exitButton.setBounds(boardWidth / 2 + 20, boardHeight - 50, 80, 30);
        this.add(restartButton);
        this.add(exitButton);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // Ship
        g.drawImage(ship.img, ship.x, ship.y, ship.width, ship.height, null);

        // Aliens
        for (Block alien : alienArray) {
            if (alien.alive) {
                g.drawImage(alien.img, alien.x, alien.y, alien.width, alien.height, null);
            }
        }

        // Bullets
        g.setColor(Color.white);
        for (Block bullet : bulletArray) {
            if (!bullet.used) {
                g.drawRect(bullet.x, bullet.y, bullet.width, bullet.height);
            }
        }

        // Score
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 32));
        if (gameOver) {
            g.drawString("Game Over: " + score, 10, 35);

            // Show the restart and exit buttons when game is over
            restartButton.setVisible(true);
            exitButton.setVisible(true);
        } else {
            g.drawString(String.valueOf(score), 10, 35);
        }
    }

    public void move() {
        if (gameOver) return; // Stop moving when game is over

        // Move aliens
        for (Block alien : alienArray) {
            if (score >= 100 && point >= 100) {
                alienVelocityX += 5;
                point = 0;
            }
            if (alien.alive) {
                alien.x += alienVelocityX;

                // If alien touches the borders
                if (alien.x + alien.width >= boardWidth || alien.x <= 0) {
                    alienVelocityX *= -1;
                    alien.x += alienVelocityX * 2;

                    // Move all aliens down by one row
                    for (Block a : alienArray) {
                        a.y += alienHeight;
                    }
                }

                // If an alien reaches the ship level
                if (alien.y >= ship.y) {
                    gameOver = true;
                }
            }
        }

        // Move bullets
        for (Block bullet : bulletArray) {
            bullet.y += bulletVelocityY;

            // Bullet collision with aliens
            for (Block alien : alienArray) {
                if (!bullet.used && alien.alive && detectCollision(bullet, alien)) {
                    bullet.used = true;
                    alien.alive = false;
                    alienCount--;
                    score += 100;
                }
            }
        }

        // Clear bullets that are off-screen or used
        bulletArray.removeIf(bullet -> bullet.used || bullet.y < 0);

        // If all aliens are defeated, start next level
        if (alienCount == 0) {
            score += alienColumns * alienRows * 100; 
            point += 10;
            alienColumns = Math.min(alienColumns + 1, columns / 2 - 2);
            alienRows = Math.min(alienRows + 1, rows - 6);
            alienArray.clear();
            bulletArray.clear();
            createAliens();
        }
    }

    // Create aliens for the game
    public void createAliens() {
        Random random = new Random();
        for (int c = 0; c < alienColumns; c++) {
            for (int r = 0; r < alienRows; r++) {
                int randomImgIndex = random.nextInt(alienImgArray.size());
                Block alien = new Block(alienX + c * alienWidth, alienY + r * alienHeight, alienWidth, alienHeight, alienImgArray.get(randomImgIndex));
                alienArray.add(alien);
            }
        }
        alienCount = alienArray.size();
    }

    // Collision detection
    public boolean detectCollision(Block a, Block b) {
        return a.x < b.x + b.width && a.x + a.width > b.x && a.y < b.y + b.height && a.y + a.height > b.y;
    }

    // Restart game
    public void restartGame() {
        gameOver = false;
        score = 0;
        alienColumns = 3;
        alienRows = 2;
        alienVelocityX = 1;
        alienArray.clear();
        bulletArray.clear();
        createAliens();
        restartButton.setVisible(false);
        exitButton.setVisible(false);
        gameLoop.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver) {
            gameLoop.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) { 
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            ship.x = Math.max(ship.x - shipVelocityX, 0);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            ship.x = Math.min(ship.x + shipVelocityX, boardWidth - shipWidth);
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            Block bullet = new Block(ship.x + shipWidth / 2 - bulletWidth / 2, ship.y, bulletWidth, bulletHeight, null);
            bulletArray.add(bullet);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}
