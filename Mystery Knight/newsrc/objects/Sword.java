package objects;

import main.GameSetting;

public class Sword extends Object{
    public Sword(GameSetting gs, int width, int height) {
        super(gs, width, height);
        name="Sword";
        description = "[" + name + "]\nLegendary Sword made by best Blacksmith";
        getImage(name,width,height);
    }
}
