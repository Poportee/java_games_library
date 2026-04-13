package pacman;

import projetfinal.GameModule;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class PacmanModule implements GameModule {
    
    private BufferedImage captPacman;
    private BufferedImage logoPacman;

    public PacmanModule() {
        try {
            captPacman = ImageIO.read(getClass().getResourceAsStream("/projetfinal/Images/captPacman.png"));
            logoPacman = ImageIO.read(getClass().getResourceAsStream("/pacman/Images/logo.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void drawPresentation(Graphics2D g2, Component observer) {
        g2.setColor(new Color(87,138,52));
        g2.fillRect(50, 175, 600, 400);
        g2.drawImage(captPacman, 70, 195, 360, 360, observer);
        g2.drawImage(logoPacman, 440, 190, 200, 100, observer);
    }

    @Override
    public void launch(JFrame frame) {
        GamePanel game = new GamePanel(3, 7*1000, 3, frame);
        frame.setSize(514, 738);
        game.setSize(500, 700);
        frame.setLocationRelativeTo(null);
        frame.add(game);
        game.lancePartie();
    }
}