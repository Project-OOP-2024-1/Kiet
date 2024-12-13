package object;

import main.GamePanel;

import java.awt.*;

public class Key extends NormalObject {
    GamePanel gp;
    public Key(GamePanel gp) {
        super(gp);
        this.gp=gp;
        name = "Key";
        solidregion=new Rectangle(4,0,32,32);
        description = "[" + name + "]\nIt opens a chest";
        image=getImage("/objects/key_test.png");
        collision = true;
        alive=true;
    }
}