package lab;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class BulletAnimated extends WorldEntity implements DrawableSimulate, Collisionable {

    private static final double SIZE = 40;
    private Image image = new Image(this.getClass().getResourceAsStream("fireball-transparent-small.gif"));

    public BulletAnimated(World world, Point2D position, double velocity, double angle, Point2D acceleration) {
        super(world, position);
        setVelocity(velocity, angle);
        this.acceleration = acceleration;
    }

    private final Point2D acceleration;

    private Point2D velocity;

    public void simulate(double deltaT) {
        position = position.add(velocity.multiply(deltaT));
        velocity = velocity.add(acceleration.multiply(deltaT));
    }

    public void setVelocity(double velocity, double angle) {
        this.velocity = new Point2D(Math.cos(Math.toRadians(angle)) * velocity,
            Math.sin(Math.toRadians(angle)) * velocity);
    }

    @Override
    protected void drawInternal(GraphicsContext gc) {
        gc.drawImage(image, getPosition().getX(), getPosition().getY(), SIZE, SIZE);
        gc.strokeRect(position.getX(), position.getY(), SIZE, SIZE);
    }

    public Rectangle2D getBoundingBox() {
        return new Rectangle2D(position.getX(), position.getY(), SIZE, SIZE);
    }

    @Override
    public boolean intersect(Rectangle2D another) {
        return getBoundingBox().intersects(another);
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    @Override
    public void hitBy(Collisionable another) {
        if (another instanceof Ufo) {
            this.world.reload();
        }
    }
}
