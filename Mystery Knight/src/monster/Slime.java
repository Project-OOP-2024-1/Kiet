package monster;

import entity.Projectile;
import main.GamePanel;
import projectiles.SlimeBall;

import java.awt.*;


public class Slime extends Monster {
    public Slime(GamePanel gp) {
        super(gp);
        this.gp=gp;
        frameCount=4;
        name = "Slime";
        direction="idle";
        speed=1;
        maxLife=4;
        life=maxLife;
        solidregion= new Rectangle(8,16,32,24);
        Attackregion= new Rectangle(-gp.tileSize*5,-gp.tileSize*5,gp.tileSize*11,gp.tileSize*11);
        image=getImage("/monster/slime_animation_w_trans.png");
        attack=false;
        alive=true;
        damaged=false;
        death=false;
        Projectile projectile = new SlimeBall(gp);
    }
}
