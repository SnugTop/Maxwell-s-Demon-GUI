import java.util.Timer;
import java.util.TimerTask;

public class GameController {
    private PlayArea playArea;
    private GameWindow gameWindow;
    private Timer movementTimer;
    private boolean isGameRunning;

   public GameController(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        this.playArea = gameWindow.getPlayArea();
        setupControls();

        // Setup and start the timer
        movementTimer = new Timer();
        movementTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                for (Particle particle : playArea.getParticles()) {
                    particle.move();
                }
                playArea.repaint();
            }
        }, 0, 100);
    }

    private void setupControls() {
        gameWindow.getAddButton().addActionListener(e -> {
            playArea.addParticles();
            playArea.repaint();
        });

        gameWindow.getResetButton().addActionListener(e -> {
            playArea.resetGame();
            playArea.updateTemperatures();
        });
    }
    public void startGame() {
        isGameRunning = true;
        
        // Start the timer and any other necessary components
    }

    // Pause the game
    public void pauseGame() {
        if (isGameRunning) {
            movementTimer.cancel();
            isGameRunning = false;
            // Additional pause logic
        }
    }

    // Resume the game
    public void resumeGame() {
        if (!isGameRunning) {
            startTimer();
            isGameRunning = true;
            // Additional resume logic
        }
    }

    // Stop the game
    public void stopGame() {
        if (isGameRunning) {
            movementTimer.cancel();
            isGameRunning = false;
            // Additional stopping logic
        }
    }

    // Helper method to start the timer
    private void startTimer() {
        movementTimer = new Timer();
        movementTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Timer task logic

            }
        }, 0, 100);
    }

    // Method to change particle speed
    public void setParticleSpeed(int speed) {
        // Logic to change the speed of the particles
        

    }

    // Cleanup resources
    public void cleanup() {
        if (movementTimer != null) {
            movementTimer.cancel();
        }
        // Other cleanup logic
    }

   
}
