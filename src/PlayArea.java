import javax.swing.JPanel;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayArea extends JPanel {
    private List<Particle> particles;
    private Random random = new Random();

    public PlayArea() {
        particles = new ArrayList<>();
        resetGame(); // Initialize the game with the starting particles
    }

    @FunctionalInterface
public interface TemperatureUpdateListener {
    void updateTemperatures(double leftTemp, double rightTemp);
}


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw each particle
        for (Particle particle : particles) {
            g.setColor(particle.getColor());
            g.fillOval(particle.getX(), particle.getY(), Constants.PARTICLE_SIZE, Constants.PARTICLE_SIZE);
        }
    }

    public void addParticles() {
        // Add one hot and one cold particle to each chamber
        particles.add(createParticle(true, true)); // Hot particle in left/top chamber
        particles.add(createParticle(false, true)); // Cold particle in left/top chamber
        particles.add(createParticle(true, false)); // Hot particle in right/bottom chamber
        particles.add(createParticle(false, false)); // Cold particle in right/bottom chamber
    }

    public void resetGame() {
        particles.clear(); // Remove all particles
        addParticles(); // Add initial set of particles
        repaint(); // Repaint the play area
        updateTemperatures(); // Update temperature displays
    }

    private Particle createParticle(boolean isHot, boolean isInLeftChamber) {
        int speed = isHot ? randomSpeed(Constants.HOT_MIN_SPEED, Constants.HOT_MAX_SPEED)
                          : randomSpeed(Constants.COLD_MIN_SPEED, Constants.COLD_MAX_SPEED);
        int x = isInLeftChamber ? randomPosition(0, getWidth() / 2)
                                : randomPosition(getWidth() / 2, getWidth());
        int y = randomPosition(0, getHeight());
        return new Particle(x, y, speed, isHot, getAlignmentX());
    }

    private int randomSpeed(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    private int randomPosition(int min, int max) {
        if (max <= min) {
            // Handle this case appropriately, maybe log an error or adjust values
            System.out.println("Invalid bounds for randomPosition: min=" + min + ", max=" + max);
            return min; // or some other default handling
        }
        return random.nextInt(max - min) + min;
    }
    public double calculateTemperature(List<Particle> particlesInChamber) {
        if (particlesInChamber.isEmpty()) {
            return 0;
        }

        double totalSquaredSpeed = 0;
        for (Particle particle : particlesInChamber) {
            double speed = particle.getSpeed();
            totalSquaredSpeed += speed * speed;
        }

        return totalSquaredSpeed / particlesInChamber.size();
    }

    public void updateTemperatures() {
        double leftTemp = calculateTemperature(particles/* particles in the left chamber */);
        double rightTemp = calculateTemperature(particles/* particles in the right chamber */);
        // Trigger an update in the temperature displays, possibly through a callback
    }
   
        
    

}
