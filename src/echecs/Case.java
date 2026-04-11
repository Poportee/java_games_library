package echecs;

import java.awt.*;

public class Case extends Canvas{
    private boolean colourBlanc;
    private Piece piece;
    private Coordonnees co;
    private boolean surligne;
    
    public Case (boolean c, Piece p, Coordonnees coor){
        colourBlanc = c;
        piece = p;
        co = coor;
        surligne = false;
    }
    
    public void dessineToi(Graphics g, int echHaut, int echLarg){
    //Dessine une case de dimenseion echHaut*echLarg aux coordonnées co
    //Rajoute un point si surligne
        int x = co.getI()*echLarg;
        int y = co.getJ()*echHaut;
        g.drawRect(x,y,echLarg,echHaut);
        if (colourBlanc){
            g.setColor(new Color(255, 218, 179));
            g.fillRect(x,y,echLarg,echHaut);
        }
        else{
            g.setColor(new Color(145, 52, 0));
            g.fillRect(x,y,echLarg,echHaut);
        }
        if(surligne){
            g.setColor(Color.GRAY);
            g.drawRect(x+2,y+2,echLarg-5,echHaut-5);
            //g.setColor(new Color(0,0,0,150));
            g.fillOval(x+echLarg/3,y+echHaut/3,echLarg/3,echHaut/3);
        }
    }
    
    public Piece getPiece (){
        return piece;
    }
    
    public Coordonnees getCoor(){
        return co;
    }
    
    public void setCoordonnees (Coordonnees co){
        this.co = co;
    }
    
    public void setPiece(Coordonnees c, Type t, boolean estBlanc){
        piece = new Piece(c,t,estBlanc);
    }
    
    public void setPiece(Piece p){
        piece = p;
    }
    
    public boolean getSurligne(){
        return surligne;
    }
    
    public void setSurligne(boolean val){
        surligne = val;
    }
    
    public boolean estBlanc(){
        return colourBlanc;
    }
}