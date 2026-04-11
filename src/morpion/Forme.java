package morpion;

import java.awt.Color;
import java.awt.Graphics;

public class Forme {
    private boolean estCercle;
    private int x, y;   //Coordonnées du centre 
    
    public Forme(boolean cercle, int x, int y){
        estCercle = cercle;
        this.x = x;
        this.y = y;
    }
    
    public void dessineToi(Graphics g){
        if(estCercle){
            g.setColor(Color.RED);
            g.fillOval(x-80, y-80, 160, 160);
            g.setColor(Color.WHITE);
            g.fillOval(x-70, y-70, 140, 140);
        }
        else{
            g.setColor(Color.BLUE);
            g.fillPolygon(new int[]{x-75,x-80,x+75,x+80},new int[] {y-80,y-75,y+80,y+75}, 4);
            g.fillPolygon(new int[]{x+75,x+80,x-75,x-80},new int[] {y-80,y-75,y+80,y+75}, 4);
        }
    }
    
    public boolean estUnCercle(){
        return estCercle;
    }
}
