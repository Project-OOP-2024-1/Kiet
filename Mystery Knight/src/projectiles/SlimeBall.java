package projectiles;

import entity.Projectile;
import main.GamePanel;
import main.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SlimeBall extends Projectile {
    public SlimeBall(GamePanel gp){
        super(gp);
        this.gp=gp;
        name="Slimeball";
        speed = 4;
        maxLife = 80;
        life = maxLife;
        damage = 1;
        alive = false;
        solidregion = new Rectangle(0,0,24,24);
        getImage();
    }
    public void getImage() {
        SpriteSheet sheet = new SpriteSheet("/objects/slime_ball.png", gp.originalTileSize, gp.originalTileSize);

        rightSprites = new BufferedImage[2];
        leftSprites = new BufferedImage[2];
        upSprites = new BufferedImage[2];
        downSprites = new BufferedImage[2];

        for (int i = 0; i < 2; i++) {
            rightSprites[i] = sheet.getSprite(0, 0); // Extract the sprites
            leftSprites[i] = sheet.getSprite(0, 0);
            upSprites[i] = sheet.getSprite(0, 0);
            downSprites[i] = sheet.getSprite(0, 0);
        }
    }
}
