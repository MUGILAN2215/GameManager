
package montyandflower;

import javax.swing.SwingUtilities;

public class App1 {
    public static void main(String[] args) throws Exception {
        // Start with the login panel instead of directly starting the game
        SwingUtilities.invokeLater(() -> new LoginPanel("Welcome to Whac-A-Mole!", "Start Game", "C:\\Users\\MUGILAN\\Downloads\\image\\whac a mole.jpg"));
    }
}
