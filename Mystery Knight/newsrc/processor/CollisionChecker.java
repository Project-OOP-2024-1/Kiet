package processor;

import entity.SolidEntity;
import main.GamePanel;
import main.GameSetting;

import java.util.ArrayList;

public class CollisionChecker {
    GameSetting gs;
    public CollisionChecker(GameSetting gs){
        this.gs=gs;
    }
    //checkTile for solidEntity
    public void checkTile(SolidEntity entity){
        int Left_Xentity= entity.x+entity.solidArea.x;
        int Right_Xentity=entity.x+entity.solidArea.x+entity.solidArea.width;
        int Top_Yentity= entity.y+entity.solidArea.y;
        int Bottom_Yentity= entity.y+entity.solidArea.y+entity.solidArea.height;

        int Left_Col = Left_Xentity/gs.tileSize;
        int Right_Col = Right_Xentity/gs.tileSize;
        int Top_Row = Top_Yentity/gs.tileSize;
        int Bottom_Row = Bottom_Yentity/gs.tileSize;

        int numTile1_1=0;
        int numTile1_2=0;
        int numTile2_1=0;// this is tile corresponding to tile in map;
        int numTile2_2=0;
        switch (entity.direction){
            case "up":
                Top_Row = (Top_Yentity-entity.speed)/gs.tileSize;
                numTile1_1=gs.tileM.mapTileNum1[Left_Col][Top_Row];
                numTile1_2=gs.tileM.mapTileNum1[Right_Col][Top_Row];
                numTile2_1=gs.tileM.mapTileNum2[Left_Col][Top_Row];
                numTile2_2=gs.tileM.mapTileNum2[Right_Col][Top_Row];
                break;
            case "down":
                Bottom_Row = (Bottom_Yentity+entity.speed)/gs.tileSize;
                numTile1_1=gs.tileM.mapTileNum1[Left_Col][Bottom_Row];
                numTile1_2=gs.tileM.mapTileNum1[Right_Col][Bottom_Row];
                numTile2_1=gs.tileM.mapTileNum2[Left_Col][Bottom_Row];
                numTile2_2=gs.tileM.mapTileNum2[Right_Col][Bottom_Row];
                break;
            case  "right":
                Right_Col = (Right_Xentity+entity.speed)/gs.tileSize;
                numTile1_1=gs.tileM.mapTileNum1[Right_Col][Bottom_Row];
                numTile1_2=gs.tileM.mapTileNum1[Right_Col][Top_Row];
                numTile2_1=gs.tileM.mapTileNum2[Right_Col][Bottom_Row];
                numTile2_2=gs.tileM.mapTileNum2[Right_Col][Top_Row];

                break;
            case "left":
                Left_Col = (Left_Xentity-entity.speed)/gs.tileSize;
                numTile1_1=gs.tileM.mapTileNum1[Left_Col][Bottom_Row];
                numTile1_2=gs.tileM.mapTileNum1[Left_Col][Top_Row];
                numTile2_1=gs.tileM.mapTileNum2[Left_Col][Bottom_Row];
                numTile2_2=gs.tileM.mapTileNum2[Left_Col][Top_Row];
                break;
        }

        if (gs.tileM.tile[numTile1_1].collision || gs.tileM.tile[numTile1_2].collision
                || gs.tileM.tile[numTile2_1].collision || gs.tileM.tile[numTile2_2].collision){
            entity.collisionOn=true;
        }


    }
    //check collision of two different objects
    public void checkEntity(SolidEntity entity, ArrayList<SolidEntity> target){
        int default_e_x=entity.solidArea.x;
        int default_e_y=entity.solidArea.y;
        int default_t_x;
        int default_t_y;
        for(SolidEntity t: target){
            if (t!=null){
                default_t_x=t.solidArea.x;
                default_t_y=t.solidArea.y;
                entity.solidArea.x= entity.x+entity.solidArea.x;
                entity.solidArea.y=entity.y +entity.solidArea.y;
                //
                t.solidArea.x=t.x+t.solidArea.x;
                t.solidArea.y=t.y+t.solidArea.y;

                switch (entity.direction){
                    case "up": entity.solidArea.y-=entity.speed;break;
                    case "down": entity.solidArea.y+=entity.speed;break;
                    case "right": entity.solidArea.x+=entity.speed;break;
                    case "left": entity.solidArea.x-=entity.speed;break;
                }
                if(entity.solidArea.intersects(t.solidArea)) {
                    entity.collisionOn=true;
                }
                // return origin
                entity.solidArea.x=default_e_x;
                entity.solidArea.y=default_e_y;
                t.solidArea.x=default_t_x;
                t.solidArea.y=default_t_y;
            }
        }
    }
    //check collision of monster forward to player
    public void checkPlayer(SolidEntity entity){
        SolidEntity t =gs.player;
        int default_e_x=entity.solidArea.x;
        int default_e_y=entity.solidArea.y;
        int default_t_x=t.solidArea.x;
        int default_t_y=t.solidArea.y;
        entity.solidArea.x= entity.x+entity.solidArea.x;
        entity.solidArea.y=entity.y +entity.solidArea.y;
        //
        t.solidArea.x=t.x+t.solidArea.x;
        t.solidArea.y=t.y+t.solidArea.y;

        switch (entity.direction){
            case "up": entity.solidArea.y-=entity.speed;break;
            case "down": entity.solidArea.y+=entity.speed;break;
            case "right": entity.solidArea.x+=entity.speed;break;
            case "left": entity.solidArea.x-=entity.speed;break;
        }
        if(entity.solidArea.intersects(t.solidArea)) {
            entity.collisionOn=true;
        }
        // return origin
        entity.solidArea.x=default_e_x;
        entity.solidArea.y=default_e_y;
        t.solidArea.x=default_t_x;
        t.solidArea.y=default_t_y;
    }
}