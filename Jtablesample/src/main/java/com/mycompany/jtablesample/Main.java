/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jtablesample;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import models.Pacman;
import models.PacmanTableModel;

public class Main extends JFrame {

    private JTable table;
    private PacmanTableModel tableModel;
    private PacManRenderer renderer;

    public Main() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Pacman");
        setPreferredSize(new Dimension(640, 480));

        // create the data for the table
        int[][] data = new int[20][20];
        data[5][5] = 1;
        Pacman pacman = new Pacman(5,5);

        // create the table model
        tableModel = new PacmanTableModel(pacman,data);

        // create the table and set the table model
        table = new JTable(tableModel);

        // create the renderer and set it as the default cell renderer for the table
        renderer = new PacManRenderer();
        //table.setDefaultRenderer(Integer.class, renderer);
        table.getColumnModel().getColumn(5).setCellRenderer(renderer);
        // set the background color of the table
        table.setBackground(Color.BLACK);

        // set the foreground (text) color of the table
        table.setForeground(Color.WHITE);

        // set the color of the grid lines
        table.setGridColor(Color.WHITE);
        // set the preferred size of the table cells
        table.setRowHeight(32);
        table.getColumnModel().getColumn(0).setPreferredWidth(32);

        // add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);

        // add the scroll pane to the frame
        add(scrollPane, BorderLayout.CENTER);

        // create the Pacman and set it in the table model and the renderer
        
        tableModel.setPacman(pacman);
        renderer.setPacman(pacman);

        // pack and show the frame
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}


/**
 *
 * @author Hp
 */

/*
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import models.PacmanTableModel;
import models.Pacman;

public class Main extends JFrame {

    public Main() {
        
        Pacman pac = new Pacman(5,4);
        int boardSize = 0;
        while (boardSize < 10 || boardSize > 100) {
            boardSize = Integer.parseInt(JOptionPane.showInputDialog("Enter board size (10-100):"));
        }
        // create sample data
        int[][] data = new int[boardSize][boardSize];

        // create the table model with the data
        PacmanTableModel model = new PacmanTableModel(data);

        // create the table
        JTable table = new JTable(model);

        // set the renderer for column 1 to PacmanRenderer
        //table.getColumnModel().getColumn(1).setCellRenderer((TableCellRenderer) new PacManRenderer());
        // create a new PacManRenderer instance with the Pacman object
        PacManRenderer pacManRenderer = new PacManRenderer(pac);

        //table.setDefaultRenderer(Object.class, pacManRenderer);

        // set the custom cell renderer for the specified cell
       table.getColumnModel().getColumn(pac.getCol()).setCellRenderer(pacManRenderer);
        

        // set the background color of the table
        table.setBackground(Color.BLACK);

        // set the foreground (text) color of the table
        table.setForeground(Color.WHITE);

        // set the color of the grid lines
        table.setGridColor(Color.WHITE);

        // add the table to the frame
        this.add(new JScrollPane(table));

        this.setTitle("Pacman Table Example");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main();
            }
        });
    }

}

*/

