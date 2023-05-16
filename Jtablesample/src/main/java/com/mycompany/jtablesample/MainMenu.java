package com.mycompany.jtablesample;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controller.PacmanController;

public class MainMenu extends JFrame implements ActionListener {

    public MainMenu() {
        setTitle("Menu");
        setSize(400, 300);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.BLACK);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        JButton newGameButton = createButton("New Game");
        JButton highScoresButton = createButton("High Scores");
        JButton exitButton = createButton("Exit");

        newGameButton.addActionListener(this);
        highScoresButton.addActionListener(this);
        exitButton.addActionListener(this);

        panel.add(newGameButton, gbc);
        gbc.gridy++;
        panel.add(highScoresButton, gbc);
        gbc.gridy++;
        panel.add(exitButton, gbc);

        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 40));
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(Color.DARK_GRAY);
        button.setFocusPainted(false);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("New Game")) {
            PacmanController.startGame();
        } else if (e.getActionCommand().equals("High Scores")) {
            // Handle high scores button click
        } else if (e.getActionCommand().equals("Exit")) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainMenu menu = new MainMenu();
            menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
    }
}
