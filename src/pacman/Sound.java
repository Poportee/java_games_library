package pacman;

import java.net.URL;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;



public class Sound {
    private Clip clip;
    private URL soundURL[] = new URL[6];
    
    public Sound(){
        soundURL[0] = getClass().getResource("/pacman/Sounds/beginning.wav");
        soundURL[1] = getClass().getResource("/pacman/Sounds/chomp.wav");
        soundURL[2] = getClass().getResource("/pacman/Sounds/eatfruit.wav");
        soundURL[3] = getClass().getResource("/pacman/Sounds/eatghost.wav");
        soundURL[4] = getClass().getResource("/pacman/Sounds/death.wav");
        soundURL[5] = getClass().getResource("/pacman/Sounds/tesNul.mp3");       
    }
    
    public void setFile(int i){
        try{
            AudioInputStream mix =AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(mix);
        }
        catch(Exception e){
        }
    }
    public void start(){
        clip.start();
    }
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){
        clip.stop();
    }
}
