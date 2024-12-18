package objects;

import main.GameSetting;

public class Reward extends Object{
    public Reward(GameSetting gs, String name, int width, int height) {
        super(gs, width, height);
        this.name=name+" core";
        this.description="[" + name + "]\nKernel of "+name;
        if(name.equals("Ghost")) {
            this.name="Key";
            this.description="[" + name + "]\nIt opens a chest";
        }
        getImage(name+"Reward",width,height);
    }
}
