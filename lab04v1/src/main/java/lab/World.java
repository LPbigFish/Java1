package lab;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class World {

	private final double width;

	private final double height;

    private final List<DrawableSimulate> objects;

	private final Bullet bullet;
	private final BulletAnimated bullet2;
	private final Cannon cannon;

	public World(double width, double height) {
		this.width = width;
		this.height = height;
        this.objects = new ArrayList<>();
		bullet = new Bullet(this, new Point2D(0, height), 150, -30, new Point2D(0, 9.81));
        objects.add(bullet);
		bullet2 = new BulletAnimated(this, new Point2D(0, height), 130, -80, new Point2D(0, 9.81));
        objects.add(bullet2);
		cannon = new Cannon(this, new Point2D(0, height-20), -45);
        objects.add(cannon);
		for (int i = 0; i < 5; i++) {
			Ufo ufo = new Ufo(this);
            objects.add(ufo);
		}
	}

	public void draw(GraphicsContext gc) {
		gc.clearRect(0, 0, width, height);

		gc.save();
		for (DrawableSimulate object : objects) {
            object.draw(gc);
        }
		gc.restore();
	}

	public void simulate(double deltaTime) {
        for (int i = 0; i < objects.size() - 1; i++) {
            DrawableSimulate object = objects.get(i);
            object.simulate(deltaTime);
            for (int j = i + 1; j < objects.size(); j++) {
                DrawableSimulate obj2 = objects.get(j);
                if (obj2 instanceof Collisionable c2 &&
                    object instanceof Collisionable c &&
                    c2.intersect(c.getBoundingBox())) {
                        c2.hitBy(c);
                        c.hitBy(c2);
                    }

            }
        }
	}

    public void reload() {
        bullet2.setPosition(new Point2D(0, height));
        bullet2.setVelocity(200, cannon.getAngle());
    }

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

}
