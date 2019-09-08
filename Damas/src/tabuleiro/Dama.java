/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tabuleiro;

/**
 *
 * @author gusta
 */
public class Dama {
    private int row;
    private int col;
    private boolean dama;
    private String color;
    
    public Dama(int row,int col,String color){
        this.row = row;
        this.col = col;
        this.color = color;
        dama = false;
    }

    public String getColor() {
        return color;
    }
    


    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isDama() {
        return dama;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setDama(boolean dama) {
        this.dama = dama;
    }
    
    
    
}
