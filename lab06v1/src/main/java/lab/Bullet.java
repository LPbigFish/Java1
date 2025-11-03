package lab;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Bullet extends WorldEntity implements Collisionable {
    private static final double SIZE = 20;

    protected final Point2D acceleration;
    protected Point2D velocity;

    protected List<HitListener> hitListeners = new ArrayList<>();

    public Bullet(World world, Point2D position, double velocity, double angle, Point2D acceleration) {
        super(world, position);
        setVelocity(velocity, angle);
        this.acceleration = acceleration;
    }

    public void setVelocity(double velocity, double angle) {
        this.velocity = new Point2D(Math.cos(Math.toRadians(angle)) * velocity,
            Math.sin(Math.toRadians(angle)) * velocity);
    }

    @Override
    public void drawInternal(GraphicsContext gc) {
        gc.setFill(Color.SILVER);
        gc.fillOval(position.getX(), position.getY(), SIZE, SIZE);
    }

    @Override
    public void simulate(double deltaTime) {
        position = position.add(velocity.multiply(deltaTime));
        velocity = velocity.add(acceleration.multiply(deltaTime));
    }

    @Override
    public Rectangle2D getBoundingBox() {
        return new Rectangle2D(position.getX(), position.getY(), SIZE, SIZE);
    }

    @Override
    public boolean intersect(Rectangle2D another) {
        return getBoundingBox().intersects(another);
    }

    @Override
    public void hitBy(Collisionable another) {
        if (another instanceof Ufo u) {
            fireUfoDestroyed();
            world.removeEntity(u);
            world.removeEntity(this);
        }
    }

    public boolean addHitListener(HitListener hitListener) {
        return hitListeners.add(hitListener);
    }

    public boolean removeHitListener(HitListener hitListener) {
        return hitListeners.remove(hitListener);
    }

    protected void fireUfoDestroyed() {
        hitListeners.forEach(HitListener::ufoDestroyed);
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }
}
