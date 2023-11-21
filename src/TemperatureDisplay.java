import javax.swing.JLabel;

public class TemperatureDisplay extends JLabel {

    public TemperatureDisplay() {
        super("Temp: 0°C");
    }

    public void updateTemperature(double temperature) {
        setText("Temp: " + String.format("%.2f", temperature) + "°C");
    }
}
