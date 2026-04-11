package pacman.ghost;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.*;

public abstract class SuperGhost {
    int couleur;        //1:Bleu  2:Rouge  3:Orange  4:Rose
    long heureSortie;   //Millisecondes après lesquelles le fantome devient actif
    BufferedImage image;
    int echHaut,echLarg;
    long startTime;
    boolean doitSortir;
    //int speed;
    
    //Etat
    boolean enDanger = false;
    public int countDanger = 0;
    private int dureeDanger = 6*60;
    
    public SuperGhost(int c, int hS, int eH, int eL, boolean sort){
        couleur = c;
        heureSortie = hS;
        echHaut = eH;
        echLarg = eL;
        doitSortir = sort;
        try{
            switch(couleur){
                case(1): image = ImageIO.read(getClass().getResourceAsStream("/pacman/Images/ghostBleu.png")); break;
                case(2): image = ImageIO.read(getClass().getResourceAsStream("/pacman/Images/ghostRouge.png")); break;
                case(3): image = ImageIO.read(getClass().getResourceAsStream("/pacman/Images/ghostOrange.png")); break;
                case(4): image = ImageIO.read(getClass().getResourceAsStream("/pacman/Images/ghostRose.png")); break;
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        startTime = System.currentTimeMillis();
    }
    
    abstract void bouge(Graphics2D g);
    
    public void dessineToi(int x, int y, int largeur, int longueur, Graphics2D g2){
        try{    
            if(enDanger){
                countDanger++;
                if(countDanger == 1){
                    image = ImageIO.read(getClass().getResourceAsStream("/pacman/Images/ghostEnDanger.png"));
                }
                if(countDanger == dureeDanger){   
                    countDanger = 0;
                    enDanger = false;
                    switch(couleur){
                        case(1): image = ImageIO.read(getClass().getResourceAsStream("/pacman/Images/ghostBleu.png")); break;
                        case(2): image = ImageIO.read(getClass().getResourceAsStream("/pacman/Images/ghostRouge.png")); break;
                        case(3): image = ImageIO.read(getClass().getResourceAsStream("/pacman/Images/ghostOrange.png")); break;
                        case(4): image = ImageIO.read(getClass().getResourceAsStream("/pacman/Images/ghostRose.png")); break;
                    }
                }
            }  
        }
        catch(IOException e){
            e.printStackTrace();
        }
        g2.drawImage(image,x-largeur/2,y-largeur/2,largeur,longueur,null);
    }
    
    public void setEnDanger(boolean b){
        enDanger = b;
    }
    
    public boolean getEnDanger(){
        return enDanger;
    }
    
    public void setImage(String s){
        try {
            image = ImageIO.read(getClass().getResourceAsStream(s));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
