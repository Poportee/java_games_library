package projetfinal;

import javax.swing.JFrame;

public class Launcher {

    public static void main(String[] args) {
        JFrame f = new JFrame();
        Menu menu = new Menu(f);
        f.setSize(710, 738);
        menu.setSize(700, 700);
        f.add(menu);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }   
}