package monster;

import entity.Projectile;
import main.GamePanel;
import main.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Ghost extends Monster{
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
            invincible=true;
            life--;
        }
        if (life<=0) {
            gp.player.inventory.add(new OBJ_Key(gp));
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
        if (attack && !projectile.alive){
            projectile.set(x,y,direction,true,this);
            projectile.speed=1;
            gp.projectileList.add(projectile);
        }

    }
    public void draw(Graphics2D g2) {
        image = null;
        int screenX = x - gp.player.x + gp.player.screenX;
        int screenY = y - gp.player.y + gp.player.screenY;
        if (x + gp.tileSize > gp.player.x - gp.player.screenX &&
                x - gp.tileSize < gp.player.x + gp.player.screenX &&
                y + gp.tileSize > gp.player.y - gp.player.screenY &&
                y - gp.tileSize < gp.player.y + gp.player.screenY) {
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
            //Hp monster
            g2.setColor(new Color(35,35,35));
            g2.fillRect(screenX,screenY-15,120,10);
            g2.setColor(new Color(255,0,30));
            g2.fillRect(screenX,screenY-15, life*10,10);
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
            g2.drawImage(image, screenX, screenY, 30*4, 30*4, null);
            //reset;
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
        }
    }

}
