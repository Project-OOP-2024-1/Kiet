package entity;

import main.GameSetting;
import processors.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SolidEntity extends Entity{
    public Rectangle solidArea;
    public BufferedImage[] rightSprites,leftSprites, upSprites, downSprites, idleSprites, deathSprites;
    public int counterSprite = 0;
    public int numSprite = 0;
    public int life;
    public int maxLife;
    public int speed;
    public int damage;
    public int frameCount;
    public String direction;
    public boolean collisionOn;
    public boolean invincible=false;
    public SolidEntity(GameSetting gs) {
        super(gs);
        this.gs=gs;
    }
    protected void getImage(String name,int width, int height){
        SpriteSheet sheet = new SpriteSheet("/characters/"+name+".png", width, height);

        rightSprites = new BufferedImage[frameCount];
        leftSprites = new BufferedImage[frameCount];
        upSprites = new BufferedImage[frameCount];
        downSprites= new BufferedImage[frameCount];
        idleSprites= new BufferedImage[frameCount];
        deathSprites=new BufferedImage[frameCount];

        for (int i = 0; i < frameCount; i++) {
            rightSprites[i] = sheet.getSprite(i,2 ); // Extract the sprites
            leftSprites[i] = sheet.getSprite(i, 3);
            upSprites[i] = sheet.getSprite(i, 1);
            downSprites[i] = sheet.getSprite(i,0 );
            idleSprites[i] = sheet.getSprite(i, 4);
            deathSprites[i]=sheet.getSprite(i,5);
        }
    }
    public void update(){
        if (!collisionOn) {
            if (direction.equals("up")) y -= speed;
            if (direction.equals("down") || direction.equals(("idle"))) y += speed;
            if (direction.equals("left")) x -= speed;
            if (direction.equals("right")) x += speed;
            life--;
        }
        else {
            life=0;
        }
        if (life <= 0) alive = false;
        counterSprite++;
        if (counterSprite > 15) {
            numSprite++;
            if (numSprite>frameCount-1){
                numSprite=0;
            }
            counterSprite=0;
        }
    }
    public void draw(Graphics2D g2){
        int screenX = x - gs.player.x + gs.player.screenX;
        int screenY = y - gs.player.y + gs.player.screenY;
        if (x + gs.tileSize > gs.player.x - gs.player.screenX &&
                x - gs.tileSize < gs.player.x + gs.player.screenX &&
                y + gs.tileSize > gs.player.y - gs.player.screenY &&
                y - gs.tileSize < gs.player.y + gs.player.screenY) {
            switch (direction) {
                case "right":
                    image = rightSprites[numSprite];
                    break;
                case "left":
                    image = leftSprites[numSprite];
                    break;
                case "down", "idle":
                    image = downSprites[numSprite];
                    break;
                case "up":
                    image = upSprites[numSprite];
                    break;
            }
            g2.drawImage(image, screenX, screenY, gs.tileSize, gs.tileSize, null);
        }
    }
}
