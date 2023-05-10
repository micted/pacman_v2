/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JTable;
import models.PacmanTableModel;

public class PacmanController implements KeyListener {
    
    private JTable table;
    private PacmanTableModel model;
    private int pacmanRow;
    private int pacmanCol;
    
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
    }
    
    
    
    

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        int newRow = pacmanRow;
        int newCol = pacmanCol;
        // update the new row and column based on the arrow key pressed
        switch (keyCode) {
            case KeyEvent.VK_UP:
                newRow--;
                break;
            case KeyEvent.VK_DOWN:
                newRow++;
                break;
            case KeyEvent.VK_LEFT:
                newCol--;
                break;
            case KeyEvent.VK_RIGHT:
                newCol++;
                break;
            default:
                return; // ignore other keys
        }
        // check if the new row and column are within the bounds of the table
        if (newRow < 0 || newRow >= model.getRowCount() || newCol < 0 || newCol >= model.getColumnCount()) {
            return; // ignore out-of-bounds moves
        }
        // check if the new cell is already occupied by another Pacman image
        if (model.getValueAt(newRow, newCol) != null) {
            return; // ignore overlapping moves
        }
        // move the Pacman image to the new cell and update the model
        model.setValueAt(0, pacmanRow, pacmanCol);
        model.setValueAt(1, newRow, newCol);
        table.repaint(); // force table to redraw
        pacmanRow = newRow;
        pacmanCol = newCol;
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
