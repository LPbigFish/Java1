package lab.interfaces;

import javafx.scene.canvas.GraphicsContext;

public interface RenderableObject {

    void draw(GraphicsContext gc);

    void simulate(long delta);
}
