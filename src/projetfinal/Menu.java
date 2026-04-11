package projetfinal;

//Controller
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

//Import rendu graphique
import java.awt.*;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import javax.swing.JFrame;

//Import packages jeux
import echecs.Echequier;
import pacman.GamePanel;
import morpion.Plateau;

public class Menu extends JPanel implements MouseListener {
    //Jeu proposé : 
    //1 : Pacman
    //2 : Echecs
    private int jeu = 1;
    private int nbJeu = 3;
    BufferedImage pancarteTitre, pancarteGauche, pancarteDroite, fondHerbe, pancarteJouer;
    BufferedImage captPacman, captEchecs, captMorpion;
    BufferedImage logoPacman, logoEchecs, logoMorpion;
    JFrame frame;
    
    
    public Menu(JFrame f){
        frame = f;
        addMouseListener(this);
        initImage();
    }
    
    public void initImage(){
        try {
            fondHerbe = ImageIO.read(getClass().getResourceAsStream("/projetfinal/Images/fondMenu.png"));
            pancarteGauche = ImageIO.read(getClass().getResourceAsStream("/projetfinal/Images/pancarteGauche.png"));
            pancarteDroite = ImageIO.read(getClass().getResourceAsStream("/projetfinal/Images/pancarteDroite.png"));
            pancarteTitre = ImageIO.read(getClass().getResourceAsStream("/projetfinal/Images/pancarteTitre.png"));
            pancarteJouer = ImageIO.read(getClass().getResourceAsStream("/projetfinal/Images/pancarteJouer.png"));
            
            captPacman = ImageIO.read(getClass().getResourceAsStream("/projetfinal/Images/captPacman.png"));
            captEchecs = ImageIO.read(getClass().getResourceAsStream("/projetfinal/Images/captEchecs.png"));
            captMorpion = ImageIO.read(getClass().getResourceAsStream("/projetfinal/Images/captMorpion.png"));
            
            logoPacman = ImageIO.read(getClass().getResourceAsStream("/pacman/Images/logo.png"));
            logoEchecs = ImageIO.read(getClass().getResourceAsStream("/projetfinal/Images/logoEchecs.png"));
            logoMorpion = ImageIO.read(getClass().getResourceAsStream("/projetfinal/Images/logoMorpion.png"));
        } 
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void paintComponent(Graphics g){
    //Dessine le menu en fonction du jeu sélectionné
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        
        g.drawImage(fondHerbe, 0, 0, 700, 700, this);
        g.drawImage(pancarteTitre, 150, 0, 400, 150, this);
        g.drawImage(pancarteDroite, 520, 590, 3*237/4, 3*154/4, this);
        g.drawImage(pancarteGauche, 0, 595, 3*237/4, 3*154/4, this);
        
        g.setColor(Color.WHITE);
        g2.setFont(new Font("Arial",Font.ROMAN_BASELINE,90));
        g2.drawString("Menu", 230,110);
        
        switch(jeu){
            case(1): dessinePresPacman(g2); break;
            case(2): dessinePresEchecs(g2);break;
            case(3): dessinePresMorpion(g2);
        }
        
        dessineBoutonJouer(g2);
    }
    
    public void dessinePresPacman(Graphics2D g2){
    //Presentation pacman
        g2.setColor(new Color(87,138,52));
        g2.fillRect(50, 175, 600, 400);
        g2.drawImage(captPacman, 70, 195, 360, 360, this);
        g2.drawImage(logoPacman, 440, 190, 200, 100, this);
    }
    
    public void dessinePresEchecs(Graphics2D g2){
    //Presentation Echecs
        g2.setColor(new Color(87,138,52));
        g2.fillRect(50, 175, 600, 400);
        g2.drawImage(captEchecs, 70, 195, 360, 360, this);
        g2.drawImage(logoEchecs, 440, 180, 200, 190, this);
    }
    
    public void dessinePresMorpion(Graphics2D g2){
    //Presentation morpion
        g2.setColor(new Color(87,138,52));
        g2.fillRect(50, 175, 600, 400);
        g2.setColor(new Color(242, 242, 242));
        g2.fillRect( 70, 195, 360, 360);
        g2.drawImage(captMorpion, 70, 195, 360, 360, this);
        g2.drawImage(logoMorpion, 410, 180, 260, 120, this);
    }
    
    public void dessineBoutonJouer(Graphics2D g2){
    //Presentation bouton jouer
        g2.setColor(new Color(0, 120, 0));
        g2.drawImage(pancarteJouer, 455, 380, 170,170,this);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font(" Franklin Gothic ",Font.ROMAN_BASELINE,30));
        g2.drawString("JOUER", 488,516);
    }
    
    public void lanceJeu(){
    //Lance le jeu affiché sur le menu
        //frame.setVisible(false);
        setFocusable(false);
        frame.remove(this);
        switch(jeu){
            case(1):
                GamePanel game = new GamePanel(3,7*1000,3,frame);
                frame.setSize(514, 738);
                game.setSize(500, 700);
                frame.setLocationRelativeTo(null);
                frame.add(game);
                game.lancePartie();
                break;
            case(2):
                Echequier echequier = new Echequier(frame);
                echequier.setSize(600, 600);
                frame.add(echequier);
                frame.pack();
                frame.setLocationRelativeTo(null);
                break;
            case(3):
                Plateau plat = new Plateau(frame);
                frame.setSize(614, 638);
                plat.setSize(600, 600);
                frame.add(plat);
                frame.setLocationRelativeTo(null);
                plat.lancePartie();
                break;
        }
        //frame.setVisible(true);
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
        //Si clic pancarte gauche
        if(pt.x>0 && pt.x<140 && pt.y>610 && pt.y<685 ){
            jeu--;
            if(jeu == 0){
                jeu = nbJeu;
            }
            repaint();
        }
        //Si clic pancarte droite
        if(pt.x>540 && pt.x<690 && pt.y>610 && pt.y<680 ){
            jeu++;
            if(jeu > nbJeu){
                jeu = 1;
            }
            repaint();
        }
        //Si clic JOUER
        if(pt.x>465 && pt.x<615 && pt.y>480 && pt.y<535 ){
            lanceJeu();
        }
    }
    @Override
    public void mouseEntered(MouseEvent e) {
    }
    @Override
    public void mouseExited(MouseEvent e) {
    }
}
