import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWindow extends JFrame {

    private PlayArea playArea;
    private JButton addParticlesButton;
    private JButton resetButton;
    private TemperatureCalculator temperatureCalculator;
    private JLabel temperatureRight;
    private JLabel temperatureLeft;

    public GameWindow() {
        // Windo Setup
        setTitle("Maxwell's Demon");
        setSize(800, 600);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        playArea = new PlayArea();
        temperatureCalculator = new TemperatureCalculator();
        add(playArea, BorderLayout.CENTER);

        // Intialize Buttons
        addParticlesButton = new JButton("Add Particles");
        resetButton = new JButton("Reset");
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(addParticlesButton);
        buttonPanel.add(resetButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Initialize Temperature Labels
        temperatureRight = new JLabel("Temperature Right: ");
        temperatureLeft = new JLabel("Temperature Left: ");
        JPanel temperaturePanel = new JPanel(new GridLayout(1, 2));
        temperaturePanel.add(temperatureLeft);
        temperaturePanel.add(temperatureRight);
        add(temperaturePanel, BorderLayout.NORTH);

        setupButtonListeners();

    }

    private void setupButtonListeners() {
        addParticlesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playArea.addParticles();
                playArea.repaint();
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playArea.resetGame();
                playArea.repaint();
            }
        });
    }

    public void setTemperatureRight(double temperature) {
        temperatureRight.setText("Temp Right: " + temperature);
    }

    public void setTemperatureLeft(double temperature) {
        temperatureLeft.setText("Temp Left: " + temperature);
    }
}
