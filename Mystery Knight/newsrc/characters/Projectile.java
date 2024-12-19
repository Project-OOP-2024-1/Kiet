package characters;

import entity.SolidEntity;
import main.GameSetting;
import processors.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

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
        if (name.equals("Slime")) frameCount=1;
        solidArea=new Rectangle(width*scale/4,height*scale/4,width*scale/2,height*scale/2);
        this.alive=false;
        damage=5;
        getImage(name+"Ball",width,height);
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
    public void update() {
        damage= new Random().nextInt(1,5);
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
            g2.drawImage(image,screenX,screenY,width*scale,height*scale,null);
        }
    }
}
