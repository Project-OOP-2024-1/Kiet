package monster;

import entity.Projectile;
import main.GamePanel;
import main.SpriteSheet;
import object.Key;
import projectiles.Soul;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Ghost extends Monster{
    Projectile projectile;
    public Ghost(GamePanel gp){
        super(gp);
        this.gp=gp;
        frameCount=5;
        name = "Ghost";
        direction="idle";
        speed=1;
        maxLife=12;
        life=maxLife;
        solidregion= new Rectangle(8,16,80,80);
        Attackregion= new Rectangle(-gp.tileSize*5,-gp.tileSize*5,gp.tileSize*11,gp.tileSize*11);
        image=getImage("/characters/pixil-frame-0.png");
        attack=false;
        alive=true;
        damaged=false;
        death=false;
        projectile= new Soul(gp);
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
