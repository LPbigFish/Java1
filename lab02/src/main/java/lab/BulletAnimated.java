package lab;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Objects;

public class BulletAnimated {
    private Point2D position;
    private Point2D velocity;
    private Point2D acceleration;

    private Image image;

    public BulletAnimated(Point2D position, Point2D velocity, Point2D acceleration) {
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration;
        image = new Image(Objects.requireNonNull(BulletAnimated.class.getResourceAsStream("fireball-transparent-small.gif")));
    }

    public void draw(GraphicsContext gc) {
        gc.drawImage(image, position.getX(), position.getY(), 30, 30);
    }

    public void simulate(double delta) {
        position = position.add(velocity.multiply(delta));
        velocity = velocity.add(acceleration.multiply(delta));
    }
}
