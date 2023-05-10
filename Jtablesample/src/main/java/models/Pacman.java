/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author Hp
 */
public class Pacman {
    private int x;
    private int y;

    public Pacman(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getRow() {
        return x;
    }

    public int getCol() {
        return y;
    }

    public void setRow(int x) {
        this.x = x;
    }

    public void setCol(int y) {
        this.y = y;
    }

    public void setPosition(int row, int column) {
        this.x = row;
        this.y = column;
    }

}

