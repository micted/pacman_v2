package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import models.PacmanTableModel;

public class PacmanController implements KeyListener {

    private JTable table;
    private PacmanTableModel model;
    private int pacmanRow;
    private int pacmanCol;
    private Thread pacmanThread;

    public PacmanController(JTable table, PacmanTableModel model) {
        this.table = table;
        this.model = model;
        // find the initial row and column of the Pacman image
        for (int row = 0; row < model.getRowCount(); row++) {
            for (int col = 0; col < model.getColumnCount(); col++) {
                if (model.getValueAt(row, col) != null) {
                    pacmanRow = row;
                    pacmanCol = col;
                    break;
                }
            }
        }

        pacmanThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    movePacman();
                }
            }
        });
        pacmanThread.start();
    }

    private void movePacman() {
        int currentRow = pacmanRow;
        int currentCol = pacmanCol;
        // determine the next cell to move to based on the direction of the last arrow key pressed
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
        // check if the new cell is within the bounds of the table
        if (currentRow >= 0 && currentRow < model.getRowCount() && currentCol >= 0 && currentCol < model.getColumnCount()) {
            // check if the new cell is already occupied by another Pacman image
            if (model.getValueAt(currentRow, currentCol) == null) {
                // move the Pacman image to the new cell
                model.setValueAt(0, pacmanRow, pacmanCol); // erase the old Pacman image
                model.setValueAt(1, currentRow, currentCol);
                table.repaint(); // force table to redraw

                // update the current position of the Pacman image
                pacmanRow = currentRow;
                pacmanCol = currentCol;
            }
        }
    }

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
