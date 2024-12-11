package object;

import entity.Entity;
import entity.inAnimation;
import main.GamePanel;
import main.SpriteSheet;

import java.awt.*;


public class OBJ_Chest extends inAnimation {

    GamePanel gp;

    public OBJ_Chest(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = "Chest";
        description = "[" + name + "]\nNeed a key";
        solidregion = new Rectangle(8, 0, 32, 32);
        getImage();
        collision = true;
        alive=true;
    }
    public void getImage(){
        SpriteSheet sheet = new SpriteSheet("/objects/chest2_closed.png", gp.originalTileSize, gp.originalTileSize);
        image = sheet.getSprite(0, 0);
    }
}