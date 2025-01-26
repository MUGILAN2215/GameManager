package spaceinvaders;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

import main.Main1;

public class SpaceInvaders extends JPanel implements ActionListener, KeyListener {
    // Game board dimensions
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

    // Blocks representing game objects
    class Block {
        int x, y, width, height;
        Image img;
        boolean alive = true;
        boolean used = false; // Used for bullets
        int hp = 1; // Health points

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
    int shipVelocityX = tileSize;
    int shipHP = 100;
    Block ship;

    // Aliens and their settings
    ArrayList<Block> alienArray;
    int alienWidth = tileSize * 2;
    int alienHeight = tileSize;
    int alienX = tileSize;
    int alienY = tileSize;
    int alienRows = 2;
    int alienColumns = 3;
    int alienCount = 0;
    int alienVelocityX = 1;
    
    // Bullets and shooting
    ArrayList<Block> bulletArray;
    ArrayList<Block> alienBulletArray;
    int bulletWidth = tileSize / 8;
    int bulletHeight = tileSize / 2;
    int bulletVelocityY = -10;
    int alienBulletVelocityY = 5;

    Timer gameLoop;
    boolean gameOver = false;
    int score = 0;
    int highScore = 0;

    // Game buttons
    JButton restartButton, exitButton;

    // Constructor
 // Constructor
    public SpaceInvaders(JFrame frame) {
        // Set the layout of the frame
        frame.setLayout(new BorderLayout());

        this.setPreferredSize(new Dimension(boardWidth, boardHeight));
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
        alienBulletArray = new ArrayList<>();

        // Game loop timer
        gameLoop = new Timer(1000 / 60, this);
        createAliens();
        gameLoop.start();

        // Create and set up buttons
        restartButton = new JButton("Restart");
        exitButton = new JButton("Exit");

        // Add action listeners to buttons
        restartButton.addActionListener(e -> restartGame());
        exitButton.addActionListener(e -> System.exit(0));  // Closes the window on exit

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout()); // FlowLayout for horizontal alignment
        buttonPanel.add(restartButton);
        buttonPanel.add(exitButton);

        // Add components to frame
        frame.add(this, BorderLayout.CENTER);  // Add game area to the center
        frame.add(buttonPanel, BorderLayout.SOUTH);  // Buttons at the bottom
    }


    // Paints the game objects on the screen
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    // Draw the ship, aliens, bullets, and score
    public void draw(Graphics g) {
        // Ship
        g.drawImage(ship.img, ship.x, ship.y, ship.width, ship.height, null);
        g.setColor(Color.green);
        g.drawString("HP: " + shipHP, 10, 55);

        // Aliens
        for (Block alien : alienArray) {
            if (alien.alive) {
                g.drawImage(alien.img, alien.x, alien.y, alien.width, alien.height, null);
                g.setColor(Color.red);
                g.drawString("HP: " + alien.hp, alien.x + 10, alien.y - 5); // Display alien HP
            }
        }

        // Bullets
        g.setColor(Color.white);
        for (Block bullet : bulletArray) {
            if (!bullet.used) {
                g.fillRect(bullet.x, bullet.y, bullet.width, bullet.height);
            }
        }

        // Alien Bullets
        g.setColor(Color.red);
        for (Block bullet : alienBulletArray) {
            g.fillRect(bullet.x, bullet.y, bullet.width, bullet.height);
        }

        // Score and Game Over Message
        g.setFont(new Font("Arial", Font.PLAIN, 32));
        if (gameOver) {
            g.drawString("Game Over: " + score, 10, 35);
            g.drawString("High Score: " + highScore, 10, 75);
        } else {
            g.drawString("Score: " + score, 10, 35);
        }
    }

    // Move aliens, bullets, and detect collisions
    public void move() {
        // Move aliens
        for (Block alien : alienArray) {
            if (alien.alive) {
                alien.x += alienVelocityX;

                // Alien touches borders, change direction and move down
                if (alien.x + alien.width >= boardWidth || alien.x <= 0) {
                    alienVelocityX *= -1;
                    for (Block a : alienArray) {
                        a.y += alienHeight;
                    }
                }

                // Check if alien reached the ship level
                if (alien.y >= ship.y) {
                    gameOver = true;
                }

                // Aliens shoot bullets randomly
                if (new Random().nextInt(100) < 2) {
                    Block bullet = new Block(alien.x + alienWidth / 2, alien.y + alienHeight, bulletWidth, bulletHeight, null);
                    alienBulletArray.add(bullet);
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
                    alien.hp -= 2; // Decrease alien HP by 2
                    if (alien.hp <= 0) {
                        alien.alive = false;
                        alienCount--;
                        score += 100;
                    }
                }
            }
        }

        // Move alien bullets and check collision with ship
        for (Block bullet : alienBulletArray) {
            bullet.y += alienBulletVelocityY;

            // Bullet hits ship
            if (detectCollision(bullet, ship)) {
                bullet.used = true;
                shipHP -= 2; // Reduce ship's HP
                if (shipHP <= 0) {
                    gameOver = true;
                }
            }
        }

        // Clear used or off-screen bullets
        bulletArray.removeIf(b -> b.used || b.y < 0);
        alienBulletArray.removeIf(b -> b.used || b.y > boardHeight);

        // Level up if all aliens are defeated
        if (alienCount == 0) {
            alienRows = Math.min(alienRows + 1, rows - 6);  // Increase alien rows
            alienColumns = Math.min(alienColumns + 1, columns / 2 - 2); // Increase alien columns
            createAliens(); // Recreate aliens for the next level
        }
    }

    // Detects collision between two blocks
    public boolean detectCollision(Block a, Block b) {
        return a.x < b.x + b.width && a.x + a.width > b.x && a.y < b.y + b.height && a.y + a.height > b.y;
    }

    // Create aliens for the level
    public void createAliens() {
        Random random = new Random();
        for (int c = 0; c < alienColumns; c++) {
            for (int r = 0; r < alienRows; r++) {
                int alienIndex = random.nextInt(alienImgArray.size());
                Block alien = new Block(c * alienWidth, r * alienHeight, alienWidth, alienHeight, alienImgArray.get(alienIndex));
                alienArray.add(alien);
                alienCount++;
            }
        }
    }

    // Restart the game
    public void restartGame() {
        shipHP = 100;
        score = 0;
        gameOver = false;
        alienArray.clear();
        bulletArray.clear();
        alienBulletArray.clear();
        createAliens();
        gameLoop.start();
    }

    // Action event handling
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            move();
            repaint();
        }
    }

    // Key handling for ship movement and shooting
    @Override
    public void keyPressed(KeyEvent e) {
        if (!gameOver) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT && ship.x > 0) {
                ship.x -= shipVelocityX;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT && ship.x < boardWidth - shipWidth) {
                ship.x += shipVelocityX;
            }
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                Block bullet = new Block(ship.x + shipWidth / 2, ship.y, bulletWidth, bulletHeight, null);
                bulletArray.add(bullet);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    // Main method to start the game
    public static void main(String[] args) {
        JFrame frame = new JFrame("Space Invaders");
        SpaceInvaders game = new SpaceInvaders(frame);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
