package object;

import main.GamePanel;

import java.awt.*;

public class Fragment extends NormalObject {
    GamePanel gp;
    public Fragment(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = "Fragment";
        description = "[" + name + "]\nThis is Gate key";
        solidregion = new Rectangle(0, 0, 0, 0);
        //get image
        image=getImage("/objects/fragment.png");
        collision = false;
    }

}
