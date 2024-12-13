package object;

import main.GamePanel;

import java.awt.*;

public class Potion extends SuperObject {
    GamePanel gp;
    public Potion(GamePanel gp) {
        super(gp);
        this.gp=gp;
        name = "Potion";
        solidregion=new Rectangle(4,0,32,32);
        description = "[" + name + "]\nIt is a cure portion";
        active=getImage("/objects/portion.png");
        inactive=getImage("/objects/empty portion.png");
        collision = true;
        alive=false;
    }
    public void update(){
        if (alive){
            image=active;
        }
        else {
            image=inactive;
        }
        if (!collision){
            image=getImage("/objects/cure portion.png");
        }
    }

    @Override
    public void draw(Graphics2D g2) {
    }
}
