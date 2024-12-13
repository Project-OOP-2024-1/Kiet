package objects;

import entity.SolidEntity;
import main.GameSetting;
import processor.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject extends SolidEntity {
    //SuperObject is object that have functional work(like event)
    BufferedImage inactive, active;
    public Rectangle solidArea;
    private boolean collision;
    public SuperObject(GameSetting gs,String name,int width,int height,boolean collision) {
        super(gs);
        this.gs=gs;
        this.name=name;
        this.collision=collision;
        solidArea= new Rectangle(0,0,width,height);
        getImage(this.name,width,height);
        alive=true;
    }

    @Override
    protected void getImage(String name, int width, int height) {
        SpriteSheet sheet = new SpriteSheet("objects/"+name+".png",width,height);
        image=sheet.getSprite(0,0);
        inactive=sheet.getSprite(1,0);
        active=sheet.getSprite(2,0);
    }

    public void update(){
        if (this.name.equals("HealingPool")){
            //fill this
        } else if (this.name.equals("TransitionGate")) {
            //fill this
        }
    }

    @Override
    public void draw(Graphics2D g2) {

    }
}
