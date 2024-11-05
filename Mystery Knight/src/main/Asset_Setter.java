package main;

import Monster.SLime;
import entity.NPC_1;

public class Asset_Setter {
    GamePanel gp;
    public Asset_Setter(GamePanel gp){
        this.gp=gp;
    }
    public void setNPC(){
        gp.npc[0]= new NPC_1(gp);
        gp.npc[0].x=gp.tileSize*21;
        gp.npc[0].y=gp.tileSize*19;
    }
    public void setMonster(){
        gp.monster[0]= new SLime(gp);
        gp.monster[0].x=gp.tileSize*21;
        gp.monster[0].y=gp.tileSize*17;
    }
}
