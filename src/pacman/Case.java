package pacman;

import java.awt.*;

public class Case {
    private Type type;
    
    public Case (Type t){
        type = t;
    }
    public void dessineCase(int i, int j,int echLarg,int echHaut,Graphics g,int ep){
    //Dessine la case de dimension echLarg*echHaut en fonction de son type
        int x = j*echLarg;
        int y = i*echHaut;
        g.setColor(Color.BLACK);
        g.drawRect(x,y,echLarg,echHaut);
        g.fillRect(x,y,echLarg,echHaut);
        if(type==Type.NOIR0) return;
        
        drawAllAngle(x,y,echLarg,echHaut,g,ep);
        if(type == Type.PLEIN || type == Type.BARRE0 || type == Type.COIN0 || type == Type.COIN3 || type == Type.DBARRE1 || type == Type.CUL1 || type == Type.CUL2 || type == Type.CUL3){
            drawBV(x,y,echHaut,g,ep);
        }
        if(type == Type.PLEIN || type == Type.BARRE1 || type == Type.COIN0 || type == Type.COIN1 || type == Type.DBARRE0 || type == Type.CUL0 || type == Type.CUL1 || type == Type.CUL2){
            drawBH(x,y,echLarg,g,ep);
        }
        if(type == Type.PLEIN || type == Type.BARRE2 || type == Type.COIN1 || type == Type.COIN2 || type == Type.DBARRE1 || type == Type.CUL0 || type == Type.CUL1 || type == Type.CUL3){
            drawBV(x+echLarg-ep-1,y,echHaut,g,ep);
        }
        if(type == Type.PLEIN || type == Type.BARRE3 || type == Type.COIN2 || type == Type.COIN3 || type == Type.DBARRE0 || type == Type.CUL0 || type == Type.CUL2 || type == Type.CUL2){
            drawBH(x,y+echHaut-ep-1,echLarg,g,ep);
        }
    }
    
    public void drawBH(int x, int y,int echLarg,Graphics g,int ep){
    //Dessine une barre horizontale débutant en (x,y)
    //de ep px d'épaisseur vers les y positifis
    //de echLarg longueur
        g.setColor(new Color (33,33,255));
        g.drawRect(x, y, echLarg, ep);
        g.fillRect(x, y, echLarg+1, ep+1);
        /*g.drawLine(x, y, x+echLarg-1, y);
        g.drawLine(x, y+1, x+echLarg-1, y+1);
        g.drawLine(x, y+2, x+echLarg-1, y+2);
        g.drawLine(x, y+3, x+echLarg-1, y+3);*/
    }
    public void drawBV(int x, int y, int echHaut, Graphics g, int ep){
    //Dessine une barre verticale débutant en (x,y)
    //de ep px d'épaisseur vers les x positifis
    //de echHaut longueur
        g.setColor(new Color (33,33,255));
        g.drawRect(x, y, ep, echHaut);
        g.fillRect(x, y, ep+1, echHaut+1);
        /*g.drawLine(x, y, x, y);
        g.drawLine(x+1, y, x+1, y+echHaut-1);
        g.drawLine(x+2, y, x+2, y+echHaut-2);
        g.drawLine(x+3, y, x+3, y+echHaut-3);*/
    }
    public void drawAngle(int x, int y, Graphics g, int ep){
    //Dessine un rectangle plein bleu dont le coin supérieur gauche a pour
    //coordonnées (x,y) et dont les cotés sont de ep px d'épaisseur
        g.setColor(new Color (33,33,255));
        g.drawRect(x,y,ep,ep);
        g.fillRect(x,y,ep+1,ep+1);
    }
    public void drawAllAngle(int x, int y, int echLarg, int echHaut, Graphics g, int ep){
    //Dessine un carré bleue dans tous les angles de la case de coordonnées (x,y)
        drawAngle(x, y, g,ep);
        drawAngle(x, y+echHaut-ep-1, g,ep);
        drawAngle(x+echLarg-ep-1, y+echHaut-ep-1, g,ep);
        drawAngle(x+echLarg-ep-1, y, g,ep);
    }
    public void setType(Type t){
        type = t;
    }
    public Type getType(){
        return type;
    }
}
