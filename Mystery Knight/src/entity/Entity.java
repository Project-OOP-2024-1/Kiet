package entity;

import java.awt.*;

public abstract class Entity {
    public String name;
    public int x;
    public int y;
    public boolean alive;
    public int frameCount;
    public Rectangle solidregion;
    public Entity(){
    }
    public void getImage(){};
}
