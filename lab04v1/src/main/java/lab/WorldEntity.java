package lab.interfaces;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import lab.World;

import java.awt.*;

public abstract class WorldEntity implements DrawableSimulate {
    protected Point2D position;

    protected final World world;

    public WorldEntity(World world, Point2D position) {
        this.position = position;
        this.world = world;
    }

    public Point2D getPosition() {
        return this.position;
    }

    protected abstract void drawInternal(GraphicsContext gc);

    @Override
    public final void draw(GraphicsContext gc) {
        gc.save();
        drawInternal(gc);
        gc.restore();
    }
}
