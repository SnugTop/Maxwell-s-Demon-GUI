import javax.swing.*;
import java.awt.*;

/**
 * The GameWindow class extends JFrame and is responsible for creating and
 * displaying the main window of the application. It includes a play area,
 * buttons for adding particles and resetting the game, and labels to display
 * the temperature in different chambers.
 */
public class GameWindow extends JFrame {

    private PlayArea playArea;
    private JButton addParticlesButton;
    private JButton resetButton;
    private JLabel temperatureRight;
    private JLabel temperatureLeft;

    /**
     * Constructor for GameWindow. Sets up the main window with a play area, control
     * buttons, and temperature display labels.
     */
    public GameWindow() {
        // Window setup
        setTitle("Maxwell's Demon");
        setSize(800, 600);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize components
        initializeComponents();

        // Set up button listeners
        setupButtonListeners();

        // Start temperature update timer
        startTemperatureUpdateTimer();
    }

    /**
     * Initializes and adds UI components to the GameWindow.
     */
    private void initializeComponents() {
        // Initialize and add play area
        playArea = new PlayArea();
        add(playArea, BorderLayout.CENTER);

        // Initialize and add buttons
        addParticlesButton = new JButton("Add Particles");
        resetButton = new JButton("Reset");
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(addParticlesButton);
        buttonPanel.add(resetButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Initialize and add temperature labels
        temperatureRight = new JLabel("Temperature Right: ");
        temperatureLeft = new JLabel("Temperature Left: ");
        JPanel temperaturePanel = new JPanel(new GridLayout(1, 2));
        temperaturePanel.add(temperatureLeft);
        temperaturePanel.add(temperatureRight);
        add(temperaturePanel, BorderLayout.NORTH);
    }

    /**
     * Sets up listeners for the control buttons.
     */
    private void setupButtonListeners() {
        addParticlesButton.addActionListener(e -> {
            playArea.particleDistribution();
            playArea.repaint();
        });

        resetButton.addActionListener(e -> {
            playArea.resetGame();
            playArea.repaint();
        });
    }

    /**
     * Starts a timer to periodically update the temperature
     * readings in the chambers.
     */
    private void startTemperatureUpdateTimer() {
        Timer temperatureUpdateTimer = new Timer(100, e -> updateTemperatures());
        temperatureUpdateTimer.start();
    }

    /**
     * Updates the temperature readings for the left and right chambers.
     */
    private void updateTemperatures() {
        double tempLeft = playArea.calculateChamberTemperature(playArea.getLeftChamberParticles());
        double tempRight = playArea.calculateChamberTemperature(playArea.getRightChamberParticles());
        setTemperatureLeft(tempLeft);
        setTemperatureRight(tempRight);
    }

    /**
     * Sets the temperature reading for the right chamber.
     *
     * @param temperature The temperature to be displayed for the right chamber.
     */
    public void setTemperatureRight(double temperature) {
        temperatureRight.setText("Temp Right: " + String.format("%.2f", temperature));
    }

    /**
     * Sets the temperature reading for the left chamber.
     *
     * @param temperature The temperature to be displayed for the left chamber.
     */
    public void setTemperatureLeft(double temperature) {
        temperatureLeft.setText("Temp Left: " + String.format("%.2f", temperature));
    }
}
