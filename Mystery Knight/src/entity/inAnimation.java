package entity;

import main.GamePanel;

import java.awt.image.BufferedImage;

public class inAnimation extends Entity {
    public boolean collision;
    protected BufferedImage image,active,inactive;
    public String description;
    public inAnimation(GamePanel gp) {
        super(gp);
        this.gp=gp;
    }
}
