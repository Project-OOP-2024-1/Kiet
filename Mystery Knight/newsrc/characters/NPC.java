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
    private int deathSprite=0;
    private int numDeath=0;
    private int invincibleCounter =0;
    public NPC(GameSetting gs, String name,int maxLife, int x, int y, int width, int height, int scale,int speed, boolean isMonster) {
        super(gs);
        this.gs=gs;
        this.name=name;
        this.x=x* gs.tileSize;
        this.y=y*gs.tileSize;
        this.isMonster=isMonster;
        this.speed=speed;
        this.scale=scale;
        alive=true;
        direction="idle";
        frameCount=4;
        if (name.equals("Ghost")) frameCount = 5;
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
            System.out.println(life);
        }
    }
    public void attackByProjectile(){
        //do this
    }
    public void attackByHand(){
        //do this
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
        if(isMonster) gs.collisionChecker.Damaged(this);
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
            g2.setColor(new Color(35,35,35));
            g2.fillRect(screenX-6*scale,screenY-16*scale,maxLife*8*scale,10);
            g2.setColor(new Color(255,0,30));
            g2.fillRect(screenX-6*scale,screenY-16*scale, life*8*scale,10);
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
            if (numDeath>2){
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
