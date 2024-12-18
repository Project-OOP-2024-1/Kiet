package processors;

import main.GamePanel;
import main.GameSetting;
import objects.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

public class KeyHandler implements KeyListener {
    private final HashMap<Integer, Boolean> Key_Set;
    GameSetting gs;
    GamePanel gp;
    private int counter=0;
    public KeyHandler(GameSetting gs,GamePanel gp){
        this.gs = gs;
        this.gp=gp;
        //Set dictionary for key
        Key_Set = new HashMap<>();
        // Set up
        Key_Set.put(KeyEvent.VK_W,false); //"up"
        Key_Set.put(KeyEvent.VK_S,false);//"down"
        Key_Set.put(KeyEvent.VK_D,false);//"right"
        Key_Set.put(KeyEvent.VK_A,false);//"left"
        Key_Set.put(KeyEvent.VK_P,false);//"pause"
        Key_Set.put(KeyEvent.VK_K,false);//"shot"
        Key_Set.put(KeyEvent.VK_L,false);//"attack"
        Key_Set.put(KeyEvent.VK_C,false);//"Character"
        Key_Set.put(KeyEvent.VK_ENTER,false);//for dialog and title screen
    }
    /**
     * Invoked when a key has been typed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key typed event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Invoked when a key has been pressed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key pressed event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(Key_Set.containsKey(code)){
            Key_Set.put(code,true);
        }
        signListener();
    }

    /**
     * Invoked when a key has been released.
     * See the class description for {@link KeyEvent} for a definition of
     * a key released event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(Key_Set.containsKey(code)){
            Key_Set.put(code,false);
        }

    }
    public boolean isPressed(int code){
        return Key_Set.get(code);
    }
    // Listener from KeyBoard
    private void signListener(){
        if (gp.gameState==gp.titleState) {
            if (gp.ui.titleScreenState == 0) {
                if (isPressed(87)) {
                    gp.ui.commandNum--;
                    if (gp.ui.commandNum < 0) {
                        gp.ui.commandNum = 2;
                    }
                }
                if (isPressed(83)) {
                    gp.ui.commandNum++;
                    if (gp.ui.commandNum > 2) {
                        gp.ui.commandNum = 0;
                    }
                }
                if (isPressed(10)) {
                    if (gp.ui.commandNum == 0) {
                        gp.ui.titleScreenState = 1;
                    }
                    if (gp.ui.commandNum == 1) {
                        gp.ui.titleScreenState = 2;
                    }
                    if (gp.ui.commandNum == 2) {
                        System.exit(0);
                    }
                }
            } else if (gp.ui.titleScreenState == 1) {
                if (isPressed(87)) {
                    gp.ui.commandNum--;
                    if (gp.ui.commandNum < 0) {
                        gp.ui.commandNum = 2;
                    }
                }
                if (isPressed(83)) {
                    gp.ui.commandNum++;
                    if (gp.ui.commandNum > 1) {
                        gp.ui.commandNum = 0;
                    }
                }
                if (isPressed(10)) {
                    if (gp.ui.commandNum == 0) {
                        gp.gameState = gp.playState;
                        //                        gp.playMusic(0);
                    }
                    if (gp.ui.commandNum == 1) {
                        gp.ui.titleScreenState = 2;
                    }
                }
            }
            else if (gp.ui.titleScreenState==2) {
                if (isPressed(10)) {
                    gp.gameState = gp.playState;
                }
            }
        }
        else if (gp.gameState==gp.playState) {
            ArrayList<Integer> delete= new ArrayList<>();//thao tac xoa
            if (isPressed(80)){
                gp.gameState=gp.pauseState;
            }
            if (isPressed(67)){
                gp.gameState=gp.characterState;
            }
            if (gp.ui.messageOn && isPressed(10)){
                gp.gameState=gp.dialogueState;
            }
            else if (isPressed(10)){
                for (int i=0;i<gs.event.size();i++){
                    if(gs.event.get(i).name.equals("Mushroom") && gs.event.get(i).eventOn){
                        gs.player.inventory.add(new Mushroom(gs,16,16));
                        gp.ui.addMessage("You earn mushroom!");
                        delete.add(i);
                    }
                }
                for (int j :delete){
                    gs.event.remove(j);
                }
                delete.clear();
            }
        }
        else if (gp.gameState==gp.pauseState){
            if (isPressed(80)){
                gp.gameState=gp.playState;
            }
        }
        else if(gp.gameState== gp.characterState){
            if (isPressed(67)){
                gp.gameState=gp.playState;
            }
            if(isPressed(87)){
                if(gp.ui.slotRow != 0){
                    gp.ui.slotRow--;
                }
            }
            if(isPressed(65)){
                if(gp.ui.slotCol != 0){
                    gp.ui.slotCol--;
                }
            }
            if(isPressed(83)){
                if(gp.ui.slotRow != 3){
                    gp.ui.slotRow++;
                }
            }
            if(isPressed(68)){
                if(gp.ui.slotCol != 4) {
                    gp.ui.slotCol++;
                }
            }
            if (isPressed(10)){
                int indexItems = gp.ui.getItemIndexOnSlot();//take index of Items in inventory
                if(indexItems < gs.player.inventory.size()){
                    for(SuperObject event:gs.event){
                        if(event.name.equals("HealingPool") && event.eventOn){
                            if(gs.player.inventory.get(indexItems) instanceof Potion){
                                gs.player.inventory.remove(indexItems);
                                gs.player.inventory.add(new Potion(gs,"Potion",1,16,16));
                                gp.ui.addMessage("Pour water successfully!");
                            }
                        }
                        if(event.name.equals("TransitionGate") && event.eventOn && !event.alive){
                            if(gs.player.inventory.get(indexItems) instanceof Fragment){
                                event.alive=true;
                                gs.player.inventory.remove(indexItems);
                            }
                        }
                    }
                    if(gs.player.inventory.get(indexItems) instanceof Reward && gp.ui.messageOn){
                        gp.ui.pushItems(gs.player.inventory.get(indexItems).name);
                        gs.player.inventory.remove(indexItems);
                    }
                    else if(gs.player.inventory.get(indexItems) instanceof Potion ){
                        if(((Potion) gs.player.inventory.get(indexItems)).thingStatus==1 && gp.ui.messageOn){
                            gp.ui.pushItems("Water"+gs.player.inventory.get(indexItems).name);
                            gs.player.inventory.remove(indexItems);
                        }
                        else if(((Potion) gs.player.inventory.get(indexItems)).thingStatus==2) {
                            if(gs.player.life<gs.player.maxLife){
                                gs.player.life=gs.player.maxLife;
                                gs.player.inventory.remove(indexItems);
                                gp.ui.addMessage("Heal!");
                            }
                        }
                    }
                    else if(gs.player.inventory.get(indexItems) instanceof Mushroom && gp.ui.messageOn){
                        gp.ui.pushItems(gs.player.inventory.get(indexItems).name);
                        gs.player.inventory.remove(indexItems);
                    }
                }
            }
        }
        else if(gp.gameState==gp.dialogueState){
            if(isPressed(10)){
                gp.ui.messageCounter++;
            }
        }

    }
    public void controlPlayer(){
        if (isPressed(76) && !gs.player.attack) gs.player.attack=true;
        if (isPressed(87)) gs.player.direction = "up";
        else if (isPressed(83)) gs.player.direction = "down";
        else if (isPressed(68)) gs.player.direction = "right";
        else if (isPressed(65)) gs.player.direction = "left";
        else gs.player.direction= "idle";
        counter++;
        if(counter>20){
            if (isPressed(75) && !gs.player.projectile.alive){
                //set default
                gs.player.projectile.set(gs.player.x,gs.player.y,gs.player.direction,true,true,8);
                //add to list
                gs.projectile.add(gs.player.projectile);
            }
            counter=0;
        }
    }

}
