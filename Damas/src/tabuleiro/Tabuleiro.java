/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tabuleiro;

import java.util.ArrayList;

/**
 *
 * @author gusta
 */
public class Tabuleiro {

    private ArrayList<Dama> damas = new ArrayList<>();

    public Tabuleiro() {
        init();  
 
    }

    private void init() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 8; j++) {
                if(i%2==0 && j%2==0){
                  putDama(i, j,"RED");  
                }else if(i%2==1 && j%2==1){
                  putDama(i, j,"RED");    
                }
            }
        }
        for (int i = 7; i > 4; i--) {
            for (int j = 0; j < 8; j++) {
                if(i%2==0 && j%2==0){
                  putDama(i, j,"BLUE");  
                }else if(i%2==1 && j%2==1){
                  putDama(i, j,"BLUE");    
                }
            }
        }
    }
 
    public ArrayList<Dama> getDamas() {
        return damas;
    }

    public void putDama(int row, int col,String color) {
        Dama d = new Dama(row, col,color);
        damas.add(d);
    }

    public void removeDama(int row, int col) {
        Dama d = findDama(row, col);
        if (d != null) {
            damas.remove(d);
        } else {
            System.out.println("Não existe peça nessa posicao");
        }
    }

    public Dama findDama(int row, int col) {
        for (Dama d : damas) {
            if (d.getRow() == row && d.getCol() == col) {
                return d;
            }
        }
        return null;
    }

    public boolean verificaMovimento(Dama d,int nrow, int ncol){
       
        int row = d.getRow();
        int col = d.getCol();
        
        
        if(d.isDama()){ // tem q fazer aqui pra quando for dama rip
            
        }else{
           if((row+1 == nrow) && (col-1 == ncol) && (findDama(nrow,ncol) == null)) return true;
           if((row+1 == nrow) && (col+1 == ncol) && (findDama(nrow,ncol) == null)) return true;
           if((row-1 == nrow) && (col-1 == ncol) && (findDama(nrow,ncol) == null)) return true;
           if((row-1 == nrow) && (col+1 == ncol) && (findDama(nrow,ncol) == null)) return true; 
        }
        
        
       return false;
    }
}
