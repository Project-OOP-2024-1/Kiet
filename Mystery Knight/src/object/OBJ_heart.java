package object;

import human.Player;
import entity.inAnimation;
import main.GamePanel;
import main.updatable;
import utility.ImageManipulate;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class OBJ_heart extends inAnimation implements updatable {
    GamePanel gp;
    private final BufferedImage[] temp_image;
    public OBJ_heart(GamePanel gp){
        super(gp);
        temp_image = new BufferedImage[3];
        this.gp = gp;
        name = "Heart";
        getImage();
        image=temp_image[0];
    }
    public void getImage(){
        try{
            temp_image[0] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/heart_blank.png")));
            temp_image[0] = ImageManipulate.scaleImage(temp_image[0],3);
            temp_image[1] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/heart_half.png")));
            temp_image[1] = ImageManipulate.scaleImage(temp_image[1],3);
            temp_image[2] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/heart_full.png")));
            temp_image[2] = ImageManipulate.scaleImage(temp_image[2],3);        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    @Override
    public void update() {
        int [][] status = {
                {0,0,0},
                {1,0,0},
                {2,0,0},
                {2,1,0},
                {2,2,0},
                {2,2,1},
                {2,2,2}
        };
        int life = gp.player.life;
        BufferedImage[] tmp = {temp_image[status[life][0]],temp_image[status[life][1]],temp_image[status[life][2]]};
        image = ImageManipulate.concatenateImage(tmp,0);
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(image, 0, 0,image.getWidth() , image.getHeight(), null);
    }
}
