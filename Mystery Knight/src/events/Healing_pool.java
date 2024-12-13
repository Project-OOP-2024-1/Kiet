package events;

import entity.Inanimate;
import main.GamePanel;
import main.Updatable;

import java.awt.*;

public class Healing_pool extends Inanimate implements Updatable {
    GamePanel gp;
    private int Counter;
    public Healing_pool(GamePanel gp) {
        super(gp);
        this.gp = gp;
        Counter=0;
        name = "Healing pool";
        description = "[" + name + "]\nCure your hurt";
        solidregion = new Rectangle(0, 0, 48, 48);
        image=getImage("/objects/healing pool.png");
        collision = true;
    }
    public void update(){

        if (Counter==30){
            if (gp.player.life<gp.player.maxLife){
                gp.ui.addMessage("Recovery!");
            }
            else {
                gp.ui.addMessage("Full health!");
            }
            if (gp.player.life<6) gp.player.life++;
            Counter=0;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
    }
}
