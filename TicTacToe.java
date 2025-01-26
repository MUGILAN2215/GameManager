package tictat;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;

import main.Main1;

public class TicTacToe {
    JFrame frame = new JFrame("Tic-Tac-Toe");
    JPanel mainPanel = new JPanel(new CardLayout());
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();
    JPanel buttonPanel = new JPanel(); // Panel for Restart and Exit buttons
    JButton[][] board = new JButton[3][3];

    String playerX = "X";
    String playerO = "O";
    String currentPlayer = playerX;
    boolean gameOver = false;
    int turns = 0;

    BufferedImage backgroundImage;

    public TicTacToe() {
        // Load background image
        try {
            backgroundImage = ImageIO.read(new File("C:\\Users\\MUGILAN\\Downloads\\tic-tac-toe-scaled.jpg")); // Update the path
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Frame setup
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setLayout(new BorderLayout());

        frame.add(mainPanel, BorderLayout.CENTER);

        // Initialize game panel directly
        initializeGamePanel();

        frame.setVisible(true);
    }

    public void initializeGamePanel() {
        // Reset the main panel for the game
        JPanel gamePanel = new JPanel(new BorderLayout());

        // Text label on top
        textLabel.setBackground(Color.darkGray);
        textLabel.setForeground(Color.white);
        textLabel.setFont(new Font("Arial", Font.BOLD, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Tic-Tac-Toe");
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        gamePanel.add(textPanel, BorderLayout.NORTH);

        // Game board panel
        boardPanel.setLayout(new GridLayout(3, 3));
        boardPanel.setBackground(Color.darkGray);

        // Initialize the board with buttons
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                JButton tile = new JButton();
                board[r][c] = tile;
                boardPanel.add(tile);

                tile.setBackground(Color.darkGray);
                tile.setForeground(Color.white);
                tile.setFont(new Font("Arial", Font.BOLD, 120));
                tile.setFocusable(false);

                tile.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (gameOver) return;
                        JButton tile = (JButton) e.getSource();
                        if (tile.getText().equals("")) {
                            tile.setText(currentPlayer);
                            turns++;
                            checkWinner();
                            if (!gameOver) {
                                currentPlayer = currentPlayer.equals(playerX) ? playerO : playerX;
                                textLabel.setText(currentPlayer + "'s turn.");
                            }
                        }
                    }
                });
            }
        }

        gamePanel.add(boardPanel, BorderLayout.CENTER);

        // Button panel for restart and exit buttons
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBackground(Color.darkGray);
        buttonPanel.setVisible(false); // Hide initially
        gamePanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(gamePanel, "gamePanel");
        ((CardLayout) mainPanel.getLayout()).show(mainPanel, "gamePanel");
    }

    void checkWinner() {
        // Horizontal
        for (int r = 0; r < 3; r++) {
            if (!board[r][0].getText().equals("") &&
                    board[r][0].getText().equals(board[r][1].getText()) &&
                    board[r][1].getText().equals(board[r][2].getText())) {
                declareWinner(board[r][0], board[r][1], board[r][2]);
                return;
            }
        }

        // Vertical
        for (int c = 0; c < 3; c++) {
            if (!board[0][c].getText().equals("") &&
                    board[0][c].getText().equals(board[1][c].getText()) &&
                    board[1][c].getText().equals(board[2][c].getText())) {
                declareWinner(board[0][c], board[1][c], board[2][c]);
                return;
            }
        }

        // Diagonal
        if (!board[0][0].getText().equals("") &&
                board[0][0].getText().equals(board[1][1].getText()) &&
                board[1][1].getText().equals(board[2][2].getText())) {
            declareWinner(board[0][0], board[1][1], board[2][2]);
            return;
        }

        // Anti-diagonal
        if (!board[0][2].getText().equals("") &&
                board[0][2].getText().equals(board[1][1].getText()) &&
                board[1][1].getText().equals(board[2][0].getText())) {
            declareWinner(board[0][2], board[1][1], board[2][0]);
            return;
        }

        // Check tie
        if (turns == 9) {
            textLabel.setText("It's a Tie!");
            gameOver = true;
            showEndGameButtons(); // Show buttons after the game is completed
        }
    }

    void declareWinner(JButton... winningTiles) {
        for (JButton tile : winningTiles) {
            tile.setForeground(Color.green);
            tile.setBackground(Color.gray);
        }
        textLabel.setText(currentPlayer + " wins!");
        gameOver = true;
        showEndGameButtons(); // Show buttons after the game is completed
    }

    void showEndGameButtons() {
        JButton restartButton = new JButton("Restart");
        JButton exitButton = new JButton("Exit");

        restartButton.addActionListener(e -> {
            // Reset game
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    board[r][c].setText("");
                    board[r][c].setForeground(Color.white);
                    board[r][c].setBackground(Color.darkGray);
                }
            }
            gameOver = false;
            turns = 0;
            currentPlayer = playerX;
            textLabel.setText(currentPlayer + "'s turn.");
            buttonPanel.setVisible(false); // Hide buttons
        });

        exitButton.addActionListener(e -> {
            frame.setVisible(false);
            new Main1();
        });

        buttonPanel.removeAll(); // Clear previous buttons
        buttonPanel.add(restartButton);
        buttonPanel.add(exitButton);
        buttonPanel.setVisible(true); // Show buttons
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TicTacToe::new);
    }
}
