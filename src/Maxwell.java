import javax.swing.SwingUtilities;

/**
 * IMPORTANT:
 * I could not figure out how to clone this properly, so I just copied it from
 * my personal Repo. Please let me know if there are any issues with running it.
 * It runs for me with now issues passing many test so please let me know if there are 
 * any issues.
 * 
 * The Maxwell class is responsible for initializing and displaying the game
 * window.
 * It uses SwingUtilities to ensure that the creation and display of the window
 * are done on the Event Dispatch Thread (EDT).
 */
public class Maxwell {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameWindow window = new GameWindow();
            window.setVisible(true);
        });
    }
}
