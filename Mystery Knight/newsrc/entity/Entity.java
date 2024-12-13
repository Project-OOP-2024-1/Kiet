package entity;

import main.GameSetting;
import processor.SpriteSheet;

import java.awt.image.BufferedImage;

public abstract class Entity {
    public String name;
    public int x;
    public int y;
    public boolean alive;
    protected GameSetting gs;
    public BufferedImage image;
    public Entity(GameSetting gs){
        this.gs=gs;
    }
    protected void getImage(String name,int width,int height){
        SpriteSheet sheet = new SpriteSheet(name+".png", width, height);
        image= sheet.getSprite(0, 0);
    }
}

