package objects;

import entity.Entity;
import main.GameSetting;
import processor.SpriteSheet;

public class Object extends Entity {
    //Object in this is used for inventory and active skill
    public String description;
    public Object(GameSetting gs, int width, int height) {
        super(gs);
        this.gs=gs;
        getImage(name,width,height);
    }

    @Override
    protected void getImage(String name, int width, int height) {
        SpriteSheet sheet = new SpriteSheet("objects/"+name+".png", width, height);
        image= sheet.getSprite(0, 0);
    }
}
