package object;

import entity.inAnimation;
import main.GamePanel;
import main.SpriteSheet;

import java.awt.*;

public class OBJ_Mushroom extends inAnimation {
    GamePanel gp;
    public OBJ_Mushroom(GamePanel gp) {
        super(gp);
        this.gp=gp;
        name = "Mushroom";
        solidregion=new Rectangle(16,8,16,32);
        description = "[" + name + "]\nIt is a medical ingredient";
        collision = true;
        alive=true;
    }
    public void getImage(){
        SpriteSheet sheet = new SpriteSheet("/tiles/map_tileset.png", gp.originalTileSize, gp.originalTileSize);
        image = sheet.getSprite(0, 14);
    }

}
