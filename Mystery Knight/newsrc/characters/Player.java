package characters;

import entity.DeathAnimation;
import entity.SolidEntity;
import main.GameSetting;
import objects.Object;
import processor.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends SolidEntity implements DeathAnimation {
    public int screenX;
    public int screenY;
    private BufferedImage[] rightAttack,leftAttack,upAttack,downAttack;
    public boolean attack;
    private int deathSprite=0;
    private int numDeath=0;
    private int attackSprite=0;
    private int numAttack=0;
    private int invincibleCounter =0;
    public Projectile projectile;
    public ArrayList<Object> inventory=new ArrayList<>();
    public Player(GameSetting gs,int x,int y) {
        super(gs);
        this.gs=gs;
        name="Player";
        this.x=x;
        this.y=y;
        this.speed=3;
        maxLife=6;
        life=maxLife;
        frameCount=4;
        direction="idle";
        solidArea=new Rectangle(8,20,32,28);
        screenX = gs.screenWidth/2 - (gs.tileSize/2);
        screenY = gs.screenHeight/2 - (gs.tileSize/2);
        attack=false;
        projectile=new Projectile(gs,"FireBall",gs.originalTileSize,gs.originalTileSize,3,80);
        getImage(name,gs.originalTileSize,gs.originalTileSize);
    }

    @Override
    protected void getImage(String name, int width, int height) {
        super.getImage(name, width, height);
        //Attack left right
        SpriteSheet sheet1 = new SpriteSheet("/characters/"+name+".png", width*2, height);
        rightAttack= new BufferedImage[frameCount];
        leftAttack= new BufferedImage[frameCount];
        upAttack= new BufferedImage[frameCount];
        downAttack= new BufferedImage[frameCount];

        for (int i = 0; i < frameCount; i++) {
            rightAttack[i] = sheet1.getSprite(i,10 ); // Extract the sprites
            leftAttack[i] = sheet1.getSprite(i, 11);
        }
        //up and down
        SpriteSheet sheet2 = new SpriteSheet("/characters/"+name+".png", width, height*2);
        for (int i = 0; i < frameCount; i++) {
            downAttack[i] = sheet2.getSprite(i, 3); // Extract the sprites
            upAttack[i] = sheet2.getSprite(i, 4);
        }
    }

    @Override
    public void update() {
        collisionOn=false;
        gs.collisionChecker.checkTile(this);
        gs.collisionChecker.checkEntity(this,gs.npc);
        if(!collisionOn && !attack){
            switch (direction){
                case "up":  y -= speed;break;
                case "down": y+= speed;break;
                case  "right": x+=speed;break;
                case  "left" : x-=speed;break;
            }
        }
        //Sprite when walk
        counterSprite++;
        if (counterSprite > 20){
            numSprite++;
            if (numSprite>frameCount-1) numSprite = 0;
            counterSprite=0;
        }
        //Sprite when attack
        if (attack){
            attackSprite++;
            if (attackSprite>5){
                numAttack++;
                if (numAttack>frameCount-1) {
                    numAttack=0;
                    attack=false;
                }
                attackSprite=0;
            }
        }
        //Sprite when invincible
        if (invincible){
            invincibleCounter++;
            if (invincibleCounter >60) {
                invincible = false;
                invincibleCounter =0;
            }
        }
        if(life<=0){
            updateDeath();
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        if (!attack){
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
        }else {
            switch (direction) {
                case "right":
                    image = rightAttack[numAttack];
                    g2.drawImage(image, screenX, screenY, gs.tileSize*2, gs.tileSize, null);
                    break;
                case "left":
                    image = leftAttack[numAttack];
                    g2.drawImage(image, screenX-gs.tileSize, screenY, gs.tileSize*2, gs.tileSize, null);
                    break;
                case "down", "idle":
                    image = downAttack[numAttack];
                    g2.drawImage(image, screenX, screenY, gs.tileSize, gs.tileSize*2, null);
                    break;
                case "up":
                    image = upAttack[numAttack];
                    g2.drawImage(image, screenX, screenY-gs.tileSize, gs.tileSize, gs.tileSize*2, null);
                    break;
            }
        }
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
