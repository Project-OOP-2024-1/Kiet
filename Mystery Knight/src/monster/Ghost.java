package monster;

import entity.Projectile;
import main.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Ghost extends Monster{
    Gamepanel gp;
    Projectile projectile;
    public Ghost(GamePanel gp){
        super(gp);
        this.gp=gp;
        name = "Ghost";
        direction="idle";
        speed=1;
        maxLife=12;
        life=maxLife;
        solidregion= new Rectangle(8,16,80,80);
        Attackregion= new Rectangle(-gp.tileSize*5,-gp.tileSize*5,gp.tileSize*11,gp.tileSize*11);
        getImage();
        attack=false;
        alive=true;
        damaged=false;
        death=false;
        projectile= new OBJ_Soul(gp);
    }
    public void getImage(){
        SpriteSheet sheet = new SpriteSheet("/SLIME/pixil-frame-0.png", 30, 30);

        rightSprites = new BufferedImage[frameCount];
        leftSprites = new BufferedImage[frameCount];
        upSprites = new BufferedImage[frameCount];
        downSprites= new BufferedImage[frameCount];
        idleSprites= new BufferedImage[frameCount];
        deathSprites= new BufferedImage[frameCount];

        for (int i = 0; i < frameCount; i++) {
            rightSprites[i] = sheet.getSprite(i,1 ); // Extract the sprites
            leftSprites[i] = sheet.getSprite(i, 0);
            upSprites[i] = sheet.getSprite(i, 3);
            downSprites[i] = sheet.getSprite(i,2 );
            idleSprites[i] = sheet.getSprite(i, 4);
            deathSprites[i]= sheet.getSprite(i, 5);
        }
    }
    public void setAction(){
        if(defaultX==0 && defaultY==0){
            defaultX=x;
            defaultY=y;
        }
        if (attack && !collisionOn){
            if (Math.abs(gp.player.x-x) < Math.abs(gp.player.y-y)){
                if (Math.abs(gp.player.x-x)>48) {
                    if (gp.player.x - x > 0) {
                        direction = "right";
                    } else if (gp.player.x - x < 0) {
                        direction = "left";
                    }
                }
                else{
                    if (gp.player.y>y){
                        direction="down";
                    }
                    else {
                        direction="up";
                    }
                }
            }
            else{
                if(Math.abs(gp.player.y-y)>48) {
                    if (gp.player.y > y) {
                        direction = "down";
                    } else if (gp.player.y < y) {
                        direction = "up";
                    }
                }
                else {
                    if (gp.player.x>x){
                        direction="right";
                    }
                    else {
                        direction="left";
                    }
                }
            }
        }
        else{
            if (Math.abs(defaultX-x) < Math.abs(defaultY-y)){
                if (Math.abs(defaultX-x)>48) {
                    if (defaultX - x > 0) {
                        direction = "right";
                    } else  {
                        direction = "left";
                    }
                }
                else{
                    if (defaultY>y){
                        direction="down";
                    }
                    else {
                        direction="up";
                    }
                }
            }
            else{
                if(defaultY-y>24) {
                    if (defaultY> y) {
                        direction = "down";
                    } else {
                        direction = "up";
                    }
                }
                else {
                    if (x<defaultX-gp.tileSize*3){
                        direction="right";
                    }
                    else if(x>defaultX+gp.tileSize*3){
                        direction="left";
                    }
                    else {
                        Random ran = new Random();
                        int num= ran.nextInt(3);
                        switch (num){
                            case 0: direction="right";break;
                            case 1: direction="idle"; break;
                            case 2: direction="left"; break;
                        }
                    }
                }
            }
        }
    }
}
