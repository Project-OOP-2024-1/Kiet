package objects;

import main.GameSetting;

public class Key extends Object{
    public Key(GameSetting gs, int width, int height) {
        super(gs, width, height);
        name = "Key";
        description = "[" + name + "]\nIt opens a chest";
        getImage(name,width,height);
    }
}
