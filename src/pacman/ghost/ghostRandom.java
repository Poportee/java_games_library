package pacman.ghost;

import java.awt.*;

public class ghostRandom extends SuperGhost{
    private int x, y;
    
    public ghostRandom(int c, int hS, int eH, int eL, int x, int y, boolean sort){
        super(c,hS,eH,eL,sort);
        this.x = x;
        this.y = y;
    }
    
    public void bouge(Graphics2D g2) {
        int largeur = 3*echLarg/4;
        int longueur = 3*echHaut/4;
        dessineToi(x,y,largeur,longueur,g2);
    }    
}
