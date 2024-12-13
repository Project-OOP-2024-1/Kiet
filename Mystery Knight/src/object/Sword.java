package object;

import entity.Inanimate;
import main.GamePanel;

public class Sword extends Inanimate {
    public Sword(GamePanel gp){
        super(gp);
        name= "Legend Sword";
        description = "[" + name + "]\nAttack monster";
        image=getImage("objects/sword.png");
    }
}
