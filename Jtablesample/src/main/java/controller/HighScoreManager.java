package controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import models.HighScore;

public class HighScoreManager {

    private static List<HighScore> highScores = new ArrayList<>();
    private static final String HIGH_SCORES_FILE = "data/highscores.ser";

    public static void loadHighScores() {
        try {
            FileInputStream fileIn = new FileInputStream(HIGH_SCORES_FILE);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            highScores = (List<HighScore>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void saveHighScores() {
        try {
            FileOutputStream fileOut = new FileOutputStream(HIGH_SCORES_FILE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(highScores);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addHighScore(HighScore highScore) {
        highScores.add(highScore);
    }

    public static List<HighScore> getHighScores() {
        highScores.sort(Comparator.comparingInt(HighScore::getScore).reversed());
        return highScores;
    }
}
