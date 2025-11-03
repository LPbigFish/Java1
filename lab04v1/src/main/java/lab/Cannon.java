package lab;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Transform;

public class Cannon extends WorldEntity implements DrawableSimulate {

    private static final double LENGTH = 60;
    private static final double WIDTH = 15;
    private double angle;
    private double angleDelta = -25;

    public Cannon(World world, Point2D position, double angle) {
        super(world, position);
        this.angle = angle;
    }

    @Override
    protected void drawInternal(GraphicsContext gc) {
        gc.transform(new Affine(Transform.rotate(angle, position.getX(), position.getY() + WIDTH / 2)));
        gc.setFill(Color.BROWN);
        gc.fillRect(position.getX(), position.getY(), LENGTH, WIDTH);
    }

    public void simulate(double deltaT) {
        // do nothing yet
        angle += angleDelta * deltaT;
        if (angle >= 0 || angle <= -90) {
            angleDelta = -angleDelta;
        }
    }

    public double getAngle() {
        return angle;
    }
}
