import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SuperBullet extends Bullet implements ActionListener {

    private Timer timer;
    private final int DELAY = 10;

    public SuperBullet(int x, int y, int no, int dir) {
        
        super(x, y, no, dir);
        damage *= 2;

        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        speed++;
    }
}