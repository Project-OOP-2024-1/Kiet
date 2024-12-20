package main;

import entity.*;
import human.NPC;
import human.Player;
import monster.Monster;
import object.Heart;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {
    // Screen setting
    public final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;
    //Set Value of tile
    public final int tileSize = originalTileSize * scale; // 48x48 tile
    public int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = maxScreenCol * tileSize; // Window size
    public final int screenHeight = maxScreenRow * tileSize;

    //WORLD SETTINGS
    public final int maxWorldCol = 60;
    public final int maxWorldRow = 60;
    //Full Screen
    int screenWidth2  = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;


    int FPS = 60;// Frame per second
    //Tile Map
    public TileManager tileM = new TileManager(this);
    //KeyHandler
    KeyHandler keyH = new KeyHandler(this);
    Thread gameThread;
    //Player
    public Player player = new Player(this, keyH);
    //Object
    public Monster[] monster = new Monster[20];
    public SuperObject[] object = new SuperObject[20];
    Heart player_heart = new Heart(this);
    public ArrayList<Projectile> projectileList = new ArrayList<>();
    public ArrayList<Updatable> entityList= new ArrayList<>();
    //GameState
    public int gameState;
    public  final int titleState = 0;
    public final int playState = 1;
    public final int pauseState =2;
    public final int dialogueState=3;
    public final int characterState=4;
    //part of Game setting
    Asset_Setter Setter = new Asset_Setter(this);
    public Collision_checker colis =new Collision_checker(this);
    //Npc
    public NPC[] npc = new NPC[10];
    // UI
    public UI ui;

    {
        ui = new UI(this);
    }



    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);// Recognizes pressed keys
        this.setFocusable(true);// This is required for the panel to receive keyboard events
    }
    public void setFullScreen(){
        // Get local screen device
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);
        // Get full screen width and height
        screenWidth2= Main.window.getWidth();
        screenHeight2= Main.window.getHeight();
    }
    public void startGameThread() {
        // Instantiate a thread
        gameThread = new Thread(this);
        gameThread.start();
    }
    public void Game_setup(){
        Setter.setGeneral();
        gameState= titleState;
        //
        tempScreen = new BufferedImage(screenWidth,screenHeight,BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();
//        setFullScreen();
    }
    @Override
    public void run() {

        double drawInterval = (double) 1000000000 /FPS;// time interval between Frames
        double delta=0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount=0;
        // Game loop
        while(gameThread != null) {

            currentTime = System.nanoTime();
            timer += (currentTime-lastTime);
            delta+= (currentTime-lastTime)/ drawInterval;
            lastTime=currentTime;
            // 1. Update
            if (delta>=1){
                update();
                drawToTempComponent();
                drawToScreen();
                delta--;
                drawCount++;
            }
            if (timer >= 1000000000){
                drawCount=0;
                timer= 0;
            }
        }
    }

    public void update() {
        if (gameState==playState) {
            player.update();
            player_heart.update();
            //NPC
            for (NPC e : npc){
                if (e!=null){
                    e.update();
                }
            }
            //Monster
            int i =0;
            for (Monster e : monster){
                if (e!=null && e.life!=0){
                    e.update();
                }
                if (e!=null && !e.alive) {
                    e.update_death();
                }
                if (e!=null && e.death){
                    monster[i]=null;
                }
                i++;
            }
            //Object
            i=0;
            for (Entity e: object){
                if (e!=null && !e.alive){
                    System.out.println("Nooo");
                    object[i]=null;
                }
                i++;
            }
            //Skill
            for(i=0;i<projectileList.size();i++){
                if(projectileList.get(i)!=null){
                    if(projectileList.get(i).alive){
                        projectileList.get(i).update();
                    }
                    if(!projectileList.get(i).alive){
                        projectileList.remove(i);
                    }
                }
            }
        }
    }

    //Output for every updating
    public void drawToTempComponent() {
        if (gameState == titleState) {
            //for output screen
            ui.draw(g2);
        } else {
            // Map
            tileM.draw(g2);
            //player heart
            player_heart.draw(g2);
            //player
            entityList.add(player);
            //event

            //npc
            for (NPC e : npc) {
                if (e != null) {
                    entityList.add(e);
                }
            }
            //Object
            for (SuperObject e : object) {
                if (e != null) {
                    entityList.add(e);
                }
            }
            player.draw(g2);
            //Monster
            for (Monster e : monster) {
                if (e != null && !e.death) {
                    entityList.add(e);
                }
            }
            //Skill
            for (Projectile entity : projectileList) {
                if (entity != null) {
                    entityList.add(entity);
                }
            }
            entityList.sort(((o1, o2) -> {o1.y,o2.y}));

            //Pause state
            ui.draw(g2);
        }
    }
    public void drawToScreen(){
        Graphics g = getGraphics();
        g.drawImage(tempScreen,0,0,screenWidth2,screenHeight2,null);
        g.dispose();
    }
}
