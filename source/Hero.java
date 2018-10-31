import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;

public class Hero extends MovingCharacter implements ActionListener {

    private int no, dir, player, hp, speed;
    private int leftButton = 0, rightButton = 0, upButton = 0, downButton = 0;
    private List < Bullet > bullets;
    private int buttons[][] = { { KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_ALT, KeyEvent.VK_SHIFT },
                                { KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_SLASH, KeyEvent.VK_PERIOD } };
    private int superpower;
    private Timer timer;
    private final int WAIT = 7000;
    private final int LENGTH = 3000;

    public Hero(int no, int x, int y, int dir) {
        
        super(x, y);
        
        this.no = no;
        this.dir = dir;
        
        if(dir == 1) {
            player = 0;
        } else {
            player = 1;
        }

        hp = HeroStats.hp[no];
        speed = HeroStats.speed[no];

        superpower = 0;
        timer = new Timer(WAIT, this);
        timer.setRepeats(false);
        timer.start();

        loadImages("hero" + no, true);

        bullets = new ArrayList < Bullet >();
        setDir(dir);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(superpower == 0) {
            
            superpower = 1;
        }

        if(superpower == 2) {
            
            superpower = 0;
            timer = new Timer(WAIT, this);
            timer.setRepeats(false);
            timer.start();
        } 
    }

    public void setDir(int dir) {
        
        this.dir = dir;
        
        if(this.dir == 1) {
            image = leftImage;
        } else {
            image = rightImage;
        }
    }

    public int getHP() {
        return hp;
    }

    public int getSuperpower() {
        return superpower;
    }

    public void takeLife() {
        
        hp += 2;
        
        if(hp > HeroStats.hp[no]) {
            hp = HeroStats.hp[no];
        }
    }

    public void hit(int damage) {
        hp -= damage;
    }

    public void move() {
        x += speed * rightButton - speed * leftButton;
        y += speed * downButton - speed * upButton;

        int err = 20;

        if(y < 0) {
            y = 0;
        }

        if(y + height > GameConst.FRAME_HEIGHT - err) {
            y = GameConst.FRAME_HEIGHT - height - err;
        }

        if(x < 0) {
            x = 0;
        }

        if(x + width > GameConst.FRAME_WIDTH) {
            x = GameConst.FRAME_WIDTH - width;
        }
    }

    public List < Bullet > getBullets() {
        return bullets;
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == buttons[player][4]) {
            fire();
        }

        if (key == buttons[player][5] && superpower == 1) {
            superpower = 2;
            timer = new Timer(LENGTH, this);
            timer.setRepeats(false);
            timer.start();
        }

        if (key == buttons[player][0]) {
            leftButton = 1;
        }

        if (key == buttons[player][1]) {
            rightButton = 1;
        }

        if (key == buttons[player][2]) {
            upButton = 1;
        }

        if (key == buttons[player][3]) {
            downButton = 1;
        }
    }

    private void fire() {

        if(superpower == 2) {
            bullets.add(new SuperBullet(x + dir * width, y + height / 2 - 10, no, dir));
        } else {
            bullets.add(new Bullet(x + dir * width, y + height / 2 - 10, no, dir));
        }
    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == buttons[player][0]) {
            leftButton = 0;
        }

        if (key == buttons[player][1]) {
            rightButton = 0;
        }

        if (key == buttons[player][2]) {
            upButton = 0;
        }

        if (key == buttons[player][3]) {
            downButton = 0;
        }
    }
}