import javax.swing.ImageIcon;
import java.awt.Image;

public class MyImage {

    private Image image;

    public MyImage(String filename) {

        ImageIcon tmp = new ImageIcon("../images/" + filename);
        image = tmp.getImage();
    }

    public Image getImage() {
        return image;
    }

    public int getWidth() {
        return image.getWidth(null);
    }

    public int getHeight() {
        return image.getHeight(null);
    }
}