package mem_gamee;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class LoginPage {
    private JFrame frame;
    private JTextField nameField;
    private JButton startButton;

    // Custom JPanel to draw the background image
    class ImagePanel extends JPanel {
        private Image backgroundImage;

        public ImagePanel(String imagePath) {
            try {
                backgroundImage = ImageIO.read(new File(imagePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public LoginPage() {
        frame = new JFrame("Welcome to Match Cards");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300); // Increase the size here (width, height)
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        // Create an instance of ImagePanel with the path to your image
        ImagePanel imagePanel = new ImagePanel("C:\\Users\\MUGILAN\\Downloads\\memo.jpg");
        imagePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Center components with spacing

        // Custom label with white text and black border
        JLabel nameLabel = createCustomLabel("Enter your name:");

        nameField = new JTextField(20); // Increase text field size
        startButton = new JButton("Start");

        // Set font and color for button
        startButton.setFont(new Font("Cursive", Font.BOLD, 14));
        startButton.setForeground(Color.WHITE);
        startButton.setBackground(Color.BLACK);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String playerName = nameField.getText();
                if (playerName.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter your name.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    frame.dispose(); // Close the login frame
                    new MatchCards(playerName); // Pass the name to the game
                }
            }
        });

        // Add components to the image panel instead of the frame
        imagePanel.add(nameLabel);
        imagePanel.add(nameField);
        imagePanel.add(startButton);
        
        // Add the image panel to the frame
        frame.add(imagePanel);
        frame.setVisible(true);
    }

    // Custom method to create a label with a border effect
    private JLabel createCustomLabel(String text) {
        JLabel label = new JLabel(text) {
            @Override
            protected void paintComponent(Graphics g) {
                // Draw black border by drawing text in black first
                g.setColor(Color.BLACK);
                g.drawString(text, 2, getHeight() - 4); // Slightly offset for border
                g.drawString(text, 0, getHeight() - 2); // Slightly offset for border
                g.drawString(text, -2, getHeight()); // Slightly offset for border
                g.drawString(text, 0, getHeight() + 2); // Slightly offset for border
                // Now draw the white text
                g.setColor(Color.WHITE);
                g.drawString(text, 0, getHeight() - 2);
            }
        };

        label.setFont(new Font("Cursive", Font.BOLD, 16)); // Set cursive font style and size
        label.setForeground(Color.WHITE); // Set label text color to white
        return label;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginPage::new);
    }
}
