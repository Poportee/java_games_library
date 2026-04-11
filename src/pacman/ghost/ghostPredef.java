package pacman.ghost;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class ghostPredef extends SuperGhost{
    private Random rand = new Random();
    int largeur = 3*echLarg/4;
    int longueur = 3*echHaut/4;
    
    //Position 
    private int locaParcours = 0;
    private int locaLong = 0;
    private int countLong = 24;
    private int x, y;
    
    //Différents parcours prédéfinis pour les fantômes
    private int[][] parcours = 
       {{0,3,3,3,2,3,2,2,2,3,2,1,2,1,0,0,1,1,2,2,4,2,2,1,1,1,0,0,1,1,2,2,2,2,3,3,2,2,2,3,3,3,0,3,0,0,0,3,0,3,3,0,1,0,1,2,2,1,1,1,1,1,2,3,2,3},
        {2,1,2,1,0,1,1,2,2,3,3,3,2,2,1,0,0,0,0,0,0,0,3,3,3,3,3,3,2,3,2,3,0,0,0,0,1,2,1,0,1,2,2,2,2,3,2,1,2,2,1,1,0,3,0,0,0,1,1,2},    
        {1,0,1,2,2,2,2,2,2,3,0,0,3,3,3,3,0,0,3,0,0,0,3,0,1,0,1,2,2,2,2,3,2,2,2,1,1,1,2,2,4,2,2,1,1,0,0,1,1,1,2,2,2,2,3,3,2,2,3,0,3,0},
        {0,3,3,2,2,2,3,0,3,2,2,3,2,2,3,0,0,0,0,1,2,1,0,0,0,3,2,3,0,0,0,0,1,2,2,1,1,1,1,1,1,1,2,3,2,3,2,2,3,2,2,2,4,2,2,1,1,1,1,1,2,2,3,3,0,0,1,1,2,2,3,3,2,2,3,0,3,0},
        {2,2,3,2,1,1,1,1,1,2,2,3,3,0,0,3,2,2,1,0,0,0,0,0,0,0,3,3,3,3,3,3,3,0,0,3,2,2,2,2,2,2,2,2,2,1,0,0,1,1,0,0,3,0,1,0,1,1,1,2},
        {0,3,0,0,0,5,0,0,3,3,0,0,3,2,2,1,2,2,3,0,3,2,3,0,0,0,0,0,1,0,1,0,3,0,1,0,1,2,2,1,1,1,1,0,0,1,1,1,2,2,3,3,2,2,2,2,2,3,3,3,0,1,0,0}
       };
    private int[] parcoursSuivi;
    
    
    public ghostPredef(int c, int hS, int eH, int eL, int x, int y, boolean sort){
        super(c,hS,eH,eL,sort);
        this.x = x;
        this.y = y;
        
        //Sélection d'un parcours aléatoire pour le fantôme
        parcoursSuivi = parcours[rand.nextInt(parcours.length)];
    }
    
    public void bouge(Graphics2D g2) {
        dessineToi(x,y,largeur,longueur,g2);
        
        if(System.currentTimeMillis()>startTime+heureSortie){
            if(doitSortir){
                x=4*echLarg+echLarg/2;
                y=6*echHaut+echHaut/2;
                doitSortir = false;
            }
            if(locaLong==countLong){
                locaLong=0;
                locaParcours++;
                if(locaParcours == parcoursSuivi.length){
                    locaParcours = 0;
                }
            }
            else{
                locaLong++;
            }
            switch(parcoursSuivi[locaParcours]){
                case(0): x-=2; break;
                case(1): y-=2; break;
                case(2): x+=2; break;
                case(3): y+=2; break;
                case(4): x=echLarg/2; locaParcours++; break;
                case(5): x=475; locaParcours++; 
            }
        }
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
}
