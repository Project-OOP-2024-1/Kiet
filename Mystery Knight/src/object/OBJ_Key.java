//package object;
//
//import entity.Entity;
//import main.GamePanel;
//
//import sprite.SpriteSheet;
//
//public class OBJ_Key extends Entity {
//
//    public OBJ_Key(GamePanel gp) {
//        super(gp);
//        name = "Key";
//        try {
//            SpriteSheet sheet = new SpriteSheet("/objects/key_test.png", gp.originalTileSize, gp.originalTileSize,1,1);
//            image = sheet.getSprite(0, 0);
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
//        collision = true;
//    }
//}