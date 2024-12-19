package characters;

import entity.DeathAnimation;
import entity.SolidEntity;
import main.GameSetting;

import java.awt.*;
import java.util.Random;

public class NPC extends SolidEntity implements DeathAnimation {
    public boolean isMonster;//attribute use to determine whether monster or not
    public int counterNPC=0; //Control the action of npc
    public int scale;
    public boolean attack=false;
    private int deathSprite=0;
    private int numDeath=0;
    private int invincibleCounter =0;
    private int defaultX=0,defaultY=0;
    private Projectile projectile=new Projectile(gs,"Nothing",1,1,3,80);
    public NPC(GameSetting gs, String name,int maxLife, int x, int y, int width, int height, int scale,int speed, boolean isMonster) {
        super(gs);
        this.gs=gs;
        this.name=name;
        this.x=x*gs.tileSize;
        this.y=y*gs.tileSize;
        this.isMonster=isMonster;
        this.speed=speed;
        this.scale=scale;
        alive=true;
        direction="idle";
        frameCount=4;
        if (name.equals("Ghost")) {
            frameCount = 5;
            projectile= new Projectile(gs,name,30,30,3,60);
        }
        if(name.equals("Slime")){
            projectile= new Projectile(gs,name,16,16,3,60);
        }
        this.maxLife=maxLife;
        life=maxLife;
        solidArea=new Rectangle(10*scale,20*scale,28*scale,28*scale);//set solidArea for collisionChecker
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
            //
            if (name.equals("Slime") ) {
                attackByProjectile();
            }
            if (name.equals("Shit")) {
                attackByHand();
            }
            if(name.equals("Ghost")){
                attackMix();
            }
        }
    }
    //method attack
    public void attackByProjectile() {
        if (defaultX == 0 && defaultY == 0) {
            defaultX = x;
            defaultY = y;
        }
        Random ran = new Random();
        int choice = ran.nextInt(2);
        //calculate range Attack for monster
        int distanceX = gs.player.x - x;
        int distanceY = gs.player.y - y;
        double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);
        // setAction  for every distance
        if (distance < gs.tileSize * 5) {
            //locate Player in 4 direction
            if (!projectile.alive){
                //set default
                projectile.set(x,y,direction,true,false,4);
                //add to list
                gs.projectile.add(projectile);
            }
            if (distanceX > 0 && distanceY > 0) {//SouthEast
                switch (choice) {
                    case 0:
                        direction = "down";
                        break;
                    case 1:
                        direction = "right";
                        break;
                }
            }
            if (distanceX < 0 && distanceY > 0) {//SouthWest
                switch (choice) {
                    case 0:
                        direction = "down";
                        break;
                    case 1:
                        direction = "left";
                        break;
                }
            }
            if (distanceX > 0 && distanceY < 0) {//NorthEast
                switch (choice) {
                    case 0:
                        direction = "up";
                        break;
                    case 1:
                        direction = "right";
                        break;
                }
            }
            if (distanceX < 0 && distanceY < 0) {//NorthWest
                switch (choice) {
                    case 0:
                        direction = "up";
                        break;
                    case 1:
                        direction = "left";
                        break;
                }
            }
            if(distance<gs.tileSize*2){
                attack=true;
                if(Math.abs(distanceX)<Math.abs(distanceY)){
                    if(distanceY>0){
                        direction="down";
                    }
                    else {
                        direction="up";
                    }
                }
                else {
                    if(distanceX>0){
                        direction="right";
                    }
                    else{
                        direction="left";
                    }
                }
            }
            else {
                attack=false;
            }
        } else {
            attack=false;
            distanceX=defaultX-x;
            distanceY=defaultY-y;
            if(!collisionOn){
                if (distanceX > 0 && distanceY > 0) {//SouthEast
                    switch (choice) {
                        case 0:
                            direction = "down";
                            break;
                        case 1:
                            direction = "right";
                            break;
                    }
                }
                if (distanceX < 0 && distanceY > 0) {//SouthWest
                    switch (choice) {
                        case 0:
                            direction = "down";
                            break;
                        case 1:
                            direction = "left";
                            break;
                    }
                }
                if (distanceX > 0 && distanceY < 0) {//NorthEast
                    switch (choice) {
                        case 0:
                            direction = "up";
                            break;
                        case 1:
                            direction = "right";
                            break;
                    }
                }
                if (distanceX < 0 && distanceY < 0) {//NorthWest
                    switch (choice) {
                        case 0:
                            direction = "up";
                            break;
                        case 1:
                            direction = "left";
                            break;
                    }
                }
            }
            else {
                choice=ran.nextInt(4);
                switch (choice){
                    case 0:direction="up";break;
                    case 1:direction="down";break;
                    case 2:direction="right";break;
                    case 3:direction="left";break;
                }
            }
        }
    }
    public void attackByHand(){
        //do this
        if(defaultX==0 && defaultY==0){
            defaultX=x;
            defaultY=y;
        }
        Random ran = new Random();
        int choice = ran.nextInt(2);
        //calculate range Attack for monster
        int distanceX = gs.player.x - x;
        int distanceY = gs.player.y - y;
        double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);
        // setAction  for every distance
        if (distance < gs.tileSize * 6) {
            //locate Player in 4 direction
            if (distanceX > 0 && distanceY > 0) {//SouthEast
                switch (choice) {
                    case 0:
                        direction = "down";
                        break;
                    case 1:
                        direction = "right";
                        break;
                }
            }
            if (distanceX < 0 && distanceY > 0) {//SouthWest
                switch (choice) {
                    case 0:
                        direction = "down";
                        break;
                    case 1:
                        direction = "left";
                        break;
                }
            }
            if (distanceX > 0 && distanceY < 0) {//NorthEast
                switch (choice) {
                    case 0:
                        direction = "up";
                        break;
                    case 1:
                        direction = "right";
                        break;
                }
            }
            if (distanceX < 0 && distanceY < 0) {//NorthWest
                switch (choice) {
                    case 0:
                        direction = "up";
                        break;
                    case 1:
                        direction = "left";
                        break;
                }
            }
            if(distance<40){
                if(choice==0){
                    if(gs.player.life>0 && !gs.player.invincible){
                        gs.player.invincible=true;
                        gs.player.life--;
                    }
                }
            }
        } else {
            attack=false;
            distanceX=defaultX-x;
            distanceY=defaultY-y;
            if(!collisionOn){
                if (distanceX > 0 && distanceY > 0) {//SouthEast
                    switch (choice) {
                        case 0:
                            direction = "down";
                            break;
                        case 1:
                            direction = "right";
                            break;
                    }
                }
                if (distanceX < 0 && distanceY > 0) {//SouthWest
                    switch (choice) {
                        case 0:
                            direction = "down";
                            break;
                        case 1:
                            direction = "left";
                            break;
                    }
                }
                if (distanceX > 0 && distanceY < 0) {//NorthEast
                    switch (choice) {
                        case 0:
                            direction = "up";
                            break;
                        case 1:
                            direction = "right";
                            break;
                    }
                }
                if (distanceX < 0 && distanceY < 0) {//NorthWest
                    switch (choice) {
                        case 0:
                            direction = "up";
                            break;
                        case 1:
                            direction = "left";
                            break;
                    }
                }
            }
            else {
                choice=ran.nextInt(4);
                switch (choice){
                    case 0:direction="up";break;
                    case 1:direction="down";break;
                    case 2:direction="right";break;
                    case 3:direction="left";break;
                }
            }
        }

    }
    public void attackMix(){
        if (defaultX == 0 && defaultY == 0) {
            defaultX = x;
            defaultY = y;
        }
        Random ran = new Random();
        int choice = ran.nextInt(2);
        //calculate range Attack for monster
        int distanceX = gs.player.x - x;
        int distanceY = gs.player.y - y;
        double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);
        // setAction  for every distance
        if (distance < gs.tileSize * 24) {
            //locate Player in 4 direction
            if (!projectile.alive && choice==0){
                //set default
                projectile.set(x,y,direction,true,false,4);
                //add to list
                gs.projectile.add(projectile);
            }
            if (distanceX > 0 && distanceY > 0) {//SouthEast
                switch (choice) {
                    case 0:
                        direction = "down";
                        break;
                    case 1:
                        direction = "right";
                        break;
                }
            }
            if (distanceX < 0 && distanceY > 0) {//SouthWest
                switch (choice) {
                    case 0:
                        direction = "down";
                        break;
                    case 1:
                        direction = "left";
                        break;
                }
            }
            if (distanceX > 0 && distanceY < 0) {//NorthEast
                switch (choice) {
                    case 0:
                        direction = "up";
                        break;
                    case 1:
                        direction = "right";
                        break;
                }
            }
            if (distanceX < 0 && distanceY < 0) {//NorthWest
                switch (choice) {
                    case 0:
                        direction = "up";
                        break;
                    case 1:
                        direction = "left";
                        break;
                }
            }
            if(distance<gs.tileSize*7){
                attack=true;
                if(Math.abs(distanceX)<Math.abs(distanceY)){
                    if(distanceY>0){
                        direction="down";
                    }
                    else {
                        direction="up";
                    }
                }
                else {
                    if(distanceX>0){
                        direction="right";
                    }
                    else{
                        direction="left";
                    }
                }
            }
            else {
                attack=false;
            }
            if(distanceX< gs.tileSize*scale){
                choice=ran.nextInt(6);
                if(choice==0){
                    if(gs.player.life>0 && !gs.player.invincible){
                        gs.player.invincible=true;
                        gs.player.life--;
                    }
                }
            }
        } else {
            attack=false;
            distanceX=defaultX-x;
            distanceY=defaultY-y;
            if(!collisionOn){
                if (distanceX > 0 && distanceY > 0) {//SouthEast
                    switch (choice) {
                        case 0:
                            direction = "down";
                            break;
                        case 1:
                            direction = "right";
                            break;
                    }
                }
                if (distanceX < 0 && distanceY > 0) {//SouthWest
                    switch (choice) {
                        case 0:
                            direction = "down";
                            break;
                        case 1:
                            direction = "left";
                            break;
                    }
                }
                if (distanceX > 0 && distanceY < 0) {//NorthEast
                    switch (choice) {
                        case 0:
                            direction = "up";
                            break;
                        case 1:
                            direction = "right";
                            break;
                    }
                }
                if (distanceX < 0 && distanceY < 0) {//NorthWest
                    switch (choice) {
                        case 0:
                            direction = "up";
                            break;
                        case 1:
                            direction = "left";
                            break;
                    }
                }
            }
            else {
                choice=ran.nextInt(4);
                switch (choice){
                    case 0:direction="up";break;
                    case 1:direction="down";break;
                    case 2:direction="right";break;
                    case 3:direction="left";break;
                }
            }
        }
    }
    @Override
    public void update() {
        if(!collisionOn && !attack && life>0){
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
        //Sprite when invincible
        if (invincible){
            invincibleCounter++;
            if (invincibleCounter >80) {
                invincible = false;
                invincibleCounter =0;
            }
        }
        if(life<=0){
            updateDeath();
        }
        //Giving action for npc
        counterNPC++;
        if (counterNPC>25){
            setAction();
            counterNPC=0;
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
        //Hp monster
        if(isMonster){
            if(!name.equals("Ghost")){
                g2.setColor(new Color(35,35,35));
                g2.fillRect(screenX-(maxLife*8*scale-48)/2,screenY-16*scale,maxLife*8*scale,10);
                g2.setColor(new Color(255,0,30));
                g2.fillRect(screenX-(maxLife*8*scale-48)/2,screenY-16*scale, life*8*scale,10);
            }
            else {
                g2.setColor(new Color(35,35,35));
                g2.fillRect((gs.screenWidth-maxLife*8*scale)/2,gs.screenHeight-30,maxLife*8*scale,15);
                g2.setColor(new Color(255,0,30));
                g2.fillRect((gs.screenWidth-maxLife*8*scale)/2,gs.screenHeight-30, life*8*scale,15);
                g2.setColor(Color.white);
                g2.drawString(name,(gs.screenWidth-maxLife*8*scale)/2,gs.screenHeight-40);
            }
        }
        if (invincible){
            if (invincibleCounter<20) g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.2f));
            else if (invincibleCounter<40) g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.8f));
            else if (invincibleCounter<60) g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.4f));
            else g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.6f));
        }
        if(life<=0){
            drawDeath(g2);
        }
        g2.drawImage(image, screenX, screenY, gs.tileSize*scale, gs.tileSize*scale, null);
        //reset;
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
    }

    @Override
    public void updateDeath() {
        deathSprite++;
        if (deathSprite>20){
            if (numDeath>frameCount-2){
                numDeath=0;
                alive=false;
            }
            numDeath++;
            deathSprite=0;
        }
    }

    @Override
    public void drawDeath(Graphics2D g2) {
        image=deathSprites[numDeath];
        if (deathSprite>10){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.2f));
        }
        else{
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
        }
    }
}
