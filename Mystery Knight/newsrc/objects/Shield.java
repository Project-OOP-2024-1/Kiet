package objects;

import main.GameSetting;

public class Shield extends Object{
    public Shield(GameSetting gs, int width, int height) {
        super(gs, width, height);
        name="Shield";
        description="[" + name + "]\nShield is made from Oak Wood";
        getImage(name,width,height);
    }
}
