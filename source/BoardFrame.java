import javax.swing.JFrame;

public class BoardFrame extends Frame {
    
    public BoardFrame(int no, int playerA, int playerB, int scoreA, int scoreB, boolean bot) {
        
        super();
        add(new BoardPanel(no, playerA, playerB, scoreA, scoreB, bot));
    }
}