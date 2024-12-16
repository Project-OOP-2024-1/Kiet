package objects;

import main.GameSetting;

public class HolyGrail extends Object{
    public HolyGrail(GameSetting gs, int width, int height) {
        super(gs, width, height);
        name="Holy Grail";
        description="[" + name + "]\nReward for Winner";
    }
}
