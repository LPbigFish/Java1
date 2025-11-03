package lab;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class BulletAnimated extends Bullet {

	private static final double SIZE = 40;
	private final Image image = new Image(this.getClass().getResourceAsStream("fireball-transparent.gif"));

	public BulletAnimated(World world, Point2D position, double velocity, double angle, Point2D acceleration) {
		super(world, position, velocity, angle, acceleration);
	}

	@Override
	public void drawInternal(GraphicsContext gc) {
		gc.drawImage(image, getPosition().getX(), getPosition().getY(), SIZE, SIZE);
		gc.strokeRect(position.getX(), position.getY(), SIZE, SIZE);
	}

	@Override
	public void hitBy(Collisionable another) {
		if (another instanceof Ufo) {
			//world.reload(200);
		}
	}
}
