import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import java.util.Random;

public class MenuPanel extends JPanel implements ActionListener {

    private MyImage background[] = new MyImage[4];
    private Hero hero[] = new Hero[9];
    private int buttons[][] = { { KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_ALT },
                                { KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_SLASH } };
    private Random gen = new Random();
    private Timer timer;
    private final int DELAY = 3000;
    private int rx[] = new int[2], ry[] = new int[2], rh[] = new int[2], rd[] = new int[2], rand, rc2, cnt;
    private final int len = 150;
    private Color rc[] = {Color.GREEN, Color.RED, Color.BLUE, Color.YELLOW, Color.MAGENTA};

    public MenuPanel() {

        for(int i = 0; i < 4; i++) {
            background[i] = new MyImage("background" + i + ".png");
        }

        for(int i = 0; i < 9; i++) {
            hero[i] = new Hero(i, 0, 0, 1);
        }

        rand = randRange(4);
        updateRand();

        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.WHITE);
        setDoubleBuffered(true);
        
        timer = new Timer(DELAY, this);
        timer.start();
    }

    private int randRange(int r) {

        int result = gen.nextInt() % r;
        if(result < 0) result += r;
        return result;
    }

    private void updateRand() {

        rc2 = randRange(5);
        
        for(int i = 0; i < 2; i++) {

            if(i == 0) {
                rd[i] = 1;
            } else {
                rd[i] = -1; 
            }

            if(i == 0) {
                rx[i] = 20 + randRange(120);
                ry[i] = 200 + randRange(400);
            } else {
                rx[i] = GameConst.FRAME_WIDTH - (20 + randRange(120) + len);
                ry[i] = 200 + randRange(400);
            }

           rh[i] = randRange(9);
        }
    }

    private void drawHero(Graphics2D graphics, int id, int sx, int sy, int len, int dir) {

        hero[id].setDir(dir);

        double w = hero[id].getWidth(), h = hero[id].getHeight();

        if(w < h)
            graphics.drawImage(hero[id].getImage(), sx + (int)((double)len / 2.0 - w / h / 2.0 * (double)len), sy, (int)(w / h * (double)len), len, null);
        else
            graphics.drawImage(hero[id].getImage(), sx, sy + (int)((double)len / 2.0 - h / w / 2.0 * (double)len), len, (int)(h / w * (double)len), null);
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D graphics = (Graphics2D)g;
        
        graphics.drawImage(background[rand].getImage(), 0, 0, this);
        graphics.setColor(new Color(20, 20, 20, 120));
        graphics.fillRect(0, 0, GameConst.FRAME_WIDTH, GameConst.FRAME_HEIGHT);

        Color defaultColor = Color.BLACK;
        Color backgroundColor = Color.DARK_GRAY;

        int gap = 5, gap2 = 30;
        int hi = 120, wi = 400;
        int px = GameConst.FRAME_WIDTH / 2 - wi / 2, py = GameConst.FRAME_HEIGHT - 23 - 2 * gap2 - 2 * hi;
        
        graphics.setColor(defaultColor);
        graphics.setStroke(new BasicStroke(gap));
        graphics.drawRect(px, py, wi, hi);
        
        graphics.setColor(new Color(20, 20, 20, 150));
        graphics.fillRect(px + gap - 2, py + gap - 2, wi - 2 * gap + 5, hi - 2 * gap + 5);
        
        graphics.setColor(defaultColor);
        Font font = new Font("Arial", Font.BOLD, 60);
        graphics.setFont(font);
        graphics.drawString("NEW GAME", px + gap + 30, py + hi - gap - 40);
        
        graphics.setColor(rc[rc2]);
        font = new Font("Arial", Font.BOLD, 60);
        graphics.setFont(font);
        graphics.drawString("NEW GAME", px + gap + 25, py + hi - gap - 35);

        for(int i = 0; i < 2; i++) {
            drawHero(graphics, rh[i], rx[i], ry[i], len, rd[i]);
        }

        MyImage logo = new MyImage("logo.png");
        graphics.drawImage(logo.getImage(), 20, 50, this);

        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        updateRand();
        repaint();
    }

    private void move(KeyEvent e) {
        
        int key = e.getKeyCode();

        for(int player = 0; player < 2; player++) {
            
            // left, right, up, down

            if(key == buttons[player][4]) {
            
                new BackgroundFrame().setVisible(true);
                JFrame frame = (JFrame)SwingUtilities.getWindowAncestor(this);
                frame.setVisible(false);
                frame.dispose();
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