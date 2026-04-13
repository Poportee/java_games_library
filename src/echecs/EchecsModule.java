package echecs;

import projetfinal.GameModule;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class EchecsModule implements GameModule {
    
    private BufferedImage captEchecs;
    private BufferedImage logoEchecs;

    public EchecsModule() {
        try {
            captEchecs = ImageIO.read(getClass().getResourceAsStream("/projetfinal/Images/captEchecs.png"));
            logoEchecs = ImageIO.read(getClass().getResourceAsStream("/projetfinal/Images/logoEchecs.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void drawPresentation(Graphics2D g2, Component observer) {
    //Presentation échecs
        g2.setColor(new Color(87,138,52));
        g2.fillRect(50, 175, 600, 400);
        g2.drawImage(captEchecs, 70, 195, 360, 360, observer);
        g2.drawImage(logoEchecs, 440, 180, 200, 190, observer);
    }

    @Override
    public void launch(JFrame frame) {
        Echequier echequier = new Echequier(frame);
        echequier.setSize(600, 600);
        frame.add(echequier);
        frame.pack();
        frame.setLocationRelativeTo(null);
        echequier.setFocusable(true);
        echequier.requestFocusInWindow();
    }
}