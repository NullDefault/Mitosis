/**
David Nesterov-Rappoport
Class: Mainloop
Purpose: Starts the game
 */
import java.awt.EventQueue;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Mainloop extends JFrame {

    public Mainloop() {
        
        initUI();
    }
    
    private void initUI() {
    	Scoreboard scores = new Scoreboard();
        add(new GameMaster(scores));

        setTitle("Mitosis");
        setSize(800, 600);
        
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void exec() {

    		EventQueue.invokeLater(() -> {
            Mainloop ex = new Mainloop();
            ex.setIconImage(Screen.ICON.getImage());
            ex.setVisible(true);
        });
    }
}
