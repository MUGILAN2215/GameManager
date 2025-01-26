package snake_game;

//LoginFrame.java
import javax.swing.*;

public class LoginFrame extends JFrame {

 LoginFrame() {
     this.setTitle("Snake Game - Login");
     this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     this.setSize(600, 400);
     this.setResizable(false);
     this.setLocationRelativeTo(null); // Center the frame
     this.setContentPane(new LoginPanel(this));  // Set LoginPanel as content
     this.setVisible(true);
 }
}
