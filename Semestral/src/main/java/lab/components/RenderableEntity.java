package lab.components;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import lab.interfaces.RenderableObject;

public abstract class RenderableEntity implements RenderableObject {
    protected Point2D pos;
    protected RenderableEntity(Point2D pos) {
        this.pos = pos;
    }

    protected abstract void internalDraw(GraphicsContext gc);

    public void draw(GraphicsContext gc) {
        gc.save();
        internalDraw(gc);
        gc.restore();
    }

    public void simulate(long delta) {

    }
}
