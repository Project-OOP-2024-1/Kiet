package objects;

import main.GameSetting;
import processors.SpriteSheet;

import java.awt.image.BufferedImage;

public class Potion extends Object {
    public final int thingStatus;
    public Potion(GameSetting gs, String name, int thingStatus, int width, int height) {
        super(gs, width, height);
        this.name=name;
        this.description="[" + name + "]\n??????";
        this.thingStatus=thingStatus;
        getImage(name,16,16);
    }
    protected void getImage(String name, int width, int height) {
        SpriteSheet sheet = new SpriteSheet("/objects/" + name + ".png", width, height);
        BufferedImage empty = sheet.getSprite(0, 0);
        BufferedImage water = sheet.getSprite(1, 0);
        BufferedImage medical = sheet.getSprite(2, 0);
        switch (thingStatus){
            case 0:image= empty;break;
            case 1:image= water;break;
            case 2:image= medical;break;
        }
    }
}
