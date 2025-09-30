package lab.objects;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lab.interfaces.RenderableObject;

public class Plane implements RenderableObject {
    private final Color color;

    private final Point2D[] points;

    public Plane(Color color) {
        this.color = color;
        points = new Point2D[]{
                new Point2D(275, 125),
                new Point2D(325, 150),
                new Point2D(325, 250),
                new Point2D(275, 275)
        };
    }

    public Plane(Color color, Point2D[] points) {
        this.color = color;
        this.points = points;
    }

    public Plane(Color color, double[] points) {
        this.color = color;
        this.points = new Point2D[points.length / 2];
        for (int i = 0; i < points.length / 2; i += 2) {
            this.points[i] = new Point2D(points[i], points[i + 1]);
        }
    }

    public Plane(Color color, int[] points) {
        this.color = color;
        this.points = new Point2D[points.length / 2];
        for (int i = 0; i < points.length / 2; i += 2) {
            this.points[i] = new Point2D(points[i], points[i + 1]);
        }
    }

    public void render(GraphicsContext gc) {
        gc.setFill(color);
        double[] pointsX = new double[points.length];
        double[] pointsY = new double[points.length];
        for (int i = 0; i < points.length; i++) {
            pointsX[i]  = points[i].getX();
            pointsY[i]  = points[i].getY();
        }
        gc.fillPolygon( pointsX, pointsY, points.length );

    }

    public void simulate(long delta) {
        // TODO document why this method is empty
    }
}
