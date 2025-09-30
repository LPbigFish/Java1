package lab;

import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lab.interfaces.RenderableObject;
import lab.objects.GameStore;
import lab.objects.Grid;
import lab.objects.Plane;

public class DrawingThread extends AnimationTimer {

    private final Canvas canvas;
    private final GraphicsContext gc;
    private final GameStore store;

    private static final double FPS_LIMIT = 24.0;
    private static final long FRAME_TIME = (long) (1_000_000_000.0 / FPS_LIMIT);
    private long lastUpdate = 0;

    private final RenderableObject[] renderableObjects;

    public DrawingThread(Canvas canvas, GameStore gameStoreReference) {
        this.store = gameStoreReference;
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
        Point2D[] points = new Point2D[]{
                new Point2D(275, 125),
                new Point2D(325, 150),
                new Point2D(325, 250),
                new Point2D(275, 275)
        };
        this.renderableObjects = new RenderableObject[]{
                new Grid(canvas),
                new Plane(Color.BLUE, points),
                new Plane(Color.YELLOW, new Point2D[]{points[0], points[0].add(200, 0), points[1].add(100, 0), points[1]}),
                new Plane(Color.RED, new Point2D[]{points[0].add(200, 0), points[1].add(100, 0), points[1].add(100, 100), points[0].add(200, 150)}),
                new Plane(Color.LIME, new Point2D[]{points[0].add(0, 150), points[1].add(0, 100), points[1].add(100, 100), points[0].add(200, 150)}),
                new Plane(Color.DARKCYAN, new Point2D[]{points[1], points[1].add(100, 0), points[1].add(100, 100), points[1].add(0, 100)}),
        };
    }

    /**
     * Draws objects into the canvas. Put you code here.
     */
    @Override
    public void handle(long now) {
        if (lastUpdate != 0 && (now - lastUpdate) < FRAME_TIME) return;
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (RenderableObject renderableObject : renderableObjects) {
            renderableObject.simulate(now);
            renderableObject.render(gc);
        }

        lastUpdate = now;
    }
}
