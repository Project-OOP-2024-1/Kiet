package object;

import main.GamePanel;
import utility.ImageManipulate;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Heart extends SuperObject {
    GamePanel gp;
    private final BufferedImage[] temp_image;
    public Heart(GamePanel gp){
        super(gp);
        temp_image = new BufferedImage[3];
        this.gp = gp;
        name = "Heart";
        //setup image
        temp_image[0]=getImage("/objects/heart_blank.png");
        temp_image[1]=getImage("/objects/heart_half.png");
        temp_image[2]=getImage("/objects/heart_full.png");
        image=temp_image[0];
    }
    public BufferedImage getImage(String filepath){
        try {
            image = getImage("/objects/heart_blank.png");
            image = ImageManipulate.scaleImage(image, 3);
            return image;
        }
        catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(image, 0, 0,image.getWidth() , image.getHeight(), null);
    }
}
