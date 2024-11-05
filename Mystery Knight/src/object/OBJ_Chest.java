//package object;
//
//import main.GamePanel;
//
//import entity.Entity;
//import sprite.SpriteSheet;
//
//
//public class OBJ_Chest extends Entity {
//
//
//    public OBJ_Chest(GamePanel gp) {
//        super(gp);
//        name = "Chest";
//        try {
//            SpriteSheet sheet = new SpriteSheet("/objects/chest2_closed.png", gp.originalTileSize, gp.originalTileSize,1,1);
//            image = sheet.getSprite(0, 0);
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
//        collision = true;
//    }
//}