package objects;

import main.GameSetting;

public class Sword extends Object{
    public Sword(GameSetting gs, int width, int height) {
        super(gs, width, height);
        name="Sword";
        description="This is DragonKiller Sword";
        getImage(name,gs.originalTileSize,gs.originalTileSize);
    }
}
