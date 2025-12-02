package lab;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class World {

	public static final Point2D GRAVITY = new Point2D(0, 9.81);
	private final double width;

	private final double height;

	private final List<DrawableSimulable> entities;
	private final Collection<DrawableSimulable> entitiesToRemove = new LinkedList<>();
	private final Collection<DrawableSimulable> entitiesToAdd = new LinkedList<>();

	private final Cannon cannon;

    private Comparator<DrawableSimulable> comparator;

	public World(double width, double height) {
        this.width = width;
        this.height = height;
        entities = new ArrayList<>();
        entities.add(new UfoSpawner(this));
        cannon = new Cannon(this, new Point2D(0, height - 20), -45);
        entities.add(cannon);
        entities.addAll(Stream.generate(() -> new UfoSpawner(this)).limit(10).toList());

        entities.add(
          new RotatingUfoFormation(
              this,
              new Point2D(100, 100),
              new Point2D(60, 0),
              Stream.generate(() -> new Ufo(this)).limit(5).toArray(Ufo[]::new)
          )
        );

        comparator = (o1, o2) -> {
            if (o1 instanceof Bullet b1 && o2 instanceof Bullet b2) {
                return 0;
            }
            if (o1 instanceof Bullet) {
                return 1;
            }
            if (o2 instanceof Bullet) {
                return -1;
            }
            if (o1 instanceof Ufo u1 && o2 instanceof Ufo u2) {
                if (u1.getWidth() < u2.getWidth() && u1.getHeight() < u2.getHeight()) {
                    return -1;
                }
                if (u1.getWidth() > u2.getWidth() && u1.getHeight() > u2.getHeight()) {
                    return 1;
                }
                return 0;
            }
            return 0;
        };
        comparator = comparator.reversed();
        entities.sort(comparator);
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
		for (DrawableSimulable e1: entities) {
			if (e1 instanceof Collisionable c1) {
				for (DrawableSimulable e2: entities) {
					if (e2 instanceof Collisionable c2) {
						if (c1 != c2 && c1.intersect(c2)) {
							c1.hitBy(c2);
						}
					}
				}
			}
		}
		entities.removeAll(entitiesToRemove);
		entities.addAll(entitiesToAdd);
		entitiesToAdd.clear();
		entitiesToRemove.clear();
        entities.sort(comparator);
    }

	public double getWidth() {
		return width;
	}

	public void add(DrawableSimulable entity) {
		entitiesToAdd.add(entity);
	}
	public void remove(DrawableSimulable entity) {
		entitiesToRemove.add(entity);

	}

	public double getHeight() {
		return height;
	}

	public Cannon getCannon() {
		return cannon;
	}

}
