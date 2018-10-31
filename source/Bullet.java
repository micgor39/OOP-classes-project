public class Bullet extends MovingCharacter {

    protected int no, dir, px, py;
    protected int damage, speed;

    public Bullet(int x, int y, int no, int dir) {
        
        super(x, y);

        this.no = no;
        this.dir = dir;
        
        loadImages("bullet" + no, false);

        if(dir == 1) {
            image = leftImage;
        } else {
            image = rightImage;
        }

        if(dir == 1) {
            px = image.getWidth();
        } else {
            px = 0;
        }
        py = image.getHeight() / 2;

        damage = HeroStats.bulletDamage[no];
        speed = HeroStats.bulletSpeed[no];
    }

    public int getPX() {
        return px;
    }

    public int getPY() {
        return py;
    }

    public int getDamage() {
        return damage;
    }

    public void setVisible(boolean b) {
        visible = b;
    }

    public void move() {
        
        x += speed * dir;
        if (-width > x || x > GameConst.FRAME_WIDTH) {
            visible = false;
        }
    }
}