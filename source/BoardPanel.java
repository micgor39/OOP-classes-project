import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class BoardPanel extends JPanel implements ActionListener {

    private final int DELAY = 10;
    private final int PERIOD = 4000 / DELAY;
    private Timer timer;
    private Hero heroA, heroB;
    private MyImage leftLife, rightLife, life, background;
    private MyImage number[] = new MyImage[4];
    private List < FallingLife > fallingLives;
    private int LIFE_WIDTH;
    private int no, playerA, playerB, scoreA, scoreB;
    private boolean bot;
    private Timer timer2, timer3;
    private int wait, counter;
    private boolean finished = false;
    private Random gen;

    public BoardPanel(int no, int playerA, int playerB, int scoreA, int scoreB, boolean bot) {
        
        this.no = no;
        this.playerA = playerA;
        this.playerB = playerB;
        this.scoreA = scoreA;
        this.scoreB = scoreB;
        this.bot = bot;

        wait = 0;
        counter = 0;
        fallingLives = new ArrayList < FallingLife >();

        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.BLACK);
        setDoubleBuffered(true);

        gen = new Random();

        heroA = new Hero(playerA, GameConst.FRAME_WIDTH / 4 + rand(), GameConst.FRAME_HEIGHT / 2 + rand(), 1);
        heroB = new Hero(playerB, GameConst.FRAME_WIDTH * 3 / 4 + rand(), GameConst.FRAME_HEIGHT / 2 + rand(), -1);

        loadImages(no);
        
        timer = new Timer(DELAY, this);
        timer.start();

        timer2 = new Timer(500, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                wait++;
                if(wait == 6) timer2.stop();
            }});
        timer2.start();
    }

    private int rand() {
        return gen.nextInt() % 200;
    }

    private void loadImages(int no) {
        
        life = new MyImage("heart.png");
        leftLife = new MyImage("left-heart.png");
        rightLife = new MyImage("right-heart.png");

        LIFE_WIDTH = life.getWidth();

        background = new MyImage("background" + no + ".png");

        for(int i = 0; i < 4; i++) {
            number[i] = new MyImage("number" + i + ".png");
        }
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D graphics = (Graphics2D)g;
        
        graphics.drawImage(background.getImage(), 0, 0, this);
        graphics.setColor(new Color(20, 20, 20, 100));
        graphics.fillRect(0, 0, GameConst.FRAME_WIDTH, GameConst.FRAME_HEIGHT);

        for(FallingLife life: fallingLives) {
            graphics.drawImage(life.getImage(), life.getX(), life.getY(), this);
        }

        List < Bullet > bullets = heroA.getBullets();
        for (Bullet bullet: bullets) {
            graphics.drawImage(bullet.getImage(), bullet.getX(), bullet.getY(), this);
        }   

        bullets = heroB.getBullets();
        for (Bullet bullet: bullets) {
            graphics.drawImage(bullet.getImage(), bullet.getX(), bullet.getY(), this);
        }   

        graphics.drawImage(heroA.getImage(), heroA.getX(), heroA.getY(), heroA.getWidth(), heroA.getHeight(), this);
        graphics.drawImage(heroB.getImage(), heroB.getX(), heroB.getY(), heroB.getWidth(), heroB.getHeight(), this);

        int py = 10, px = 10;
        for(int i = 0; i < heroA.getHP() / 2; i++) {
            graphics.drawImage(life.getImage(), px, py, this);
            px += LIFE_WIDTH;
        }
        if(heroA.getHP() % 2 == 1) {
            graphics.drawImage(leftLife.getImage(), px, py, this);
        }

        px = GameConst.FRAME_WIDTH - 10 - LIFE_WIDTH;
        for(int i = 0; i < heroB.getHP() / 2; i++) {
            graphics.drawImage(life.getImage(), px, py, this);
            px -= LIFE_WIDTH;
        }
        if(heroB.getHP() % 2 == 1) {
            graphics.drawImage(rightLife.getImage(), px + LIFE_WIDTH / 2, py, this);
        }

        int len = 80;
        graphics.drawImage(number[scoreA].getImage(), GameConst.FRAME_WIDTH / 2 - len - 10, py, len, len, this);
        graphics.drawImage(number[scoreB].getImage(), GameConst.FRAME_WIDTH / 2 + 10, py, len, len, this);
        graphics.setColor(Color.WHITE);
        graphics.fillRect(GameConst.FRAME_WIDTH / 2 - 5, py + len / 2 - 3, 10, 6);

        MyImage leftButton = new MyImage("button.png");
        MyImage rightButton = new MyImage("buttonm.png");

        if(heroA.getSuperpower() == 1) {
            graphics.drawImage(leftButton.getImage(), 20, GameConst.FRAME_HEIGHT - 150, this);
        }
        if(heroA.getSuperpower() == 2) {
            graphics.drawImage(leftButton.getImage(), 30, GameConst.FRAME_HEIGHT - 140, 80, 80, this);
        }

        if(heroB.getSuperpower() == 1) {
            graphics.drawImage(rightButton.getImage(), GameConst.FRAME_WIDTH - 120, GameConst.FRAME_HEIGHT - 150, this);
        }
        if(heroB.getSuperpower() == 2) {
            graphics.drawImage(rightButton.getImage(), GameConst.FRAME_WIDTH - 110, GameConst.FRAME_HEIGHT - 140, 80, 80, this);
        }

        if(wait < 6) {
            
            if(!finished) {
            
                graphics.setColor(new Color(20, 20, 20, 150));
                graphics.fillRect(0, 0, GameConst.FRAME_WIDTH, GameConst.FRAME_HEIGHT);
            
                if(wait % 2 == 0) {

                    int nr = 3 - wait / 2, len2 = 150;
                    MyImage im = new MyImage("number" + nr + ".png");
                    graphics.drawImage(im.getImage(), GameConst.FRAME_WIDTH / 2 - len2 / 2, GameConst.FRAME_HEIGHT / 2 - len2 / 2, this);
                }
            } else {

                graphics.setColor(new Color(20, 20, 20, 150));
                graphics.fillRect(0, 0, GameConst.FRAME_WIDTH, GameConst.FRAME_HEIGHT);
            
                if(wait % 2 == 0) {

                    MyImage im = new MyImage("gameover.png");
                    graphics.drawImage(im.getImage(), GameConst.FRAME_WIDTH / 2 - im.getWidth() / 2, GameConst.FRAME_HEIGHT / 2 - im.getHeight() / 2, this);
                }
            }
        }

        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        counter++;
        
        if(counter == PERIOD) {
        
            counter = 0;
            fallingLives.add(new FallingLife());
        }

        updateHeroes();
        
        updateLives();

        updateBullets(heroA.getBullets());
        updateBullets(heroB.getBullets());
        
        updateHits();
        
        if(heroA.getHP() <= 0 && heroB.getHP() <= 0) {
            gameOver(0);
        } else if(heroA.getHP() <= 0) {
            gameOver(1);
        } else if(heroB.getHP() <= 0) {
            gameOver(-1);
        }
        
        if(heroA.getX() <= heroB.getX()) {
            heroA.setDir(1);
            heroB.setDir(-1);
        } else {
            heroA.setDir(-1);
            heroB.setDir(1);
        }

        repaint();
    }

    private void colseFrame() {

        new GameOverFrame(heroA, heroB, scoreA, scoreB).setVisible(true);
        JFrame frame = (JFrame)SwingUtilities.getWindowAncestor(this);
        frame.setVisible(false);
        frame.dispose();
    }

    private void gameOver(int state) {

        timer.stop();

        if(state == 0) {
            scoreA++;
            scoreB++;
        } else if(state == 1) {
            scoreB++;
        } else {
            scoreA++;
        }

        if(scoreA == 3 || scoreB == 3) {

            wait = 0;
            finished = true;

            repaint();

            timer3 = new Timer(500, new ActionListener() {
                
                public void actionPerformed(ActionEvent evt) {
                
                    wait++;
                
                    if(wait == 6) {
                        timer3.stop();
                        colseFrame();
                        return;
                    }

                    repaint();
                }
            });
            timer3.start();

        } else {

            new BoardFrame(no, playerA, playerB, scoreA, scoreB, bot).setVisible(true);
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.setVisible(false);
            frame.dispose();
        }
    }

    private void updateBullets(List < Bullet > bullets) {

        for (int i = 0; i < bullets.size(); i++) {

            Bullet bullet = bullets.get(i);

            if (bullet.isVisible()) {
                bullet.move();
            } else {
                bullets.remove(i);
            }
        }
    }

    private int min(int a, int b) {
        return (a < b ? a : b);
    }

    private int max(int a, int b) {
        return (a > b ? a : b);
    }

    private boolean intersection(int a, int b, int c, int d, int x, int y, int z, int t) {
        return max(a, x) <= min(b, y) && max(c, z) <= min(d, t);
    }

    private void updateLives() {

        for(FallingLife life: fallingLives) {
            
            int a = life.getX(), b = a + life.getWidth(), c = life.getY(), d = c + life.getHeight();
            int x1 = heroA.getX(), y1 = x1 + heroA.getWidth(), z1 = heroA.getY(), t1 = z1 + heroA.getHeight();
            int x2 = heroB.getX(), y2 = x2 + heroB.getWidth(), z2 = heroB.getY(), t2 = z2 + heroB.getHeight();

            if(counter % 2 == 0) {
                
                if(intersection(a, b, c, d, x1, y1, z1, t1)) {
                    heroA.takeLife();
                    life.setVisible(false);
                    continue;
                }

                if(intersection(a, b, c, d, x2, y2, z2, t2)) {
                    heroB.takeLife();
                    life.setVisible(false);
                    continue;
                }
            } else {

                if(intersection(a, b, c, d, x2, y2, z2, t2)) {
                    heroB.takeLife();
                    life.setVisible(false);
                    continue;
                }

                if(intersection(a, b, c, d, x1, y1, z1, t1)) {
                    heroA.takeLife();
                    life.setVisible(false);
                    continue;
                }
            }
        }

        for(int i = 0; i < fallingLives.size(); i++) {

            FallingLife life = fallingLives.get(i);

            if (life.isVisible()) {
                life.move();
            } else {
                fallingLives.remove(i);
            }
        }
    }

    private void updateHeroes() {

        heroA.move();
        heroB.move();
    }

    private void updateHits() {

        List < Bullet > bullets = heroA.getBullets();
        
        for(Bullet bullet: bullets) {
        
            int px = bullet.getX() + bullet.getPX();
            int py = bullet.getY() + bullet.getPY();
        
            if(heroB.getX() <= px && px <= heroB.getX() + heroB.getWidth()
            && heroB.getY() <= py && py <= heroB.getY() + heroB.getHeight()) {
        
                heroB.hit(bullet.getDamage());
                bullet.setVisible(false);
            }
        }

        bullets = heroB.getBullets();
        
        for(Bullet bullet: bullets) {
        
            int px = bullet.getX() + bullet.getPX();
            int py = bullet.getY() + bullet.getPY();
        
            if(heroA.getX() <= px && px <= heroA.getX() + heroA.getWidth()
            && heroA.getY() <= py && py <= heroA.getY() + heroA.getHeight()) {
        
                heroA.hit(bullet.getDamage());
                bullet.setVisible(false);
            }
        }
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            
            if(wait < 6) return;

            heroA.keyReleased(e);
            heroB.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {

            if(wait < 6) return;

            heroA.keyPressed(e);
            heroB.keyPressed(e);
        }
    }
}