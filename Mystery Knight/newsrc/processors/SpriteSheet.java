package processors;

import utility.UtilityTool;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;


public class SpriteSheet {

    private BufferedImage spriteSheet;
    private int spriteWidth;
    private int spriteHeight;
    UtilityTool utilityTool = new UtilityTool();
    public SpriteSheet(String filePath, int spriteWidth, int spriteHeight) {
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;

        try {
            // Load the sprite sheet image
            spriteSheet = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Extract sprites
    public BufferedImage getSprite(int col, int row) {
        BufferedImage tmp = spriteSheet.getSubimage(col * spriteWidth, row * spriteHeight, spriteWidth, spriteHeight);
        tmp = utilityTool.scaleImage(tmp,spriteWidth,spriteHeight);
        return tmp;
    }
}
//SpriteSheet sheet = new SpriteSheet("2D_game/resources/player/walk.png", gp.originalTileSize, gp.originalTileSize, 8, 4);
