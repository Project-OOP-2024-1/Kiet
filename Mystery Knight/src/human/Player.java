package human;

import entity.Character;
import entity.Entity;
import entity.Projectile;
import main.KeyHandler;
import main.SpriteSheet;
import main.updatable;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class Player extends Character implements updatable {
    public int attackSprite = 0;
    private BufferedImage[] rightAttack, leftAttack, upAttack, downAttack;
    public int NumAttack = 0;
    public final int screenX;
    public final int screenY;
    Projectile projectile;
    KeyHandler keyH;
    public ArrayList<Entity> inventory = new ArrayList<>();
    private HashMap<String,Boolean> hasItems;
    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.gp = gp;
        this.keyH = keyH;
        name="player";
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);
        getImage(); // Load the player's sprites
        setDefaultValue();
        setItems();
    }
    public void setDefaultValue() {
        x = gp.tileSize*14 ;
        y = gp.tileSize*12 ;//set Asset
        speed = 4;
        direction = "idle";
        //player status
        maxLife = 6;
        life=6;
        attack=false;
        invincible=false;
        //
        projectile=new OBJ_Fireball(gp);
        solidregion = new Rectangle(8,20,32,28);
        Attackregion= new Rectangle(0,0,36,36);
    }

    //skill
    public void getImage() {

        SpriteSheet sheet = new SpriteSheet("/player/walk.png", gp.originalTileSize, gp.originalTileSize);

        rightSprites = new BufferedImage[frameCount];
        leftSprites = new BufferedImage[frameCount];
        upSprites = new BufferedImage[frameCount];
        downSprites= new BufferedImage[frameCount];
        idleSprites= new BufferedImage[frameCount];

        for (int i = 0; i < frameCount; i++) {
            rightSprites[i] = sheet.getSprite(i,2 ); // Extract the sprites
            leftSprites[i] = sheet.getSprite(i, 3);
            upSprites[i] = sheet.getSprite(i, 1);
            downSprites[i] = sheet.getSprite(i,0 );
            idleSprites[i] = sheet.getSprite(i, 4);
        }
        //Attack left right
        SpriteSheet sheet1 = new SpriteSheet("/player/walk.png", gp.originalTileSize*2, gp.originalTileSize);
        rightAttack= new BufferedImage[frameCount];
        leftAttack= new BufferedImage[frameCount];
        upAttack= new BufferedImage[frameCount];
        downAttack= new BufferedImage[frameCount];

        for (int i = 0; i < frameCount; i++) {
            rightAttack[i] = sheet1.getSprite(i,10 ); // Extract the sprites
            leftAttack[i] = sheet1.getSprite(i, 11);
        }
        //up and down
        SpriteSheet sheet2 = new SpriteSheet("/player/walk.png", gp.originalTileSize, gp.originalTileSize*2);
        for (int i = 0; i < frameCount; i++) {
            downAttack[i] = sheet2.getSprite(i, 3); // Extract the sprites
            upAttack[i] = sheet2.getSprite(i, 4);
        }
    }
    public void update() {
        int coor_x= x/ gp.tileSize;
        int coor_y=y/gp.tileSize;
        System.out.println(coor_x+" "+coor_y);
        if (keyH.isPressed(87)) direction = "up";
        else if (keyH.isPressed(83)) direction = "down";
        else if (keyH.isPressed(68)) direction = "right";
        else if (keyH.isPressed(65)) direction = "left";
        else direction= "idle";
        if (keyH.isPressed(76) && !attack) attack=true;
        collisionOn=false;
        gp.colis.checkTile(this);
        gp.colis.checkObject(this,gp.object);
        gp.colis.checkEntity(this, gp.monster);
        gp.colis.checkEntity(this,gp.npc);
        gp.colis.checkEvent(gp.event);
        if (!collisionOn && !attack){
            switch (direction){
                case "up":  y -= speed;break;
                case "down": y+= speed;break;
                case  "right": x+=speed;break;
                case  "left" : x-=speed;break;
            }
        }
        if (attack){
            attackSprite++;
            if (attackSprite>5){
                NumAttack++;
                if (NumAttack>frameCount-1) {
                    NumAttack=0;
                    attack=false;
                }
                attackSprite=0;
            }
        }


        counterSprite++;
        if (counterSprite > 20){
            numSprite++;
            if (numSprite>frameCount) numSprite = 1;
            counterSprite=0;
        }
        if (invincible){
            invincibleCounter++;
            if (life>0 && invincibleCounter==1) life--;
            if (invincibleCounter>60) {
                invincible = false;
                invincibleCounter=0;
            }
        }
        if (keyH.isPressed(75) && !projectile.alive && hasItems.get(projectile.name)){
            //set sefault
            projectile.set(x,y,direction,true,this);
            //add to list
            gp.projectileList.add(projectile);

        }
    }

    // Draw frame
    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        if (invincible){
            if (invincibleCounter<30){
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.4f));
            }
            else {
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
            }
        }
        if (!attack){
            switch (direction) {
                case "right":
                    image = rightSprites[numSprite - 1];
                    break;
                case "left":
                    image = leftSprites[numSprite - 1];
                    break;
                case "down":
                    image = downSprites[numSprite - 1];
                    break;
                case "up":
                    image = upSprites[numSprite - 1];
                    break;
                case "idle":
                    image = idleSprites[numSprite - 1];
                    break;
            }
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }else {
            switch (direction) {
                case "right":
                    image = rightAttack[NumAttack];
                    g2.drawImage(image, screenX, screenY, gp.tileSize*2, gp.tileSize, null);
                    break;
                case "left":
                    image = leftAttack[NumAttack];
                    g2.drawImage(image, screenX-gp.tileSize, screenY, gp.tileSize*2, gp.tileSize, null);
                    break;
                case "down":
                    image = downAttack[NumAttack];
                    g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize*2, null);
                    break;
                case "up":
                    image = upAttack[NumAttack];
                    g2.drawImage(image, screenX, screenY-gp.tileSize, gp.tileSize, gp.tileSize*2, null);
                    break;
                case "idle":
                    image = downAttack[NumAttack];
                    g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize*2, null);
                    break;
            }
        }
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
    }
    //getStatus
    public boolean has(String items){
        return hasItems.get(items);
    }
    public void setHas(String items,boolean fact){
        hasItems.put(items,fact);
    }


}
