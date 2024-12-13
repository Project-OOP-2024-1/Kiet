package object;

import entity.Inanimate;
import main.GamePanel;
import main.Updatable;

import java.awt.*;

public class SuperObject extends Inanimate implements Updatable {
    public SuperObject(GamePanel gp) {
        super(gp);
        this.gp=gp;
    }

    @Override
    public void update() {
    }

    public void draw(Graphics2D g2){
        int screenX = x - gp.player.x + gp.player.screenX;
        int screenY = y - gp.player.y + gp.player.screenY;
        if (x + gp.tileSize > gp.player.x - gp.player.screenX &&
                x - gp.tileSize < gp.player.x + gp.player.screenX &&
                y + gp.tileSize > gp.player.y - gp.player.screenY &&
                y - gp.tileSize < gp.player.y + gp.player.screenY) {
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }
}
