package pacman;

import java.awt.*;

public class Player {
    int echLarg,echHaut,WIDTH,HEIGHT;
    
    //Infos player
    int orientationP;
    int dP = 30;
    int xP,yP;
    int speedP;
    boolean alive = true;
    
    //Animation player
    int dureeAnim = 4;         
    int compteurAnim = 0;
    
    public Player(int echLarg, int echHaut, int WIDTH, int HEIGHT, int speedP){
        this.echLarg = echLarg;
        this.echHaut = echHaut;
        this.speedP = speedP;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        xP = 5*echLarg;
        yP = 10*echHaut + echHaut/2;
        orientationP = 0;
    }
    
    public boolean murGauche(Type t){
        return (t == Type.PLEIN || t == Type.BARRE0 || t == Type.COIN0 || t == Type.COIN3 || t == Type.DBARRE1 || t == Type.CUL1 || t == Type.CUL2 || t == Type.CUL3);
    }
    
    public boolean murHaut(Type t){
        return (t == Type.PLEIN || t == Type.BARRE1 || t == Type.COIN0 || t == Type.COIN1 || t == Type.DBARRE0 || t == Type.CUL0 || t == Type.CUL1 || t == Type.CUL2);
    }
     
    public boolean murDroite(Type t){
        return (t == Type.PLEIN || t == Type.BARRE2 || t == Type.COIN1 || t == Type.COIN2 || t == Type.DBARRE1 || t == Type.CUL0 || t == Type.CUL1 || t == Type.CUL3);
    }
      
    public boolean murBas(Type t){
        return (t == Type.PLEIN || t == Type.BARRE3 || t == Type.COIN2 || t == Type.COIN3 || t == Type.DBARRE0 || t == Type.CUL0 || t == Type.CUL2 || t == Type.CUL2);
    }
       
    public boolean estCentreCase(){
        return(xP%echLarg >= echLarg/2 - speedP && xP%echLarg <= echLarg/2 + speedP && yP%echHaut >= echHaut/2 - speedP && yP%echHaut <= echHaut/2 + speedP);
    }
    
    public void avance(Type[][] formeNiv){
        switch(orientationP){
            case(0): 
                if(!(this.estCentreCase() && murGauche(formeNiv[yP/echHaut][xP/echLarg]))){ 
                    xP-=speedP;
                }
                break;
            case(1):
                if(!(this.estCentreCase() && murHaut(formeNiv[yP/echHaut][xP/echLarg]))){ 
                    yP-=speedP;
                }
                break;
            case(2): 
                if(!(this.estCentreCase() && murDroite(formeNiv[yP/echHaut][xP/echLarg]))){ 
                    xP+=speedP;
                }  
                break;
            case(3): 
                if(!(this.estCentreCase() && murBas(formeNiv[yP/echHaut][xP/echLarg]))){ 
                    yP+=speedP;
                }
        }
    }
    
    public void tourne(Type[][] formeNiv, boolean left, boolean right, boolean up, boolean down){
        if(left && ( (this.estCentreCase() && !murGauche(formeNiv[yP/echHaut][xP/echLarg])) || orientationP == 2) ){
            orientationP = 0;
        }
        if(up && ( (this.estCentreCase() && !murHaut(formeNiv[yP/echHaut][xP/echLarg])) || orientationP == 3)){
            orientationP = 1;
        }
        if(right && ( (this.estCentreCase() && !murDroite(formeNiv[yP/echHaut][xP/echLarg])) || orientationP == 0)){
            orientationP = 2;
        }
        if(down && ( (this.estCentreCase() && !murBas(formeNiv[yP/echHaut][xP/echLarg])) || orientationP == 1)){
            orientationP = 3;
        }
        if(xP>=WIDTH*echLarg-speedP-2){
            xP = speedP+3;
        }
        else if(xP<=speedP+2){
            xP = WIDTH*echLarg-speedP-3;
        }
    } 

    public void dessinePlayer(Graphics2D g2){
        g2.setColor(Color.YELLOW);
        g2.drawOval(xP-dP/2,yP-dP/2,dP,dP);
        g2.fillOval(xP-dP/2,yP-dP/2,dP,dP);
        if (compteurAnim <= 2*dureeAnim){
            switch(orientationP){
                case(0): 
                    g2.setColor(Color.BLACK);
                    g2.drawPolygon(new int[] {xP, xP-dP/2, xP-dP/2}, new int[] {yP, yP+dP*7/20 , yP-dP*7/20}, 3);
                    g2.fillPolygon(new int[] {xP, xP-dP/2, xP-dP/2}, new int[] {yP, yP+dP*7/20 , yP-dP*7/20}, 3);
                    break;
                case(1): 
                    g2.setColor(Color.BLACK);
                    g2.drawPolygon(new int[] {xP, xP+dP*7/20, xP-dP*7/20}, new int[] {yP, yP-dP/2 , yP-dP/2}, 3);
                    g2.fillPolygon(new int[] {xP, xP+dP*7/20, xP-dP*7/20}, new int[] {yP, yP-dP/2 , yP-dP/2}, 3);
                    break;
                case(2): 
                    g2.setColor(Color.BLACK);
                    g2.drawPolygon(new int[] {xP, xP+dP/2, xP+dP/2}, new int[] {yP, yP+dP*7/20 , yP-dP*7/20}, 3);
                    g2.fillPolygon(new int[] {xP, xP+dP/2, xP+dP/2}, new int[] {yP, yP+dP*7/20 , yP-dP*7/20}, 3);
                    break;
                case(3): 
                    g2.setColor(Color.BLACK);
                    g2.drawPolygon(new int[] {xP, xP+dP*7/20, xP-dP*7/20}, new int[] {yP, yP+dP/2 , yP+dP/2}, 3);
                    g2.fillPolygon(new int[] {xP, xP+dP*7/20, xP-dP*7/20}, new int[] {yP, yP+dP/2 , yP+dP/2}, 3);
            }
            
        }
        if(compteurAnim == 3*dureeAnim){
            compteurAnim = 0;
        }
        compteurAnim += 1;
    }
}
