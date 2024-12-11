package human;

import entity.Animation;

public class Character extends Animation {
    public int maxLife;
    public int x, y;
    public int speed;
    public int life;
    public int counterDeath= 0;
    public int NumDeath = 0;
    public int invincibleCounter=0;
    public boolean damaged;
    public boolean invincible;
    public boolean attack;
    public void update_death(){};
    public void draw_death(){};
}
