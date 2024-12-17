package objects;

import main.GameSetting;

public class Mushroom extends Object{
    public Mushroom(GameSetting gs, int width, int height) {
        super(gs, width, height);
        name="Mushroom";
        description="[" + name + "]\nImportant ingredient for medical";
    }
}
