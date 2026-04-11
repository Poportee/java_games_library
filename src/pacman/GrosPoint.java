package pacman;

import java.awt.Color;
import java.awt.Graphics;

public class GrosPoint {
    int x,y,d=18;
    
    public GrosPoint(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public void dessineToi(Graphics g){
        g.setColor(Color.WHITE);
        g.drawOval(x-d/2, y-d/2, d, d);
        g.fillOval(x-d/2, y-d/2, d, d);
    }
}
