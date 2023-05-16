package controller;

import view.TableExample;
import config.Config;
import interfaces.PacmanTableListener;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import models.PacmanTableModel;
import view.MainMenu;
import javax.swing.JFrame;
import controller.HighScoreManager;
import java.util.Scanner;
import javax.swing.JOptionPane;
import models.HighScore;


public class PacmanController implements KeyListener {

    private JTable table;
    private PacmanTableModel model;
    private int pacmanRow;
    private int pacmanCol;
    private int enemyRow;
    private int enemyCol;
    private int enemyTwoCol;
    private int enemyTwoRow;
    private Thread pacmanThread;
    private Thread enemyThread;
    private Thread enemyTwoThread;
    private List<PacmanTableListener> tableListeners = new ArrayList<>();
    int randomValue;
    
    
    
    
    private int previousVal = 0;
     
    private final Object lock = new Object();
    private final Object enemyLock = new Object();

    public PacmanController(JTable table, PacmanTableModel model) {
        this.table = table;
        this.model = model;
        
        
        
        
        // find the initial row and column of the Pacman image
        for (int row = 0; row < model.getRowCount(); row++) {
            for (int col = 0; col < model.getColumnCount(); col++) {
                // FIRST GRAB THE INITIAL POSITION OF PACMAN
                if (model.getValueAt(row, col) != null) {
                    if (model.getValue(row, col) == 1) { // Pacman
                    pacmanRow = row;
                    pacmanCol = col;
                    break;
                } else if (model.getValue(row, col) == 2) { // Enemy
                    enemyRow = row;
                    enemyCol = col;
                    break;                    
                }
                else if (model.getValue(row, col) == 12) { // Enemy
                    enemyTwoRow = row;
                    enemyTwoCol = col;
                    break;                    
                  }
                    
                }
            }
        }

        pacmanThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(150);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (lock) { 
                        randomValue = getRandomValue();
                        movePacman();
                        checkCollisionWithEnemy();
                        
                    }
                    
                }
            }
        });
        pacmanThread.start();
        
        enemyThread = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock) { 
                    moveEnemy();
                }
            }
        }
        });
        enemyThread.start();
        
        
        enemyTwoThread = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock) { 
                    moveEnemyTwo();
                }
            }
        }
        });
        enemyTwoThread.start();
        
    }

    private void movePacman() {
        
        int currentRow = pacmanRow;
        int currentCol = pacmanCol;
        // determine the next cell according to lastkeycode 
        switch (lastKeyCode) {
            case KeyEvent.VK_UP:
                currentRow--;
                break;
            case KeyEvent.VK_DOWN:
                currentRow++;
                break;
            case KeyEvent.VK_LEFT:
                currentCol--;
                break;
            case KeyEvent.VK_RIGHT:
                currentCol++;
                break;
            default:
                return; // ignore other keys
        }
        // check new cell within the bound
        if (currentRow >= 0 && currentRow < model.getRowCount() && currentCol >= 0 && currentCol < model.getColumnCount()) {
            
            

            // check if the new cell is already occupied by another Pacman image
            if (model.getValueAt(currentRow, currentCol) == null || model.getCellValue(currentRow, currentCol) == 7) {
                
                
                if (model.getCellValue(currentRow, currentCol) == 7) {
                int score = Config.getScore() + 1;
                Config.setScore(score);
                System.out.println(score);
                Config.getScoreLabel().setText("Score: " + Config.getScore());
                }
                
                                   
                
                model.setValueAt(0, pacmanRow, pacmanCol);
                model.setValueAt(1, currentRow, currentCol);
                //System.out.println("normal pacman way"+model.getValue(currentRow, currentCol));
                table.repaint(); 

                // update the current position of the Pacman image
                pacmanRow = currentRow;
                pacmanCol = currentCol;
                Config.setPosition(pacmanRow, pacmanCol);
                
                // calculate rotation angle and notify listeners
                int angle = calculateRotationAngle();
                
                
                for (PacmanTableListener listener : tableListeners) {
                    if(listener != null) {
                        listener.onPacmanRotated(angle,randomValue);
                        
                    }
                }
            }
            
            if (model.getCellValue(currentRow, currentCol) == 2|| model.getCellValue(currentRow, currentCol) == 12) {
                int life = Config.getLife() - 1;
                Config.setLife(life);
                System.out.println(life);
                Config.getLifeLabel().setText("Lives: " + Config.getLife());
                model.setValueAt(0, pacmanRow, pacmanCol);
                model.setValueAt(0, enemyRow, enemyCol);
                model.setValueAt(0, enemyTwoRow, enemyTwoCol);
                enemyRow = 0;
                enemyCol = 0;
                enemyTwoRow = 0;
                enemyTwoCol = 0;
                System.out.println("collision");
                // remove enemy from cell and update table
                table.repaint(); 

                if (life <= 0) {
                    addHighScoreDialog();
                    // Gameover condition: no more lives left
                    gameover();
                    //new MainMenu();
                }else {
                    // Respawn pacman at the bottom right corner
                    pacmanRow = table.getRowCount() - 1;
                    pacmanCol = table.getColumnCount() - 1;
                    model.setValueAt(1, pacmanRow, pacmanCol);
                    table.repaint();
                }
            }
            
            
        }
    }
    
    
    
    private void moveEnemy() {
        int currentRow = enemyRow;
        int currentCol = enemyCol;

        // calculate distance to player
        int playerRow = Config.getPlayerRow();
        int playerCol = Config.getPlayerCol();
        int distance = Math.abs(currentRow - playerRow) + Math.abs(currentCol - playerCol);

        if (distance < 5) {
            // move towards player's last known position
            int newRow = currentRow;
            int newCol = currentCol;

            if (playerRow > currentRow) {
                newRow++;
            } else if (playerRow < currentRow) {
                newRow--;
            }

            if (playerCol > currentCol) {
                newCol++;
            } else if (playerCol < currentCol) {
                newCol--;
            }

            if (model.getCellValue(newRow, newCol) != 21) {
                if (previousVal == 0) {
                 model.setValueAt(0, currentRow, currentCol);
                }
                else if (previousVal == 7) {
                 model.setValueAt(7, currentRow, currentCol);
                }
                // move to new position
                if (model.getCellValue(newRow,newCol) == 0) {
                    previousVal = 0;
                } else if(model.getCellValue(newRow,newRow) == 7) {
                    previousVal = 7;
                } 

                model.setValueAt(2, newRow, newCol);
                table.repaint();
                enemyRow = newRow;
                enemyCol = newCol;
            }
        } else {
            // move randomly
            int[] rowOffsets = {-1, 0, 1, 0};
            int[] colOffsets = {0, -1, 0, 1};
            int direction = new Random().nextInt(4);

            // try moving in the chosen direction, until an empty cell is found
            for (int i = 0; i < 4; i++) {
                int newRow = currentRow + rowOffsets[direction];
                int newCol = currentCol + colOffsets[direction];
                if (newRow >= 0 && newRow < model.getRowCount() && newCol >= 0 && newCol < model.getColumnCount()) {
                    if (model.getCellValue(newRow, newCol) != 21) {
                        if (previousVal == 0) {
                         model.setValueAt(0, currentRow, currentCol);
                        }
                        else if (previousVal == 7) {
                         model.setValueAt(7, currentRow, currentCol);
                        }
                        // move to new position
                        if (model.getCellValue(newRow,newCol) == 0) {
                            previousVal = 0;
                        } else if(model.getCellValue(newRow,newRow) == 7) {
                            previousVal = 7;
                        } 

                        model.setValueAt(2, newRow, newCol);
                        table.repaint();
                        enemyRow = newRow;
                        enemyCol = newCol;        
                        break;
                    } else {
                        // enemy hit a wall, change direction
                        direction = (direction + 1) % 4;
                    }
                }
                direction = (direction + 1) % 4; // try the next direction
            }
        }
}
    
    
    private void moveEnemyTwo() {
        int currentRow = enemyTwoRow;
        int currentCol = enemyTwoCol;

        // Calculate distance to player
        int playerRow = Config.getPlayerRow();
        int playerCol = Config.getPlayerCol();
        int distance = Math.abs(currentRow - playerRow) + Math.abs(currentCol - playerCol);

        if (distance < 5) {
            // Move towards player's last known position
            int newRow = currentRow;
            int newCol = currentCol;

            if (playerRow > currentRow) {
                newRow++;
            } else if (playerRow < currentRow) {
                newRow--;
            }

            if (playerCol > currentCol) {
                newCol++;
            } else if (playerCol < currentCol) {
                newCol--;
            }

            if (model.getCellValue(newRow, newCol) != 21) {
                if (previousVal == 0) {
                 model.setValueAt(0, currentRow, currentCol);
                }
                else if (previousVal == 7) {
                 model.setValueAt(7, currentRow, currentCol);
                }
                // move to new position
                if (model.getCellValue(newRow,newCol) == 0) {
                    previousVal = 0;
                } else if(model.getCellValue(newRow,newRow) == 7) {
                    previousVal = 7;
                } 

                model.setValueAt(12, newRow, newCol);
                table.repaint();
                enemyTwoRow = newRow;
                enemyTwoCol = newCol;
            }
        } else {
        // Move towards a random zero cell value within a specific range
        int range = 5; // Specify the range you want to consider
        List<int[]> zeroCellsInRange = findZeroCellsInRange(currentRow, currentCol, range);

        if (!zeroCellsInRange.isEmpty()) {
            int[] targetCell = zeroCellsInRange.get(new Random().nextInt(zeroCellsInRange.size()));
            int targetRow = targetCell[0];
            int targetCol = targetCell[1];

            int newRow = currentRow;
            int newCol = currentCol;

            if (targetRow > currentRow) {
                newRow++;
            } else if (targetRow < currentRow) {
                newRow--;
            }

            if (targetCol > currentCol) {
                newCol++;
            } else if (targetCol < currentCol) {
                newCol--;
            }

            if (model.getCellValue(newRow, newCol) != 21) {
                if (previousVal == 0) {
                 model.setValueAt(0, currentRow, currentCol);
                }
                else if (previousVal == 7) {
                 model.setValueAt(7, currentRow, currentCol);
                }
                // move to new position
                if (model.getCellValue(newRow,newCol) == 0) {
                    previousVal = 0;
                } else if(model.getCellValue(newRow,newRow) == 7) {
                    previousVal = 7;
                } 

                model.setValueAt(12, newRow, newCol);
                table.repaint();
                enemyTwoRow = newRow;
                enemyTwoCol = newCol;
            }
        }
    }
}

    private List<int[]> findZeroCellsInRange(int row, int col, int range) {
        List<int[]> zeroCells = new ArrayList<>();

        for (int i = Math.max(0, row - range); i <= Math.min(model.getRowCount() - 1, row + range); i++) {
            for (int j = Math.max(0, col - range); j <= Math.min(model.getColumnCount() - 1, col + range); j++) {
                if (model.getCellValue(i, j) == 0) {
                    int[] zeroCell = {i, j};
                    zeroCells.add(zeroCell);
                }
            }
        }

        return zeroCells;
    }


    
    
    
    
    

    public int calculateRotationAngle() {
    switch (lastKeyCode) {
        case KeyEvent.VK_UP:
            return 270;
        case KeyEvent.VK_DOWN:
            return 90;
        case KeyEvent.VK_LEFT:
            return 180;
        case KeyEvent.VK_RIGHT:
            return 0;
        default:
            return 0;
    }
    }
    
    public int getRandomValue() {
        Random rand = new Random();
        return rand.nextInt(2);
    }
    
    private void checkCollisionWithEnemy() {
        if ((pacmanRow == enemyRow && pacmanCol == enemyCol) || (pacmanRow == enemyTwoRow && pacmanCol == enemyTwoCol)) {
            int life = Config.getLife() - 1;
            Config.setLife(life);
            System.out.println(life);
            Config.getLifeLabel().setText("Lives: " + Config.getLife());
            model.setValueAt(0, pacmanRow, pacmanCol);
            enemyRow = 0;
            enemyCol = 0;
            enemyTwoRow = 0;
            enemyTwoCol = 0;
            System.out.println("collision");
            // remove enemy from cell and update table
            table.repaint(); 

            if (life <= 0) {
                // Gameover condition: no more lives left
                enemyRow = 0;
                enemyCol = 0;
                enemyTwoRow = 0;
                enemyTwoCol = 0;
                addHighScoreDialog();
                gameover();
                
                //new MainMenu();
            }
            else {
                // Respawn pacman at the bottom right corner
                pacmanRow = table.getRowCount() - 1;
                pacmanCol = table.getColumnCount() - 1;
                model.setValueAt(1, pacmanRow, pacmanCol);
                table.repaint();
            }
        }
    }

    
   private void addHighScoreDialog() {
        String playerName = JOptionPane.showInputDialog(null, "Enter your name:");
        if (playerName != null) {
            HighScore hs = new HighScore(playerName, Config.getScore());
            HighScoreManager.addHighScore(hs);
        }
    }

    
    
    private void gameover() {
        System.out.println("Game Over");       

        // Pause the game
        pacmanThread.interrupt();
        enemyThread.interrupt();
        enemyTwoThread.interrupt();

        // Close the window
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(table);
        frame.dispose(); // Close the window
        
}


    public static void startGame() {
        // Prompt user for board size
        int boardSize = Integer.parseInt(javax.swing.JOptionPane.showInputDialog(null, "Enter board size (between 10 and 100):"));
        Config.setBoardSize(boardSize);

        // Check if board size is within range
        if (boardSize < 10 || boardSize > 100) {
            System.out.println("Invalid board size.");
            System.exit(0);
        }

        // Create and start the game
        SwingUtilities.invokeLater(() -> {
            new TableExample(boardSize);
        });
    }




    
    
    
    
    // ...
    
    public void addTableListener(PacmanTableListener listener) {
        tableListeners.add(listener);
    }
    
    public void removeTableListener(PacmanTableListener listener) {
        tableListeners.remove(listener);
    }
    
    

    
    
    // way of tracking the last key pressed 
    private int lastKeyCode = -1;

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        lastKeyCode = e.getKeyCode();
    }

    @Override
    public void keyReleased(KeyEvent e) {}

}
