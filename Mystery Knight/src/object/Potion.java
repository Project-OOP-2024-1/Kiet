<<<<<<< HEAD
package object;

import main.GamePanel;
import sprite.SpriteSheet;
import entity.Inanimated;
import java.awt.Rectangle;

public class Potion extends Inanimated {

    public Potion(GamePanel gp) {        
        super(gp);
        name = "Potion";
        description = "[" + name + "]\nIt is a cure potion";
        solidRegion = new Rectangle(4,0,32,32);
        collision = true;
        alive = false;     
        image = getImage("/objects/Potion.png");
    }
=======
package object;

import main.GamePanel;
import sprite.SpriteSheet;
import entity.Inanimated;
import java.awt.Rectangle;

public class Potion extends Inanimated {

    public Potion(GamePanel gp) {        
        super(gp);
        name = "Potion";
        description = "[" + name + "]\nIt is a cure potion";
        solidRegion = new Rectangle(4,0,32,32);
        collision = true;
        alive = false;     
        image = getImage("/objects/Potion.png");
    }
>>>>>>> 710ca1fc1349553dd06c31d12a68e98e4e65ada8
}