package lab;

import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public abstract class Formation<T extends DrawableSimulable> extends WorldEntity implements Collisionable {
    protected List<T> entitiesInFormation;

    Point2D velocity;

    @SafeVarargs
    protected Formation(World world, Point2D pos, Point2D velocity, T... entities) {
        super(world, pos);
        this.velocity = velocity;
        this.entitiesInFormation = List.of(entities);
    }

    @Override
    public void drawInternal(GraphicsContext gc) {
        entitiesInFormation.forEach(x -> x.draw(gc));
    }

    public abstract void simulateElements(long delta);

    public void simulate(long delta) {
        position = position.add(velocity.multiply(delta));
        simulateElements(delta);
    }

    @Override
    public Rectangle2D getBoundingBox() {
        return entitiesInFormation.stream().map(DrawableSimulable::getBoundingBox)
            .reduce(new Rectangle2D(position.getX(), position.getY(), 0, 0), (a, b) -> {
                double minX = Math.min(a.getMinX(), b.getMinX());
                double minY = Math.min(a.getMinY(), b.getMinY());
                double maxX = Math.max(a.getMaxX(), b.getMaxX());
                double maxY = Math.max(a.getMaxY(), b.getMaxY());
                return new Rectangle2D(minX, minY, maxX - minX, maxY - minY);
            });
    }

    public List<Collisionable> getCollisionableEntities() {
        return entitiesInFormation.stream().filter(Collisionable.class::isInstance).map(Collisionable.class::cast).toList();
    }

    @Override
    public void hitBy(Collisionable other) {
        if (other instanceof Formation<? extends DrawableSimulable> another) {
            getCollisionableEntities().forEach(x -> {
                another.getCollisionableEntities().stream().filter(y -> y.intersect(x)).forEach(z -> z.hitBy(another));
            });
            return;
        }
        getCollisionableEntities().stream().filter(x -> x.intersect(other)).forEach(x -> x.hitBy(other));
    }
}
