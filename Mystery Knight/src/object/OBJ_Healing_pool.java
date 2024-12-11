package object;

import entity.inAnimation;
import main.GamePanel;
import main.SpriteSheet;
import main.updatable;

import java.awt.*;

public class OBJ_Healing_pool extends inAnimation implements updatable {
    GamePanel gp;
    private int Counter;
    public OBJ_Healing_pool(GamePanel gp) {
        super(gp);
        this.gp = gp;
        Counter=0;
        name = "Healing pool";
        description = "[" + name + "]\nCure your hurt";
        solidregion = new Rectangle(0, 0, 48, 48);
        getImage();
        collision = true;
    }
    public void getImage(){
        SpriteSheet sheet = new SpriteSheet("/objects/healing pool.png", gp.originalTileSize, gp.originalTileSize);
        image = sheet.getSprite(0, 0);
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
