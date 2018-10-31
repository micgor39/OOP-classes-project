import java.awt.Image;

public class MovingCharacter {

    protected int x, y, width, height;
    protected boolean visible;
    protected MyImage leftImage, rightImage, image;

    public MovingCharacter(int x, int y) {

        this.x = x;
        this.y = y;
        
        visible = true;
    }

    protected void loadImages(String filename, boolean character) {
        
        leftImage = new MyImage(filename + ".png");
        rightImage = new MyImage(filename + "m.png");

        width = leftImage.getWidth();
        height = leftImage.getHeight();

        if(character) {

            double c = 65;

            if(width > height) {
                height = (int)((double)height / (double)width * c);
                width = (int)c;
            } else {
                width = (int)((double)width / (double)height * c);
                height = (int)c;
            }
        }
    }

    public Image getImage() {
        return image.getImage();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}