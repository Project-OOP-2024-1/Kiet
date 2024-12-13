package entity;

import main.GamePanel;
import main.SpriteSheet;

import java.awt.image.BufferedImage;

public class Inanimate extends Entity {
    public boolean collision;
    protected BufferedImage active,inactive;
    public String description;
    public Inanimate(GamePanel gp) {
        super(gp);
        this.gp=gp;
    }
    public BufferedImage getImage(String filepath) {
        SpriteSheet sheet = new SpriteSheet(filepath, gp.originalTileSize, gp.originalTileSize);
        return sheet.getSprite(0, 0);
    }
}
