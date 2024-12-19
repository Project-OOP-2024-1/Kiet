package main;

import characters.NPC;
import objects.*;
import objects.Object;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import static java.awt.Font.createFont;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    GameSetting gs;
    Font DroidSans;//Font using for all screen
    //Value Setting for Controlling
    public int slotCol=0;
    public int slotRow=0;
    public int titleScreenState=0;
    public int commandNum=0;
    public int messageCounter = 1;
    public boolean messageOn=false;
    //
    private int scenarioState=0;
    private String currentDialogue;
    private final ArrayList<String> message;
    private final ArrayList<Integer> messCount;
    private final HashMap<String,Integer> countItems = new HashMap<>();
    //
    public UI(GamePanel gp,GameSetting gs){
        this.gs=gs;
        this.gp=gp;
        message=new ArrayList<>();
        messCount= new ArrayList<>();
        getFont();
    }
    private void getFont(){
        try {
            InputStream is = getClass().getResourceAsStream("/fonts/DroidSans.ttf");
            assert is != null;
            DroidSans = createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }
    public void draw(Graphics2D g2){
        this.g2=g2;//Synchronize to all GamePanel
        //Set value for Font
        g2.setFont(DroidSans);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(Color.white);
        //Scrolling message
        drawMessage();
        //Draw for every gameState
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }
        // PAUSE STATE
        if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }
        // DIALOGUE STATE (More advanced)
        if(gp.gameState == gp.dialogueState){
            setContent();
            drawDialogueScreen();
        }
        //Inventory
        if(gp.gameState==gp.characterState){
            drawCharacterScreen();
            drawInventory();
        }
        //GameOver
        if(gp.gameState==gp.gameOverState){
            drawGameOverScreen();
        }
        //Ending
        if(gp.gameState==gp.Ending){
            drawEnding();
        }
    }

    private void drawEnding() {
        g2.setColor(new Color(0,0,0,150));
        g2.fillRect(0, 0, gs.screenWidth, gs.screenHeight);

        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,80f));

        text = "You are Winner!";
        x = getXForCenteredText(text);
        y = gs.tileSize*4;
        //Shadow
        g2.setColor(Color.black);
        g2.drawString(text, x, y);
        //Main
        g2.setColor(Color.white);
        g2.drawString(text, x-4, y-4);
        g2.setFont(g2.getFont().deriveFont(40f));
        text = "Quit";
        x = getXForCenteredText(text);
        y += gs.tileSize*4;
        g2.drawString(text, x, y);
        if(commandNum == 0) {
            g2.drawString(">", x-40, y);
        }
    }

    private void drawInventory() {
        int frameX = gs.tileSize*9;
        int frameY = gs.tileSize;
        int frameWidth = gs.tileSize*6;
        int frameHeight = gs.tileSize*5;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //SLOT
        final int slotXStart = frameX + 20;
        final int slotYStart = frameY + 20;
        int slotX = slotXStart;
        int slotY = slotYStart;
        int slotSize = gs.tileSize + 3;

        //DrawPlayerItems
        for(int i = 0; i < gs.player.inventory.size(); i ++) {
            g2.drawImage(gs.player.inventory.get(i).image, slotX, slotY, gs.tileSize, gs.tileSize, null);
            slotX += slotSize;
            if (i == 4 || i == 9 || i == 14) {
                slotX = slotXStart;
                slotY += slotSize;
            }

        }

        // CURSOR
        int cursorX = slotXStart + (slotSize * slotCol);
        int cursorY = slotYStart + (slotSize * slotRow);
        int cursorWidth = gs.tileSize;
        int cursorHeight = gs.tileSize;

        // DRAW CURSOR
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

        // DESCRIPTION FRAME
        int dFrameY = frameY + frameHeight;
        int dFrameHeight = gs.tileSize*3;
        drawSubWindow(frameX,dFrameY, frameWidth,dFrameHeight);

        // DRAW DESCRIPTION TEXT
        int textX = frameX + 20;
        int textY = dFrameY + gs.tileSize;
        g2.setFont(g2.getFont().deriveFont(28F));

        int  itemIndex = getItemIndexOnSlot();
        if (itemIndex < gs.player.inventory.size()) {
            for (String line: gs.player.inventory.get(itemIndex).description.split("\n")) {

                g2.drawString(line, textX, textY);
                textY += 32;
            }
        }
    }

    private void drawMessage() {
        int messageX = gs.tileSize;
        int messageY = gs.tileSize*4;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,28F));
        int i=0;
        for (String text : message ){
            g2.setColor(Color.black);
            g2.drawString(text,messageX+2,messageY+2);

            g2.setColor(Color.white);
            g2.drawString(text,messageX,messageY);
            messCount.set(i,messCount.get(i)+1);
            messageY+= 50;
            i++;
        }
        while (!message.isEmpty() && messCount.getFirst()>180){
            message.removeFirst();
            messCount.removeFirst();
        }
    }

    private void drawCharacterScreen() {
        final int frameX = gs.tileSize;
        final int frameY = gs.tileSize;
        final int frameWidth = gs.tileSize*6;
        final int frameHeight = gs.tileSize*6;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
        //Text
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));

        int textX = frameX + 20;
        int textY = frameY + gs.tileSize;
        final int lineHeight = 35;

        g2.drawString("Life", textX, textY);
        textY += lineHeight;
        g2.drawString("Weapons", textX, textY);

        //Values
        int tailX = (frameX + frameWidth) - 30;
        textY = frameY + gs.tileSize;
        String value;

        value = gs.player.life + "/" + gs.player.maxLife;
        textX = getXForAlignToRightText(value,tailX);
        g2.drawString(value, textX, textY);
        textY+=lineHeight;
        value = "Sword";
        textX = getXForAlignToRightText(value,tailX);
        g2.drawString(value, textX, textY);
    }

    private void drawDialogueScreen() {
        //	WINDOW
        int x = gs.tileSize*2;
        int y = gs.tileSize/2;
        int width = gs.screenWidth - (gs.tileSize*4);
        int height = gs.tileSize*3;

        drawSubWindow(x,y,width,height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,28F));
        x += gs.tileSize;
        y += gs.tileSize;

        for(String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 40;
        }
    }

    private void drawGameOverScreen(){
        g2.setColor(new Color(0,0,0,150));
        g2.fillRect(0, 0, gs.screenWidth, gs.screenHeight);

        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,100f));

        text = "Game Over";
        x = getXForCenteredText(text);
        y = gs.tileSize*4;
        //Shadow
        g2.setColor(Color.black);
        g2.drawString(text, x, y);
        //Main
        g2.setColor(Color.white);
        g2.drawString(text, x-4, y-4);

        //Retry
        g2.setFont(g2.getFont().deriveFont(40f));
        text = "Restart";
        x = getXForCenteredText(text);
        y += gs.tileSize*4;
        g2.drawString(text, x, y);
        if(commandNum == 0) {
            g2.drawString(">", x-40, y);
        }

        //Back to the title screen
        text = "Quit";
        x = getXForCenteredText(text);
        y += 55;
        g2.drawString(text, x, y);
        if(commandNum == 1) {
            g2.drawString(">", x-40, y);
        }
    }

    private void setContent() {
        if (scenarioState==0){
            switch (messageCounter){
                case 1: currentDialogue="Girl Magician:\nWelcome to our world!";break;
                case 2: currentDialogue="Knight:\nWhere is it?\nWho are you?";break;
                case 3: currentDialogue="Knight:\nWhat is happening?\nWhere are everybody?";break;
                case 4: currentDialogue="Girl Magician:\nThis is MystWorld!\nI'm a magician!";break;
                case 5: currentDialogue="Girl Magician:\nEverybody escaped because of monster!";break;
                case 6: currentDialogue="Girl Magician:\nAnd now Nobody lived here";break;
                case 7: currentDialogue="Knight:\nWhere....where..is..the monster???";break;
                case 8: currentDialogue="Knight:\nGru....h.h!";break;
                case 9: currentDialogue="Girl Magician:\nYour power now is not sufficient.You may\n be killed";break;
                case 10:currentDialogue="Girl Magician:\nHold on!";break;
                case 11:currentDialogue="Knight:\nWhy are you still here?. Why don't you \nfollow to other people?";break;
                case 12:currentDialogue="Girl Magician:\nI am a wizard.\nI know you will come here!";break;
                case 13:currentDialogue="Girl Magician:\nYou are strong but not enough..";break;
                case 14:currentDialogue="Girl Magician:\nDon't worry..I will teach you a special\n skill!";break;
                case 15:currentDialogue="Girl Magician:\nYou move to South and you will see \nslimes and shits";break;
                case 16:currentDialogue="Girl Magician:\nKill them and give me the reward!";break;
                case 17:currentDialogue="                     Mission unlock!\nGather 3 slime hear and 2 stone";break;
                case 18:
                    gs.player.inventory.add(new Sword(gs,16,16));
                    addMessage("Receive sword!");
                    messageCounter=19;
                case 19:messageCounter=1;scenarioState=1;gp.gameState=gp.playState;pushItems("Slime core");pushItems("Shit core");break;
            }
        }
        if(scenarioState==1){
            currentDialogue="                   Mission Procession\n"+countItems.get("Slime core")+"/3 Slime core\n"+countItems.get("Shit core")+"/2 Shit core";
            if (messageCounter==2 && countItems.get("Slime core")>=3 && countItems.get("Shit core")>=2) {
                gp.gameState = gp.playState;
                countItems.put("Slime core",countItems.get("Slime core")-3);
                countItems.put("Shit core",countItems.get("Shit core")-2);
                scenarioState=2;
                messageCounter = 0;
            }
            else if (messageCounter==2){
                messageCounter=1;
                gp.gameState=gp.playState;
            }
        }
        else if (scenarioState==2){
            switch (messageCounter){
                case 1:currentDialogue="Girl Magician:\nGood job!";break;
                case 2:currentDialogue="Girl Magician:\nIt requires a little bit time!";break;
                case 3:currentDialogue="Girl Magician:\nYeah,Complete!";break;
                case 4:currentDialogue="Girl Magician:\nCome here!";
                case 5:
                    addMessage("Congratulate!");
                    addMessage("You are learning new skill!");//Adding something here
                    addMessage("Throw Stone!");
                    gs.player.inventory.add(new Shield(gs,16,16));
                    messageCounter=1;
                    scenarioState=3;
                    gp.gameState=gp.playState;
                    break;
            }
        }
        else if(scenarioState==3){
            switch (messageCounter){
                case 1:currentDialogue="Girl Magician:\nI will formulate a health portion";break;
                case 2:currentDialogue="Knight:\nReally!!";break;
                case 3:currentDialogue="Girl Magician:\nYeah, but you must gather 8 mushrooms!";break;
                case 4: currentDialogue="Girl Magician:\nHere is it!";break;
                case 5:
                    gs.player.inventory.add(new Mushroom(gs,16,16));
                    addMessage("Receive mushroom!");
                    pushItems("Mushroom");
                    messageCounter=6;
                    break;
                case 6: currentDialogue="                   Mission Procession\n"+countItems.get("Mushroom")+"/8 mushrooms";break;
                case 7:
                    if (countItems.get("Mushroom")>=8){
                        countItems.put("Mushroom",0);
                        messageCounter++;
                    }
                    else{
                        messageCounter=6;
                        gp.gameState=gp.playState;
                    }
                    break;
                case 8, 13:currentDialogue="               Complete mission!";break;
                case 9: currentDialogue="Old village:\nOkay!Now move to lake and take cure \nwater!";break;
                case 10:
                    gs.player.inventory.add(new Potion(gs,"Potion",0,16,16));
                    gs.player.inventory.add(new Potion(gs,"Potion",0,16,16));
                    gs.player.inventory.add(new Potion(gs,"Potion",0,16,16));
                    addMessage("Receive empty bottle");
                    pushItems("WaterPotion");
                    messageCounter=11;
                    break;
                case 11: currentDialogue="                   Mission Procession\n"+countItems.get("WaterPotion")+"/3 water portion";break;
                case 12:
                    if (countItems.get("WaterPotion")>=3){
                        countItems.put("WaterPotion",0);
                        messageCounter++;
                    }
                    else {
                        messageCounter=11;
                        gp.gameState=gp.playState;
                    }
                    break;
                case 14: currentDialogue="Girl Magician:\nTake them!";break;
                case 15:
                    gs.player.inventory.add(new Potion(gs,"Potion",2,16,16));
                    gs.player.inventory.add(new Potion(gs,"Potion",2,16,16));
                    gs.player.inventory.add(new Potion(gs,"Potion",2,16,16));
                    addMessage("Receive cure bottles!");
                    messageCounter=16;
                    break;
                case 16:
                    messageCounter=1;
                    scenarioState=4;
                    gp.gameState=gp.playState;
                    break;
            }
        }
        else if (scenarioState==4){
            switch (messageCounter){
                case 1:currentDialogue="Girl Magician:\nOkay now!Are you ready?";break;
                case 2: currentDialogue="Here is key to activate the gate!";break;
                case 3:
                    gs.player.inventory.add(new Fragment(gs,16,16));
                    addMessage("Receiving the fragment!");
                    messageCounter=4;
                    break;
                case 4:
                    messageCounter=1;
                    gp.gameState=gp.playState;
                    scenarioState=5;
                    break;
            }
        }
        else if(scenarioState==5){
            currentDialogue="Girl Magician:\nGood luck!";
            if(messageCounter==1){
                gp.gameState=gp.playState;
                messageCounter=0;
            }
        }
        else if(scenarioState==6){
            switch (messageCounter){
                case 1:currentDialogue="Ghost:\nI'm waiting for you";break;
                case 2:currentDialogue="Knight:\nYou gotta die!";break;
                case 3:currentDialogue="Knight:\nYou are not welcome here!";break;
                case 4:currentDialogue="Ghost:\nHa ha ha....";break;
                case 5:
                    messageCounter=1;
                    gp.gameState=gp.playState;
                    break;
            }

        }
    }

    private void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80F));
        String text = "PAUSE";
        int x = getXForCenteredText(text);
        int y = gs.screenHeight / 2;
        g2.drawString(text,x,y);
    }

    private void drawTitleScreen() {
        if (titleScreenState == 0) {
            g2.setColor(new Color(0, 0, 0));
            g2.fillRect(0, 0, gs.screenWidth, gs.screenHeight);

            // TITLE NAME
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
            String text = "Mystery Knight";
            int x = getXForCenteredText(text);
            int y = gs.tileSize * 2 ;

            // SHADOW
            g2.setColor(Color.gray);
            g2.drawString(text, x + 5, y + 5);

            // MAIN COLOR
            g2.setColor(Color.white);
            g2.drawString(text, x, y);

            //Image
            x = gs.screenWidth / 2 - (gs.tileSize * 2) / 2;
            y += gs.tileSize*2 ;
            g2.drawImage(gs.player.downSprites[0], x, y, gs.tileSize * 2, gs.tileSize * 2, null);

            //MENU
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 35F));

            text = "NEW GAME";
            x = getXForCenteredText(text);
            y += gs.tileSize * 5;
            g2.drawString(text, x, y);
            if (commandNum == 0) {
                g2.drawString(">", x - gs.tileSize, y);
            }

            text = "TUTORIAL";
            x = getXForCenteredText(text);
            y += gs.tileSize ;
            g2.drawString(text, x, y);
            if (commandNum == 1) {
                g2.drawString(">", x - gs.tileSize, y);
            }

            text = "QUIT";
            x = getXForCenteredText(text);
            y += gs.tileSize ;
            g2.drawString(text, x, y);
            if (commandNum == 2) {
                g2.drawString(">", x - gs.tileSize, y);
            }
        } else if (titleScreenState == 1) {
            //CLASS SELECTION SCREEN
            g2.setColor(new Color(0, 0, 0));
            g2.fillRect(0, 0, gs.screenWidth, gs.screenHeight);
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(42F));

            String text = "Do you want to skip the tutorial?";
            int x = getXForCenteredText(text);
            int y = gs.tileSize * 3;
            g2.drawString(text, x, y);

            text = "Yes";
            x = getXForCenteredText(text);
            y += gs.tileSize * 3;
            g2.drawString(text, x, y);
            if (commandNum == 0) {
                g2.drawString(">", x - gs.tileSize, y);
            }
            text = "No";
            x = getXForCenteredText(text);
            y += (int) (gs.tileSize*1.5);
            g2.drawString(text, x, y);
            if (commandNum == 1) {
                g2.drawString(">", x - gs.tileSize, y);
            }
        }
        if (titleScreenState==2){
            g2.setColor(new Color(0, 0, 0));
            g2.fillRect(0, 0, gs.screenWidth, gs.screenHeight);
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(42F));

            String text = "Press A,W,S,D to move";
            int x = getXForCenteredText(text)-gs.tileSize*3;
            int y = gs.tileSize * 2;
            g2.drawString(text, x, y);

            text = "Press P to Pause";
            y+=gs.tileSize;
            g2.drawString(text,x,y);

            text = "Press L to attack";
            y+=gs.tileSize;
            g2.drawString(text,x,y);

            text = "Press K to shoot";
            y+=gs.tileSize;
            g2.drawString(text,x,y);

            text = "Press C to open inventory";
            y+=gs.tileSize;
            g2.drawString(text,x,y);

            text = "Press Enter to gather, communicate";
            y+=gs.tileSize;
            g2.drawString(text,x,y);

            text = "Play";
            x=getXForAlignToRightText(text,15*gs.tileSize);
            y+=gs.tileSize*4;
            g2.drawString(text,x,y);
            g2.drawString(">", x - gs.tileSize, y);

        }
    }
    //Some useful methods used in UI
    public int getItemIndexOnSlot() {
        return slotCol + (slotRow*5);
    }

    private void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0, 0, 0, 210);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }

    private int getXForCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gs.screenWidth / 2 - length / 2;
    }

    private int getXForAlignToRightText(String text, int tailX) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return tailX - length;
    }

    public void addMessage(String text){
        message.add(text);
        messCount.add(0);
    }
    //This uses for update message
    public void update(){
        for (SuperObject event:gs.event){
            if(event.eventOn){
                event.update();
                if (event.name.equals("TransitionGate")){
                    if(event.alive){
                        addMessage("Transition successfully!");
                        if(!gs.boss){
                            gs.npc.add(new NPC(gs,"Ghost",30,15,45,30,30,3,1,true));
                            gp.gameState=gp.dialogueState;
                            scenarioState=6;
                            gs.boss=true;
                        }
                    }
                    else {
                        event.counterSprite++;
                        if(event.counterSprite>90){
                            addMessage("You need a fragment!");
                            event.counterSprite=0;
                        }
                    }
                }
                if(event.name.equals("HealingPool")){
                    if(gs.player.life<gs.player.maxLife){
                        if(event.counterSprite>39){
                            addMessage("Healing!");
                        }
                    }
                    else {
                        if(event.counterSprite>39){
                            addMessage("Full health!");
                        }

                    }
                }
            }
        }
    }
    //Take
    public void pushItems(String name){
        if(!countItems.containsKey(name)){
            countItems.put(name,0);
        }
        else {
            countItems.put(name,countItems.get(name)+1);
        }
    }
}
