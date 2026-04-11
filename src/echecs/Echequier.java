package echecs;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.JFrame;

public class Echequier extends Canvas implements MouseListener, KeyListener {
    private static final int taille = 8;
    private boolean tourBlanc = true;
    private Case[][] plateau = new Case[taille][taille];
    private int echLarg = 600/plateau.length;
    private int echHaut = 600/plateau[0].length;
    private Piece pieceSelect;
    private JFrame frame;
    
    public Echequier(JFrame f){
    //Initialise la matrice plateau contenant les données de chaque case de l'échequier
        frame = f;
        addMouseListener(this);
        addKeyListener(this);
        for(int i=0; i<taille; i++){
            for(int j=0; j<taille; j++){
		if((j%2==0 && i%2==0) || (j%2!=0 && i%2!=0)){
                    plateau[i][j] = new Case (true,null, new Coordonnees(i,j));
		}
		else{
                    plateau[i][j] = new Case (false, null, new Coordonnees(i,j));
		}
            }
	}
        initPiece();
    }
    
    @Override
    public void paint(Graphics g){
    //Dessine les cases et les pièces du plateau
        for(int i=0; i<taille; i++){
            for(int j=0; j<taille; j++){
                plateau[i][j].dessineToi(g,echHaut,echLarg);
                if (plateau[i][j].getPiece() != null){
                    plateau[i][j].getPiece().dessineToi(this,g,echLarg,echHaut);
                }
            }
        }
    }
    
    public void initPiece(){
    //Initialise les pièces a leur position pour une partie classique d'échecs
        for(int i=0; i<taille; i++){
            plateau[i][6].setPiece(new Coordonnees(i,6),Type.PION,true);
        }
        for(int i=0; i<taille; i++){
            plateau[i][1].setPiece(new Coordonnees(i,1),Type.PION,false);        
        }
        plateau[0][0].setPiece(new Coordonnees(0,0),Type.TOUR,false);
        plateau[7][0].setPiece(new Coordonnees(7,0),Type.TOUR,false);    
        plateau[1][0].setPiece(new Coordonnees(1,0),Type.CAVALIER,false);
        plateau[6][0].setPiece(new Coordonnees(6,0),Type.CAVALIER,false);
        plateau[2][0].setPiece(new Coordonnees(2,0),Type.FOU,false);
        plateau[5][0].setPiece(new Coordonnees(5,0),Type.FOU,false);
        plateau[3][0].setPiece(new Coordonnees(3,0),Type.DAME,false);
        plateau[4][0].setPiece(new Coordonnees(4,0),Type.ROI,false);
        plateau[0][7].setPiece(new Coordonnees(0,7),Type.TOUR,true);
        plateau[7][7].setPiece(new Coordonnees(7,7),Type.TOUR,true);
        plateau[1][7].setPiece(new Coordonnees(1,7),Type.CAVALIER,true);
        plateau[6][7].setPiece(new Coordonnees(6,7),Type.CAVALIER,true);
        plateau[2][7].setPiece(new Coordonnees(2,7),Type.FOU,true);
        plateau[5][7].setPiece(new Coordonnees(5,7),Type.FOU,true);
        plateau[4][7].setPiece(new Coordonnees(4,7),Type.ROI,true);
        plateau[3][7].setPiece(new Coordonnees(3,7),Type.DAME,true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        Point pt = e.getPoint();
        pt.x /= echLarg;
        pt.y /= echHaut;
        //Mouvement d'une pièce déjà sélectionnée
        if(plateau[pt.x][pt.y].getSurligne()){
            plateau[pt.x][pt.y].setPiece(pieceSelect);
            plateau[pieceSelect.getCoordonnees().getI()][pieceSelect.getCoordonnees().getJ()].setPiece(null);
            pieceSelect.setCoordonnees(new Coordonnees (pt.x,pt.y));
            for(int i=0; i<taille;i++){
                for(int j=0;j<taille;j++){
                    plateau[i][j].setSurligne(false);
                }
            }
            pieceSelect = null;
            tourBlanc = !tourBlanc;
            repaint();
        }
        
        //Sélection d'une pièce (ou déselection)
        else if(plateau[pt.x][pt.y].getPiece() != null){
        if((tourBlanc && plateau[pt.x][pt.y].getPiece().estBlanc()) || (!tourBlanc && !plateau[pt.x][pt.y].getPiece().estBlanc())){ 
            Coordonnees co = new Coordonnees (pt.x,pt.y);
            //Obligation de parcourir tout le plateau pour effacer les possibles précédents surlignements
            for(int i=0; i<taille;i++){
                for(int j=0;j<taille;j++){
                    plateau[i][j].setSurligne(false);
                }
            }
            if(plateau[pt.x][pt.y].getPiece() != pieceSelect){
                pieceSelect = plateau[pt.x][pt.y].getPiece();
                ArrayList<Coordonnees> casePoss =  plateau[pt.x][pt.y].getPiece().mouvement(co,plateau,this,false);
                for(int i=0; i < casePoss.size();i++){
                    plateau[casePoss.get(i).getI()][casePoss.get(i).getJ()].setSurligne(!(plateau[casePoss.get(i).getI()][casePoss.get(i).getJ()].getSurligne()));
                }
            }
            else{
                pieceSelect = null;
            }
            repaint();
        }
        }
    }
      
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
    Case[][] switchedPlat(Case c0, Case c1){
    //Renvoie le plateau où la pièce en c0 a été déplacée en c1
        Case[][] newPlateau = new Case[taille][taille];
        Piece p = c0.getPiece();
        for(int i=0; i<taille; i++){
            for(int j=0; j<taille; j++){
                if (i==c0.getCoor().getI() && j==c0.getCoor().getJ()){
                    newPlateau[i][j] = new Case(plateau[i][j].estBlanc(),null,plateau[i][j].getCoor());
                }
                else if(i==c1.getCoor().getI() && j==c1.getCoor().getJ()){
                    newPlateau[i][j] = new Case(plateau[i][j].estBlanc(),p,plateau[i][j].getCoor());
                }
                else{
                    newPlateau[i][j] = plateau[i][j];
                }
            }
        }
        return newPlateau;
    }
    
    boolean blancEnEchec(Case[][] plat){
    //Teste si les blancs sont en echec dans la configuration plat
        int k = 0;
        int l = 0;
        for (int i=0; i<taille;i++){
            for (int j=0; j<taille;j++){
                if(plat[i][j].getPiece() != null){
                    if(plat[i][j].getPiece().getType() == Type.ROI && plat[i][j].getPiece().estBlanc()){
                        k = i;
                        l = j;
                        break;
                    }
                }
            }
        }
        for(int i=0; i<taille; i++){
            for(int j=0; j<taille; j++){
               if(plat[i][j].getPiece() != null && !plat[i][j].getPiece().estBlanc()){
                   for(Coordonnees c: plat[i][j].getPiece().mouvement (new Coordonnees(i,j),plat,this,true) ){
                       if (c.getI() == k && c.getJ() == l) return true;
                   }
               }
            }
        }
        return false;
    }
    
    boolean noirEnEchec(Case[][] plat){
    //Teste si les noirs sont en echec dans la configuration plat
        int k = 0;
        int l = 0;
        for (int i=0; i<taille;i++){
            for (int j=0; j<taille;j++){
                if(plat[i][j].getPiece() != null){
                    if(plat[i][j].getPiece().getType() == Type.ROI && !plat[i][j].getPiece().estBlanc()){
                        k = i;
                        l = j;
                        break;
                    }
                }
            }
        }
        for(int i=0; i<taille; i++){
            for(int j=0; j<taille; j++){
               if(plat[i][j].getPiece() != null && plat[i][j].getPiece().estBlanc()){
                   for(Coordonnees c: plat[i][j].getPiece().mouvement (new Coordonnees(i,j),plat,this,true) ){
                       if (c.getI() == k && c.getJ() == l) return true;
                   }
               }
            }
        }
        return false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        setFocusable(false);
        int code = e.getKeyCode();
        
        //Retour au Menu
        if (code == KeyEvent.VK_ESCAPE){
            projetfinal.Menu menu = new projetfinal.Menu(frame);
            frame.setSize(710, 738);
            menu.setSize(700, 700);
            frame.remove(this);
            frame.add(menu);
            frame.setLocationRelativeTo(null);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
