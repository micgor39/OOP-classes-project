import java.util.Random;
import java.awt.Image;

public class FallingLife {

    private int x, y;
    private boolean visible;
    private Random rand;
    private MyImage image;
    private final int speed = 1;

    public FallingLife() {
        
        image = new MyImage("heart.png");

        rand = new Random();
        int mod = GameConst.FRAME_WIDTH - image.getWidth();
        x = rand.nextInt() % mod;
        if(x < 0) x += mod;
        y = 0;

        visible = true;
    }

    public void setVisible(boolean b) {
        visible = b;
    }

    public boolean isVisible() {
        return visible;
    }

    public Image getImage() {
        return image.getImage();
    }

    public int getHeight() {
        return image.getHeight();
    }

    public int getWidth() {
        return image.getWidth();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void move() {
        
        int err = 23;

        y += speed;
        if (y > GameConst.FRAME_WIDTH - err - image.getHeight()) {
            visible = false;
        }
    }
}