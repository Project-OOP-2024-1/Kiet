package main;

import entity.Animated;
import entity.Character;
import entity.Entity;
import entity.Inanimate;
import human.Player;
import monster.Monster;

public class Collision_checker {
    GamePanel gp;
    public Collision_checker(GamePanel gp){
        this.gp=gp;
    }

    public void checkTile(Animated entity){
        int Left_Xentity= entity.x+entity.solidregion.x;
        int Right_Xentity=entity.x+entity.solidregion.x+entity.solidregion.width;
        int Top_Yentity= entity.y+entity.solidregion.y;
        int Bottom_Yentity= entity.y+entity.solidregion.y+entity.solidregion.height;

        int Left_Col = Left_Xentity/gp.tileSize;
        int Right_Col = Right_Xentity/gp.tileSize;
        int Top_Row = Top_Yentity/gp.tileSize;
        int Bottom_Row = Bottom_Yentity/gp.tileSize;

        int numTile1_1=0;
        int numTile1_2=0;
        int numTile2_1=0;// this is tile corresponding to tile in map;
        int numTile2_2=0;
        switch (entity.direction){
            case "up":
                Top_Row = (Top_Yentity-entity.speed)/gp.tileSize;
                numTile1_1=gp.tileM.mapTileNum1[Left_Col][Top_Row];
                numTile1_2=gp.tileM.mapTileNum1[Right_Col][Top_Row];
                numTile2_1=gp.tileM.mapTileNum2[Left_Col][Top_Row];
                numTile2_2=gp.tileM.mapTileNum2[Right_Col][Top_Row];
                break;
            case "down":
                Bottom_Row = (Bottom_Yentity+entity.speed)/gp.tileSize;
                numTile1_1=gp.tileM.mapTileNum1[Left_Col][Bottom_Row];
                numTile1_2=gp.tileM.mapTileNum1[Right_Col][Bottom_Row];
                numTile2_1=gp.tileM.mapTileNum2[Left_Col][Bottom_Row];
                numTile2_2=gp.tileM.mapTileNum2[Right_Col][Bottom_Row];
                break;
            case  "right":
                Right_Col = (Right_Xentity+entity.speed)/gp.tileSize;
                numTile1_1=gp.tileM.mapTileNum1[Right_Col][Bottom_Row];
                numTile1_2=gp.tileM.mapTileNum1[Right_Col][Top_Row];
                numTile2_1=gp.tileM.mapTileNum2[Right_Col][Bottom_Row];
                numTile2_2=gp.tileM.mapTileNum2[Right_Col][Top_Row];

                break;
            case "left":
                Left_Col = (Left_Xentity-entity.speed)/gp.tileSize;
                numTile1_1=gp.tileM.mapTileNum1[Left_Col][Bottom_Row];
                numTile1_2=gp.tileM.mapTileNum1[Left_Col][Top_Row];
                numTile2_1=gp.tileM.mapTileNum2[Left_Col][Bottom_Row];
                numTile2_2=gp.tileM.mapTileNum2[Left_Col][Top_Row];
                break;
        }

        if (gp.tileM.tile[numTile1_1].collision || gp.tileM.tile[numTile1_2].collision
        || gp.tileM.tile[numTile2_1].collision || gp.tileM.tile[numTile2_2].collision){
            entity.collisionOn=true;
        }


    }
    // for animated
    public void checkEntity(Animated entity, Character[] target){
        int default_e_x=entity.solidregion.x;
        int default_e_y=entity.solidregion.y;
        int default_t_x;
        int default_t_y;
        for(Character t: target){
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
    // for monster
    public void checkPlayer(Animated entity){
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
    public boolean Damaged(Monster entity){
        Player t =gp.player;
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
    // for monster
    public void checkDanger(Monster entity){
        Player t =gp.player;
        int default_e_x=entity.Attackregion.x;
        int default_e_y=entity.Attackregion.y;
        int default_t_x=t.solidregion.x;
        int default_t_y=t.solidregion.y;
        entity.Attackregion.x= entity.x+entity.Attackregion.x;
        entity.Attackregion.y=entity.y +entity.Attackregion.y;
        //
        t.solidregion.x=t.x+t.solidregion.x;
        t.solidregion.y=t.y+t.solidregion.y;

        switch (entity.direction){
            case "up": entity.Attackregion.y-=entity.speed;break;
            case "down": entity.Attackregion.y+=entity.speed;break;
            case "right": entity.Attackregion.x+=entity.speed;break;
            case "left": entity.Attackregion.x-=entity.speed;break;
        }
        entity.attack= entity.Attackregion.intersects(t.solidregion);
        // return origin
        entity.Attackregion.x=default_e_x;
        entity.Attackregion.y=default_e_y;
        t.solidregion.x=default_t_x;
        t.solidregion.y=default_t_y;
    }
    //for Animated check Objects
    public void checkObject(Animated entity, Entity[] target){
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
                    if (t.name.equals("Mushroom")){
                        if (gp.keyH.isPressed(10)){
                            gp.player.setHas("Mushroom",true);
                            t.alive=false;
                            gp.ui.addMessage("Take mushroom");
                            gp.player.inventory.add(t);
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
    //for Player checks Events
    public void checkEvent() {
        Player t = gp.player;

        int defaultX= t.solidregion.x;
        int defaultY= t.solidregion.y;
        for(Inanimate e: gp.event){
            if (e!=null){
                t.solidregion.x= t.x+t.solidregion.x;
                t.solidregion.y=t.y +t.solidregion.y;
                //
                e.solidregion.x=e.x;
                e.solidregion.y=e.y;
                if(e.solidregion.intersects(t.solidregion)) {
                    System.out.println("Oke");
                    //fix
                }
                // return origin
                t.solidregion.x=defaultX;
                t.solidregion.y=defaultY;
                e.solidregion.x=0;
                e.solidregion.y=0;
            }
        }

    }
    //for Projectile check
}
