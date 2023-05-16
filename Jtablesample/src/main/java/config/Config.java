/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

import com.mycompany.jtablesample.TableExample;
import javax.swing.JLabel;

/**
 *
 * @author Hp
 */
public class Config {
    private static int boardSize = 10; // Default board size
    private static TableExample tableExample;
    private static int score = 0;
    private static int life = 3;
    private static int playerRow = 0;
    private static int playerCol = 0;
    private static JLabel scoreLabel; 
    private static JLabel lifeLabel; 

    public static int getBoardSize() {
        return boardSize;
    }

    public static void setBoardSize(int boardSize) {
        Config.boardSize = boardSize;
    }
    
    public static TableExample getTableExample() {
        return tableExample;
    }

    public static void setTableExample(TableExample tableExample) {
        Config.tableExample = tableExample;
    }
    
    public static int getScore() {
        return score;
    }
    
    public static void setScore(int score) {
        Config.score = score;
        
    }
    
    public static JLabel getScoreLabel() {
    return scoreLabel;
}

    public static void setScoreLabel(JLabel scoreLabel) {
        Config.scoreLabel = scoreLabel;
    }
    
    public static int getLife() {
        return life;
    }
    
    public static void setLife(int life) {
        Config.life = life;
        
    }
    
    public static JLabel getLifeLabel() {
    return lifeLabel;
}

    public static void setLifeLabel(JLabel lifeLabel) {
        Config.lifeLabel = lifeLabel;
    }
    
    public static int getPlayerRow() {
        return playerRow;
    }
    public static int getPlayerCol() {
        return playerCol;
    }
    
    public static void setPosition(int playerRow,int playerCol) {
        
        Config.playerRow = playerRow;
        Config.playerCol = playerCol;
    
    }


}
