package pacman;

import java.awt.*;

public class PlateauNiveau1 {
    private int HEIGHT,WIDTH;
    private int echLarg;
    private int echHaut;
    private Case[][] plateau;
    private Point[][] points;
    private GrosPoint[] grosPoints = {new GrosPoint(25,150),new GrosPoint(475,150),new GrosPoint(25,500),new GrosPoint(475,500)};
    private int[][] grillePt = 
       {{0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0},
        {1,1,1,1,1,1,1,1,1,1},
        {1,0,1,0,1,1,0,1,0,1},
        {1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1},
        {0,0,1,0,0,0,0,1,0,0},
        {0,0,1,0,0,0,0,1,0,0},
        {0,0,1,0,0,0,0,1,0,0},
        {1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1},
        {0,0,0,0,0,0,0,0,0,0}
        };
    private Type[][] formeNiv = 
       {{Type.NOIR0,Type.NOIR0,Type.NOIR0,Type.NOIR0,Type.NOIR0,Type.NOIR0,Type.NOIR0,Type.NOIR0,Type.NOIR0,Type.NOIR0},
        {Type.NOIR0,Type.NOIR0,Type.NOIR0,Type.NOIR0,Type.NOIR0,Type.NOIR0,Type.NOIR0,Type.NOIR0,Type.NOIR0,Type.NOIR0},
        {Type.COIN0,Type.DBARRE0,Type.BARRE1,Type.DBARRE0,Type.COIN1,Type.COIN0,Type.DBARRE0,Type.BARRE1,Type.DBARRE0,Type.COIN1},  
        {Type.DBARRE1,Type.PLEIN,Type.DBARRE1,Type.PLEIN,Type.DBARRE1,Type.DBARRE1,Type.PLEIN,Type.DBARRE1,Type.PLEIN,Type.DBARRE1},
        {Type.BARRE0,Type.DBARRE0,Type.NOIR1,Type.BARRE1,Type.BARRE3,Type.BARRE3,Type.BARRE1,Type.NOIR1,Type.DBARRE0,Type.BARRE2},
        {Type.COIN3,Type.DBARRE0,Type.BARRE2,Type.COIN3,Type.COIN1,Type.COIN0,Type.COIN2,Type.BARRE0,Type.DBARRE0,Type.COIN2},
        {Type.DBARRE0,Type.CUL0,Type.DBARRE1,Type.COIN0,Type.BARRE3,Type.BARRE3,Type.COIN1,Type.DBARRE1,Type.CUL2,Type.DBARRE0},
        {Type.DBARRE0,Type.DBARRE0,Type.NOIR1,Type.BARRE2,Type.NOIR0,Type.NOIR0,Type.BARRE0,Type.NOIR1,Type.DBARRE0,Type.DBARRE0},
        {Type.DBARRE0,Type.CUL0,Type.DBARRE1,Type.BARRE0,Type.DBARRE0,Type.DBARRE0,Type.BARRE2,Type.DBARRE1,Type.CUL2,Type.DBARRE0},
        {Type.COIN0,Type.DBARRE0,Type.NOIR1,Type.BARRE3,Type.COIN1,Type.COIN0,Type.BARRE3,Type.NOIR1,Type.DBARRE0,Type.COIN1},
        {Type.COIN3,Type.COIN1,Type.BARRE0,Type.BARRE1,Type.BARRE3,Type.BARRE3,Type.BARRE1,Type.BARRE2,Type.COIN0,Type.COIN2},
        {Type.COIN0,Type.BARRE3,Type.COIN2,Type.COIN3,Type.COIN1,Type.COIN0,Type.COIN2,Type.COIN3,Type.BARRE3,Type.COIN1},
        {Type.COIN3,Type.DBARRE0,Type.DBARRE0,Type.DBARRE0,Type.BARRE3,Type.BARRE3,Type.DBARRE0,Type.DBARRE0,Type.DBARRE0,Type.COIN2},
        {Type.NOIR0,Type.NOIR0,Type.NOIR0,Type.NOIR0,Type.NOIR0,Type.NOIR0,Type.NOIR0,Type.NOIR0,Type.NOIR0,Type.NOIR0}
        };
    
    public PlateauNiveau1(int H, int W){
        HEIGHT = H;
        WIDTH = W;
        echLarg = 500/WIDTH;
        echHaut = 700/HEIGHT;
        plateau = new Case[HEIGHT][WIDTH];
        points = new Point[HEIGHT][WIDTH];
    }
    
    public void dessinePlateau(Graphics g){
        //Dessine les cases du plateau
        for(int i=0;i<HEIGHT;i++){
            for(int j =0;j<WIDTH;j++){
                    plateau[i][j] = new Case(formeNiv[i][j]);
                    plateau[i][j].dessineCase(i,j,echLarg,echHaut,g,2);
            }
        }
        
        //Dessine les points du plateau
        for(int i=0;i<HEIGHT;i++){
            for(int j =0;j<WIDTH;j++){
                if(grillePt[i][j]==1){
                    points[i][j] = new Point();
                    points[i][j].dessinePoint(g, i, j, echLarg, echHaut);
                }
            }
        }
        
        //Dessine les gros points du plateau
        for(int i=0;i<4;i++){
            if(grosPoints[i]!=null){
                grosPoints[i].dessineToi(g);
            }
        }
    }
    
    public Type[][] getFormeNiv(){
        return formeNiv;
    }
    public int getNombre(int i, int j){
        return grillePt[i][j];}
        
    public void setPt0(int i,int j){
        grillePt[i][j] = 0;
    }
    
    public GrosPoint getGrosPoint(int i){
        return grosPoints[i];
    }
    
    public void retireGrosPoint(int i){
        grosPoints[i] = null;
    }
}
