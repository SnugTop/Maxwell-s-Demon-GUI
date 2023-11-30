import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Particle {
    private int x, y; // Position
    private double vx, vy; // Velocity components
    private boolean isHot;
    private static final Random random = new Random();

    // Bounds for movement
    private int maxX, maxY;

    public Particle(int x, int y, int speed, boolean isHot) {
        this.x = x;
        this.y = y;
        this.isHot = isHot;

        // Initialize velocity components based on speed
        initializeVelocity(speed);
    }

    private void initializeVelocity(int speed) {
        // Random angle for velocity direction
        double angle = random.nextDouble() * 2 * Math.PI;
        vx = speed * Math.cos(angle);
        vy = speed * Math.sin(angle);
    }

    public void updateBounds(int width, int height) {
        maxX = width - Constants.PARTICLE_SIZE;
        maxY = height - Constants.PARTICLE_SIZE;
    }

    public void move() {
        x += vx;
        y += vy;

        // Boundary collision logic
        if (x < 0 || x > maxX) {
            vx = -vx; // Reverse X direction
            x = Math.max(0, Math.min(x, maxX)); // Prevent sticking to the wall
        }
        if (y < 0 || y > maxY) {
            vy = -vy; // Reverse Y direction
            y = Math.max(0, Math.min(y, maxY)); // Prevent sticking to the wall
        }
    }

    public void draw(Graphics g) {
        g.setColor(isHot ? Color.RED : Color.BLUE);
        g.fillOval(x, y, Constants.PARTICLE_SIZE, Constants.PARTICLE_SIZE);
    }

    public Color getColor() {
        return isHot ? Color.RED : Color.BLUE;
    }

    // Removed alignmentX methods since they are not used in your class

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isHot() {
        return isHot;
    }

    public void setHot(boolean isHot) {
        this.isHot = isHot;
    }

    public double getSpeed() {
        return Math.sqrt(vx * vx + vy * vy); // Returns the magnitude of the velocity vector
    }
}
