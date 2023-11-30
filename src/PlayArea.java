import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;



   public class PlayArea extends JPanel {
    private List<Particle> particles;
    private Random random = new Random();
    private TemperatureUpdateListener temperatureUpdateListener; // Define listener interface

    public PlayArea(TemperatureUpdateListener listener) {
        particles = new ArrayList<>();
        this.temperatureUpdateListener = listener;
        //resetGame(); // Initialize the game with the starting particles
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();

        // Draw border
        int borderWidth = 5; // Set the width of the border
        g.setColor(Color.BLACK); // Set border color
        g.drawRect(0, 0, getWidth() - borderWidth, getHeight() - borderWidth);

        // Draw wall and door
        int wallWidth = 10;
        int doorWidth = wallWidth;
        int doorHeight = height / 3;
        g.setColor(Color.BLACK);
        g.fillRect(width / 2 - wallWidth / 2, 0, wallWidth, height);
        g.clearRect(width / 2 - doorWidth / 2, height / 2 - doorHeight / 2, doorWidth, doorHeight);

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
        int borderWidth = 5; // Border width
        int speed = isHot ? randomSpeed(Constants.HOT_MIN_SPEED, Constants.HOT_MAX_SPEED)
                          : randomSpeed(Constants.COLD_MIN_SPEED, Constants.COLD_MAX_SPEED);
    
        // Calculate the x-coordinate for the particle
        int x;
        if (isInLeftChamber) {
            // For left chamber, position should be between the left border and the middle wall
            x = randomPosition(borderWidth, getWidth() / 2 - Constants.PARTICLE_SIZE - borderWidth);
        } else {
            // For right chamber, position should be between the middle wall and the right border
            x = randomPosition(getWidth() / 2 + borderWidth, getWidth() - Constants.PARTICLE_SIZE - borderWidth);
        }
    
        // Calculate the y-coordinate for the particle
        int y = randomPosition(borderWidth, getHeight() - Constants.PARTICLE_SIZE - borderWidth);
    
        return new Particle(x, y, speed, isHot);
    }
    

    public void updateTemperatures() {
        List<Particle> leftParticles = new ArrayList<>();
        List<Particle> rightParticles = new ArrayList<>();

        for (Particle particle : particles) {
            if (particle.getX() < getWidth() / 2) {
                leftParticles.add(particle);
            } else {
                rightParticles.add(particle);
            }
        }

        double leftTemp = calculateTemperature(leftParticles);
        double rightTemp = calculateTemperature(rightParticles);
        // Trigger an update in the temperature displays
        if (temperatureUpdateListener != null) {
            temperatureUpdateListener.updateTemperatures(leftTemp, rightTemp);
        }
    }

    @Override
public void addNotify() {
    super.addNotify();
    resetGame(); // Initialize the game here, instead of in the constructor
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

    
    public void handleResize() {
        // Logic to adjust particles or other elements when resizing
        repositionParticles();
        repaint();
    }

    public List<Particle> getParticles() {
        return particles;
    }
    
    private void repositionParticles() {
        // Adjust particle positions within the new bounds of the PlayArea
        for (Particle particle : particles) {
            particle.updateBounds(getWidth(), getHeight());
        }
    }
    
        
    

}
