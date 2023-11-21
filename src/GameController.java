public class GameController {
    private PlayArea playArea;
    private GameWindow gameWindow;

    public GameController(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        this.playArea = gameWindow.getPlayArea();
        setupControls();
    }

    private void setupControls() {
        gameWindow.getAddButton().addActionListener(e -> {
            playArea.addParticles();
            playArea.repaint();
        });

        gameWindow.getResetButton().addActionListener(e -> {
            playArea.resetGame();
            // Assuming updateTemperatures method will update the temperatures
            playArea.updateTemperatures();
        });
    }
}
