package object;

import main.GamePanel;

import java.awt.*;

public class SlimeHeart extends NormalObject {
    GamePanel gp;
    public SlimeHeart(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = "Slime Heart";
        description = "[" + name + "]\nThis is power stone";
        solidregion = new Rectangle(0, 0, 0, 0);
        //get image
        image=getImage("objects/slime heart.png");
        collision = false;
    }
}
