import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;
import java.awt.Toolkit;

/**
 * The Particle class represents a particle with a position, velocity, and
 * color.
 * It is capable of moving within a defined area, handling collisions with walls
 * and other boundaries.
 */
public class Particle {
    int x;
    int y;
    double vx;
    double vy;
    private int speed;
    private double speedPxPerSec; // Speed in pixels per second
    private Color color;

    /**
     * Constructs a Particle with specified position, speed, and color.
     * The speed for the particles can be adjusted by the divisor of the fraction.
     * This ensures they move at a speed that is easier to observe.
     * 
     * @param x             The initial x-coordinate of the particle.
     * @param y             The initial y-coordinate of the particle.
     * @param speedCmPerSec The speed of the particle in centimeters per second.
     * @param color         The color of the particle.
     */
    public Particle(int x, int y, int speedCmPerSec, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;

        // Convert speed from cm/s to px/s
        int resolution = Toolkit.getDefaultToolkit().getScreenResolution();
        double pixelsPerCm = resolution / 2.54; // Convert PPI to pixels per cm
        this.speedPxPerSec = speedCmPerSec * pixelsPerCm / 10.0; // Adjust divisor to control speed

        // Calculate velocity
        Random rand = new Random();
        double angle = rand.nextDouble() * 2.0 * Math.PI;
        vx = speedPxPerSec * Math.cos(angle);
        vy = speedPxPerSec * Math.sin(angle);

    }

    /**
     * Moves the particle within the specified bounds, handling collisions with
     * walls, borders and door.
     *
     * @param bounds     The bounding rectangle within which the particle can move.
     * @param door       The rectangle representing the door area.
     * @param doorOpen   A boolean indicating if the door is open.
     * @param centerWall The rectangle representing the center wall.
     */
    public void move(Rectangle bounds, Rectangle door, boolean doorOpen, Rectangle centerWall) {
        int nextX = x + (int) vx;
        int nextY = y + (int) vy;

        // Collision with walls
        if (nextX < bounds.x || nextX > bounds.width - 10) {
            vx = -vx;
            nextX = x;
        }
        if (nextY < bounds.y || nextY > bounds.height - 10) {
            vy = -vy;
            nextY = y;
        }

        // Collision with center wall and door
        if (centerWall.intersects(nextX, nextY, 10, 10)) {
            boolean inDoorArea = nextY >= door.y && nextY <= door.y + door.height;

            if (!doorOpen && inDoorArea) {
                vx = -vx;
                nextX = x;
            } else if (!inDoorArea) {
                vx = -vx;
                nextX = x;
            }

        }

        x = nextX;
        y = nextY;

    }

    /**
     * Draws the particle on the given graphics context.
     *
     * @param g The graphics context on which to draw the particle.
     */
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, 10, 10);
    }

    /**
     * Updates the position of the particle based on scaling factors.
     *
     * @param scaleX The scaling factor in the x-direction.
     * @param scaleY The scaling factor in the y-direction.
     */
    public void updatePosition(double scaleX, double scaleY) {
        this.x = (int) (this.x * scaleX);
        this.y = (int) (this.y * scaleY);
    }

    /**
     * Gets the bounding rectangle of the particle.
     *
     * @return A rectangle representing the bounds of the particle.
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, 10, 10);
    }

    // Getters and setters
    public int getSpeed() {
        return speed;
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

    public double getVx() {
        return vx;
    }

    public double getVy() {
        return vy;
    }
}