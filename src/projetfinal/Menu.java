package projetfinal;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Menu extends JPanel implements MouseListener {
    
    private List<GameModule> listeJeux;
    private int indexJeuActuel = 0;
    
    BufferedImage pancarteTitre, pancarteGauche, pancarteDroite, fondHerbe, pancarteJouer;
    JFrame frame;
    
    public Menu(JFrame f) {
        this.frame = f;
        this.listeJeux = new ArrayList<>();
        addMouseListener(this);
        initMenuImages();
    }
    
    // Permet d'ajouter des jeux dynamiquement depuis le Launcher
    public void addGame(GameModule game) {
        this.listeJeux.add(game);
    }
    
    // Le menu ne charge pas les images des jeux, uniquement ses propres éléments d'UI
    private void initMenuImages() {
        try {
            fondHerbe = ImageIO.read(getClass().getResourceAsStream("/projetfinal/Images/fondMenu.png"));
            pancarteGauche = ImageIO.read(getClass().getResourceAsStream("/projetfinal/Images/pancarteGauche.png"));
            pancarteDroite = ImageIO.read(getClass().getResourceAsStream("/projetfinal/Images/pancarteDroite.png"));
            pancarteTitre = ImageIO.read(getClass().getResourceAsStream("/projetfinal/Images/pancarteTitre.png"));
            pancarteJouer = ImageIO.read(getClass().getResourceAsStream("/projetfinal/Images/pancarteJouer.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        // Dessin de l'interface de base du menu
        g.drawImage(fondHerbe, 0, 0, 700, 700, this);
        g.drawImage(pancarteTitre, 150, 0, 400, 150, this);
        g.drawImage(pancarteDroite, 520, 590, 3*237/4, 3*154/4, this);
        g.drawImage(pancarteGauche, 0, 595, 3*237/4, 3*154/4, this);
        
        g.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.ROMAN_BASELINE, 90));
        g2.drawString("Menu", 230, 110);
        
        // On demande au jeu actuel de se dessiner lui-même
        if (!listeJeux.isEmpty()) {
            listeJeux.get(indexJeuActuel).drawPresentation(g2, this);
        }
        
        dessineBoutonJouer(g2);
    }
    
    private void dessineBoutonJouer(Graphics2D g2) {
    //Presentation bouton jouer
        g2.setColor(new Color(0, 120, 0));
        g2.drawImage(pancarteJouer, 455, 380, 170, 170, this);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font(" Franklin Gothic ", Font.ROMAN_BASELINE, 30));
        g2.drawString("JOUER", 488, 516);
    }
    
    private void lanceJeuActuel() {
    //Lance le jeu affiché sur le menu
        if (listeJeux.isEmpty()) return;
        
        setFocusable(false);
        frame.remove(this);
        
        listeJeux.get(indexJeuActuel).launch(frame);
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        if (listeJeux.isEmpty()) return;
        
        Point pt = e.getPoint();
        
        // Clic pancarte gauche (Précédent)
        if (pt.x > 0 && pt.x < 140 && pt.y > 610 && pt.y < 685) {
            indexJeuActuel--;
            if (indexJeuActuel < 0) {
                indexJeuActuel = listeJeux.size() - 1;
            }
            repaint();
        }
        // Clic pancarte droite (Suivant)
        if (pt.x > 540 && pt.x < 690 && pt.y > 610 && pt.y < 680) {
            indexJeuActuel++;
            if (indexJeuActuel >= listeJeux.size()) {
                indexJeuActuel = 0;
            }
            repaint();
        }
        // Clic JOUER
        if (pt.x > 465 && pt.x < 615 && pt.y > 480 && pt.y < 535) {
            lanceJeuActuel();
        }
    }

    // Méthodes de l'interface non utilisées
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}