package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.HashMap;

public class Main implements ActionListener {
    JFrame loginFrame, createAccountFrame;
    JButton loginButton, createAccountButton, forgotPasswordButton, submitButton, cancelButton;
    JTextField usernameField, passwordField;
    JTextField usernameCreateField, passwordCreateField;
    JTextField[] answerFields = new JTextField[3]; // For storing answers to multiple questions
    JLabel usernameLabel, passwordLabel, questionLabel;
    ImageIcon bgIcon;
    Image bgImage;

    // Store user credentials and recovery answers
    HashMap<String, String> userAccounts = new HashMap<>();
    HashMap<String, HashMap<String, String>> recoveryAnswers = new HashMap<>();

    // Recovery questions
    String[] questions = {
        "Who is your favorite hero?",
        "What is your pet's name?",
        "Where is your home town?"
    };

    public Main() {
        // Frame settings for login
        loginFrame = new JFrame("Login to Game Management");
        loginFrame.setContentPane(new BackgroundPanel());

        // Username label
        usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(350, 250, 100, 30);
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));

        // Username field
        usernameField = new JTextField();
        usernameField.setBounds(450, 250, 200, 30);

        // Password label
        passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(350, 300, 100, 30);
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));

        // Password field
        passwordField = new JPasswordField();
        passwordField.setBounds(450, 300, 200, 30);

        // Login button settings
        loginButton = new JButton("Login");
        loginButton.setBounds(450, 350, 100, 40);
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(Color.DARK_GRAY);
        loginButton.setFocusPainted(false);
        loginButton.setFocusable(false);
        loginButton.addActionListener(this);

        // Create Account button
        createAccountButton = new JButton("Create Account");
        createAccountButton.setBounds(570, 350, 150, 40);
        createAccountButton.setFont(new Font("Arial", Font.BOLD, 16));
        createAccountButton.setForeground(Color.WHITE);
        createAccountButton.setBackground(Color.DARK_GRAY);
        createAccountButton.setFocusPainted(false);
        createAccountButton.setFocusable(false);
        createAccountButton.addActionListener(this);

        // Forgot Password button
        forgotPasswordButton = new JButton("Forgot Password?");
        forgotPasswordButton.setBounds(450, 400, 200, 30);
        forgotPasswordButton.setFont(new Font("Arial", Font.BOLD, 14));
        forgotPasswordButton.setForeground(Color.WHITE);
        forgotPasswordButton.setBackground(Color.DARK_GRAY);
        forgotPasswordButton.setFocusPainted(false);
        forgotPasswordButton.setFocusable(false);
        forgotPasswordButton.addActionListener(this);

        // Add components to login frame
        loginFrame.add(usernameLabel);
        loginFrame.add(usernameField);
        loginFrame.add(passwordLabel);
        loginFrame.add(passwordField);
        loginFrame.add(loginButton);
        loginFrame.add(createAccountButton);
        loginFrame.add(forgotPasswordButton);

        // Frame properties
        loginFrame.setLayout(null);
        loginFrame.setSize(1100, 800);
        loginFrame.setResizable(false);
        loginFrame.setVisible(true);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new Main();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (userAccounts.containsKey(username) && userAccounts.get(username).equals(password)) {
                // Proceed to game management panel
                new Main1(); // Placeholder for game management window
                loginFrame.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Invalid username or password.");
            }
        } else if (e.getSource() == createAccountButton) {
            openCreateAccountPanel(); // Open the panel to create a new account
        } else if (e.getSource() == forgotPasswordButton) {
            resetPassword();
        } else if (e.getSource() == submitButton) {
            createAccount();
        } else if (e.getSource() == cancelButton) {
            createAccountFrame.setVisible(false);
            loginFrame.setVisible(true);
        }
    }

    // Method to open the panel to create a new account
    private void openCreateAccountPanel() {
        // Frame settings for account creation
        createAccountFrame = new JFrame("Create New Account");
        createAccountFrame.setContentPane(new BackgroundPanel());

        // Username label and field
        JLabel usernameCreateLabel = new JLabel("Username:");
        usernameCreateLabel.setBounds(350, 150, 100, 30);
        usernameCreateLabel.setForeground(Color.WHITE);
        usernameCreateLabel.setFont(new Font("Arial", Font.BOLD, 14));

        usernameCreateField = new JTextField();
        usernameCreateField.setBounds(500, 150, 200, 30);

        // Password label and field
        JLabel passwordCreateLabel = new JLabel("Password:");
        passwordCreateLabel.setBounds(350, 200, 100, 30);
        passwordCreateLabel.setForeground(Color.WHITE);
        passwordCreateLabel.setFont(new Font("Arial", Font.BOLD, 14));

        passwordCreateField = new JPasswordField();
        passwordCreateField.setBounds(500, 200, 200, 30);

        // Display each question and corresponding answer field
        for (int i = 0; i < questions.length; i++) {
            JLabel questionLabel = new JLabel(questions[i]);
            questionLabel.setBounds(300, 250 + i * 50, 200, 30);
            questionLabel.setForeground(Color.WHITE);
            questionLabel.setFont(new Font("Arial", Font.BOLD, 14));

            answerFields[i] = new JTextField();
            answerFields[i].setBounds(500, 250 + i * 50, 200, 30);
            answerFields[i].setToolTipText("Answer for: " + questions[i]);

            createAccountFrame.add(questionLabel);
            createAccountFrame.add(answerFields[i]);
        }

        // Submit and cancel buttons
        submitButton = new JButton("Submit");
        submitButton.setBounds(450, 500, 100, 40);
        submitButton.setFont(new Font("Arial", Font.BOLD, 16));
        submitButton.setForeground(Color.WHITE);
        submitButton.setBackground(Color.DARK_GRAY);
        submitButton.setFocusPainted(false);
        submitButton.setFocusable(false);
        submitButton.addActionListener(this);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(570, 500, 100, 40);
        cancelButton.setFont(new Font("Arial", Font.BOLD, 16));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setBackground(Color.DARK_GRAY);
        cancelButton.setFocusPainted(false);
        cancelButton.setFocusable(false);
        cancelButton.addActionListener(this);

        // Add components to account creation frame
        createAccountFrame.add(usernameCreateLabel);
        createAccountFrame.add(usernameCreateField);
        createAccountFrame.add(passwordCreateLabel);
        createAccountFrame.add(passwordCreateField);
        createAccountFrame.add(submitButton);
        createAccountFrame.add(cancelButton);

        // Frame properties
        createAccountFrame.setLayout(null);
        createAccountFrame.setSize(1100, 800);
        createAccountFrame.setVisible(true);
        createAccountFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    // Method to create a new account
    private void createAccount() {
        String username = usernameCreateField.getText();
        String password = passwordCreateField.getText();
        HashMap<String, String> answers = new HashMap<>();

        // Validate all fields
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(createAccountFrame, "Please fill in all required fields.");
            return;
        }

        // Check and store answers for each question
        for (int i = 0; i < questions.length; i++) {
            String answer = answerFields[i].getText();
            if (!answer.isEmpty()) {
                answers.put(questions[i], answer);
            }
        }

        // Check if at least one question is answered
        if (answers.isEmpty()) {
            JOptionPane.showMessageDialog(createAccountFrame, "Please answer at least one recovery question.");
        } else {
            userAccounts.put(username, password);
            recoveryAnswers.put(username, answers);
            JOptionPane.showMessageDialog(createAccountFrame, "Account created successfully!");
            createAccountFrame.setVisible(false);
            loginFrame.setVisible(true);
        }
    }

    // Method to handle password reset
 // Method to handle password reset
    private void resetPassword() {
        String username = JOptionPane.showInputDialog(loginFrame, "Enter your username:");
        if (username == null || username.isEmpty()) {
            return;
        }

        if (!recoveryAnswers.containsKey(username)) {
            JOptionPane.showMessageDialog(loginFrame, "No recovery questions available for this account.");
            return;
        }

        HashMap<String, String> storedAnswers = recoveryAnswers.get(username);
        String[] availableQuestions = storedAnswers.keySet().toArray(new String[0]);

        // Create a dropdown to let the user select the question they want to answer
        JComboBox<String> questionComboBox = new JComboBox<>(availableQuestions);
        int option = JOptionPane.showConfirmDialog(loginFrame, questionComboBox, 
                                                   "Select a question", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String selectedQuestion = (String) questionComboBox.getSelectedItem();
            String userAnswer = JOptionPane.showInputDialog(loginFrame, selectedQuestion);
            
            if (userAnswer != null && userAnswer.equals(storedAnswers.get(selectedQuestion))) {
                JOptionPane.showMessageDialog(loginFrame, " Recover Your Password! ");
                
                // Prompt the user to enter a new password
                String newPassword = JOptionPane.showInputDialog(loginFrame, "Enter a new password:");
                if (newPassword != null && !newPassword.isEmpty()) {
                    userAccounts.put(username, newPassword); // Update the password
                    JOptionPane.showMessageDialog(loginFrame, "Password reset successful. You can log in now.");
                } else {
                    JOptionPane.showMessageDialog(loginFrame, "Password reset canceled. Please try again.");
                }
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Incorrect answer. Password recovery failed.");
            }
        }
    }


    // Panel class for background image
    class BackgroundPanel extends JPanel {
        public BackgroundPanel() {
            // Load the image (make sure the path is correct)
            bgIcon = new ImageIcon(getClass().getResource("loginpanel.jpg"));
            bgImage = bgIcon.getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
