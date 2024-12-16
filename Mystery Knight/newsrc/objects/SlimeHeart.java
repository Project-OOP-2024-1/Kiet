package objects;

import main.GameSetting;

public class SlimeHeart extends Object{
    public SlimeHeart(GameSetting gs, int width, int height) {
        super(gs, width, height);
        name = "Slime Heart";
        description = "[" + name + "]\nCore of slime";
        getImage(name,width,height);
    }
}
