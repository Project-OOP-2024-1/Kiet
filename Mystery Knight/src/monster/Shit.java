package monster;

import main.GamePanel;

import java.awt.*;

public class Shit extends Monster{
    public Shit(GamePanel gp) {
        super(gp);
        this.gp=gp;
        frameCount=4;
        name="Shit";
        direction="idle";
        speed=1;
        maxLife=4;
        life=maxLife;
        solidregion= new Rectangle(8,16,32,24);
        Attackregion= new Rectangle(-gp.tileSize*6,-gp.tileSize*6,gp.tileSize*13,gp.tileSize*13);
        image=getImage("characters/shit_w_trans.png");
        attack=false;
        alive=true;
        damaged=false;
        death=false;
    }
}
