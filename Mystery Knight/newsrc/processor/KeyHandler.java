package processor;

import entity.Entity;
import main.GamePanel;
import main.GameSetting;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

public class KeyHandler implements KeyListener {
    private final HashMap<Integer, Boolean> Key_Set;
    GameSetting gs;
    Entity object;
    public KeyHandler(GameSetting gs){
        this.gs = gs;
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
        Key_Set.put(KeyEvent.VK_ENTER,false);//for dialog and tilte screen
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
}
