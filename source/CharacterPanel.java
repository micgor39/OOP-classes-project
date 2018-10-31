import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;

public class CharacterPanel extends JPanel {

    private int x[] = {0, 0}, y[] = {0, 0};
    private boolean ready[] = {false, false};
    private Hero hero[] = new Hero[9];
    private int buttons[][] = { { KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_ALT },
                                { KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_SLASH } };
    private int background;
    private final int gap = 5;

    public CharacterPanel(int background) {

        this.background = background;

        for(int i = 0; i < 9; i++) {
            hero[i] = new Hero(i, 0, 0, 1);
        }

        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.WHITE);
        setDoubleBuffered(true);
    }

    private int getID(int x, int y) {
        return 3 * x + y;
    }

    private void drawHero(Graphics2D graphics, int id, int sx, int sy, int len, int player) {

        if(player == 0) {
            hero[id].setDir(1);
        } else {
            hero[id].setDir(-1);
        }

        double w = hero[id].getWidth(), h = hero[id].getHeight();

        if(w < h)
            graphics.drawImage(hero[id].getImage(), sx + (int)((double)len / 2.0 - w / h / 2.0 * (double)len), sy, (int)(w / h * (double)len), len, null);
        else
            graphics.drawImage(hero[id].getImage(), sx, sy + (int)((double)len / 2.0 - h / w / 2.0 * (double)len), len, (int)(h / w * (double)len), null);
    }

    private void drawLives(Graphics2D graphics, int px, int py, int height, int cnt) {
        
        MyImage life = new MyImage("heart.png");

        int change = height;
        if(cnt > 20) {
            change = 9;
        }

        for(int i = 0; i < cnt / 2; i++) {
            graphics.drawImage(life.getImage(), px, py, height, height, null);
            px += change;
        }

        if(cnt % 2 == 1) {
            MyImage halfLife = new MyImage("left-heart.png");
            graphics.drawImage(halfLife.getImage(), px, py, height / 2, height, null);
        }
    }

    private void drawButtons(Graphics2D graphics, int l, int r, int player, Color myColor) {
        
        Color defaultColor = Color.WHITE;
        Color backgroundColor = new Color(20, 20, 20, 100);

        graphics.setColor(backgroundColor);
        
        int u = gap, d = GameConst.FRAME_HEIGHT - 20 - gap;
        graphics.fillRect(l, u, r - l, d - u);

        int len = (r - l - 4 * gap) / 3;
        int tx = gap, ty = d - 3 * (len + gap);
        for(int i = 0; i < 3; i++) {
            tx = gap;
            for(int j = 0; j < 3; j++) {
                
                if(i == x[player] && j == y[player]) {
                    graphics.setColor(myColor);
                } else {
                    graphics.setColor(defaultColor);
                }
                graphics.fillRect(l + tx, ty, len, len);

                graphics.setColor(backgroundColor);
                graphics.fillRect(l + tx + gap, ty + gap, len - 2 * gap, len - 2 * gap);

                drawHero(graphics, getID(i, j), l + tx + 2 * gap, ty + 2 * gap, len - 4 * gap, player);

                tx += len + gap;
            }
            ty += len + gap;
        }

        int hi = ((d - 3 * (len + gap)) - u - 6 * gap) / 5;
        int len2 = 4 * hi + 3 * gap;
        int qy = u + gap, qx = l + len2 + 2 * gap;

        graphics.setColor(defaultColor);
        graphics.fillRect(l + gap, qy, len2, len2);
        graphics.setColor(myColor);
        graphics.fillRect(l + 2 * gap, qy + gap, len2 - 2 * gap, len2 - 2 * gap);
        graphics.setColor(backgroundColor);
        graphics.fillRect(l + 2 * gap, qy + gap, len2 - 2 * gap, len2 - 2 * gap);
        drawHero(graphics, getID(x[player], y[player]), l + 3 * gap, qy + 2 * gap, len2 - 4 * gap, player);

        int id = getID(x[player], y[player]);

        String text[] = {"Speed: " + HeroStats.speed[id],
                         "Bullet: ",
                         "Bullet speed: " + HeroStats.bulletSpeed[id],
                         "Bullet damage: ",
                         "Lives: "};

        Font font = new Font("Arial", Font.BOLD, 20);

        for(int i = 0; i < 4; i++) {
            
            graphics.setColor(defaultColor);
            graphics.fillRect(qx, qy, r - qx - gap, hi);
            graphics.setColor(myColor);
            graphics.fillRect(qx + gap, qy + gap, r - qx - 3 * gap, hi - 2 * gap);
            graphics.setColor(backgroundColor);
            graphics.fillRect(qx + gap, qy + gap, r - qx - 3 * gap, hi - 2 * gap);
            
            graphics.setColor(defaultColor);
            graphics.setFont(font);
            graphics.drawString(text[i], qx + gap + 9, qy + hi - gap - 9);
            
            if(i == 1) {
                MyImage im = new MyImage("bullet" + getID(x[player], y[player]) + ".png");
                graphics.drawImage(im.getImage(), qx + gap + 80, qy + 2 * gap + 2, this);
            }

            if(i == 3) {
                drawLives(graphics, qx + gap + 160, qy + 2 * gap, hi - 4 * gap, HeroStats.bulletDamage[id]);
            }

            qy += hi + gap;
        }

        graphics.setColor(defaultColor);
        graphics.fillRect(l + gap, qy, r - l - 2 * gap, hi);
        graphics.setColor(myColor);
        graphics.fillRect(l + 2 * gap, qy + gap, r - l - 4 * gap, hi - 2 * gap);
        graphics.setColor(backgroundColor);
        graphics.fillRect(l + 2 * gap, qy + gap, r - l - 4 * gap, hi - 2 * gap);

        graphics.setColor(defaultColor);
        graphics.setFont(font);
        graphics.drawString(text[4], l + 2 * gap + 9, qy + hi - gap - 9);

        drawLives(graphics, l + 2 * gap + 70, qy + 2 * gap, hi - 4 * gap, HeroStats.hp[id]);

        if(ready[player]) {
            graphics.setColor(new Color(0, 0, 0, 100));
            graphics.fillRect(l, u, r - l, d - u);

            int sx = (l + r) / 2, sy = (u + d) / 2;
            Font font2 = new Font("Arial", Font.BOLD, 80);
            graphics.setColor(defaultColor);
            graphics.setFont(font2);
            graphics.drawString("READY", sx - 145, sy);
        }
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D graphics = (Graphics2D)g;
        
        graphics.setColor(new Color(20, 20, 20, 100));
        graphics.fillRect(GameConst.FRAME_WIDTH / 2 - 3, 0, 6, GameConst.FRAME_HEIGHT);

        drawButtons(graphics, gap, GameConst.FRAME_WIDTH / 2 - 3 - gap, 0, Color.BLUE);
        drawButtons(graphics, GameConst.FRAME_WIDTH / 2 + 3 + gap, GameConst.FRAME_WIDTH - gap, 1, Color.RED);

        Toolkit.getDefaultToolkit().sync();
    }

    private void move(KeyEvent e) {
        
        int key = e.getKeyCode();

        for(int player = 0; player < 2; player++) {
            
            // left, right, up, down

            if(key == buttons[player][4]) {
                ready[player] = true;
            }

            if(ready[player]) {
                continue;
            }

            if (key == buttons[player][0] && y[player] > 0) {
                y[player]--;
            }

            if (key == buttons[player][1] && y[player] < 2) {
                y[player]++;
            }

            if (key == buttons[player][2] && x[player] > 0) {
                x[player]--;
            }

            if (key == buttons[player][3] && x[player] < 2) {
                x[player]++;
            }
        }

        if(ready[0] && ready[1]) {
            new BoardFrame(background, getID(x[0], y[0]), getID(x[1], y[1]), 0, 0, false).setVisible(true);
            JFrame frame = (JFrame)SwingUtilities.getWindowAncestor(this);
            frame.setVisible(false);
            frame.dispose();
        } else {
            repaint();
        }
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            move(e);            
        }
    }
}