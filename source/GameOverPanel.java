import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import java.util.Random;

public class GameOverPanel extends JPanel {

    private int y = 0;
    private Hero heroA, heroB;
    private int scoreA, scoreB;
    private int buttons[][] = { { KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_ALT },
                                { KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_SLASH } };
    MyImage gameOver, numberA, numberB;

    public GameOverPanel(Hero heroA, Hero heroB, int scoreA, int scoreB) {

        this.heroA = heroA;
        this.heroB = heroB;
        this.scoreA = scoreA;
        this.scoreB = scoreB;

        gameOver = new MyImage("gameover.png");
        numberA = new MyImage("number" + scoreA + ".png");
        numberB = new MyImage("number" + scoreB + ".png");

        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
    }

    private void drawHero(Graphics2D graphics, Hero hero, int sx, int sy, int len, int dir) {

        hero.setDir(dir);

        double w = hero.getWidth(), h = hero.getHeight();

        if(w < h)
            graphics.drawImage(hero.getImage(), sx + (int)((double)len / 2.0 - w / h / 2.0 * (double)len), sy, (int)(w / h * (double)len), len, null);
        else
            graphics.drawImage(hero.getImage(), sx, sy + (int)((double)len / 2.0 - h / w / 2.0 * (double)len), len, (int)(h / w * (double)len), null);
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D graphics = (Graphics2D)g;

        Color defaultColor = Color.WHITE;
        Color backgroundColor = Color.BLACK;

        int gap = 5, gap2 = 30;
        int hi = 120, wi = 400;
        int px = GameConst.FRAME_WIDTH / 2 - wi / 2, py = GameConst.FRAME_HEIGHT - 23 - 2 * gap2 - 2 * hi;
        
        graphics.setColor(defaultColor);
        graphics.fillRect(px, py, wi, hi);
        graphics.setColor(backgroundColor);
        graphics.fillRect(px + gap, py + gap, wi - 2 * gap, hi - 2 * gap);

        Font font = new Font("Arial", Font.BOLD, 60);
        graphics.setColor(new Color(50, 50, 50));
        graphics.setFont(font);
        graphics.drawString("MENU", px + gap + 115, py + hi - gap - 40);
        font = new Font("Arial", Font.BOLD, 60);
        graphics.setColor(defaultColor);
        graphics.setFont(font);
        graphics.drawString("MENU", px + gap + 110, py + hi - gap - 35);
        if(y == 1) {
            graphics.setColor(new Color(0, 0, 0, 230));
            graphics.fillRect(px + gap, py + gap, wi - 2 * gap, hi - 2 * gap);
        }

        py += hi + gap2;

        graphics.setColor(defaultColor);
        graphics.fillRect(px, py, wi, hi);
        graphics.setColor(backgroundColor);
        graphics.fillRect(px + gap, py + gap, wi - 2 * gap, hi - 2 * gap);

        font = new Font("Arial", Font.BOLD, 60);
        graphics.setColor(new Color(50, 50, 50));
        graphics.setFont(font);
        graphics.drawString("QUIT", px + gap + 130, py + hi - gap - 40);
        font = new Font("Arial", Font.BOLD, 60);
        graphics.setColor(defaultColor);
        graphics.setFont(font);
        graphics.drawString("QUIT", px + gap + 125, py + hi - gap - 35);
        if(y == 0) {
            graphics.setColor(new Color(0, 0, 0, 230));
            graphics.fillRect(px + gap, py + gap, wi - 2 * gap, hi - 2 * gap);
        }
        
        int kx = 150, ky = 250, len = 150;
        int ux = GameConst.FRAME_WIDTH / 2, uy = ky + len / 2;
        
        graphics.setColor(Color.WHITE);
        graphics.fillRect(kx - 10, ky - 10, 170, 170);
        graphics.setColor(Color.BLACK);
        graphics.fillRect(kx - 5, ky - 5, 160, 160);

        graphics.setColor(Color.WHITE);
        graphics.fillRect(GameConst.FRAME_WIDTH - kx - len - 10, ky - 10, 170, 170);
        graphics.setColor(Color.BLACK);
        graphics.fillRect(GameConst.FRAME_WIDTH - kx - len - 5, ky - 5, 160, 160);

        graphics.setColor(Color.WHITE);
        graphics.fillRect(ux - 20, uy - 5, 40, 10);

        graphics.drawImage(gameOver.getImage(), GameConst.FRAME_WIDTH / 2 - gameOver.getWidth() / 2, 30, null);

        drawHero(graphics, heroA, kx, ky, len, 1);
        drawHero(graphics, heroB, GameConst.FRAME_WIDTH - kx - len, ky, len, -1);

        graphics.drawImage(numberA.getImage(), ux - 150, uy - 60, 120, 120, this);
        graphics.drawImage(numberB.getImage(), ux + 30, uy - 60, 120, 120, this);

        Toolkit.getDefaultToolkit().sync();
    }

    private void move(KeyEvent e) {
        
        int key = e.getKeyCode();

        for(int player = 0; player < 2; player++) {
            
            // left, right, up, down

            if(key == buttons[player][4]) {
                
                if(y == 0) {
                    new MenuFrame().setVisible(true);
                    JFrame frame = (JFrame)SwingUtilities.getWindowAncestor(this);
                    frame.setVisible(false);
                    frame.dispose();
                } else {
                    JFrame frame = (JFrame)SwingUtilities.getWindowAncestor(this);
                    frame.setVisible(false);
                    frame.dispose();
                    System.exit(0);
                }
            }

            if (key == buttons[player][2] && y > 0) {
                y--;
            }

            if (key == buttons[player][3] && y < 1) {
                y++;
            }
        }
        
        repaint();
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            move(e);            
        }
    }
}