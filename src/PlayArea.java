import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class PlayArea extends JPanel {
    private List<Particle> particles;
    private boolean doorOpen;
    private Rectangle leftChamber;
    private Rectangle rightChamber;
    private Rectangle door;
    private boolean initialized = false;
    private final int DOOR_WIDTH = 10;
    private int previousWidth;
    private int previousHeight;
    private Timer timer;

    public PlayArea() {
        particles = new ArrayList<>();
        doorOpen = false;
        previousWidth = getWidth();
        previousHeight = getHeight();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                doorOpen = true;
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                doorOpen = false;
                repaint();
            }
        });

        addComponentListener(new ComponentAdapter() {
            private int lastWidth = -1;
            private int lastHeight = -1;

            @Override
            public void componentResized(ComponentEvent e) {
                int currentWidth = getWidth();
                int currentHeight = getHeight();

                if (currentWidth != lastWidth || currentHeight != lastHeight) {
                    lastWidth = currentWidth;
                    lastHeight = currentHeight;

                    SwingUtilities.invokeLater(() -> {
                        if (initialized) {
                            handleResize();
                        } else if (getWidth() > 0 && getHeight() > 0) {
                            initializeGame();
                        }
                    });
                }
            }
        });
        SwingUtilities.invokeLater(() -> {
            if (!initialized) {
                initializeGame();
            }
        });

        timer = new Timer(100, e -> updateParticles()); // 100 ms delay
        timer.start();
    }

    private void initializeGame() {
        if (!initialized) {
            int currentWidth = getWidth();
            int currentHeight = getHeight();

            initializeChambersAndDoor(currentWidth, currentHeight);

            addInitialParticles();

            previousWidth = currentWidth;
            previousHeight = currentHeight;

            initialized = true;
        }
    }

    private void initializeChambersAndDoor(int width, int height) {
        int wallPosition = width / 2;
        leftChamber = new Rectangle(0, 0, wallPosition, height);
        rightChamber = new Rectangle(wallPosition, 0, wallPosition, height);
        door = new Rectangle(wallPosition - DOOR_WIDTH / 2, height / 3, DOOR_WIDTH, height / 3);
    }

    private void addInitialParticles() {
        // Add two particles to each chamber
        addParticle(leftChamber, true); // Hot particle
        addParticle(leftChamber, false); // Cold particle
        addParticle(rightChamber, true); // Hot particle
        addParticle(rightChamber, false); // Cold particle
    }

    public void addParticle(Rectangle chamber, boolean isHot) {
        Random rand = new Random();
        // Speed in cm/s
        int speedCmPerSec = isHot ? rand.nextInt(3) + 4 : rand.nextInt(2) + 2; // 4-6 for hot, 2-4 for cold
    
        // Create a new particle with the specified speed in cm/s
        Particle particle = new Particle(
                chamber.x + rand.nextInt(chamber.width),
                chamber.y + rand.nextInt(chamber.height),
                speedCmPerSec, // Pass speed in cm/s directly
                isHot ? Color.RED : Color.BLUE);
    
        particles.add(particle);
    }

    public void addParticles() {
        // Add new particles to each chamber
        addParticle(leftChamber, true);
        addParticle(leftChamber, false);
        addParticle(rightChamber, true);
        addParticle(rightChamber, false);
    }

    public void resetGame() {
        particles.clear();
        addInitialParticles();
        repaint();
    }

    private void handleResize() {
        int newWidth = getWidth();
        int newHeight = getHeight();

        if (newWidth != previousWidth || newHeight != previousHeight) {
            double scaleX = previousWidth > 0 ? (double) newWidth / previousWidth : 1.0;
            double scaleY = previousHeight > 0 ? (double) newHeight / previousHeight : 1.0;

            for (Particle particle : particles) {
                particle.updatePosition(scaleX, scaleY);
                particle.setX(Math.min(Math.max(particle.getX(), 0), newWidth - 10));
                particle.setY(Math.min(Math.max(particle.getY(), 0), newHeight - 10));
            }

            initializeChambersAndDoor(newWidth, newHeight);

            previousWidth = newWidth;
            previousHeight = newHeight;
        }

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw chambers, wall, door, and particles
        drawGameElements(g);
    }

    private void drawGameElements(Graphics g) {
        // Draw chambers
        g.setColor(Color.BLACK);
        g.fillRect(leftChamber.x, leftChamber.y, leftChamber.width, leftChamber.height);
        g.fillRect(rightChamber.x, rightChamber.y, rightChamber.width, rightChamber.height);

        // Draw the wall and door
        drawWallAndDoor(g);

        // Draw particles
        for (Particle particle : particles) {
            // particle.move(door, door, doorOpen);
            particle.draw(g);
        }
    }

    private void drawWallAndDoor(Graphics g) {
        g.setColor(Color.YELLOW);
        int wallPosition = getWidth() / 2;
        g.drawLine(wallPosition, 0, wallPosition, door.y);
        g.drawLine(wallPosition, door.y + door.height, wallPosition, getHeight()); // Line below the door

        if (!doorOpen) {
            g.setColor(Color.RED);
            g.fillRect(door.x, door.y, door.width, door.height);
        }
    }

    private void updateParticles() {
        Rectangle centerWall = new Rectangle(getWidth() / 2 - DOOR_WIDTH / 2, 0, DOOR_WIDTH, getHeight());
        for (Particle particle : particles) {
            particle.move(getBounds(), door, doorOpen, centerWall);
        }
        repaint();
    }

    double calculateChamberTemperature(List<Particle> chamberParticles) {
        double totalSquaredSpeed = 0;
        for (Particle particle : chamberParticles) {
            double vx = particle.getVx();
            double vy = particle.getVy();
            double speed = Math.sqrt(vx * vx + vy * vy);
            totalSquaredSpeed += speed * speed;
        }
        return chamberParticles.isEmpty() ? 0 : totalSquaredSpeed / chamberParticles.size();
    }
    
    public List<Particle> getLeftChamberParticles() {
        return particles.stream().filter(p -> p.x < getWidth() / 2).collect(Collectors.toList());
    }

    public List<Particle> getRightChamberParticles() {
        return particles.stream().filter(p -> p.x >= getWidth() / 2).collect(Collectors.toList());
    }

}
