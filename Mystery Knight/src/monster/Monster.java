package monster;

import entity.Character;
import entity.Projectile;
import main.GamePanel;
import main.SpriteSheet;
import main.Updatable;
import object.SlimeHeart;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;


public abstract class Monster extends Character implements Updatable {
    int defaultX=0;
    int defaultY=0;
    public int counterNPC= 0;
    BufferedImage image;
    Projectile projectile;
    public Monster(GamePanel gp){
        super(gp);
        this.gp=gp;
    }
    public BufferedImage getImage(String filepath){
        SpriteSheet sheet = new SpriteSheet(filepath, gp.originalTileSize, gp.originalTileSize);

        rightSprites = new BufferedImage[frameCount];
        leftSprites = new BufferedImage[frameCount];
        upSprites = new BufferedImage[frameCount];
        downSprites= new BufferedImage[frameCount];
        idleSprites= new BufferedImage[frameCount];
        deathSprites= new BufferedImage[frameCount];

        for (int i = 0; i < frameCount; i++) {
            rightSprites[i] = sheet.getSprite(i,2 ); // Extract the sprites
            leftSprites[i] = sheet.getSprite(i, 3);
            upSprites[i] = sheet.getSprite(i, 1);
            downSprites[i] = sheet.getSprite(i,0 );
            idleSprites[i] = sheet.getSprite(i, 4);
            deathSprites[i]= sheet.getSprite(i, 5);
        }
        return idleSprites[0];
    }

    public void setAction(){
        if(defaultX==0 && defaultY==0){
            defaultX=x;
            defaultY=y;
        }
        if (attack && !collisionOn){
            if (Math.abs(gp.player.x-x) < Math.abs(y-y)){
                if (Math.abs(gp.player.x-x)>48) {
                    if (gp.player.x - x > 0) {
                        direction = "right";
                    } else if (gp.player.x - x < 0) {
                        direction = "left";
                    }
                }
                else{
                    if (!projectile.alive){
                        projectile.set(x,y,direction,true,this);
                        gp.projectileList.add(projectile);
                    }
                    if (y>y){
                        direction="down";
                    }
                    else {
                        direction="up";
                    }
                }
            }
            else{
                if(Math.abs(y-y)>48) {
                    if (y > y) {
                        direction = "down";
                    } else if (y < y) {
                        direction = "up";
                    }
                }
                else {
                    if (!projectile.alive){
                        projectile.set(x,y,direction,true,this);
                        gp.projectileList.add(projectile);
                    }
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
    @Override
    public void update() {
        counterSprite++;
        counterNPC++;
        if (counterNPC > 30) {
            setAction();
            counterNPC = 0;
        }
        if (counterSprite > 15) {
            if (numSprite>3){
                numSprite=0;
            }
            numSprite++;
            counterSprite = 0;
        }
        collisionOn = false;
        gp.colis.checkTile(this);
        gp.colis.checkPlayer(this);
        gp.colis.checkObject(this,gp.object);
        gp.colis.checkDanger(this);
        //take damage from player
        if (gp.colis.Damaged(this) && gp.player.attack){
            damaged=true;
        }
        if (damaged && !invincible){
            if (life>0) life--;
            invincible=true;
        }
        if (life<=0) {
            gp.player.inventory.add(new SlimeHeart(gp));
            gp.ui.addMessage("Collect Slime Heart!");
            alive=false;
        }
        if (!gp.player.attack) damaged=false;
        if (!collisionOn) {
            switch (direction) {
                case "up":
                    y -= speed;
                    break;
                case "down":
                    y += speed;
                    break;
                case "right":
                    x += speed;
                    break;
                case "left":
                    x -= speed;
                    break;
            }
        }
        if (invincible){
            invincibleCounter++;
            if (invincibleCounter>60) {
                invincible = false;
                invincibleCounter=0;
            }
        }
    }
    @Override
    public void draw(Graphics2D g2) {
        int screenX = x - gp.player.x + gp.player.screenX;
        int screenY = y - y + gp.player.screenY;
        if (x + gp.tileSize > gp.player.x - gp.player.screenX &&
                x - gp.tileSize < gp.player.x + gp.player.screenX &&
                y + gp.tileSize > y - gp.player.screenY &&
                y - gp.tileSize < y + gp.player.screenY) {
            switch (direction) {
                case "right":
                    image = rightSprites[numSprite - 1];
                    break;
                case "left":
                    image = leftSprites[numSprite - 1];
                    break;
                case "down":
                    image = downSprites[numSprite - 1];
                    break;
                case "up":
                    image = upSprites[numSprite - 1];
                    break;
                case "idle":
                    image = idleSprites[numSprite - 1];
                    break;
            }
        }
        //Hp monster
        g2.setColor(new Color(35,35,35));
        g2.fillRect(screenX,screenY-15,gp.tileSize,10);
        g2.setColor(new Color(255,0,30));
        g2.fillRect(screenX,screenY-15, life*12,10);
        if (invincible){
            if (invincibleCounter<30){
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.4f));
            }
            else {
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
            }
        }
        if (!alive) {
            draw_death(g2);
        }
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        //reset;
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
    }
}
