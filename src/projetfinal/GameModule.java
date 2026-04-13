package projetfinal;

import java.awt.Graphics2D;
import java.awt.Component;
import javax.swing.JFrame;

public interface GameModule {
    // Dessine la zone de présentation centrale
    void drawPresentation(Graphics2D g2, Component observer);
    
    // Gère la logique de lancement
    void launch(JFrame frame);
}