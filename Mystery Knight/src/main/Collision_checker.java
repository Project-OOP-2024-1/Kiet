package main;

import entity.Entity;

public class Collision_checker {
    GamePanel gp;
    public Collision_checker(GamePanel gp){
        this.gp=gp;
    }

    public void checkTile(Entity entity){
        int Left_Xentity= entity.x+entity.solidregion.x;
        int Right_Xentity=entity.x+entity.solidregion.x+entity.solidregion.width;
        int Top_Yentity= entity.y+entity.solidregion.y;
        int Bottom_Yentity= entity.y+entity.solidregion.y+entity.solidregion.height;

        int Left_Col = Left_Xentity/gp.tileSize;
        int Right_Col = Right_Xentity/gp.tileSize;
        int Top_Row = Top_Yentity/gp.tileSize;
        int Bottom_Row = Bottom_Yentity/gp.tileSize;

        int numTile_1=0;
        int numTile_2=0; // this is tile corresponding to tile in map;

        switch (entity.direction){
            case "up":
                Top_Row = (Top_Yentity-entity.speed)/gp.tileSize;
                numTile_1=gp.tileM.mapTileNum1[Left_Col][Top_Row];
                numTile_2=gp.tileM.mapTileNum2[Right_Col][Top_Row];
                break;
            case "down":
                Bottom_Row = (Bottom_Yentity+entity.speed)/gp.tileSize;
                numTile_1=gp.tileM.mapTileNum1[Left_Col][Bottom_Row];
                numTile_2=gp.tileM.mapTileNum2[Right_Col][Bottom_Row];
                break;
            case  "right":
                Right_Col = (Right_Xentity+entity.speed)/gp.tileSize;
                numTile_1=gp.tileM.mapTileNum1[Right_Col][Bottom_Row];
                numTile_2=gp.tileM.mapTileNum2[Right_Col][Top_Row];
                break;
            case "left":
                Left_Col = (Left_Xentity-entity.speed)/gp.tileSize;
                numTile_1=gp.tileM.mapTileNum1[Left_Col][Bottom_Row];
                numTile_2=gp.tileM.mapTileNum2[Left_Col][Top_Row];
                break;
        }

        if (gp.tileM.tile[numTile_1].collision || gp.tileM.tile[numTile_2].collision){
            entity.collisionOn=true;
        }


    }
    // for player and having option for projecttile
    public void checkEntity(Entity entity, Entity[] target){
        int default_e_x=entity.solidregion.x;
        int default_e_y=entity.solidregion.y;
        int default_t_x;
        int default_t_y;
        for(Entity t: target){
            if (t!=null){
                default_t_x=t.solidregion.x;
                default_t_y=t.solidregion.y;
                entity.solidregion.x= entity.x+entity.solidregion.x;
                entity.solidregion.y=entity.y +entity.solidregion.y;
                //
                t.solidregion.x=t.x+t.solidregion.x;
                t.solidregion.y=t.y+t.solidregion.y;

                switch (entity.direction){
                    case "up": entity.solidregion.y-=entity.speed;break;
                    case "down": entity.solidregion.y+=entity.speed;break;
                    case "right": entity.solidregion.x+=entity.speed;break;
                    case "left": entity.solidregion.x-=entity.speed;break;
                }
                if(entity.solidregion.intersects(t.solidregion)) {
                    entity.collisionOn=true;
                    //1 time run
                    if (entity==gp.player.projectile){
                        if (t.life>0 && !t.invincible) {
                            t.life--;
                            t.invincible=true;
                        }

                    }
                }
                // return origin
                entity.solidregion.x=default_e_x;
                entity.solidregion.y=default_e_y;
                t.solidregion.x=default_t_x;
                t.solidregion.y=default_t_y;
            }
        }
    }
    // for monster
    public void checkPlayer(Entity entity){
        Entity t =gp.player;
        int default_e_x=entity.solidregion.x;
        int default_e_y=entity.solidregion.y;
        int default_t_x=t.solidregion.x;
        int default_t_y=t.solidregion.y;
        entity.solidregion.x= entity.x+entity.solidregion.x;
        entity.solidregion.y=entity.y +entity.solidregion.y;
        //
        t.solidregion.x=t.x+t.solidregion.x;
        t.solidregion.y=t.y+t.solidregion.y;

        switch (entity.direction){
            case "up": entity.solidregion.y-=entity.speed;break;
            case "down": entity.solidregion.y+=entity.speed;break;
            case "right": entity.solidregion.x+=entity.speed;break;
            case "left": entity.solidregion.x-=entity.speed;break;
        }
        if(entity.solidregion.intersects(t.solidregion)) {
            entity.collisionOn=true;
        }
        // return origin
        entity.solidregion.x=default_e_x;
        entity.solidregion.y=default_e_y;
        t.solidregion.x=default_t_x;
        t.solidregion.y=default_t_y;
    }
    // for monster
    public boolean Damaged(Entity entity){
        Entity t =gp.player;
        boolean result=false;
        int default_e_x=entity.solidregion.x;
        int default_e_y=entity.solidregion.y;
        int default_t_x=t.Attackregion.x;
        int default_t_y=t.Attackregion.y;
        int now_x= t.x;
        int now_y=t.y;
        entity.solidregion.x= entity.x+entity.solidregion.x;
        entity.solidregion.y=entity.y +entity.solidregion.y;
        //
        switch (t.direction){
            case "up": now_y-=t.Attackregion.height;break;
            case "down": now_y+=t.Attackregion.height;break;
            case "right": now_x+=t.Attackregion.width;break;
            case "left": now_x-=t.Attackregion.width;break;
        }
        t.Attackregion.x=now_x+t.Attackregion.x;
        t.Attackregion.y=now_y+t.Attackregion.y;
        switch (entity.direction){
            case "up": entity.solidregion.y-=entity.speed;break;
            case "down": entity.solidregion.y+=entity.speed;break;
            case "right": entity.solidregion.x+=entity.speed;break;
            case "left": entity.solidregion.x-=entity.speed;break;
        }
        if(entity.solidregion.intersects(t.Attackregion)) {
            result = true;
        }
        // return origin
        entity.solidregion.x=default_e_x;
        entity.solidregion.y=default_e_y;
        t.Attackregion.x=default_t_x;
        t.Attackregion.y=default_t_y;
        return result;
    }
    //for checkObjects
    public void checkObject(Entity entity, Entity[] target){
        int default_e_x=entity.solidregion.x;
        int default_e_y=entity.solidregion.y;
        int default_t_x;
        int default_t_y;
        for(Entity t: target){
            if (t!=null){
                default_t_x=t.solidregion.x;
                default_t_y=t.solidregion.y;
                entity.solidregion.x= entity.x+entity.solidregion.x;
                entity.solidregion.y=entity.y +entity.solidregion.y;
                //
                t.solidregion.x=t.x+t.solidregion.x;
                t.solidregion.y=t.y+t.solidregion.y;

                switch (entity.direction){
                    case "up": entity.solidregion.y-=entity.speed;break;
                    case "down": entity.solidregion.y+=entity.speed;break;
                    case "right": entity.solidregion.x+=entity.speed;break;
                    case "left": entity.solidregion.x-=entity.speed;break;
                }
                if(entity.solidregion.intersects(t.solidregion)) {
                    entity.collisionOn=true;
                }
                // return origin
                entity.solidregion.x=default_e_x;
                entity.solidregion.y=default_e_y;
                t.solidregion.x=default_t_x;
                t.solidregion.y=default_t_y;
            }
        }
    }

}
