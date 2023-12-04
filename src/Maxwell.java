import javax.swing.SwingUtilities;

/** 
 * The Maxwell class is responsible for initializing and displaying the game window.
 * It uses SwingUtilities to ensure that the creation and display of the window
 * are done on the Event Dispatch Thread (EDT), which is the recommended practice
 * for Swing applications.
 */
public class Maxwell {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameWindow window = new GameWindow();
            window.setVisible(true);
        });
    }
}
