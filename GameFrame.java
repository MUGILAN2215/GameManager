package snake_game;

//GameFrame.java
import javax.swing.*;

public class GameFrame extends JFrame {
	
	
	
	
	public GameFrame() {
	     setTitle("Snake Game");
	     setSize(800, 600);
	     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	     setLocationRelativeTo(null);

	     // Set up the game panel (if you have one)
	     GamePanel gamePanel = new GamePanel();
	     add(gamePanel);
	 }


 GameFrame(String userName, String snakeColor) {
     this.add(new GamePanel(userName, snakeColor,this));
     this.setTitle("Snake Game - Player: " + userName);
     this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     this.setResizable(false);
     this.pack();
     this.setVisible(true);
     this.setLocationRelativeTo(null);
 }
}
//In src/snake_game/GameFrame.java



 
