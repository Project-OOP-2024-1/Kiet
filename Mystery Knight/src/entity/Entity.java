package entity;

import main.GamePanel;
import main.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity {
    public String name;
    public int x;
    public int y;
    public boolean alive;
    public int frameCount;
    public Rectangle solidregion;
    protected GamePanel gp;
    public BufferedImage image;

    public Entity(GamePanel gp){
        this.gp=gp;
    }
    public BufferedImage getImage(String filepath){return null;}
}
