import javax.swing.JLabel;

public class TemperatureCalculator extends JLabel {

   /* private int temperatureLeft;
    private int temperatureRight;
    private int totalParticles;
    private int particlesLeft;
    private int particlesRight;

    public TemperatureCalculator() {
        temperatureLeft = 0;
        temperatureRight = 0;
        totalParticles = 0;
        particlesLeft = 0;
        particlesRight = 0;
    }

    public void updateTemperature(int particlesLeft, int particlesRight) {
        this.particlesLeft = particlesLeft;
        this.particlesRight = particlesRight;
        totalParticles = particlesLeft + particlesRight;
        if (totalParticles == 0) {
            temperatureLeft = 0;
            temperatureRight = 0;
        } else {
            temperatureLeft = (int) (particlesLeft * 100.0 / totalParticles);
            temperatureRight = (int) (particlesRight * 100.0 / totalParticles);
        }
        setText("Temperature Left: " + temperatureLeft + "% Temperature Right: " + temperatureRight + "%");
    }

    public int getTemperatureLeft() {
        return temperatureLeft;
    }

    public int getTemperatureRight() {
        return temperatureRight;
    }

    public int getTotalParticles() {
        return totalParticles;
    }

    public int getParticlesLeft() {
        return particlesLeft;
    }

    public int getParticlesRight() {
        return particlesRight;
    }
*/
}
