import javax.swing.JFrame;
import javax.swing.JLabel;

public class GameWindow extends JFrame {

    public GameWindow() {
        // Set the title for the JFrame
        super("Maxwell's Demon Game");

        // Initialize components in the JFrame
        initializeComponents();

        // Set default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the initial size of the window
        setSize(800, 600);

        // Center the window on the screen
        setLocationRelativeTo(null);
    }

    private void initializeComponents() {
        // For now, just add a simple label (you will add more components later)
        JLabel label = new JLabel("Maxwell's Demon Game", JLabel.CENTER);
        add(label);
    }
}
