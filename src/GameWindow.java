import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GameWindow extends JFrame {

   private PlayArea playArea;
   private TemperatureCalculator temperatureCalculator;

    public GameWindow() {
        setTitle("Maxwell's Demon");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        playArea = new PlayArea();
        temperatureCalculator = new TemperatureCalculator();
        add(playArea, BorderLayout.CENTER);
        
}
}

