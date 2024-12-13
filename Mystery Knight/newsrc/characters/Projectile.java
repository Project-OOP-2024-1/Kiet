package characters;

import entity.SolidEntity;
import main.GameSetting;
import processor.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Projectile extends SolidEntity {
    public boolean isPlayer;
    private int width;
    private int height;
    private int scale;
    public Projectile(GameSetting gs,String name,int width, int height,int scale,int maxLife) {
        super(gs);
        this.gs=gs;
        this.maxLife=maxLife;
        this.name=name;
        this.width=width;
        this.height=height;
        this.scale=scale;
        frameCount=2;
        if (name.equals("Ghost")) frameCount=3;
        if (name.equals("SlimeBall"))frameCount=1;
        solidArea=new Rectangle(width*scale/4,height*scale/4,width*scale/2,height*scale/2);
        this.alive=false;
        getImage(name,width,height);
    }
    public void set(int x, int y, String direction,boolean alive, boolean isPlayer,int speed){
        this.isPlayer=isPlayer;
        this.x=x;
        this.y=y;
        this.direction=direction;
        this.alive=alive;
        this.speed=speed;
        this.life=maxLife;
    }

    @Override
    protected void getImage(String name, int width, int height) {
        SpriteSheet sheet = new SpriteSheet("/objects/"+name+".png", width, height);

        rightSprites = new BufferedImage[frameCount];
        leftSprites = new BufferedImage[frameCount];
        upSprites = new BufferedImage[frameCount];
        downSprites= new BufferedImage[frameCount];
        idleSprites= new BufferedImage[frameCount];

        for (int i = 0; i < frameCount; i++) {
            rightSprites[i] = sheet.getSprite(i, 0); // Extract the sprites
            leftSprites[i] = sheet.getSprite(i, 1);
            upSprites[i] = sheet.getSprite(i, 2);
            downSprites[i] = sheet.getSprite(i, 3);
            idleSprites[i] = sheet.getSprite(i, 3);
        }
    }


    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
        int screenX = x - gs.player.x + gs.player.screenX;
        int screenY = y - gs.player.y + gs.player.screenY;
        g2.drawImage(image,screenX,screenY,width*scale,height*scale,null);
    }
}
