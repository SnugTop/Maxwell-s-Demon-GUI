import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

// Inner class for Particle
    class Particle {
        int x;
        int y;
        private int speed;
        private Color color;

        public Particle(int x, int y, int speed, Color color) {
            this.x = x;
            this.y = y;
            this.speed = speed;
            this.color = color;
        }

        public void move() {
            // Logic for moving the particle
            // Handle collisions with walls and door
        }

        public void draw(Graphics g) {
            g.setColor(color);
            g.fillOval(x, y, 10, 10); // Adjust size as needed
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
    
        public void updatePosition(double scaleX, double scaleY) {
            this.x = (int) (this.x * scaleX);
            this.y = (int) (this.y * scaleY);
        }
    }
