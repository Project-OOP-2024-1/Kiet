package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Character extends Animation {
    public int speed;
    public int counterDeath= 0;
    public int NumDeath = 0;
    public int invincibleCounter=0;
    public boolean damaged;
    public boolean invincible;
    public boolean attack;
    public Rectangle Attackregion;
    BufferedImage image;
    public Character(){
    }
    //method
    public void update_death(){
        counterDeath++;
        if (counterDeath>20){
            if (NumDeath>frameCount){
                death=true;
                NumDeath=0;
            }
            NumDeath++;
            counterDeath=0;
        }
    }
    public void draw_death(Graphics2D g2){
        image=deathSprites[NumDeath];
        if (counterDeath>10){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.2f));
        }
        else{
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
        }
    }
}
