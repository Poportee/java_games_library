package morpion;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import javax.swing.JFrame;


public class Plateau extends JPanel implements MouseListener, KeyListener{
    private Forme[][] cases = {{null,null,null},{null,null,null},{null,null,null}};
    private JFrame frame;
    private boolean tourCercle = false;
    private boolean victoire = false;
    private int[][] traitW = new int[2][4];  //Stocke les coordonnées des angles de la ligne de victoire
    
    public Plateau(JFrame f){
        frame = f;
    }
    
    public void lancePartie(){
        addKeyListener(this);
        setFocusable(true);
        requestFocus();
        addMouseListener(this);
    }
    
    public void paintComponent(Graphics g){
        //Dessine le damier
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 600, 600);
        g.setColor(Color.BLACK);
        g.fillRect(198, 10, 4, 580);
        g.fillRect(398, 10, 4, 580);
        g.fillRect(10, 198, 580, 4);
        g.fillRect(10, 398, 580, 4);
        
        //Dessine les formes
        for(Forme[] tab : cases){
            for(Forme f : tab){
                if(f!=null) f.dessineToi(g);
            }
        }
        
        //Dessine la ligne de victoire
        if(victoire){
            g.setColor(Color.ORANGE);
            g.fillPolygon(new int[]{traitW[0][0],traitW[1][0],traitW[2][0],traitW[3][0]},new int[] {traitW[0][1],traitW[1][1],traitW[2][1],traitW[3][1]}, 4);
        }
    }
    
    public void testVictoire(){
        for(int i=0;i<3;i++){
            //Victoire en colonne
            if(cases[i][0] != null && cases[i][1] != null && cases[i][2] != null){
                if(cases[i][0].estUnCercle() == cases[i][1].estUnCercle() && cases[i][0].estUnCercle() == cases[i][2].estUnCercle()){
                    traitW = new int[][]{new int[]{i*200+100-8,585},new int[]{i*200+100-8,15},new int[]{i*200+100+8,15},new int[]{i*200+100+8,585}};
                    victoire = true;
                }
            }
            //Victoire en ligne
            if(cases[0][i] != null && cases[1][i] != null && cases[2][i] != null){
                if(cases[0][i].estUnCercle() == cases[1][i].estUnCercle() && cases[0][i].estUnCercle() == cases[2][i].estUnCercle()){
                    traitW = new int[][]{new int[]{585,i*200+100-8},new int[]{15,i*200+100-8},new int[]{15,i*200+100+8},new int[]{585,i*200+100+8}};
                    victoire = true;
                }
            }           
        }
        
        //Victoire en diagonale
        if(cases[0][0] != null && cases[1][1] != null && cases[2][2] != null){
            if(cases[0][0].estUnCercle() == cases[1][1].estUnCercle() && cases[0][0].estUnCercle() == cases[2][2].estUnCercle()){
                traitW = new int[][]{new int[]{15,25},new int[]{25,15},new int[]{585,575},new int[]{575,585}};
                victoire = true;
            }
        }
        if(cases[0][2] != null && cases[1][1] != null && cases[2][0] != null){
            if(cases[0][2].estUnCercle() == cases[1][1].estUnCercle() && cases[0][2].estUnCercle() == cases[2][0].estUnCercle()){
                traitW = new int[][]{new int[]{15,575},new int[]{25,585},new int[]{585,25},new int[]{575,15}};
                victoire = true;
            }
        }  
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
        int i = pt.x/200;
        int j = pt.y/200;
        
        //Ajout d'une forme si la case cliquée est vide et que la partie n'est pas finie
        if(cases[i][j]==null && !victoire){
            if(tourCercle) cases[i][j] = new Forme(true,i*200+100,j*200+100);
            else cases[i][j] = new Forme(false,i*200+100,j*200+100);
            tourCercle = !tourCercle;
            testVictoire();
            repaint();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
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
