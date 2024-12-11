package entity;

import main.updatable;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Projectile extends Animation implements updatable {
    Gamepanel gp;
    private Entity user;
    public Projectile(GamePanel gp){
        super(gp);
        this.gp = gp;
        collisionOn=false;
    }
    public void set(int x, int y, String direction,boolean alive, Entity user){
        this.x = x;
        this.y = y;
        this.direction=direction;
        this.alive = alive;
        this.user = user;
        this.life = this.maxLife;
    }
    @Override
    public void update() {
        collisionOn=false;
        gp.colis.checkTile(this);
        if (!user.name.equals("Slime")){
            gp.colis.checkEntity(this, gp.monster);
        }
        else {
            gp.colis.checkPlayer(this);
        }
        gp.colis.checkObject(this,gp.object);
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
            if (numSprite>frameCount){
                numSprite=1;
            }
            counterSprite=0;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
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
                    image = downSprites[numSprite - 1];
                    break;
            }
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }
}
