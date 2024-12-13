package characters;

import entity.DeathAnimation;
import entity.SolidEntity;
import main.GameSetting;
import processor.SpriteSheet;
import processor.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends SolidEntity implements DeathAnimation {
    private final KeyHandler keyH;
    public int screenX;
    public int screenY;
    private BufferedImage[] rightAttack,leftAttack,upAttack,downAttack;
    private boolean attack;
    private int attackSprite=0;
    private int numAttack;
    Projectile projectile;
    public Player(GameSetting gs, KeyHandler keyH,int x,int y) {
        super(gs);
        this.keyH=keyH;
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
        gs.collisionChecker.checkEntity(this,gs.monster);
        if (keyH.isPressed(76) && !attack) attack=true;
        if (keyH.isPressed(87)) direction = "up";
        else if (keyH.isPressed(83)) direction = "down";
        else if (keyH.isPressed(68)) direction = "right";
        else if (keyH.isPressed(65)) direction = "left";
        else direction= "idle";
        if(!collisionOn && !attack){
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
        if (keyH.isPressed(75) && !projectile.alive){
            //set sefault
            projectile.set(x,y,direction,true,true,8);
            //add to list
            gs.projectile.add(projectile);
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

    }

    @Override
    public void drawDeath(Graphics2D g2) {

    }
}
