package processors;

import characters.NPC;
import characters.Player;
import characters.Projectile;
import entity.SolidEntity;
import main.GamePanel;
import main.GameSetting;
import objects.SuperObject;

import java.util.ArrayList;

public class CollisionChecker {
    GameSetting gs;
    GamePanel gp;
    public CollisionChecker(GameSetting gs, GamePanel gp){
        this.gs=gs;
        this.gp=gp;
    }
    //checkTile for solidEntity
    private void checkTile(SolidEntity entity){
        int leftXEntity= entity.x+entity.solidArea.x;
        int rightXEntity=entity.x+entity.solidArea.x+entity.solidArea.width;
        int topYEntity= entity.y+entity.solidArea.y;
        int bottomYEntity= entity.y+entity.solidArea.y+entity.solidArea.height;

        int Left_Col = leftXEntity/gs.tileSize;
        int Right_Col = rightXEntity/gs.tileSize;
        int Top_Row = topYEntity/gs.tileSize;
        int Bottom_Row = bottomYEntity/gs.tileSize;

        int numTile1_1=0;
        int numTile1_2=0;
        int numTile2_1=0;// this is tile corresponding to tile in map;
        int numTile2_2=0;
        switch (entity.direction){
            case "up":
                Top_Row = (topYEntity-entity.speed)/gs.tileSize;
                numTile1_1=gs.tileM.mapTileNum1[Left_Col][Top_Row];
                numTile1_2=gs.tileM.mapTileNum1[Right_Col][Top_Row];
                numTile2_1=gs.tileM.mapTileNum2[Left_Col][Top_Row];
                numTile2_2=gs.tileM.mapTileNum2[Right_Col][Top_Row];
                break;
            case "down":
                Bottom_Row = (bottomYEntity+entity.speed)/gs.tileSize;
                numTile1_1=gs.tileM.mapTileNum1[Left_Col][Bottom_Row];
                numTile1_2=gs.tileM.mapTileNum1[Right_Col][Bottom_Row];
                numTile2_1=gs.tileM.mapTileNum2[Left_Col][Bottom_Row];
                numTile2_2=gs.tileM.mapTileNum2[Right_Col][Bottom_Row];
                break;
            case  "right":
                Right_Col = (rightXEntity+entity.speed)/gs.tileSize;
                numTile1_1=gs.tileM.mapTileNum1[Right_Col][Bottom_Row];
                numTile1_2=gs.tileM.mapTileNum1[Right_Col][Top_Row];
                numTile2_1=gs.tileM.mapTileNum2[Right_Col][Bottom_Row];
                numTile2_2=gs.tileM.mapTileNum2[Right_Col][Top_Row];

                break;
            case "left":
                Left_Col = (leftXEntity-entity.speed)/gs.tileSize;
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
    private void checkEntity(SolidEntity entity, ArrayList<NPC> target){
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
                    if (entity instanceof Projectile && !t.name.equals("GirlMagician")){
                        if (!t.invincible){
                            t.life--;
                            t.invincible=true;
                            System.out.println("Yes, I var npc");
                        }
                    }
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
    private void checkPlayer(SolidEntity entity){
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
            if (entity instanceof Projectile){
                System.out.println("Yes,I var player");
                t.life--;
            }
        }
        // return origin
        entity.solidArea.x=default_e_x;
        entity.solidArea.y=default_e_y;
        t.solidArea.x=default_t_x;
        t.solidArea.y=default_t_y;
    }
    //check monster whether it take damage from player
    private void Damaged(NPC npc){
        Player player=gs.player;
        int distance= (int) Math.sqrt((player.x-npc.x)*(player.x-npc.x)+(player.y-npc.y)*(player.y-npc.y));
        if(player.attack && !npc.invincible && distance<npc.scale*45){
            if((player.y<npc.y && player.direction.equals("down")) ||
                (player.y<npc.y && player.direction.equals("idle"))||
                (player.y>npc.y && player.direction.equals("up"))  ||
                (player.x>npc.x && player.direction.equals("left"))||
                (player.x<npc.x && player.direction.equals("right"))){
                npc.invincible=true;
                npc.life--;
            }
        }
    }
    //check Event for Player
    private void checkEvent(ArrayList<SuperObject> event){
        Player t = gs.player;
        int defaultX= t.solidArea.x;
        int defaultY= t.solidArea.y;
        for(SuperObject e: event) {
            t.solidArea.x = t.x + t.solidArea.x;
            t.solidArea.y = t.y + t.solidArea.y;
            //
            e.solidArea.x = e.x;
            e.solidArea.y = e.y;
            if (e.solidArea.intersects(t.solidArea)) {
                e.update();
            }
            // return origin
            t.solidArea.x = defaultX;
            t.solidArea.y = defaultY;
            e.solidArea.x = 0;
            e.solidArea.y = 0;
        }
    }
    //check Region that Player can communicate
    private void checkDialog(NPC npc){
        Player player=gs.player;
        int distance= (int) Math.sqrt((player.x-npc.x)*(player.x-npc.x)+(player.y-npc.y)*(player.y-npc.y));
        if (distance < 60 * npc.scale){
            gp.ui.messageOn=true;
        }
        else {
            gp.ui.messageOn=false;
        }
    }
    //general control for all Entity
    public void controlCollision(){
        gs.player.collisionOn=false;
        gs.npc.forEach(e->e.collisionOn=false);
        gs.projectile.forEach(e->{e.collisionOn=false;});
        //Collision for player
        checkTile(gs.player);
        checkEntity(gs.player,gs.npc);
        checkEvent(gs.event);
        //Collision for npc
        for (NPC npc : gs.npc){
            checkTile(npc);
            checkPlayer(npc);
            Damaged(npc);
            if(!npc.isMonster) checkDialog(npc);
        }
        //collision for projectile
        for (Projectile projectile: gs.projectile){
            checkTile(projectile);
            if(projectile.isPlayer){
                checkEntity(projectile,gs.npc);
            }
            else {
                checkPlayer(projectile);
            }
        }
    }
}
