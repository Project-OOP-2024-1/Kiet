package objects;

import main.GameSetting;

public class Fragment extends Object{
    public Fragment(GameSetting gs, int width, int height) {
        super(gs, width, height);
        name="Fragment";
        description="[" + name + "]\nGate key";
        getImage(name,width,height);
    }
}
