package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Inanimated extends Entity {

    protected boolean collision;
    protected String description;
    protected Rectangle solidRegion;
    protected BufferedImage image;

    public Inanimated(GamePanel gp) {
        super(gp);
    }

    public BufferedImage getImage(String filepath) {
        try {
            SpriteSheet sheet = new SpriteSheet(filepath, gp.originalTileSize, gp.originalTileSize);
            return sheet.getSprite(0, 0);
        } catch (Exception e) {
            e.printStackTrace();
            return null; 
        }
    }
}

