package objects;

import characters.NPC;
import entity.SolidEntity;
import main.GameSetting;
import processors.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject extends SolidEntity {
    //SuperObject is object that have functional work(like event)
    BufferedImage inactive, active;
    public Rectangle solidArea;
    private final int scale;
    private final int width;
    private final int height;
    public boolean eventOn=false;
    public SuperObject(GameSetting gs,String name,int x,int y,int width,int height,int scale) {
        super(gs);
        this.gs=gs;
        this.name=name;
        this.x=x*gs.tileSize;
        this.y=y*gs.tileSize;
        this.scale=scale;
        this.width=width;
        this.height=height;
        solidArea= new Rectangle(0,0,width*3*scale,height*3*scale);
        getImage(this.name,width,height);
        alive=false;
    }

    @Override
    protected void getImage(String name, int width, int height) {
        SpriteSheet sheet = new SpriteSheet("/objects/"+name+".png",width,height);
        image=sheet.getSprite(0,0);
        try {
            inactive=sheet.getSprite(1,0);
            active=sheet.getSprite(2,0);
        }catch (Exception e){//
            inactive=sheet.getSprite(0,0);
            active=sheet.getSprite(0,0);
        }

    }

    public void update(){
        if (this.name.equals("HealingPool")){
            counterSprite++;
            if(counterSprite>40){
                if (gs.player.life<gs.player.maxLife) gs.player.life++;
                counterSprite=0;
            }
            //fill this
        } else if (this.name.equals("TransitionGate")) {
            if(alive){
                gs.player.x=24*gs.tileSize;
                gs.player.y=44*gs.tileSize;
            }
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        if(alive) image=active;
        int screenX = x - gs.player.x + gs.player.screenX;
        int screenY = y - gs.player.y + gs.player.screenY;
        if (x + width*3*scale > gs.player.x - gs.player.screenX &&
                x - width*3*scale < gs.player.x + gs.player.screenX &&
                y + height*3*scale > gs.player.y - gs.player.screenY &&
                y - height*3*scale < gs.player.y + gs.player.screenY) {
            g2.drawImage(image, screenX, screenY,width*scale*3,height*scale*3 , null);
        }
    }
}
