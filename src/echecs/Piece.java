package echecs;
import java.util.ArrayList;
import java.awt.*;
import javax.swing.ImageIcon;


public class Piece extends Canvas {
    private Coordonnees co;
    private Type type;
    private boolean estBlanc;
    private ImageIcon image;
    
    public Piece(Coordonnees c, Type t, boolean couleur){
        co = c;
        type = t;
        estBlanc = couleur;
        initImage();
    }
    
    public boolean estBlanc(){
        return estBlanc;
    }
    
    public Type getType(){
        return type;
    }
    
    public Coordonnees getCoordonnees(){
        return co;
    }
    
    public void setCoordonnees(Coordonnees c){
        co = c;        
    }
    
    public void setType(Type t){
        type = t;        
    }
    
    public void setEstBlanc(boolean b){
        estBlanc = b;        
    }
    
    public void initImage(){
    //Charge l'image en fonction du type et de la couleur de la pièce
        if (estBlanc){
            switch(type){
                case DAME:
                    image = new ImageIcon(ClassLoader.getSystemResource("echecs/IconesPieces/dame_blanche.png"));
                    break ;
                case PION:
                    image = new ImageIcon(ClassLoader.getSystemResource("echecs/IconesPieces/pion_blanc.png"));
                    break;
                case ROI:
                    image = new ImageIcon(ClassLoader.getSystemResource("echecs/IconesPieces/roi_blanc.png"));
                    break;                        
                case FOU:
                    image = new ImageIcon(ClassLoader.getSystemResource("echecs/IconesPieces/fou_blanc.png"));
                    break;
                case TOUR:
                    image = new ImageIcon(ClassLoader.getSystemResource("echecs/IconesPieces/tour_blanche.png"));
                    break;
                case CAVALIER:
                    image = new ImageIcon(ClassLoader.getSystemResource("echecs/IconesPieces/cavalier_blanc.png"));
                    break;
            }
        }
        else{
            switch(type){
                case DAME:
                    image = new ImageIcon(ClassLoader.getSystemResource("echecs/IconesPieces/dame_noire.png"));
                    break ;
                case PION:
                    image = new ImageIcon(ClassLoader.getSystemResource("echecs/IconesPieces/pion_noir.png"));
                    break ;
                case ROI:
                    image = new ImageIcon(ClassLoader.getSystemResource("echecs/IconesPieces/roi_noir.png"));
                    break;                        
                case FOU:
                    image = new ImageIcon(ClassLoader.getSystemResource("echecs/IconesPieces/fou_noir.png"));
                    break;
                case TOUR:
                    image = new ImageIcon(ClassLoader.getSystemResource("echecs/IconesPieces/tour_noire.png"));
                    break;
                case CAVALIER:
                    image = new ImageIcon(ClassLoader.getSystemResource("echecs/IconesPieces/cavalier_noir.png"));
                    break;
            }
        }
    }
    
    public void dessineToi(Component observer, Graphics g, int echLarg, int echHaut){
        image.paintIcon(this, g,co.getI()*echLarg,co.getJ()*echHaut);
    }
    
    public ArrayList<Coordonnees> mouvement (Coordonnees p0, Case[][] plateau, Echequier ech, boolean testeurEchec ){
    //Retourne la liste des cases accessibles par la pièce depuis c0 sur plateau 
        ArrayList<Coordonnees> casePoss = new ArrayList<>();
        int i = p0.getI();
        int j = p0.getJ();
        boolean dedans, pasCoequipier;
        switch (type){
            case CAVALIER:
                int[][] toutes = {{i+1,j+2},{i-1,j+2},{i+2,j+1},{i+2,j-1},{i-2,j+1},{i-2,j-1},{i-1,j-2},{i+1,j-2}};
                for (int[] c : toutes){
                    dedans = c[0]>=0 && c[0]<=7 && c[1]>=0 && c[1]<=7;
                    if (dedans){
                        pasCoequipier = plateau[c[0]][c[1]].getPiece() == null || plateau[c[0]][c[1]].getPiece().estBlanc != estBlanc;
                        if (pasCoequipier){
                            demandeAjoutPiece(c[0],c[1],casePoss,ech,plateau,testeurEchec);
                        }
                    }
                }
                break;
            case PION:
                if (estBlanc && j-1>=0){
                    if(plateau[i][j-1].getPiece() == null){
                        demandeAjoutPiece(i,j-1,casePoss,ech,plateau,testeurEchec);
                        if(j==6 && plateau[i][j-2].getPiece() == null ){
                            demandeAjoutPiece(i,j-2,casePoss,ech,plateau,testeurEchec);
                        }
                    }
                    if(i+1<=7 && plateau[i+1][j-1].getPiece() != null && !plateau[i+1][j-1].getPiece().estBlanc()){
                        demandeAjoutPiece(i+1,j-1,casePoss,ech,plateau,testeurEchec);
                    }
                    if(i-1>=0 && i+1<=7 && plateau[i-1][j-1].getPiece() != null && !plateau[i-1][j-1].getPiece().estBlanc()){
                        demandeAjoutPiece(i-1,j-1,casePoss,ech,plateau,testeurEchec);
                    }
                }
                if (!estBlanc && j+1<=7){
                    if(plateau[i][j+1].getPiece() == null){
                        demandeAjoutPiece(i,j+1,casePoss,ech,plateau,testeurEchec);
                        if(j==1 && plateau[i][j+2].getPiece() == null ){
                            demandeAjoutPiece(i,j+2,casePoss,ech,plateau,testeurEchec);
                        }
                    }
                    if(i+1<=7 && plateau[i+1][j+1].getPiece() != null && plateau[i+1][j+1].getPiece().estBlanc()){
                        demandeAjoutPiece(i+1,j+1,casePoss,ech,plateau,testeurEchec);
                    }
                    if(i-1>=0 && i+1<=7 && plateau[i-1][j+1].getPiece() != null && plateau[i-1][j+1].getPiece().estBlanc()){
                        demandeAjoutPiece(i-1,j+1,casePoss,ech,plateau,testeurEchec);
                    }
                }
                break;
            case TOUR, DAME:
                for (int k=i+1; k<=7; k++){
                    if (plateau[k][j].getPiece() == null){
                        demandeAjoutPiece(k,j,casePoss,ech,plateau,testeurEchec);
                    }
                    else {
                        if(plateau[k][j].getPiece().estBlanc != estBlanc){
                            demandeAjoutPiece(k,j,casePoss,ech,plateau,testeurEchec);
                        }
                        break;
                    }
                }
                for (int k=i-1; k>=0; k--){
                    if (plateau[k][j].getPiece() == null){
                        demandeAjoutPiece(k,j,casePoss,ech,plateau,testeurEchec);
                    }
                    else {
                        if(plateau[k][j].getPiece().estBlanc != estBlanc){
                            demandeAjoutPiece(k,j,casePoss,ech,plateau,testeurEchec);
                        }
                        break;
                    }
                }
                for (int k=j-1; k>=0; k--){
                    if (plateau[i][k].getPiece() == null){
                        demandeAjoutPiece(i,k,casePoss,ech,plateau,testeurEchec);
                    }
                    else {
                        if(plateau[i][k].getPiece().estBlanc != estBlanc){
                            demandeAjoutPiece(i,k,casePoss,ech,plateau,testeurEchec);
                        }
                        break;
                    }
                }
                for (int k=j+1; k<=7; k++){
                    if (plateau[i][k].getPiece() == null){
                        demandeAjoutPiece(i,k,casePoss,ech,plateau,testeurEchec);
                    }
                    else {
                        if(plateau[i][k].getPiece().estBlanc != estBlanc){
                            demandeAjoutPiece(i,k,casePoss,ech,plateau,testeurEchec);
                        }
                        break;
                    }
                }
                break;
            case ROI:
                int[][] toutes2 = {{i+1,j},{i+1,j-1},{i,j-1},{i-1,j-1},{i-1,j},{i-1,j+1},{i,j+1},{i+1,j+1}};
                for (int[] c : toutes2){
                    dedans = c[0]>=0 && c[0]<=7 && c[1]>=0 && c[1]<=7;
                    if (dedans){
                        pasCoequipier = plateau[c[0]][c[1]].getPiece() == null || plateau[c[0]][c[1]].getPiece().estBlanc != estBlanc;
                        if (pasCoequipier){
                            demandeAjoutPiece(c[0],c[1],casePoss,ech,plateau,testeurEchec);
                        }
                    }
                }
                break;
        }
        if(type == Type.FOU || type == Type.DAME){
                for (int k=0; k<min(7-i,7-j); k++){
                    if (plateau[i+k+1][j+k+1].getPiece() == null){
                        demandeAjoutPiece(i+k+1,j+k+1,casePoss,ech,plateau,testeurEchec);
                    }
                    else {
                        if(plateau[i+k+1][j+k+1].getPiece().estBlanc != estBlanc){
                            demandeAjoutPiece(i+k+1,j+k+1,casePoss,ech,plateau,testeurEchec);
                        }
                        break;
                    }
                }
                for (int k=0; k<min(7-i,j); k++){
                    if (plateau[i+k+1][j-k-1].getPiece() == null){
                        demandeAjoutPiece(i+k+1,j-k-1,casePoss,ech,plateau,testeurEchec);
                    }
                    else {
                        if(plateau[i+k+1][j-k-1].getPiece().estBlanc != estBlanc){
                            demandeAjoutPiece(i+k+1,j-k-1,casePoss,ech,plateau,testeurEchec);
                        }
                        break;
                    }
                }
                for (int k=0; k<min(i,7-j); k++){
                    if (plateau[i-k-1][j+k+1].getPiece() == null){
                        demandeAjoutPiece(i-k-1,j+k+1,casePoss,ech,plateau,testeurEchec);
                    }
                    else {
                        if(plateau[i-k-1][j+k+1].getPiece().estBlanc != estBlanc){
                            demandeAjoutPiece(i-k-1,j+k+1,casePoss,ech,plateau,testeurEchec);
                        }
                        break;
                    }
                }
                for (int k=0; k<min(i,j); k++){
                    if (plateau[i-k-1][j-k-1].getPiece() == null){
                        demandeAjoutPiece(i-k-1,j-k-1,casePoss,ech,plateau,testeurEchec);
                    }
                    else {
                        if(plateau[i-k-1][j-k-1].getPiece().estBlanc != estBlanc){
                            demandeAjoutPiece(i-k-1,j-k-1,casePoss,ech,plateau,testeurEchec);
                        }
                        break;
                    }
                }
        }
        return casePoss;
    }
    
    public int min(int a, int b){
        if (a<b){
            return a;
        }
        else{
            return b;
        }
   }
    
    public void demandeAjoutPiece(int i, int j, ArrayList<Coordonnees> casePoss, Echequier ech, Case[][] plateau, boolean testeurEchec){
    //Ajoute {i,j} dans casePoss si la pièce peut être déplacée en i,j d'après les contraintes d'échecs
    //cad pas si elle entraine la mise en echec de son roi 
    //Ajoute directement la case si testeurEchec
        if (testeurEchec || (estBlanc && !ech.blancEnEchec(ech.switchedPlat(plateau[co.getI()][co.getJ()],plateau[i][j]))) || (!estBlanc && !ech.noirEnEchec(ech.switchedPlat(plateau[co.getI()][co.getJ()],plateau[i][j])))) casePoss.add(new Coordonnees(i,j));
    }
}
