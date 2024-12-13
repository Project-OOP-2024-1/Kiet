package object;

import main.GamePanel;

import java.awt.*;

public class Mushroom extends SuperObject {
    GamePanel gp;
    public Mushroom(GamePanel gp) {
        super(gp);
        this.gp=gp;
        name = "Mushroom";
        solidregion=new Rectangle(16,8,16,32);
        description = "[" + name + "]\nIt is a medical ingredient";
        image=getImage("/tiles/map_tileset.png");
        collision = true;
        alive=true;
    }
}
