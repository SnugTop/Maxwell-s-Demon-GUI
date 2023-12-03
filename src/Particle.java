import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;
import java.awt.Toolkit;

public class Particle {
    int x;
    int y;
    private int speed;
    double vx;
    double vy;
    private double speedPxPerSec; // Speed in pixels per second
    private Color color;

    public Particle(int x, int y, int speedCmPerSec, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;

        // Convert speed from cm/s to px/s
        int resolution = Toolkit.getDefaultToolkit().getScreenResolution();
        double pixelsPerCm = resolution / 2.54; // Convert PPI to pixels per cm
        this.speedPxPerSec = speedCmPerSec * pixelsPerCm / 2000.0; // Adjust divisor to control speed

        // Calculate velocity components
        Random rand = new Random();
        double angle = rand.nextDouble() * 2.0 * Math.PI;
        vx = speedPxPerSec * Math.cos(angle);
        vy = speedPxPerSec * Math.sin(angle);

    }

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

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, 10, 10);
    }

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

    public void updatePosition(double scaleX, double scaleY) {
        this.x = (int) (this.x * scaleX);
        this.y = (int) (this.y * scaleY);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 10, 10);
    }
}
