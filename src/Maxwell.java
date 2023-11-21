public class Maxwell {
    private static void createAndShowGUI() {
        GameWindow gameWindow = new GameWindow();
        gameWindow.setVisible(true);
        
        GameController gameController = new GameController(gameWindow);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> createAndShowGUI());
    }
}