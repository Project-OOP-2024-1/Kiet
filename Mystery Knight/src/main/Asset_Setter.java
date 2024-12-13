package main;

import monster.Ghost;
import monster.Monster;
import monster.Slime;
import monster.Shit;
import human.NPC;
import object.*;

public class Asset_Setter {
    GamePanel gp;
    public Asset_Setter(GamePanel gp){
        this.gp=gp;
    }
    public void setGeneral(){
        setNPC();
        setMonster(0,43,46,new Slime(gp));
        setMonster(1,43,47,new Shit(gp));
        setMonster(2,15,45,new Ghost(gp));
        setMonster(3,40,13,new Shit(gp));
        setMonster(4,40,18,new Shit(gp));
        setMonster(5,49,29,new Slime(gp));
        setMonster(6,40,30,new Slime(gp));
        setMonster(7,38,44,new Shit(gp));
        setMonster(8,36,42,new Slime(gp));
        setMonster(9,32,31,new Slime(gp));
        setObject(0,6,50,new Chest(gp));
        setObject(1,9,15,new Mushroom(gp));
        setObject(2,17,17,new Mushroom(gp));
        setObject(3,9,24,new Mushroom(gp));
        setObject(4,19,31,new Mushroom(gp));
        setObject(5,51,31,new Mushroom(gp));
        setObject(6,42,38,new Mushroom(gp));
        setObject(7,48,49,new Mushroom(gp));
    }
    public void setNPC(){
        gp.npc[0]= new NPC(gp);
        gp.npc[0].x=gp.tileSize*14;
        gp.npc[0].y=gp.tileSize*15;
    }
    public void setMonster(int index, int x, int y, Monster monster){
        gp.monster[index]= monster;
        gp.monster[index].x=gp.tileSize*x;
        gp.monster[index].y=gp.tileSize*y;
    }
    public void setObject(int index, int x, int y, SuperObject Object){
        gp.object[index]= Object;
        gp.object[index].x=gp.tileSize*x;
        gp.object[index].y=gp.tileSize*y;
    }
    public void setEvent(){
        //event here
    }

}
