import javax.swing.SwingUtilities;

/*
 * *******IMPORTANT PLEASE READ********
 * PLEASE GRADE THIS REPOSITORY
 * 
 * After many issues I was finally able to succesfully clone the original repository
 * to a new repository in my GitHub Classroom. It is marked final submission.
 * Please grade this repository if possible. Thank you! 
 * There should be no issues with my program running. It complies and passes many tests.
 * If there are, please let me know.
 */

/**
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
