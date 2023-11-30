import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class GameWindow extends JFrame {

    private PlayArea playArea;
    private TemperatureDisplay tempDisplayLeft, tempDisplayRight;
    private JButton addButton, resetButton;

    public GameWindow() {
        setTitle("Maxwell's Demon Game");
        setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initializeComponents();
    }

    private void initializeComponents() {
        tempDisplayLeft = new TemperatureDisplay();
        tempDisplayRight = new TemperatureDisplay();
    
        TemperatureUpdateListener tempListener = (leftTemp, rightTemp) -> {
            tempDisplayLeft.setText("Left Temp: " + leftTemp);
            tempDisplayRight.setText("Right Temp: " + rightTemp);
        };
    
        playArea = new PlayArea(tempListener); // Now tempDisplayLeft and tempDisplayRight are already initialized
        add(playArea, BorderLayout.CENTER);
    
        add(tempDisplayLeft, BorderLayout.WEST);
        add(tempDisplayRight, BorderLayout.EAST);
    
        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add Particles");
        resetButton = new JButton("Reset");
        buttonPanel.add(addButton);
        buttonPanel.add(resetButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getResetButton() {
        return resetButton;
    }

    public PlayArea getPlayArea() {
        return playArea;
    }

    
        
}

