package lab;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import lab.components.RenderableEntity;
import lab.interfaces.RenderableObject;

import java.util.ArrayList;
import java.util.List;

public class DrawingThread extends AnimationTimer {

    private final Canvas canvas;
    private final GraphicsContext gc;
    private final long FRAME_TIME;
    private long SIMULATION_TIME;
    private long lastFrameTime = 0;
    private long lastSimulationTime = 0;
    private final List<RenderableEntity> renderableObjects;
    public DrawingThread(Canvas canvas, List<RenderableEntity> renderableObjects) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
        this.gc.scale(1.9, 1.9);
        this.SIMULATION_TIME = 1_000_000_000 / 80;
        this.renderableObjects = new ArrayList<>();
        this.renderableObjects.addAll(renderableObjects);
        double FPS_LIMIT = 60.0;
        FRAME_TIME = (long) (1_000_000_000 / FPS_LIMIT);
    }

    @Override
    public void handle(long now) {
        if (lastFrameTime == 0) {
            lastFrameTime = now;
            lastSimulationTime = now;
            return;
        }

        while (now - lastSimulationTime >= SIMULATION_TIME) {
            for  (RenderableObject renderableObject : renderableObjects) {
                renderableObject.simulate(now);
                lastSimulationTime += SIMULATION_TIME;
            }
        }

        if (now - lastFrameTime >= FRAME_TIME) {
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            for (RenderableObject renderableObject : renderableObjects) {
                renderableObject.draw(gc);
            }
            lastFrameTime = now;
        }
    }

    public void updateSpeed(int speed) {
        this.SIMULATION_TIME = 1_000_000_000 / speed;
    }
}
