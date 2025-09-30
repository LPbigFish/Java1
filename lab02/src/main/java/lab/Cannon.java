package lab;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Transform;

public class Cannon {
    private Point2D position;
    private double angle;

    private static double WIDTH = 40;
    private static int LENGTH = 150;

    public Cannon(Point2D position, double angle) {
        this.position = position;
        this.angle = angle;
    }

    public void draw(GraphicsContext gc) {
        gc.save();
        gc.transform(new Affine(Transform.rotate(angle, position.getX(), position.getY()+WIDTH/2)));
        gc.setFill(Color.BROWN);
        gc.fillRect(position.getX(), position.getY(), LENGTH, WIDTH);
        gc.restore();
    }

    public void simulate(double delta) {

    }
}
