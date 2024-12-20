package projectiles;

import entity.Projectile;
import main.GamePanel;
import main.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Fireball extends Projectile {
    public Fireball(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = "Fireball";
        speed = 10;
        maxLife = 80;
        life = maxLife;
        damage = 2;
        alive = false;
        solidregion = new Rectangle(0,0,24,24);
        getImage();
    }

    public void getImage() {
        SpriteSheet sheet = new SpriteSheet("/objects/fire_ball.png", gp.originalTileSize, gp.originalTileSize);

        rightSprites = new BufferedImage[2];
        leftSprites = new BufferedImage[2];
        upSprites = new BufferedImage[2];
        downSprites = new BufferedImage[2];

        for (int i = 0; i < 2; i++) {
            rightSprites[i] = sheet.getSprite(i, 0); // Extract the sprites
            leftSprites[i] = sheet.getSprite(i, 1);
            upSprites[i] = sheet.getSprite(i, 2);
            downSprites[i] = sheet.getSprite(i, 3);
        }
    }
}
