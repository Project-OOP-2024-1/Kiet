package object;

import main.GamePanel;

import java.awt.*;


public class Chest extends SuperObject {

    GamePanel gp;

    public Chest(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = "Chest";
        description = "[" + name + "]\nNeed a key";
        solidregion = new Rectangle(8, 0, 32, 32);
        image=getImage("/objects/chest2_closed.png");
        collision = true;
        alive=true;
    }
}