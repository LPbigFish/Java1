package lab.interfaces;

import javafx.scene.canvas.GraphicsContext;

public interface DrawableSimulate {
    void draw(GraphicsContext gc);

    void simulate(double delta);
}
