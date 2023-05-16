package view;

import controller.HighScoreManager;
import models.HighScore;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HighScoreWindow extends JFrame {

    private JTextArea highScoreTextArea;

    public HighScoreWindow() {
        initialize();
        loadHighScores();
    }

    private void initialize() {
        setTitle("High Scores");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        highScoreTextArea = new JTextArea();
        highScoreTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(highScoreTextArea);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadHighScores() {
        HighScoreManager.loadHighScores();
        List<HighScore> highScores = HighScoreManager.getHighScores();

        StringBuilder sb = new StringBuilder();
        for (HighScore highScore : highScores) {
            sb.append(highScore.getPlayerName()).append("\t").append(highScore.getScore()).append("\n");
        }

        highScoreTextArea.setText(sb.toString());
    }

    
}
