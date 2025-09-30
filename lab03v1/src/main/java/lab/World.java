package lab;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class World {

    private static final Random RANDOM = new Random();

	private final double width;

	private final double height;

	private final Bullet bullet;
	private final BulletAnimated bullet2;
	private final Cannon cannon;
    private final Ufo[] ufos = new Ufo[5];

	public World(double width, double height) {
		this.width = width;
		this.height = height;
		bullet = new Bullet(new Point2D(0, height), new Point2D(30, -30), new Point2D(0, 9.81));
		bullet2 = new BulletAnimated(new Point2D(0, height), new Point2D(50, -80), new Point2D(0, 9.81));
		cannon = new Cannon(new Point2D(0, height-20), -45);
        for (int i = 0; i < 5; i++) {
            ufos[i] = new Ufo(this, new Point2D(RANDOM.nextDouble(1, this.getWidth() * 0.3), RANDOM.nextDouble(0, this.getHeight() * 0.3)), new Point2D(100, 0));
        }
	}

	public void draw(GraphicsContext gc) {
		gc.clearRect(0, 0, width, height);

		gc.save();
		bullet.draw(gc);
		bullet2.draw(gc);
		cannon.draw(gc);
        for (Ufo ufo : ufos) {
            ufo.draw(gc);
        }
		gc.restore();
	}

	public void simulate(double deltaT) {
		bullet.simulate(deltaT);
		bullet2.simulate(deltaT);
		cannon.simulate(deltaT);
        for  (Ufo ufo : ufos) {
            if (ufo.getBoundingBox().intersects(bullet2.getBoundingBox())) {
                ufo.changeDirection(true);
            }
            ufo.simulate(deltaT);
        }
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

}
