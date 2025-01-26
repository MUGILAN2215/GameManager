package main;

import javax.swing.JFrame;

public class MainGameManagement {
    public MainGameManagement() {
        // Create the main game management window
        JFrame gameFrame = new JFrame("Game Management Panel");
        gameFrame.setSize(800, 600);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setVisible(true);
    }
}