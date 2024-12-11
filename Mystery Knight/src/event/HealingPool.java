package event;

import entity.Entity;
import entity.Inanimated;
import entity.Updatable;
import java.awt.Graphics2D;

public class HealingPool extends Inanimated implements Updatable {

    private int Counter;

    public HealingPool(GamePanel gp) {
        super(gp);
        name = "Healing pool";
        description = "[" + name + "]\nCure your hurt";
        solidregion = new Rectangle(0, 0, 48, 48);
        collision = true;
        alive = false;
        image = getImage("/objects/healling pool.png");
    }
    
    public void draw(Graphics2D g2) {
        int screenX = x - gp.player.x + gp.player.screenX;
        int screenY = y - gp.player.y + gp.player.screenY;
        if (x + gp.tileSize > gp.player.x - gp.player.screenX &&
                x - gp.tileSize < gp.player.x + gp.player.screenX &&
                y + gp.tileSize > gp.player.y - gp.player.screenY &&
                y - gp.tileSize < gp.player.y + gp.player.screenY) {
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }

    public void update(){
        Counter++;
        if (Counter==30){
            if (life<maxLife){
                gp.ui.addMessage("Recovery!");
            }
            else {
                gp.ui.addMessage("Full health!");
            }
            if (gp.player.life<6) gp.player.life++;
            Counter=0;
        }
    }
}
