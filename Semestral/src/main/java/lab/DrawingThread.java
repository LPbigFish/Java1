package lab;

import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class DrawingThread extends AnimationTimer {

    private final Canvas canvas;
    private final GraphicsContext gc;

    private static final double FPS_LIMIT = 60.0;
    private static final long FRAME_TIME = (long) (1_000_000_000.0 / FPS_LIMIT);
    private long lastUpdate = 0;

    private final List<RenderableEntity> renderableObjects;

    public DrawingThread(Canvas canvas) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
        this.gc.scale(2, 2);
        this.renderableObjects = new ArrayList<>();
        renderableObjects.add(new Grid(new Point2D(canvas.getWidth() / 10 - 52,20), new Point2D(200, 320)));
        renderableObjects.add(new Timer(new Point2D(2, 10)));
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
            renderableObject.draw(gc);
        }
        lastUpdate = now;
    }
}
