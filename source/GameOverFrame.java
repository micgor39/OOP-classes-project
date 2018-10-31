import javax.swing.JFrame;

public class GameOverFrame extends Frame {
    
    public GameOverFrame(Hero heroA, Hero heroB, int scoreA, int scoreB) {
        
        super();
        add(new GameOverPanel(heroA, heroB, scoreA, scoreB));
    }
}