import javax.swing.JFrame;

public class Frame extends JFrame {
    
    public Frame() {
        
        setSize(GameConst.FRAME_WIDTH, GameConst.FRAME_HEIGHT);
        setResizable(false);
        
        setTitle(GameConst.title);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}