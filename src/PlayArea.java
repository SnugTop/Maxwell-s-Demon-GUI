import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Disclaimer: I used Chat GPT to help me with this class.
 * 
 * The PlayArea class extends JPanel and represents the main area of the game.
 * It manages the particles, their movement, and interactions within the game
 * environment, including collisions with walls and the door. It also checks
 * for the win condition and handles the game reset.
 * 
 */
public class PlayArea extends JPanel {
    private List<Particle> particles;
    private boolean doorOpen;
    private Rectangle leftChamber;
    private Rectangle rightChamber;
    private Rectangle door;
    private boolean initialized = false;
    private final int doorWidth = 25;
    private final int wallThickness = 25;
    private int previousWidth;
    private int previousHeight;
    private Timer timer;

    /**
     * Constructor for PlayArea. Initializes the game environment, including
     * particles, chambers, and the door. Sets up event listeners for mouse
     * actions and component resizing.
     */
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

        timer = new Timer(50, e -> updateParticles()); // 100 ms delay
        timer.start();
    }

    /**
     * Initializes the game by setting up the chambers, door, and initial particles.
     */
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

    /**
     * Initializes the chambers and door based on the current width and height of
     * the PlayArea.
     *
     * @param width  The current width of the PlayArea.
     * @param height The current height of the PlayArea.
     */
    private void initializeChambersAndDoor(int width, int height) {
        int wallPosition = width / 2;
        leftChamber = new Rectangle(0, 0, wallPosition, height);
        rightChamber = new Rectangle(wallPosition, 0, wallPosition, height);
        door = new Rectangle(wallPosition - doorWidth / 2, height / 3, doorWidth, height / 3);
    }

    /**
     * Adds initial particles to each chamber.
     * One hot and one cold in each chamber.
     */
    private void addInitialParticles() {
        // Add two particles to each chamber
        addParticle(leftChamber, true); // Hot particle
        addParticle(leftChamber, false); // Cold particle
        addParticle(rightChamber, true); // Hot particle
        addParticle(rightChamber, false); // Cold particle
    }

    /**
     * Adds a particle to a specified chamber.
     * This method creates a new particle with a random position within the
     * specified chamber.
     * The position is calculated to ensure the particle does not spawn too close to
     * walls or outside the chamber.
     * If the chamber is too small to safely spawn a particle, the method will not
     * add a particle.
     *
     * @param chamber The chamber (Rectangle) where the particle will be added.
     * @param isHot   Indicates if the particle is hot (true) or cold (false). Hot
     *                particles have higher speeds.
     */
    public void addParticle(Rectangle chamber, boolean isHot) {
        Random rand = new Random();
        int speedCmPerSec = isHot ? rand.nextInt(3) + 4 : rand.nextInt(2) + 2; // 4-6 for hot, 2-4 for cold

        int particleSize = 10;
        int safeMargin = 15; // Minimum distance from walls

        // Ensure the chamber is large enough for spawning particles
        if (chamber.width <= safeMargin * 2 + particleSize || chamber.height <= safeMargin * 2 + particleSize) {

            return;
        }

        // Calculate safe bounds for particle creation
        int minX = chamber.x + safeMargin;
        int maxX = chamber.x + chamber.width - safeMargin - particleSize;
        int minY = chamber.y + safeMargin;
        int maxY = chamber.y + chamber.height - safeMargin - particleSize;

        // Create a new particle within the safe bounds
        Particle particle = new Particle(
                minX + rand.nextInt(maxX - minX + 1),
                minY + rand.nextInt(maxY - minY + 1),
                speedCmPerSec,
                isHot ? Color.RED : Color.BLUE);

        particles.add(particle);
    }

    /**
     * This is how the particles are distributed
     * specifically throughout the chambers
     */
    public void particleDistribution() {
        // Add new particles to each chamber
        addParticle(leftChamber, true);
        addParticle(leftChamber, false);
        addParticle(rightChamber, true);
        addParticle(rightChamber, false);
    }

    public void resetGame() {
        doorOpen = false;
        particles.clear();
        addInitialParticles();
        repaint();
        if (!timer.isRunning()) {
            timer.start();
        }
    }

    /**
     * Handles resizing of the PlayArea, adjusting the position and scale of
     * particles.
     */
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

    /**
     * Draws the game elements, including chambers, walls, door, and particles.
     *
     * @param g The Graphics object used for drawing.
     */
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
        g.setColor(Color.GRAY);

        int wallX = getWidth() / 2 - wallThickness / 2;

        // Draw the upper part of the wall (above the door)
        g.fillRect(wallX, 0, wallThickness, door.y);

        // Draw the lower part of the wall (below the door)
        int lowerWallY = door.y + door.height;
        int lowerWallHeight = getHeight() - lowerWallY;
        g.fillRect(wallX, lowerWallY, wallThickness, lowerWallHeight);

        // Draw the door
        if (!doorOpen) {
            g.setColor(Color.RED);
            g.fillRect(door.x, door.y, door.width, door.height);
        }
    }

    /**
     * Checks if all hot particles are in one chamber and all cold particles
     * are in the other chamber, and the door is closed. If this condition is met,
     * stops the game, displays a win message with final temperatures, and
     * offers options to replay or end the game.
     */
    private void updateParticles() {
        Rectangle centerWall = new Rectangle(getWidth() / 2 - wallThickness / 2, 0, wallThickness, getHeight());
        for (Particle particle : particles) {
            particle.move(getBounds(), door, doorOpen, centerWall);
        }
        repaint();

        if (checkWinCondition()) {
            timer.stop();

            double tempLeft = calculateChamberTemperature(getLeftChamberParticles());
            double tempRight = calculateChamberTemperature(getRightChamberParticles());

            // Win button options
            Object[] options = { "Replay", "End" };
            String winMessage = String.format(
                    "You Win!\nTemperature Left: %.2f\nTemperature Right: %.2f\nWhat would you like to do next?",
                    tempLeft, tempRight);
            int choice = JOptionPane.showOptionDialog(this,
                    winMessage,
                    "Game Over",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]);

            if (choice == JOptionPane.YES_OPTION) {
                resetGame();
            } else {
                System.exit(0);
            }
        }
    }

    /**
     * Calculates the average kinetic energy (temperature) of
     * particles in a chamber. This is used to display the temperature of each
     * chamber in the game.
     *
     * @param chamberParticles The list of particles in a chamber.
     * @return The calculated temperature of the chamber.
     */
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

    /**
     * Retrieves the list of particles in the left chamber.
     *
     * @return A list of particles in the left chamber.
     */
    public List<Particle> getLeftChamberParticles() {
        return particles.stream().filter(p -> p.x < getWidth() / 2).collect(Collectors.toList());
    }

    /**
     * Retrieves the list of particles in the right chamber.
     *
     * @return A list of particles in the right chamber.
     */
    public List<Particle> getRightChamberParticles() {
        return particles.stream().filter(p -> p.x >= getWidth() / 2).collect(Collectors.toList());
    }

    /**
     * Checks the win condition based on the distribution of hot and cold
     * particles in the chambers. The win condition is met when all hot
     * particles are in one chamber and all cold particles are in the other,
     * and the door is closed.
     *
     * @return true if the win condition is met, false otherwise.
     */
    public boolean checkWinCondition() {
        if (!doorOpen) {
            List<Particle> leftParticles = getLeftChamberParticles();
            List<Particle> rightParticles = getRightChamberParticles();

            boolean leftHot = leftParticles.stream().allMatch(p -> p.getColor().equals(Color.RED));
            boolean rightCold = rightParticles.stream().allMatch(p -> p.getColor().equals(Color.BLUE));

            boolean leftCold = leftParticles.stream().allMatch(p -> p.getColor().equals(Color.BLUE));
            boolean rightHot = rightParticles.stream().allMatch(p -> p.getColor().equals(Color.RED));

            return (leftHot && rightCold) || (leftCold && rightHot);
        }
        return false;
    }

}
