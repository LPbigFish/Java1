package lab;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Arrays;

public class Grid extends RenderableEntity {
    private final Point2D gridSize;
    private final Point2D gridPos;
    private final byte[][] grid;

    private long dropped = 0;

    public Grid(Point2D position, Point2D gridSize) {
        super(position);
        this.gridSize = gridSize;
        this.gridPos = position.add(2, 2);
        this.grid = new byte[(int) gridSize.getY()][(int) gridSize.getX()];
        for (int i = 0; i < gridSize.getY(); i++) {
            grid[i] = new byte[(int) gridSize.getY()];
            for (int j = 0; j < gridSize.getX(); j++) {
                grid[i][j] = 0;
            }
        }

        grid[159][0] = UsableColor.RED_DARK.getId();
        grid[159][50] = UsableColor.DARK_YELLOW.getId();
        grid[159][99] = UsableColor.DARK_GREEN.getId();
        for (int i = 0; i < gridSize.getX(); i++) {
            grid[150][i] = UsableColor.GREEN.getId();
        }
        grid[0][0] = 1;
        grid[0][50] = 1;
        grid[0][99] = 1;

        dropped = 3 + 3 + (long) gridSize.getX();
    }

    @Override
    protected void internalDraw(GraphicsContext gc) {
        gc.setFill(Color.DARKRED);
        gc.fillRect(pos.getX(), pos.getY(), 2, gridSize.getY() + 2);
        gc.fillRect(pos.getX(), pos.getY() + gridSize.getY(), gridSize.getX() + 4, 2);
        gc.fillRect(pos.getX() + gridSize.getX() + 2, pos.getY(), 2, gridSize.getY() + 2);
        for (int i = 0; i < gridSize.getY(); i++) {
            for (int j = 0; j < gridSize.getX(); j++) {
                byte current = grid[i][j];
                if (current == 0) {
                    continue;
                }
                UsableColor usableColor = UsableColor.fromId(current);

                assert usableColor != null;
                gc.setFill(usableColor.getColor());
                gc.fillRect(gridPos.getX() + j, gridPos.getY() + gridSize.getY() - i - 3, 1, 1);
            }
        }
    }

    @Override
    public void simulate(long time) {
        if (time % 2 == 0) {
            grid[(int) gridSize.getY() - 1][(int) gridSize.getX() / 2] = UsableColor.RED_DARK.getId();
            dropped++;
        }
        if (time % 3 == 0) {
            grid[(int) gridSize.getY() - 1][(int) gridSize.getX() / 2 + 1] = UsableColor.DARK_YELLOW.getId();
            dropped++;
        }
        for (int i = 1; i < gridSize.getY(); i++) {
            int center = (int) (gridSize.getX() / 2) - 1;
            int l = center;
            int r = center + 1;
            while (l != -1 && r != gridSize.getX()) {
                classify(i, r);
                classify(i, l);
                r++;
                l--;
            }
        }

        if (time % 32 == 0) {
            System.out.println(dropped);
            long sum = Arrays.stream(grid).map(row -> {
                long count = 0;
                for (int i = 0; i < row.length; i++) {
                    if (row[i] != 0) {
                        count++;
                    }
                }
                return count;
            }).reduce(Long::sum).orElse(0L);
            System.out.println(sum);

        }
    }

    private void classify(int i, int j) {
        if (grid[i][j] == 0) {
            return;
        }
        if (j == 0) {
            leftExtreme(i, j);
        } else if (j == gridSize.getX() - 1) {
            rightExtreme(i, j);
        } else {
            centerExtreme(i, j);
        }
    }

    private void centerExtreme(int i, int j) {
        byte down = grid[i - 1][j];
        byte right = grid[i - 1][j + 1];
        byte left = grid[i - 1][j - 1];
        byte nextL = grid[i][j - 1];
        byte nextR = grid[i][j + 1];
;        if (down == 0) {
            grid[i - 1][j] = grid[i][j];
            grid[i][j] = 0;
            return;
        }
        if (nextL == 0 || nextR == 0) {
            if (left == 0) {
                grid[i - 1][j - 1] = grid[i][j];
                grid[i][j] = 0;
            } else if (right == 0) {
                grid[i - 1][j + 1] = grid[i][j];
                grid[i][j] = 0;
            }
        }
    }

    private void leftExtreme(int i, int j) {
        byte down = grid[i - 1][j];
        byte right = grid[i - 1][j + 1];
        byte nextR = grid[i][j + 1];
        if (down == 0) {
            grid[i - 1][j] = grid[i][j];
            grid[i][j] = 0;
            return;
        }
        if (nextR == 0 && right == 0) {
            grid[i - 1][j + 1] = grid[i][j];
            grid[i][j] = 0;
        }
    }

    private void rightExtreme(int i, int j) {
        byte down = grid[i - 1][j];
        byte left = grid[i - 1][j - 1];
        byte nextL = grid[i][j - 1];
        if (down == 0) {
            grid[i - 1][j] = grid[i][j];
            grid[i][j] = 0;
        } else if (left == 0 && nextL == 0) {
            grid[i - 1][j - 1] = grid[i][j];
            grid[i][j] = 0;
        }
    }

    public void zeroOut() {
        for (int i = 0; i < gridSize.getY(); i++) {
            for (int j = 0; j < gridSize.getX(); j++) {
                grid[i][j] = 0;
            }
        }
    }
}
