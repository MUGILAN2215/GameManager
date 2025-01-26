package snake_game;

// LoginPanel.java
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import main.Main1;

public class LoginPanel extends JPanel {

    JTextField nameField;
    JComboBox<String> colorChoices;
    JButton startButton, exitButton;
    Image background;

    LoginPanel(JFrame frame) {
        this.setLayout(null); // Absolute layout to position components freely
        
        // Load background image
        background = new ImageIcon("C:\\Users\\MUGILAN\\Downloads\\snakegame.jpg").getImage();  // Set your image path here

        // Name label and input field
        JLabel nameLabel = new JLabel("Enter your name: ");
        nameLabel.setBounds(150, 100, 300, 30); // Adjust position
        nameLabel.setForeground(Color.white);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));

        nameField = new JTextField("Player", 20);  // Set default name here
        nameField.setBounds(150, 130, 300, 30);

        // Color label and selection
        JLabel colorLabel = new JLabel("Choose Snake Color: ");
        colorLabel.setBounds(150, 160, 300, 30);
        colorLabel.setForeground(Color.white);
        colorLabel.setFont(new Font("Arial", Font.BOLD, 18));

        String[] colors = {"Red", "Blue", "White", "Grey", "Purple"};
        colorChoices = new JComboBox<>(colors);
        colorChoices.setBounds(150, 190, 300, 30);

        // Start button
        startButton = new JButton("Start Game");
        startButton.setBounds(150, 230, 140, 40);
        startButton.setFont(new Font("Arial", Font.BOLD, 18));

        // Exit button
        exitButton = new JButton("Exit");
        exitButton.setBounds(310, 230, 140, 40);
        exitButton.setFont(new Font("Arial", Font.BOLD, 18));

        // Add action listeners
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userName = nameField.getText().trim();
                
                // Set default name if the user didn't provide one
                if (userName.isEmpty()) {
                    userName = "Player"; // Default name
                }

                String selectedColor = colorChoices.getSelectedItem().toString();
                new GameFrame(userName, selectedColor);  // Start the game
                frame.dispose();  // Close the login frame after starting the game
            }
        });

        exitButton.addActionListener(e -> { 
        	frame.setVisible(false);
        new Main1();}
        );

        // Add components to the panel
        this.add(nameLabel);
        this.add(nameField);
        this.add(colorLabel);
        this.add(colorChoices);
        this.add(startButton);
        this.add(exitButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image
        g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}
