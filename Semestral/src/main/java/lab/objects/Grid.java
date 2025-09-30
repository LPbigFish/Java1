package lab.objects;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import lab.interfaces.RenderableObject;

public class Grid implements RenderableObject {
    private final Point2D canvasSize;

    public Grid(Canvas canvas) {
        this.canvasSize = new Point2D(canvas.getWidth(), canvas.getHeight());
    }

    public void render(GraphicsContext gc) {
        gc.setFill(Color.DARKGREEN);
        for (int i = 0; i < canvasSize.getX(); i += 25) {
            if (i > canvasSize.getX() / 2) {
                gc.setFill(Color.LIME);
            }
            gc.fillRect(i, 0, 2, canvasSize.getY());
        }
        gc.setFill(Color.DARKRED);
        for (int i = 0; i < canvasSize.getY(); i += 25) {
            if (i > canvasSize.getY() / 2) {
                gc.setFill(Color.RED);
            }
            gc.fillRect(0, i, canvasSize.getX(), 2);
        }
    }

    public void simulate(long time) {

    }
}
