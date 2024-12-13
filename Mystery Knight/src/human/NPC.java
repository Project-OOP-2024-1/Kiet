package human;

import entity.Character;
import main.GamePanel;
import main.SpriteSheet;
import main.Updatable;

import java.awt.*;
import java.awt.image.BufferedImage;

public class NPC extends Character implements Updatable {
    public NPC(GamePanel gp) {
        super(gp);
        this.gp=gp;
        name="NPC";
        direction = "idle";
        speed = 1;
        solidregion = new Rectangle(8,16,32,32);
        frameCount=4;
        image=getImage("/SLIME/NPC.png");
    }
    public BufferedImage getImage(String filepath) {

        SpriteSheet sheet = new SpriteSheet(filepath, gp.originalTileSize, gp.originalTileSize);
        idleSprites = new BufferedImage[frameCount];
        for (int i = 0; i < frameCount; i++) {
            idleSprites[i] = sheet.getSprite(i, 0);
        }
        return idleSprites[0];
    }

    @Override
    public void update() {
        counterSprite++;
        if (counterSprite > 15) {
            numSprite++;
            if(numSprite>frameCount){
                numSprite=1;
            }
            counterSprite = 0;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
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
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }
}
