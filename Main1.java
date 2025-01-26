package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import snake_game.SnakeGame;
import spaceinvaders.SpaceInvaders;
import mem_gamee.App;
import tictat.TicTacToe;
import montyandflower.App1;

public class Main1 implements ActionListener {
    JFrame frame;
    JButton button1, button2, button3, button4, button5, exitButton;

    ImageIcon icon1 = new ImageIcon(Main1.class.getResource("snake.jpg"));
    ImageIcon resizedIcon1 = resizeIcon(icon1, 300, 300);

    ImageIcon icon2 = new ImageIcon(Main1.class.getResource("memory card.jpg"));
    ImageIcon resizedIcon2 = resizeIcon(icon2, 300, 300);

    ImageIcon icon3 = new ImageIcon(Main1.class.getResource("whacmole.png"));
    ImageIcon resizedIcon3 = resizeIcon(icon3, 300, 300);

    ImageIcon icon4 = new ImageIcon(Main1.class.getResource("spaceinvader.png"));
    ImageIcon resizedIcon4 = resizeIcon(icon4, 300, 300);

    ImageIcon icon5 = new ImageIcon(Main1.class.getResource("TicTacToe.jpg"));
    ImageIcon resizedIcon5 = resizeIcon(icon5, 300, 300);

    ImageIcon bgIcon = new ImageIcon(Main1.class.getResource("gameimg01.jpg"));
    Image bgImage = bgIcon.getImage();

    public Main1() {
        frame = new JFrame();
        frame.setContentPane(new BackgroundPanel());

        button1 = createButton(resizedIcon1, 50, 50);
        button2 = createButton(resizedIcon2, 400, 50);
        button3 = createButton(resizedIcon3, 50, 400);
        button4 = createButton(resizedIcon4, 400, 400);
        button5 = createButton(resizedIcon5, 750, 50);

        exitButton = new JButton("EXIT");
        exitButton.setFont(new Font("cursive", Font.PLAIN, 19));
        exitButton.setBounds(500, 720, 100, 40);
        exitButton.setForeground(Color.WHITE);
        customizeButton(exitButton);
        exitButton.addActionListener(this);

        frame.add(button1);
        frame.add(button2);
        frame.add(button3);
        frame.add(button4);
        frame.add(button5);
        frame.add(exitButton);

        frame.setLayout(null);
        frame.setSize(1100, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new Main1();
    }

    private JButton createButton(ImageIcon icon, int x, int y) {
        JButton button = new JButton();
        button.setIcon(icon);
        button.setBounds(x, y, 300, 300);
        customizeButton(button);
        button.addActionListener(this);
        return button;
    }

    private static ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    private void customizeButton(JButton button) {
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button1) {
            SnakeGame.main(null);
            frame.setVisible(false);
        } else if (e.getSource() == button2) {
            App.main(null);
            frame.setVisible(false);
        } else if (e.getSource() == button3) {
            try {
                App1.main(null);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            frame.setVisible(false);
        } else if (e.getSource() == button4) {
            SpaceInvaders.main(null);
            frame.setVisible(false);
        } else if (e.getSource() == button5) {
            TicTacToe.main(null);
            frame.setVisible(false);
        } else if (e.getSource() == exitButton) {
            System.exit(0);
        }
    }

    // Custom JPanel to set background image
    class BackgroundPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
