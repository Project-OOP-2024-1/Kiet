package object;

import main.GamePanel;

import java.awt.*;

public class HolyGrail extends NormalObject {
    public HolyGrail(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = "Holy Grail";
        description = "[" + name + "]\nThis is power stone";
        solidregion = new Rectangle(0, 0, 0, 0);
        //get image
        image=getImage("objects/treasure.png");
        collision = true;
    }
}
