package snake_game;


import javax.swing.*;

public class SnakeGame {  
	
	    public void start() {
	        // Create and display the game frame
	        GameFrame gameFrame = new GameFrame(null, null);
	        gameFrame.setVisible(true);
	    
	}
	
    public static void main(String[] args) {
        // Start the game by showing the login panel
        JFrame frame = new JFrame();
        frame.add(new LoginPanel(frame)); // Pass the frame to the LoginPanel
        frame.setTitle("Snake Game - Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false); // Prevent resizing the frame
        frame.setPreferredSize(new java.awt.Dimension(1300, 750)); // Set preferred size
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
