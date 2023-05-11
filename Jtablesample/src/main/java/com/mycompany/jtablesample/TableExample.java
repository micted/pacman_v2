/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jtablesample;

/**
 *
 * @author Hp
 */

import controller.PacmanController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import models.PacmanTableModel;


public class TableExample extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    public TableExample(int boardSize) {
        // create the model and table with zeros
        int[][] data = new int[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                data[i][j] = 0;
            }
        }
        // randomly place a single "1" value
        Random random = new Random();
        int row = random.nextInt(boardSize);
        int col = random.nextInt(boardSize);
        data[row][col] = 1;

        PacmanTableModel model = new PacmanTableModel(data);
        JTable table = new JTable(model);
        //table.setRowSelectionAllowed(false);
        table.setSelectionBackground(Color.BLACK);
        table.setDefaultRenderer(Image.class, new Renderer());
        
        // *** STYLING TABLE ***// 
        
        // set the background color of the table
        table.setBackground(Color.BLACK);

        // set the color of the grid lines
        table.setGridColor(Color.WHITE);
        // set the preferred size of the table cells
        table.setRowHeight(32);
        table.getColumnModel().getColumn(0).setPreferredWidth(32);
        table.setTableHeader(null);
        

        
        

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
        
        
        // create and add PacmanController as key listener
        PacmanController controller = new PacmanController(table, model);
        controller.addTableListener(model);
        table.addKeyListener(controller);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        // prompt user for board size
        int boardSize = Integer.parseInt(javax.swing.JOptionPane.showInputDialog(null, "Enter board size (between 10 and 100):"));

        // check if board size is within range
        if (boardSize < 10 || boardSize > 100) {
            System.out.println("Invalid board size.");
            System.exit(0);
        }

        new TableExample(boardSize);
    }
}


/*
import java.awt.BorderLayout;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import models.PacmanTableModel;

public class TableExample extends JFrame {

    private JTable table;
    private DefaultTableModel model;

   public TableExample() {
    int[][] data = new int[][] {
        {0, 0, 0},
        {1, 0, 0},
        {0, 0, 0}
    };
    PacmanTableModel model = new PacmanTableModel(data);
    JTable table = new JTable(model);
    table.setDefaultRenderer(Image.class, new Renderer());

    JScrollPane scrollPane = new JScrollPane(table);
    add(scrollPane);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    pack();
    setLocationRelativeTo(null);
    setVisible(true);
}

    public static void main(String[] args) {
        new TableExample();
    }
}
*/
