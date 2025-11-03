package lab;

import java.util.Iterator;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class World {

    private final double width;

    private final double height;

    private DrawableSimulable[] entities;

    private Cannon cannon;
    private Bullet bulletAnimated;

    private double speed = 200.0;

    public World(double width, double height) {
        this.width = width;
        this.height = height;
        entities = new DrawableSimulable[8];
        cannon = new Cannon(this, new Point2D(0, height - 20), -45);
        entities[0] = cannon;
        entities[1] = new Bullet(this, new Point2D(0, height), 100, -45, new Point2D(0, 9.81));
        bulletAnimated = new BulletAnimated(this, new Point2D(0, height), 200, -45, new Point2D(0, 9.81));
        entities[2] = bulletAnimated;
        for (int i = 3; i < entities.length; i++) {
            entities[i] = new Ufo(this);
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
        for (int i = 0; i < entities.length; i++) {
            if (entities[i] instanceof Collisionable c1) {
                for (int j = i + 1; j < entities.length; j++) {
                    if (entities[j] instanceof Collisionable c2) {
                        if (c1.intersect(c2.getBoundingBox())) {
                            c1.hitBy(c2);
                            c2.hitBy(c1);
                        }
                    }
                }
            }
        }
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public Cannon getCannon() {
        return cannon;
    }

    public void reload(double speed) {
        bulletAnimated.setPosition(cannon.getPosition());
        bulletAnimated.setVelocity(speed, cannon.getAngle());
    }
}
