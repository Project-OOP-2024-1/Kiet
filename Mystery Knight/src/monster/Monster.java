package monster;

import entity.Character;
import main.updatable;

import java.awt.*;
import java.util.Random;

public abstract class Monster extends Character implements updatable {
    int defaultX=0;
    int defaultY=0;
    public Monster(){
    }
    public void setAction(){
        if(defaultX==0 && defaultY==0){
            defaultX=x;
            defaultY=y;
        }
        if (attack && !collisionOn){
            if (Math.abs(gp.player.x-x) < Math.abs(gp.player.y-y)){
                if (Math.abs(gp.player.x-x)>48) {
                    if (gp.player.x - x > 0) {
                        direction = "right";
                    } else if (gp.player.x - x < 0) {
                        direction = "left";
                    }
                }
                else{
                    if (gp.player.y>y){
                        direction="down";
                    }
                    else {
                        direction="up";
                    }
                }
            }
            else{
                if(Math.abs(gp.player.y-y)>48) {
                    if (gp.player.y > y) {
                        direction = "down";
                    } else if (gp.player.y < y) {
                        direction = "up";
                    }
                }
                else {
                    if (gp.player.x>x){
                        direction="right";
                    }
                    else {
                        direction="left";
                    }
                }
            }
        }
        else{
            if (Math.abs(defaultX-x) < Math.abs(defaultY-y)){
                if (Math.abs(defaultX-x)>48) {
                    if (defaultX - x > 0) {
                        direction = "right";
                    } else  {
                        direction = "left";
                    }
                }
                else{
                    if (defaultY>y){
                        direction="down";
                    }
                    else {
                        direction="up";
                    }
                }
            }
            else{
                if(defaultY-y>24) {
                    if (defaultY> y) {
                        direction = "down";
                    } else {
                        direction = "up";
                    }
                }
                else {
                    if (x<defaultX-gp.tileSize*3){
                        direction="right";
                    }
                    else if(x>defaultX+gp.tileSize*3){
                        direction="left";
                    }
                    else {
                        Random ran = new Random();
                        int num= ran.nextInt(3);
                        switch (num){
                            case 0: direction="right";break;
                            case 1: direction="idle"; break;
                            case 2: direction="left"; break;
                        }
                    }
                }
            }
        }
    }
    @Override
    public void update() {
    }
    @Override
    public void draw(Graphics2D g2) {

    }
}
