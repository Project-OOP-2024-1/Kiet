package objects;

import entity.SolidEntity;
import main.GameSetting;
import processors.SpriteSheet;
import utility.ImageManipulate;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Heart extends SolidEntity {
    GameSetting gs;
    public BufferedImage[] temp_image;
    public Heart(GameSetting gs) {
        super(gs);
        this.gs=gs;
        name="Heart";
        temp_image = new BufferedImage[3];
        getImage(name,16,16);
    }
    protected void getImage(String name, int width, int height) {
        SpriteSheet sheet = new SpriteSheet("/objects/"+name+".png",width,height);
        for (int i=0;i<3;i++){
            temp_image[i]=sheet.getSprite(i,0);
        }
        image=temp_image[0];
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
        int life = gs.player.life;
        BufferedImage[] tmp = {temp_image[status[life][0]],temp_image[status[life][1]],temp_image[status[life][2]]};
        image = ImageManipulate.concatenateImage(tmp,0);
    }
    public void draw(Graphics2D g2) {
        g2.drawImage(image, 0, 0,48*3 , 48, null);
    }
}
