package lab.objects;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lab.interfaces.RenderableEntity;

public class Grid extends RenderableEntity {
    private final Point2D gridSize;
    private byte[][] grid;

    public Grid(Point2D position, Point2D gridSize) {
        super(position);
        this.gridSize = gridSize;
        this.grid = new byte[(int) gridSize.getX()][(int) gridSize.getY()];
        for  (int i = 0; i < gridSize.getX(); i++) {
            grid[i] = new byte[(int) gridSize.getY()];
            for (int j = 0; j < gridSize.getY(); j++) {
                grid[i][j] = 0;
            }
        }
    }

    @Override
    protected void internalDraw(GraphicsContext gc) {
        gc.setFill(Color.DARKRED);
        gc.fillRect(pos.getX(), pos.getY(), 2, gridSize.getY() + 2);
        gc.fillRect(pos.getX(), pos.getY() + gridSize.getY() + 2, gridSize.getX() + 4, 2);
        gc.fillRect(pos.getX() + gridSize.getX() + 2, pos.getY(), 2, gridSize.getY() + 2 );
        for  (int i = 0; i < gridSize.getX(); i++) {
            for (int j = 0; j < gridSize.getY(); j++) {
                gc.setFill(Color.color((double) (grid[i][j] + 128) / 255, (double) (grid[i][j] + 128) / 255, (double) (grid[i][j] + 128) / 255));
                gc.fillRect(this.pos.getX() + 2 + i,  this.pos.getY() + 2 + j, 1, 1);
            }
        }
    }

    @Override
    public void simulate(long time) {
        for (int i = 0; i < gridSize.getX(); i++) {
            for (int j = 0; j < gridSize.getY(); j++) {
                grid[i][j] = (byte) ((i + j) % 256 - 128);
            }
        }
    }

    public void zeroOut() {
        for (int i = 0; i < gridSize.getX(); i++) {
            for (int j = 0; j < gridSize.getY(); j++) {
                grid[i][j] = 0;
            }
        }
    }
}
