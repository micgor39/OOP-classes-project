import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;

public class BackgroundPanel extends JPanel {

    private int x = 0, y = 0;
    MyImage background[] = new MyImage[4];
    private int buttons[][] = { { KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_ALT },
                                { KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_SLASH } };

    public BackgroundPanel() {

        for(int i = 0; i < 4; i++) {
            background[i] = new MyImage("background" + i + ".png");
        }

        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.WHITE);

        setDoubleBuffered(true);
    }

    private int getID(int x, int y) {
        return 2 * x + y;
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D graphics = (Graphics2D)g;
        
        graphics.drawImage(background[getID(x, y)].getImage(), 0, 0, this);
        graphics.setColor(new Color(20, 20, 20, 230));
        graphics.fillRect(0, 0, GameConst.FRAME_WIDTH, GameConst.FRAME_HEIGHT);

        int gap = 50;
        int u = gap, d = GameConst.FRAME_HEIGHT - gap, l = gap, r = GameConst.FRAME_WIDTH - gap;
        int lx = (r - l - gap) / 2, ly = (d - u - gap) / 2;
        
        int px = gap, py = gap;
        for(int i = 0; i < 2; i++) {
            
            px = gap;
            for(int j = 0; j < 2; j++) {

                graphics.setColor(Color.WHITE);
                graphics.fillRect(px, py, lx, ly);

                int c = 5;
                graphics.drawImage(background[getID(i, j)].getImage(), px + c, py + c, lx - 2 * c, ly - 2 * c, this);
                graphics.setColor(new Color(20, 20, 20, 150));
                if(i != x || j != y) {
                    graphics.fillRect(px + c, py + c, lx - 2 * c, ly - 2 * c);
                }

                px += lx + gap;
            }
            py += ly + gap;
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void move(KeyEvent e) {
        
        int key = e.getKeyCode();

        for(int player = 0; player < 2; player++) {
            
            // left, right, up, down

            if(key == buttons[player][4]) {

                new CharacterFrame(getID(x, y)).setVisible(true);
                JFrame frame = (JFrame)SwingUtilities.getWindowAncestor(this);
                frame.setVisible(false);
                frame.dispose();
            }

            if (key == buttons[player][0] && y > 0) {
                y--;
            }

            if (key == buttons[player][1] && y < 1) {
                y++;
            }

            if (key == buttons[player][2] && x > 0) {
                x--;
            }

            if (key == buttons[player][3] && x < 1) {
                x++;
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