package projetfinal;

import javax.swing.JFrame;
import echecs.EchecsModule;
import pacman.PacmanModule;
import morpion.MorpionModule;

public class Launcher {

    public static void main(String[] args) {
        JFrame f = new JFrame();
        Menu menu = new Menu(f);
        
        // Ajout dynamique des jeux au menu (Principe OCP)
        menu.addGame(new PacmanModule());
        menu.addGame(new EchecsModule());
        menu.addGame(new MorpionModule());
        
        f.setSize(710, 738);
        menu.setSize(700, 700);
        f.add(menu);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }   
}