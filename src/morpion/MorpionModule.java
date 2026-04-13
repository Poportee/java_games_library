package morpion;

import projetfinal.GameModule;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class MorpionModule implements GameModule {
    
    private BufferedImage captMorpion;
    private BufferedImage logoMorpion;

    public MorpionModule() {
        try {
            captMorpion = ImageIO.read(getClass().getResourceAsStream("/projetfinal/Images/captMorpion.png"));
            logoMorpion = ImageIO.read(getClass().getResourceAsStream("/projetfinal/Images/logoMorpion.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void drawPresentation(Graphics2D g2, Component observer) {
    //Presentation morpion
        g2.setColor(new Color(87,138,52));
        g2.fillRect(50, 175, 600, 400);
        g2.setColor(new Color(242, 242, 242));
        g2.fillRect( 70, 195, 360, 360);
        g2.drawImage(captMorpion, 70, 195, 360, 360, observer);
        g2.drawImage(logoMorpion, 410, 180, 260, 120, observer);
    }

    @Override
    public void launch(JFrame frame) {
        Plateau plat = new Plateau(frame);
        frame.setSize(614, 638);
        plat.setSize(600, 600);
        frame.add(plat);
        frame.setLocationRelativeTo(null);
        
        plat.lancePartie(); 
    }
}