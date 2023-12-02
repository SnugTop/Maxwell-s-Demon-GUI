import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayArea extends JPanel {
    private List<Particle> particles;
    private boolean doorOpen;
    private Rectangle leftChamber;
    private Rectangle rightChamber;
    private Rectangle door;
    private boolean initialized = false;
    private final int DOOR_WIDTH = 10; // Adjust as needed

    public PlayArea() {
        particles = new ArrayList<>();
        doorOpen = false;

        // Initialize chambers and door
        //initializeChambersAndDoor();

        // Add mouse listener for door control
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
            @Override
            public void componentShown(ComponentEvent e) {
                if (!initialized) {
                    initializeChambersAndDoor();
                    addInitialParticles();
                    initialized = true;
                }
            }
        });

    }

    private void initializeGame() {
        if (!initialized) {
            initializeChambersAndDoor();
            addInitialParticles();
            initialized = true;
        }
    }

    private void initializeChambersAndDoor() {
        int width = getWidth();
        int height = getHeight();
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
        int speed = isHot ? rand.nextInt(3) + 4 : rand.nextInt(2) + 2; // 4-6 for hot, 2-4 for cold
        Particle particle = new Particle(
                chamber.x + rand.nextInt(chamber.width),
                chamber.y + rand.nextInt(chamber.height),
                convertCmToPixels(speed),
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

    private double calculateTemperature(List<Particle> chamberParticles) {
        // Calculate the temperature of a chamber
        double totalSquaredSpeed = 0;
        for (Particle particle : chamberParticles) {
            totalSquaredSpeed += Math.pow(particle.getSpeed(), 2);
        }
        return chamberParticles.isEmpty() ? 0 : totalSquaredSpeed / chamberParticles.size();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        initializeGame();
        // Draw chambers
        g.setColor(Color.WHITE);
        g.fillRect(leftChamber.x, leftChamber.y, leftChamber.width, leftChamber.height);
        g.fillRect(rightChamber.x, rightChamber.y, rightChamber.width, rightChamber.height);

        // Draw the wall and door
        g.setColor(Color.BLACK); // Color for the wall
        int wallPosition = getWidth() / 2;
        // Draw lines for the wall above and below the door
        g.drawLine(wallPosition, 0, wallPosition, door.y); // Line above the door
        g.drawLine(wallPosition, door.y + door.height, wallPosition, getHeight()); // Line below the door

        if (!doorOpen) {
            g.setColor(Color.GRAY);
            g.fillRect(door.x, door.y, door.width, door.height); // Draw the door
        }

        // Draw particles
        for (Particle particle : particles) {
            particle.move();
            particle.draw(g);
        }
    }

    private int convertCmToPixels(double cm) {
        int resolution = Toolkit.getDefaultToolkit().getScreenResolution();
        double pixelsPerInch = resolution;
        double pixelsPerCm = pixelsPerInch / 2.54; // 1 inch = 2.54 cm
        return (int) (cm * pixelsPerCm);
    }

}
