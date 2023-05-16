package view;






import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import models.Pacman;

public class PacManRenderer extends JLabel implements TableCellRenderer {

    private Pacman pacman;
    
    public PacManRenderer() {        
        setOpaque(true);
    }
    

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        // set the background color
        if (value.equals(1) && row == 5 && column == 5) {
            setBackground(Color.BLACK);
        } else {
            setBackground(table.getBackground());
        }

       

        return this;
    }




    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // create a Graphics2D object
        Graphics2D g2d = (Graphics2D) g.create();

        // enable anti-aliasing for smoother edges
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // calculate the size of the Pacman based on the size of the cell
        int size = Math.min(getWidth(), getHeight()) * 3 / 4;

        // calculate the position of the Pacman within the cell
        int x = (getWidth() - size) / 2;
        int y = (getHeight() - size) / 2;

        // draw the Pacman
        g2d.setColor(Color.YELLOW);
        g2d.fillArc(x, y, size, size, 45, 270);
        g2d.setColor(Color.BLACK);
        g2d.drawArc(x, y, size, size, 45, 270);
        g2d.setColor(Color.WHITE);
        g2d.fillOval(x + size / 2 - size / 10, y + size / 2 - size / 5, size / 5, size / 5);

        // dispose the Graphics2D object
        g2d.dispose();
    }
    
    public void setPacman(Pacman pacman) {
        this.pacman = pacman;
    }

}


