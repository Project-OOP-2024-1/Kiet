package entity;

import main.GamePanel;

import java.awt.*;

public abstract class Entity {
    public String name;
    public int x;
    public int y;
    public boolean alive;
    public int frameCount;
    public Rectangle solidregion;
    protected GamePanel gp;

    public Entity(GamePanel gp){
        this.gp=gp;
    }
    public void getImage(){};
}
