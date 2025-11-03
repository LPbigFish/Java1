package lab;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Bullet extends WorldEntity implements DrawableSimulate, Collisionable {
    private static final double SIZE = 20;

    private final Point2D acceleration;

    private Point2D velocity;

    public Bullet(World world, Point2D position, double velocity, double angle, Point2D acceleration) {
        super(world, position);
        setVelocity(velocity, angle);
        this.acceleration = acceleration;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public void simulate(double deltaTime) {
        position = position.add(velocity.multiply(deltaTime));
        velocity = velocity.add(acceleration.multiply(deltaTime));
    }

    @Override
    public Rectangle2D getBoundingBox() {
        return new  Rectangle2D(position.getX(), position.getY(), SIZE, SIZE);
    }

    @Override
    public boolean intersect(Rectangle2D another) {
        return getBoundingBox().intersects(another);
    }

    @Override
    public void hitBy(Collisionable another) {
    }

    @Override
    protected void drawInternal(GraphicsContext gc) {
        gc.setFill(Color.SILVER);
        gc.fillOval(position.getX(), position.getY(), SIZE, SIZE);
    }

    public void setVelocity(double velocity, double angle) {
        this.velocity = new Point2D(Math.cos(Math.toRadians(angle)) * velocity,
            Math.sin(Math.toRadians(angle)) * velocity);
    }
}
