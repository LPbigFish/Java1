package lab.interfaces;

import javafx.scene.canvas.GraphicsContext;

public interface RenderableObject {

    void render(GraphicsContext gc);

    void simulate(long delta);
}
