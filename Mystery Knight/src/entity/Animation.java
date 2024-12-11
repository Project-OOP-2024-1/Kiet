package entity;

import java.awt.image.BufferedImage;

public class Animation extends Entity {
    public BufferedImage[] rightSprites,leftSprites, upSprites, downSprites, idleSprites, deathSprites;
    public int counterSprite = 0;
    public int numSprite = 1;
    public int life;
    public int maxLife;
    public int damage;
    public String direction;
    public boolean collisionOn;
    public boolean physical;
    public boolean death;
}
