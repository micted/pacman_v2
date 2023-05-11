package models;
import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;
import java.awt.Image;
import controller.PacmanController;
import interfaces.PacmanTableListener;

public class PacmanTableModel extends AbstractTableModel implements PacmanTableListener{
    
    

    private int[][] data; // two-dimensional array of integers representing Pacman locations
    private Image pacmanImage;
    private int currentRow;
    private int currentCol;
    private int angle = 0;
        
    

    public PacmanTableModel(int[][] data) {
        this.data = data;
        //this.pacmanImage = new ImageIcon("C://Users//Hp//Pictures//pacman.png").getImage();
       
        

    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return data[0].length;
    }
    
    @Override
    public void onPacmanRotated(int angle) {
        this.angle = angle;
        fireTableDataChanged();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        
        
        if (data[rowIndex][columnIndex] == 1) {
            
             if (angle >= -45 && angle < 45) {
            // pacman facing right
                pacmanImage = new ImageIcon("C://Users//Hp//Pictures//pacman.png").getImage();
            } else if (angle >= 45 && angle < 135) {
                // pacman facing up
                pacmanImage = new ImageIcon("C://Users//Hp//Pictures//pacman_down.png").getImage();
            } else if (angle >= 135 && angle < 225) {
                // pacman facing left
                pacmanImage = new ImageIcon("C://Users//Hp//Pictures//pacman_left.png").getImage();
            } else if (angle >= 225 && angle < 315) {
                // pacman facing down
                pacmanImage = new ImageIcon("C://Users//Hp//Pictures//pacman_up.png").getImage();
            } else {
                // pacman facing right
                pacmanImage = new ImageIcon("C://Users//Hp//Pictures//pacman.png").getImage();
            }
            return pacmanImage.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        } else {
            return null;
        }
    }

    @Override        
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        
        if (value != null) {
            data[rowIndex][columnIndex] = (int) value;
            fireTableCellUpdated(rowIndex, columnIndex);
        }
    }


    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return Image.class;
    }
}

/*
import javax.swing.table.AbstractTableModel;

public class PacmanTableModel extends AbstractTableModel {
    private int[][] data;
    private Pacman pacman;

    public PacmanTableModel(Pacman pacman,int[][] data) {
        this.data = data;
        this.pacman = pacman;
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return data[0].length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return null;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return Integer.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    public void setValueAt(int value, int rowIndex, int columnIndex) {
        data[rowIndex][columnIndex] = (int) value;
        if (value == 1) {
            pacman.setPosition(rowIndex, columnIndex);
        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    public void setPacman(Pacman pacman) {
        this.pacman = pacman;
    }

    public Pacman getPacman() {
        return pacman;
    }
}
*/