package main;

import entity.SolidEntity;
import processor.KeyHandler;


import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {
    //Take value from GameSetting
    GameSetting gs ;
    Thread gameThread;
    BufferedImage tempScreen;
    Graphics2D g2;
    KeyHandler keyH;
    public UI ui;
    //GameState
    public int gameState;
    public  final int titleState = 0;
    public final int playState = 1;
    public final int pauseState =2;
    public final int dialogueState=3;
    public final int characterState=4;
    //
    public GamePanel() {
        gs=new GameSetting();
        ui=new UI(this,gs);
        keyH=new KeyHandler(gs,this);
        this.setPreferredSize(new Dimension(gs.screenWidth, gs.screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);// Recognizes pressed keys
        this.setFocusable(true);// This is required for the panel to receive keyboard events
    }
    public void startGameThread() {
        // Instantiate a thread
        gameThread = new Thread(this);
        gameThread.start();
    }
    public void gameSetup(){
        tempScreen = new BufferedImage(gs.screenWidth,gs.screenHeight,BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();
        gs.Setting();
        gameState=titleState;
//        setFullScreen();
    }
    /**
     * Runs this operation.
     */
    @Override
    public void run() {
        double drawInterval = (double) 1000000000 /gs.FPS;// time interval between Frames
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

    private void update() {
        if (gameState==playState) {
            keyH.controlPlayer();
            gs.player.update();
            //monster
            for (int i = 0; i < gs.npc.size(); i++) {
                if (gs.npc.get(i).alive) {
                    gs.npc.get(i).update();
                } else {
                    gs.npc.remove(i);
                }
            }
            for (int i = 0; i < gs.projectile.size(); i++) {
                if (gs.projectile.get(i).alive) {
                    gs.projectile.get(i).update();
                } else {
                    gs.projectile.remove(i);
                }
            }
        }
    }

    private void drawToScreen() {
        if(gameState==titleState){
            ui.draw(g2);
        }
        else{
            gs.tileM.draw(g2);
            ArrayList<SolidEntity> draw =gs.orderDraw();
            for (SolidEntity entity:draw){
                entity.draw(g2);
            }
            ui.draw(g2);
        }
    }

    private void drawToTempComponent() {
        Graphics g = getGraphics();
        g.drawImage(tempScreen,0,0,gs.screenWidth,gs.screenHeight,null);
        g.dispose();
    }
}
