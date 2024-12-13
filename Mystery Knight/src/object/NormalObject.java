package object;

import entity.Inanimate;
import main.GamePanel;

public class NormalObject extends Inanimate {
    public NormalObject(GamePanel gp) {
        super(gp);
        this.gp=gp;
    }
}
