package main;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

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
    private int scenarioState=0;
    private String currentDialogue;
    private final ArrayList<String> message;
    private final ArrayList<Integer> messCount;
    public boolean messageOn=false;
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
        //Scrolling message
        drawMessage();
    }

    private void drawInventory() {
        int frameX = gs.tileSize*9;
        int frameY = gs.tileSize;
        int frameWidth = gs.tileSize*6;
        int frameHeight = gs.tileSize*5;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //SLOT
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gs.tileSize + 3;

        //DrawPlayerItems
        for(int i = 0; i < gs.player.inventory.size(); i ++) {
            g2.drawImage(gs.player.inventory.get(i).image, slotX, slotY, gs.tileSize, gs.tileSize, null);
            slotX += slotSize;
            if (i == 4 || i == 9 || i == 14) {
                slotX = slotXstart;
                slotY += slotSize;
            }

        }

        // CURSOR
        int cursorX = slotXstart + (slotSize * slotCol);
        int cursorY = slotYstart + (slotSize * slotRow);
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

    private void setContent() {
        if (scenarioState==0){
            switch (messageCounter){
                case 1: currentDialogue="Old village:\nGood morning Young Knight!";break;
                case 2: currentDialogue="Knight:\nYeah,Good morning!";break;
                case 3: currentDialogue="Knight:\nWhat is happening?\nWhere are everybody?";break;
                case 4: currentDialogue="Old village:\nSome of they have luckily escaped, others \nare killed by that monster.....";break;
                case 5: currentDialogue="Knight:\nHuh...";break;
                case 6: currentDialogue="Knight:\nThey are killed.....";break;
                case 7: currentDialogue="Knight:\nWhere....where..is..that monster???";break;
                case 8: currentDialogue="Knight:\nGru....h.h!";break;
                case 9: currentDialogue="Old village:\nYour power now is not sufficient.You may\n be killed";break;
                case 10:currentDialogue="Old village:\nHold on!";break;
                case 11:currentDialogue="Knight:\nWhy are you still here?. Why don't you \nfollow to other people?";break;
                case 12:currentDialogue="Old village:\nI am a wizard.\nI know you will come here!";break;
                case 13:currentDialogue="Old village:\nYou are strong but not enough..";break;
                case 14:currentDialogue="Old village:\nDon't worry..I will teach you a special\n skill!";break;
                case 15:currentDialogue="Old village:\nYou move to South and you will see slimes\n and shits";break;
                case 16:currentDialogue="Old village:\nKill them and give me the reward!";break;
                case 17:currentDialogue="                     Mission unlock!\nGather 3 slime hear and 2 stone";break;
                case 18:messageCounter=0;scenarioState=1;gp.gameState= gp.playState;break;
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
}
