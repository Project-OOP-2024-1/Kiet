package object;

import entity.inAnimation;
import main.GamePanel;
import main.SpriteSheet;

import java.awt.*;

public class OBJ_Key extends inAnimation {
    GamePanel gp;
    public OBJ_Key(GamePanel gp) {
        super(gp);
        this.gp=gp;
        name = "Key";
        solidregion=new Rectangle(4,0,32,32);
        description = "[" + name + "]\nIt opens a chest";
        getImage();
        collision = true;
        alive=true;
    }
    public void getImage(){
        SpriteSheet sheet = new SpriteSheet("/objects/key_test.png", gp.originalTileSize, gp.originalTileSize);
        image = sheet.getSprite(0, 0);
    }
}