package pacman;

import java.awt.*;
import javax.swing.JPanel;
import pacman.ghost.ghostPredef;
import pacman.ghost.ghostRandom;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import javax.swing.JFrame;

public class GamePanel extends JPanel implements Runnable {
    //Constantes
    private static final int WIDTH = 10;
    private static final int HEIGHT = 14;
    private int echLarg = 500/WIDTH;
    private int echHaut = 700/HEIGHT;
    
    //Plateau
    PlateauNiveau1 plateau; 
    Sound sound = new Sound();
    BufferedImage logo;
    
    //Player
    Player player;
    int vieMax;
    int vie;
    int speedP;
    
    //Fantomes
    private int spawnTimeGhost;         //En millisecondes
    public int numMange = 1;
    private ghostPredef fantBleu;
    private ghostPredef fantRose;
    private ghostPredef fantRouge;
    private ghostPredef fantOrange;
    
    //Gestion exécution jeu
    JFrame frame;
    Thread gameThread = new Thread(this);;
    KeyHandler keyH = new KeyHandler();
    private int FPS = 60;
    private int score = 0;
    private int highScore = 0;
    
    public GamePanel(int playerSpeed, int spawnTimeGhost, int nbVies, JFrame frame){
        speedP = playerSpeed;
        vieMax = nbVies;
        vie = nbVies;
        this.frame = frame;
        
        this.spawnTimeGhost = spawnTimeGhost;
        addKeyListener(keyH);
        
        try{
            logo = ImageIO.read(getClass().getResourceAsStream("/pacman/Images/logo.png"));
        }
        catch(IOException e){
            e.printStackTrace();
        }        
    }
    
    public void nouvPartie(){
    //Lance une nouvelle partie : réinitialise la position du joueur, des fantômes,
    //le nombre de vies, le score...
        player = new Player(echLarg,echHaut,WIDTH,HEIGHT,speedP);
        plateau = new PlateauNiveau1(HEIGHT, WIDTH);
        initFantome();
        if(score>highScore){
            highScore = score;
        }
        score = 0;
        vie = vieMax;
        paintComponent(getGraphics());
        playMusic(0);
        long time = System.currentTimeMillis();
        while(System.currentTimeMillis()<time+4700){}
        playLoopMusic(1);
    }
    
    public void lancePartie(){
        nouvPartie();
        setFocusable(true);
        requestFocus();
        gameThread.start();
    }
    
    public void initFantome(){
        fantRouge = new ghostPredef(2,0,echHaut,echLarg,4*echLarg+echLarg/2, 6*echHaut+echHaut/2, false);
        fantBleu = new ghostPredef(1,spawnTimeGhost,echHaut,echLarg,4*echLarg+17, 7*echHaut+echHaut/2, true);
        fantRose = new ghostPredef(4,2*spawnTimeGhost,echHaut,echLarg,4*echLarg+50, 7*echHaut+echHaut/2,true);
        fantOrange = new ghostPredef(3,3*spawnTimeGhost,echHaut,echLarg,4*echLarg+83, 7*echHaut+echHaut/2,true);
    }

    @Override
    public void run() {
    //Boucle de test tout au long du jeu 
    //Lancé par le thread
        double upIntervalle = 1000000000/FPS;
        while(gameThread != null){
            update();
            repaint();
            testColGhost();
            try {
                Thread.sleep((long)upIntervalle/1000000);
            } 
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void update(){
    //Traite les positions et les colisions au cours du jeu 
        player.avance(plateau.getFormeNiv());
        player.tourne(plateau.getFormeNiv(),keyH.left,keyH.right,keyH.up,keyH.down);
        
        //Collision du joueur et d'un point
        if (testMangePoint()){
            score += 10;
        }
        if (testMangeGrosPoint()){
            score += 25;
            fantRouge.setEnDanger(true);
            fantRouge.countDanger = 0;
            fantOrange.setEnDanger(true);
            fantOrange.countDanger = 0;
            fantBleu.setEnDanger(true);
            fantBleu.countDanger = 0;
            fantRose.setEnDanger(true);
            fantRose.countDanger = 0;
        }
        
        //Retour au menu
        if(keyH.escape){
            stopMusic();
            projetfinal.Menu menu = new projetfinal.Menu(frame);
    
            
            menu.addGame(new pacman.PacmanModule());
            menu.addGame(new echecs.EchecsModule());
            menu.addGame(new morpion.MorpionModule());
            
            frame.setSize(710, 738);
            menu.setSize(700, 700);
            frame.remove(this);
            frame.add(menu);
            frame.setLocationRelativeTo(null);
        }
    }
    
    @Override
    public void paintComponent(Graphics g){
    //Actualise les graphismes au cours du jeu 
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        
        //PLATEAU
        plateau.dessinePlateau(g);
        g.setColor(Color.WHITE);
        g.drawRect(echLarg*4+echLarg/2+5, 7*echHaut-3, 40, 2);
        g.fillRect(echLarg*4+echLarg/2+5, 7*echHaut-3, 40, 2);
        
        //LOGO
        g2.drawImage(logo,WIDTH*echLarg/4-5,-25,WIDTH*echLarg/2+20,2*echHaut+echHaut/2+10,null);
        
        //NIVEAU + SCORE
        Font fonte = new Font(" Franklin Gothic ",Font.BOLD,25);
        g2.setFont(fonte);
        g2.drawString("Score : " + score, 10,echHaut+2*echHaut/3+10);
        g2.drawString("Niveau 1", WIDTH*echLarg-110,echHaut+2*echHaut/3+10);
        
        //JOUEUR
        player.dessinePlayer(g2);
        
        //FANTOMES
        fantBleu.bouge(g2);
        fantRose.bouge(g2);
        fantRouge.bouge(g2);
        fantOrange.bouge(g2);        
        
        for(int i=0; i<vie; i++){
            dessineVie(g2,i,40);
        }
    }
    
    public void dessineVie(Graphics2D g2, int i, int d){
        int x = 10+i*(d+5);           //Coin supérieur gauche de la vie
        int y = 13*echHaut+5;           //Coin supérieur gauche de la vie
        g2.setColor(Color.YELLOW);
        g2.drawOval(x,y,d,d);
        g2.fillOval(x,y,d,d);
        g2.setColor(Color.BLACK);
        g2.drawPolygon(new int[] {x+d/2, x, x}, new int[] {y+d/2, y+d*17/20 , y+d*3/20}, 3);
        g2.fillPolygon(new int[] {x+d/2, x, x}, new int[] {y+d/2, y+d*17/20 , y+d*3/20}, 3);
    }
    
    public boolean testMangePoint(){
        if (player.estCentreCase() && plateau.getNombre(player.yP/echHaut,player.xP/echLarg)!=0){
            plateau.setPt0(player.yP/echHaut,player.xP/echLarg);
            return true;
        }
        return false;       
    }
    
    public boolean testMangeGrosPoint(){
        for(int i=0;i<4;i++){
            GrosPoint pt = plateau.getGrosPoint(i); 
            if (pt!=null){
                if(abs(pt.x,player.xP)<player.dP/4 && abs(pt.y,player.yP)<player.dP/4){
                    plateau.retireGrosPoint(i);
                    return true;
                }
            }
        }
        return false;     
    }
    
    public int abs(int a, int b){
        if(a>b) return a-b;
        return b-a;
    }
    
    public void testColGhost(){
        boolean toucheRouge = abs(player.xP,fantRouge.getX())<player.dP/2 && abs(player.yP,fantRouge.getY())<player.dP/2;
        boolean toucheOrange = abs(player.xP,fantOrange.getX())<player.dP/2 && abs(player.yP,fantOrange.getY())<player.dP/2;
        boolean toucheRose = abs(player.xP,fantRose.getX())<player.dP/2 && abs(player.yP,fantRose.getY())<player.dP/2;
        boolean toucheBleu = abs(player.xP,fantBleu.getX())<player.dP/2 && abs(player.yP,fantBleu.getY())<player.dP/2;
        
        if(toucheRouge || toucheOrange || toucheBleu || toucheRose){
            //Si on peut manger les fantomes, on les fait respawn et on gagne des points
            if(toucheRouge && fantRouge.getEnDanger()){
                fantRouge = new  ghostPredef(2,3000,echHaut,echLarg,4*echLarg+17, 7*echHaut+echHaut/2, true);
                score += 100*numMange;
                numMange ++;
            }
            else if(toucheOrange && fantOrange.getEnDanger()){
                fantOrange = new  ghostPredef(3,3000,echHaut,echLarg,4*echLarg+50, 7*echHaut+echHaut/2, true);
                score += 100*numMange;
                numMange ++;
            }
            else if(toucheBleu && fantBleu.getEnDanger()){
                fantBleu = new  ghostPredef(1,3000,echHaut,echLarg,4*echLarg+83, 7*echHaut+echHaut/2, true);
                score += 100*numMange;
                numMange ++;
            }
            else if(toucheRose && fantRose.getEnDanger()){
                fantRose = new  ghostPredef(4,3000,echHaut,echLarg,4*echLarg+17, 7*echHaut+echHaut/2, true);
                score += 100*numMange;
                numMange ++;
            }
            
            //Si on ne peut pas manger les fantômes, on perd une vie et on réinitialise
            //les positions (+ lancement d'une musique)
            else{
                if (numMange == 5 || (!fantRose.getEnDanger() && !fantRouge.getEnDanger() && !fantOrange.getEnDanger() && !fantBleu.getEnDanger())){
                    numMange = 0;
                }
                vie--;
                stopMusic();
                playMusic(4);
                long time = System.currentTimeMillis();
                while(System.currentTimeMillis()<time+2000){}
                if(vie == 0){
                    playMusic(5);
                    try{Thread.sleep((long)5000);}catch(Exception e){e.printStackTrace();}
                    nouvPartie();
                }
                initFantome();
                player = new Player(echLarg,echHaut,WIDTH,HEIGHT,speedP);
                playLoopMusic(1);
            }
        }
    }
    
    public void playMusic(int i){
        sound.setFile(i);
        sound.start();
    }
    
    public void playLoopMusic(int i){
        sound.setFile(i);
        sound.start();
        sound.loop();
    }
    
    public void stopMusic(){
        sound.stop();
    }
}
