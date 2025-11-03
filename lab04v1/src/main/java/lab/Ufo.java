package lab;

import java.util.Random;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Ufo extends WorldEntity implements DrawableSimulate, Collisionable {

	private static final Random RANDOM = new Random();
	private Image image = new Image(this.getClass().getResourceAsStream("ufo-small.gif"));
	private Point2D velocity;

	public Ufo(World world) {
		this(world,
				new Point2D(RANDOM.nextDouble(world.getWidth()),
						RANDOM.nextDouble(0, world.getHeight()*0.3)),
				new Point2D(RANDOM.nextDouble(70, 150), 0));
	}

	public Ufo(World world, Point2D position, Point2D velocity) {
        super(world, position);
		this.velocity = velocity;
	}

	public void changeDirection(){
		velocity = velocity.multiply(-1);
	}
	public void simulate(double deltaT) {
		position = position.add(velocity.multiply(deltaT));
		position = new Point2D(position.getX()%world.getWidth(), position.getY());
	}

    @Override
    protected void drawInternal(GraphicsContext gc) {
        gc.drawImage(image, getPosition().getX(), getPosition().getY());
    }

    public Rectangle2D getBoundingBox() {
		return new Rectangle2D(position.getX(), position.getY(), image.getWidth(), image.getHeight());
	}

    @Override
    public boolean intersect(Rectangle2D another) {
        return getBoundingBox().intersects(another);
    }

    @Override
    public void hitBy(Collisionable another) {
        if (another instanceof Bullet || another instanceof BulletAnimated) {
            changeDirection();
        }
    }
}
