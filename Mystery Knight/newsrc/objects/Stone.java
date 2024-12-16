package objects;

import main.GameSetting;

public class Stone extends Object{
    public Stone(GameSetting gs, int width, int height) {
        super(gs, width, height);
        name="Stone";
        description="[" + name + "]\nThis is special \ningredient";
        getImage(name,width,height);
    }
}
