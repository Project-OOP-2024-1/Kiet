package object;

import main.GamePanel;

import java.awt.*;

public class Stone extends NormalObject {
    GamePanel gp;
    public Stone(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = "Stone";
        description = "[" + name + "]\nThis is special \ningredient";
        solidregion = new Rectangle(0, 0, 0, 0);
        //get image
        image=getImage("objects/stone.png");
        collision = false;
    }
}
