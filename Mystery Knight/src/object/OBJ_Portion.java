package object;

import entity.inAnimation;
import main.GamePanel;
import main.SpriteSheet;
import main.updatable;

import java.awt.*;
import java.awt.image.BufferedImage;

public class OBJ_Portion extends inAnimation implements updatable {
    GamePanel gp;
    public OBJ_Portion(GamePanel gp) {
        super(gp);
        this.gp=gp;
        name = "Portion";
        solidregion=new Rectangle(4,0,32,32);
        description = "[" + name + "]\nIt is a cure portion";
        try {
            SpriteSheet sheet = new SpriteSheet("/objects/empty portion.png", gp.originalTileSize, gp.originalTileSize*2);
            inactive = sheet.getSprite(0, 0);
            sheet=new SpriteSheet("/objects/portion.png", gp.originalTileSize, gp.originalTileSize*2);
            active= sheet.getSprite(0,0);
        } catch(Exception e) {
            e.printStackTrace();
        }
        collision = true;
        alive=false;
    }
    public void getImage()
    public void update(){
        if (alive){
            image=active;
        }
        else {
            image=inactive;
        }
        if (!collision){
            SpriteSheet sheet=new SpriteSheet("/objects/cure portion.png", gp.originalTileSize, gp.originalTileSize*2);
            image=sheet.getSprite(0,0);
        }
    }

    @Override
    public void draw(Graphics2D g2) {
    }
}
