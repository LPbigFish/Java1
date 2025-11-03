package lab;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class World {
    public static final Point2D GRAVITY = new Point2D(0, -9.41);

    private final double width;

    private final double height;

    private final Collection<DrawableSimulable> entitiesToRemove;
    private final Collection<DrawableSimulable> entitiesToAdd;
    private final List<DrawableSimulable> entities;

    private final List<Ufo> ufos = new ArrayList<>();

    private Cannon cannon;
    private Bullet bulletAnimated;

    public World(double width, double height) {
        this.width = width;
        this.height = height;
        entities = new ArrayList<>();
        entitiesToRemove = new ArrayList<>();
        entitiesToAdd = new ArrayList<>();
        cannon = new Cannon(this, new Point2D(0, height - 20), -45);
        entities.add(cannon);
        entities.add(new Bullet(this, new Point2D(0, height), 100, -45, new Point2D(0, 9.81)));
        bulletAnimated = new BulletAnimated(this, new Point2D(0, height), 200, -45, new Point2D(0, 9.81));
        entities.add(bulletAnimated);
        for (int i = 0; i < 7; i++) {
            entities.add(new Ufo(this));
        }
    }

    public void draw(GraphicsContext gc) {
        gc.clearRect(0, 0, width, height);
        gc.save();
        for (DrawableSimulable entity : entities) {
            entity.draw(gc);
        }
        gc.restore();
    }

    public void simulate(double deltaTime) {
        for (DrawableSimulable entity : entities) {
            entity.simulate(deltaTime);
        }

        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i) instanceof Collisionable c1) {
                for (int j = i + 1; j < entities.size(); j++) {
                    if (entities.get(j) instanceof Collisionable c2 && c1 != c2 && c1.intersect(c2.getBoundingBox())) {
                        c1.hitBy(c2);
                        c2.hitBy(c1);
                    }
                }
            }
        }

        entities.removeAll(entitiesToRemove);
        entities.addAll(entitiesToAdd);
        entitiesToAdd.clear();
        entitiesToRemove.clear();
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public void reload(double speed) {
        bulletAnimated.setPosition(cannon.getPosition());
        bulletAnimated.setVelocity(speed, cannon.getAngle());
    }

    public Cannon getCannon() {
        return cannon;
    }

    public void add(DrawableSimulable bullet) {
        entitiesToAdd.add(bullet);
    }

    public void removeEntity(DrawableSimulable e) {
        entitiesToRemove.add(e);
    }
}
