import javax.swing.JFrame;

public class CharacterFrame extends Frame {
    
    public CharacterFrame(int background) {
        
        super();
        add(new CharacterPanel(background));
    }
}