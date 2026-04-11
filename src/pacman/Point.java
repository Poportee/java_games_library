package pacman;

import java.awt.Color;
import java.awt.Graphics;

public class Point {
    
    //int x,y;
    int d=5;
    
   
    public Point(/*int x,int y*/){
        //this.x=x;
        //this.y=y;
    }
    
    public void dessinePoint(Graphics g, int i, int j, int echLarg, int echHaut){
        int x =j*echHaut+echHaut/2;
        int y = i*echLarg+echLarg/2;
        g.setColor(Color.white);
        g.drawOval(x-d/2, y-d/2,d,d);
        g.fillOval(x-d/2, y-d/2,d,d);
    }
}
