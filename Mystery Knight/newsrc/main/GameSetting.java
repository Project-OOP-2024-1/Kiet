package main;

import characters.NPC;
import characters.Player;
import characters.Projectile;
import entity.SolidEntity;
import objects.Heart;
import objects.SuperObject;
import tiles.TileManager;

import java.util.ArrayList;
import java.util.Comparator;

public class GameSetting {
    public final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;
    public boolean boss=false;
    //Set Value of tile
    public final int tileSize = originalTileSize * scale; // 48x48 tile
    public int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = maxScreenCol * tileSize; // Window size
    public final int screenHeight = maxScreenRow * tileSize;
    int FPS = 60;

    //WORLD SETTINGS
    public final int maxWorldCol = 60;
    public final int maxWorldRow = 60;
    //Test
    public TileManager tileM;
    public Player player;
    public ArrayList<NPC> npc;
    public ArrayList<Projectile> projectile;
    public ArrayList<SuperObject> event;
    public Heart heart;
    public GameSetting(){
        player= new Player(this,14*tileSize,12*tileSize);
        tileM=new TileManager(this);
        npc=new ArrayList<>();
        event=new ArrayList<>();
        projectile=new ArrayList<>();
        heart=new Heart(this);
    }
    public void Setting(){
        setNPC("GirlMagician",6,14,15,180,180,1,1,false);
        setNPC("Slime",8,43,46,16,16,1,1,true);
        setNPC("Slime",8,49,29,16,16,1,1,true);
        setNPC("Slime",8,36,42,16,16,1,1,true);
        setNPC("Slime",8,32,31,16,16,1,1,true);
        setNPC("Shit",8,43,47,16,16,1,2,true);
        setNPC("Shit",8,40,13,16,16,1,2,true);
        setNPC("Shit",8,40,18,16,16,1,2,true);
        setEvent("TransitionGate",40,7,16,32,2);
        setEvent("HealingPool",41,36,16,16,1);
        setEvent("Mushroom",9,15,16,16,1);
        setEvent("Mushroom",17,17,16,16,1);
        setEvent("Mushroom",9,24,16,16,1);
        setEvent("Mushroom",51,31,16,16,1);
        setEvent("Mushroom",19,31,16,16,1);
        setEvent("Mushroom",42,38,16,16,1);
        setEvent("Mushroom",48,49,16,16,1);
        setEvent("Chest",6,50,16,16,1);
        setNPC("Slime",6,16,17,16,16,1,1,true);
        setEvent("Mushroom",15,20,16,16,1);
        setNPC("Shit",4,14,17,16,16,1,2,true);
    }
    private void setNPC(String name,int maxLife, int x, int y, int width, int height, int scale,int speed, boolean isMonster){
        npc.add(new NPC(this,name,maxLife, x, y, width, height,  scale,speed, isMonster));
    }
    private void setEvent(String name,int x,int y,int width,int height,int scale){
        event.add(new SuperObject(this,name,x,y,width,height,scale));
    }
    public ArrayList<SolidEntity> orderDraw() {
        ArrayList<SolidEntity> entityList = new ArrayList<>();
        //player
        entityList.add(player);
        //monster
        entityList.addAll(npc);
        for (SuperObject entity: event){
            if(!entity.name.equals("HealingPool")){
                entityList.add(entity);
            }
        }
        entityList.addAll(projectile);
        entityList.sort(Comparator.comparingInt(o -> o.y));
        for (SuperObject entity: event){
            if(entity.name.equals("HealingPool")){
                entityList.addFirst(entity);
            }
        }
        return entityList;
    }

}
