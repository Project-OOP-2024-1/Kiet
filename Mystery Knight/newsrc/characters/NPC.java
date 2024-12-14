package characters;

import entity.DeathAnimation;
import entity.SolidEntity;
import main.GameSetting;

import java.awt.*;
import java.util.Random;

public class NPC extends SolidEntity implements DeathAnimation {
    public boolean isMonster;//attribute use to determine whether monster or not
    public int counterNPC = 0; //Control the action of npc;
    public int defaultX = 0;
    public int defaultY = 0;
    public boolean attack;
    public Projectile projectile;
    public NPC(GameSetting gs, String name,int maxLife, int x, int y, int width, int height, int scale,int speed, boolean isMonster) {
        super(gs);
        this.gs=gs;
        this.name=name;
        this.x=x* gs.tileSize;
        this.y=y*gs.tileSize;
        this.isMonster=isMonster;
        this.speed=speed;
        attack = false;
        alive=true;
        direction="idle";
        frameCount=4;
        if (name.equals("Ghost")) frameCount = 5;
        this.maxLife=maxLife;
        life=maxLife;
        solidArea=new Rectangle(10,20,28,28);//set solidArea for collisionChecker
        projectile = new Projectile(gs,name,width,height,scale - 1,80);
        getImage(name,width,height);
    }

    public void setAction(){
        if (!isMonster){
            Random rnd = new Random();
            int action = rnd.nextInt(5);
            switch (action){
                case 0: direction="up";break;
                case 1: direction="down";break;
                case 2: direction="right";break;
                case 3: direction="left";break;
                case 4: direction="idle";break;
            }
        }
        else {
            if (name.equals("Slime") || name.equals("Ghost")) {
                attackByProjectile();
            }
            if (name.equals("Shit")) {
                attackByHand();
            }
        }
    }

    public void attackByProjectile(){
        if(defaultX==0 && defaultY==0){
            defaultX=x;
            defaultY=y;
        }
        if (attack && !collisionOn){
            if (Math.abs(gs.player.x-x) < Math.abs(gs.player.y-y)){
                if (Math.abs(gs.player.x-x)>48) {
                    if (gs.player.x - x > 0) {
                        direction = "right";
                    } else if (gs.player.x - x < 0) {
                        direction = "left";
                    }
                }
                else{
                    if (!projectile.alive){
                        projectile.set(x,y,direction,alive,false,speed);
                        gs.projectileList.add(projectile);
                    }
                    if (gs.player.y>y){
                        direction="down";
                    }
                    else {
                        direction="up";
                    }
                }
            }
            else{
                if(Math.abs(gs.player.y-y)>48) {
                    if (gs.player.y > y) {
                        direction = "down";
                    } else if (gs.player.y < y) {
                        direction = "up";
                    }
                }
                else {
                    if (!projectile.alive){
                        projectile.set(x,y,direction,alive,false,speed);
                        gs.projectileList.add(projectile);
                    }
                    if (gs.player.x>x){
                        direction="right";
                    }
                    else {
                        direction="left";
                    }
                }
            }
        }
        else{
            if (Math.abs(defaultX-x) < Math.abs(defaultY-y)){
                if (Math.abs(defaultX-x)>48) {
                    if (defaultX - x > 0) {
                        direction = "right";
                    } else  {
                        direction = "left";
                    }
                }
                else{
                    if (defaultY>y){
                        direction="down";
                    }
                    else {
                        direction="up";
                    }
                }
            }
            else{
                if(defaultY-y>24) {
                    if (defaultY> y) {
                        direction = "down";
                    } else {
                        direction = "up";
                    }
                }
                else {
                    if (x<defaultX-gs.tileSize*3){
                        direction="right";
                    }
                    else if(x>defaultX+gs.tileSize*3){
                        direction="left";
                    }
                    else {
                        Random ran = new Random();
                        int num= ran.nextInt(3);
                        switch (num){
                            case 0: direction="right";break;
                            case 1: direction="idle"; break;
                            case 2: direction="left"; break;
                        }
                    }
                }
            }
        }
    }

    public void attackByHand(){
        if(defaultX==0 && defaultY==0){
            defaultX=x;
            defaultY=y;
        }
        if (attack && !collisionOn){
            if (Math.abs(gs.player.x-x) < Math.abs(gs.player.y-y)){
                if (Math.abs(gs.player.x-x)>48) {
                    if (gs.player.x - x > 0) {
                        direction = "right";
                    } else if (gs.player.x - x < 0) {
                        direction = "left";
                    }
                }
                else{
                    if (gs.player.y>y){
                        direction="down";
                    }
                    else {
                        direction="up";
                    }
                }
            }
            else {
                if(Math.abs(gs.player.y-y)>48) {
                    if (gs.player.y > y) {
                        direction = "down";
                    } else if (gs.player.y < y) {
                        direction = "up";
                    }
                }
                else {
                    if (gp.player.x>x){
                        direction="right";
                    }
                    else {
                        direction="left";
                    }
                }
            }
        }
        else{
            if (Math.abs(defaultX-x) < Math.abs(defaultY-y)){
                if (Math.abs(defaultX-x)>48) {
                    if (defaultX - x > 0) {
                        direction = "right";
                    } else  {
                        direction = "left";
                    }
                }
                else{
                    if (defaultY>y){
                        direction="down";
                    }
                    else {
                        direction="up";
                    }
                }
            }
            else{
                if(defaultY-y>24) {
                    if (defaultY> y) {
                        direction = "down";
                    } else {
                        direction = "up";
                    }
                }
                else {
                    if (x<defaultX-gs.tileSize*3){
                        direction="right";
                    }
                    else if(x>defaultX+gs.tileSize*3){
                        direction="left";
                    }
                    else {
                        Random ran = new Random();
                        int num= ran.nextInt(3);
                        switch (num){
                            case 0: direction="right";break;
                            case 1: direction="idle"; break;
                            case 2: direction="left"; break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void update() {
        counterNPC++;
        if (counterNPC>25){
            setAction();
            counterNPC=0;
        }
        collisionOn=false;
        gs.collisionChecker.checkTile(this);
        gs.collisionChecker.checkPlayer(this);
        if(!collisionOn){
            switch (direction){
                case "up":  y -= speed;break;
                case "down": y+= speed;break;
                case  "right": x+=speed;break;
                case  "left" : x-=speed;break;
            }
        }
        counterSprite++;
        if (counterSprite > 20){
            numSprite++;
            if (numSprite>frameCount-1) numSprite = 0;
            counterSprite=0;
        }

    }
    public void draw(Graphics2D g2) {
        int screenX = x - gs.player.x + gs.player.screenX;
        int screenY = y - gs.player.y + gs.player.screenY;
        switch (direction) {
            case "right":
                image = rightSprites[numSprite];
                break;
            case "left":
                image = leftSprites[numSprite];
                break;
            case "down":
                image = downSprites[numSprite];
                break;
            case "up":
                image = upSprites[numSprite];
                break;
            case "idle":
                image = idleSprites[numSprite];
                break;
        }
        g2.drawImage(image, screenX, screenY, gs.tileSize, gs.tileSize, null);
    }

    @Override
    public void updateDeath() {

    }

    @Override
    public void drawDeath(Graphics2D g2) {

    }
}
