package main;

import characters.NPC;
import characters.Player;
import entity.SolidEntity;
import processor.CollisionChecker;
import tiles.TileManager;

import java.util.ArrayList;

public class GameSetting {
    public final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;
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
    public CollisionChecker collisionChecker;
    public ArrayList<SolidEntity> npc;
    public ArrayList<SolidEntity> projectile;
    public GameSetting(){
        player= new Player(this,14*tileSize,12*tileSize);
        collisionChecker = new CollisionChecker(this);
        tileM=new TileManager(this);
        npc=new ArrayList<>();
        projectile=new ArrayList<>();
    }
    public void Setting(){
        setNPC("GirlMagician",6,14,15,180,180,2,2,false);
        setNPC("Slime",8,16,17,16,16,1,3,true);
    }
    private void setNPC(String name,int maxLife, int x, int y, int width, int height, int scale,int speed, boolean isMonster){
        npc.add(new NPC(this,name,maxLife, x, y, width, height,  scale,speed, isMonster));
    }
    public ArrayList<SolidEntity> orderDraw() {
        ArrayList<SolidEntity> entityList = new ArrayList<>();
        //player
        entityList.add(player);
        //monster
        entityList.addAll(npc);
        entityList.addAll(projectile);
        entityList.sort((o1, o2) -> Integer.compare(o1.y,o2.y));
        return entityList;
    }

}
