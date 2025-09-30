package lab;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Bullet {
    private Point2D acceleration;
    private static final double SIZE = 20;

    private Point2D velocity;
    private Point2D position;

    public Bullet() {
        this(new Point2D(0, -5), new Point2D(100, 50), new Point2D(50, 50));
    }

    public Bullet(Point2D position, Point2D velocity, Point2D acceleration) {
        this.acceleration = acceleration;
        this.velocity = velocity;
        this.position = position;
    }

    public void simulate(double delta) {
        position = position.add(velocity.multiply(delta));
        velocity = velocity.add(acceleration.multiply(delta));
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.DARKGREEN);
        gc.fillOval(position.getX(), position.getY(), SIZE, SIZE);
    }

    public Point2D getPosition() {
        return position;
    }
}
