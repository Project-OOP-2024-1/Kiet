package object;

import entity.Inanimate;
import main.GamePanel;

public class Shield extends Inanimate {
    public Shield(GamePanel gp){
        super(gp);
        name="Dragon Shield";
        description = "[" + name + "]\nAgainst damage\nfrom monster";
        image=getImage("objects/shield.png");
    }
}
