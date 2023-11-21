import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Particle {

    private int x, y; // Position
    private double vx, vy; // Velocity components
    private boolean isHot;
    private float alignmentX;
    private static final Random random = new Random();

    public Particle(int x, int y, int speed, boolean isHot, float alignmentX) {
        this.x = x;
        this.y = y;
        this.isHot = isHot;
        this.alignmentX = alignmentX;

        // Initialize velocity components based on speed
        initializeVelocity(speed);
    }

    private void initializeVelocity(int speed) {
        // Random angle for velocity direction
        double angle = random.nextDouble() * 2 * Math.PI;
        vx = speed * Math.cos(angle);
        vy = speed * Math.sin(angle);
    }

    public void move(int width, int height) {
        x += vx;
        y += vy;

        // Boundary collision logic
        if (x < 0 || x > width - Constants.PARTICLE_SIZE) {
            vx = -vx; // Reverse X direction
            x = Math.max(0, Math.min(x, width - Constants.PARTICLE_SIZE)); // Prevent sticking to the wall
        }
        if (y < 0 || y > height - Constants.PARTICLE_SIZE) {
            vy = -vy; // Reverse Y direction
            y = Math.max(0, Math.min(y, height - Constants.PARTICLE_SIZE)); // Prevent sticking to the wall
        }
    }


    public void draw(Graphics g) {
        g.setColor(isHot ? Color.RED : Color.BLUE);
        g.fillOval(x, y, Constants.PARTICLE_SIZE, Constants.PARTICLE_SIZE);
    }

        public Color getColor() {
        return isHot ? Color.RED : Color.BLUE;
    }

    public float getAlignmentX() {
        return alignmentX;
    }

    public void setAlignmentX(float alignmentX) {
        this.alignmentX = alignmentX;
    }

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
