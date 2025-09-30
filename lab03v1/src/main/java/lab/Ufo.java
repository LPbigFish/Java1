package lab;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Objects;
import java.util.Random;

public class Ufo {
    private static final double SIZE = 40;

    private static final Random RANDOM = new Random();

    private final World world;

    private Point2D pos;
    private Point2D vel;

    private boolean hitBullet = false;

    private Image image = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("ufo.gif")));

    public Ufo(World world) {
        this.world = world;
        this.pos = new Point2D(RANDOM.nextDouble(0, world.getWidth() * 0.3),  RANDOM.nextDouble(0, world.getHeight() * 0.3));
        this.vel = new Point2D(100, 0);
    }

    public Ufo(World world, Point2D pos, Point2D vel) {
        this.pos = pos;
        this.vel = vel;
        this.world = world;
    }

    public Point2D getPos() {
        return pos;
    }

    public void draw(GraphicsContext gc) {
        gc.drawImage(image, pos.getX(), pos.getY(), SIZE, SIZE);
    }

    public void changeDirection() {
        vel = vel.multiply(-1);
    }

    public void changeDirection(boolean bullet) {
        if (!hitBullet) {
            vel = vel.multiply(-1);
        }
        this.hitBullet = bullet;
    }

    public void simulate(double delta) {
        if (pos.getX() > world.getWidth() - SIZE || pos.getX() < 1) {
            changeDirection(false);
        }
        pos = pos.add(vel.multiply(delta));
    }

    public Rectangle2D getBoundingBox() {
        return new Rectangle2D(pos.getX(), pos.getY(), SIZE, SIZE);
    }
}
