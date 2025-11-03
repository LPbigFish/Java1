package lab;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
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

    private final Comparator<DrawableSimulable> comparator = new Comparator<DrawableSimulable>() {
        @Override
        public int compare(DrawableSimulable o1, DrawableSimulable o2) {
            if (o1 instanceof Ufo u1 && o2 instanceof Ufo u2) {
                return Double.compare(u2.getSize(), u1.getSize());
            } else if (o1 instanceof Ufo && o2 instanceof Bullet) {
                return -1;
            } else if (o1 instanceof Bullet && o2 instanceof Cannon) {
                return -1;
            }
            return 0;
        }
    };

	public World(double width, double height) {
		this.width = width;
		this.height = height;
		entities = new ArrayList<>();
		cannon = new Cannon(this, new Point2D(0, height - 20), -45);
		entities.add(cannon);
		for (int i = 0; i < 5; i++) {
            entities.add(new Ufo(this));
		}
        entities.add(new UfoSpawner(this));
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
					if (e2 instanceof Collisionable c2 && c1 != c2 && c1.intersect(c2.getBoundingBox())) {
							c1.hitBy(c2);
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
