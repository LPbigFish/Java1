package lab;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.*;

public class Grid extends RenderableEntity {
    private final int rows;
    private final int cols;
    private final ShortPoint gridPos;
    private final UsableColor[][] grid;
    private final WritableImage image;
    private final WritableImage staticBorderImage;

    public Grid(Point2D position, Point2D gridSize) {
        super(position);
        this.rows = (int) gridSize.getY();
        this.cols = (int) gridSize.getX();
        this.gridPos = new ShortPoint(position.add(2, 2));
        this.grid = new UsableColor[(int) rows][(int) cols];

        int BORDER_THICKNESS = 2;
        int totalWidth = cols + (BORDER_THICKNESS * 2);
        int totalHeight = rows + BORDER_THICKNESS;

        staticBorderImage = new WritableImage(totalWidth, totalHeight);
        PixelWriter borderWriter = staticBorderImage.getPixelWriter();
        Color borderColor = Color.DARKRED;

        // 2. Draw Left Wall pixels (x=0)
        writeRectPixels(borderWriter, 0, 0, BORDER_THICKNESS, totalHeight, borderColor);

        // 3. Draw Right Wall pixels (x far right)
        writeRectPixels(borderWriter, cols + BORDER_THICKNESS, 0, BORDER_THICKNESS, totalHeight, borderColor);

        // 4. Draw Floor pixels (y at bottom)
        writeRectPixels(borderWriter, 0, rows, totalWidth, BORDER_THICKNESS, borderColor);

        this.image = new WritableImage(cols, rows);

        for (int i = 0; i < rows; i++) {
            Arrays.fill(grid[i], UsableColor.BLANK);
        }

        grid[159][0] = UsableColor.RED_DARK;
        grid[159][50] = UsableColor.DARK_YELLOW;
        grid[159][99] = UsableColor.DARK_GREEN;
        Arrays.fill(grid[159], UsableColor.GREEN);
        grid[0][0] = UsableColor.RED_DARK;
        grid[0][50] = UsableColor.RED_DARK;
        grid[0][99] = UsableColor.RED_DARK;
    }

    @Override
    protected void internalDraw(GraphicsContext gc) {
        gc.setImageSmoothing(false);

        PixelWriter pw = image.getPixelWriter();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                UsableColor current = grid[i][j];
                Color c = (current != UsableColor.BLANK) ? current.getColor() : Color.TRANSPARENT;

                pw.setColor(j, rows - i - 1, c);
            }
        }

        gc.drawImage(image, gridPos.getX() + 1, gridPos.getY() - 2);

        gc.drawImage(staticBorderImage, pos.getX(), pos.getY());
    }

    @Override
    public void simulate(long time) {
        if (time % 2 == 0) {
            grid[rows - 1][cols / 2] = UsableColor.RED_DARK;
        }
        if (time % 3 == 0) {
            grid[rows - 1][cols / 2 + 1] = UsableColor.DARK_YELLOW;
        }
        for (int i = 1; i < rows; i++) {
            int center = (cols / 2) - 1;
            int l = center;
            int r = center + 1;
            while (l != -1 && r != cols) {
                classify(i, r);
                classify(i, l);
                r++;
                l--;
            }
        }
        //classifyDeletion();
    }

    public void classifyDeletion() {
        final int boundY = cols - 1;

        Queue<Pair<ShortPoint, ColorGroup>> l = new LinkedList<>();
        Queue<Pair<ShortPoint, ColorGroup>> r = new LinkedList<>();

        Pair<ShortPoint, ColorGroup> l_pair = new Pair<>(new ShortPoint(0, 0), grid[0][0].getGroup());
        boolean l_pair_set = false;
        Pair<ShortPoint, ColorGroup> r_pair = new Pair<>(new ShortPoint(0, cols - 1), grid[0][boundY].getGroup());
        boolean r_pair_set = false;

        for (int i = 1; i < rows - 1; i++) {
            UsableColor[] current = { grid[i][0], grid[i][boundY] };
            if (l_pair.getValue().equals(current[0].getGroup())) {
                if (!l_pair_set)
                    l.add(l_pair);
                l_pair_set = true;
            } else {
                l_pair = new Pair<>(new ShortPoint(i, 0), current[0].getGroup());
                l_pair_set = false;
            }
            if (r_pair.getValue().equals(current[1].getGroup())) {
                if (!r_pair_set)
                    r.add(r_pair);
                r_pair_set = true;
            } else {
                r_pair = new Pair<>(new ShortPoint(i, boundY), current[1].getGroup());
                r_pair_set = false;
            }
        }

        if (l.isEmpty() && r.isEmpty())
            return;

        for (Pair<ShortPoint, ColorGroup> pair : l) {
            if (r.stream().anyMatch(p -> p.getValue().equals(pair.getValue()))) {
                int score = findPath(pair.getKey(), pair.getValue());
            }
        }
    }

    private int findPath(ShortPoint start, ColorGroup color) {
        boolean[][] visited = new boolean[(int) rows][(int) cols];
        for (int i = 0; i < rows; i++) {
            visited[i] = new boolean[(int) rows];
        }
        Queue<ShortPoint> queue = new LinkedList<>();
        queue.add(start);
        while (!queue.isEmpty()) {
            ShortPoint current = queue.poll();
            int x = current.getX();
            int y = current.getY();
            visited[x][y] = true;
            if (!color.equals(grid[x][y].getGroup())) {
                continue;
            }
            List<ShortPoint> path = new ArrayList<>(4);
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    if ((i + j) % 2 != 0) {
                        int nx = x + i;
                        int ny = y + j;
                        UsableColor c = grid[nx][ny];
                        if (c.getGroup().equals(color)) {
                            path.add(new ShortPoint(x + i, y + j));
                        }
                    }
                }
            }
            queue.addAll(path);
        }

        int score = 0;

        if (Arrays.stream(visited).noneMatch(row -> row[cols - 1])) {
            return -1;
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (visited[i][j]) {
                    grid[i][j] = UsableColor.BLANK;
                    score++;
                }
            }
        }

        return score;
    }

    private void classify(int i, int j) {
        if (grid[i][j] == UsableColor.BLANK) {
            return;
        }
        if (j == 0) {
            leftExtreme(i, j);
        } else if (j == cols - 1) {
            rightExtreme(i, j);
        } else {
            centerExtreme(i, j);
        }
    }

    private void centerExtreme(int i, int j) {
        UsableColor down = grid[i - 1][j];
        UsableColor right = grid[i - 1][j + 1];
        UsableColor left = grid[i - 1][j - 1];
        UsableColor nextL = grid[i][j - 1];
        UsableColor nextR = grid[i][j + 1];
        if (down.getId() == 0) {
            grid[i - 1][j] = grid[i][j];
            grid[i][j] = UsableColor.BLANK;
            return;
        }
        if (nextL.getId() == 0 || nextR.getId() == 0) {
            if (left.getId() == 0) {
                grid[i - 1][j - 1] = grid[i][j];
                grid[i][j] = UsableColor.BLANK;
            } else if (right.getId() == 0) {
                grid[i - 1][j + 1] = grid[i][j];
                grid[i][j] = UsableColor.BLANK;
            }
        }
    }

    private void leftExtreme(int i, int j) {
        UsableColor down = grid[i - 1][j];
        UsableColor right = grid[i - 1][j + 1];
        UsableColor nextR = grid[i][j + 1];
        if (down.getId() == 0) {
            grid[i - 1][j] = grid[i][j];
            grid[i][j] = UsableColor.BLANK;
            return;
        }
        if (nextR.getId() == 0 && right.getId() == 0) {
            grid[i - 1][j + 1] = grid[i][j];
            grid[i][j] = UsableColor.BLANK;
        }
    }

    private void rightExtreme(int i, int j) {
        UsableColor down = grid[i - 1][j];
        UsableColor left = grid[i - 1][j - 1];
        UsableColor nextL = grid[i][j - 1];
        if (down.getId() == 0) {
            grid[i - 1][j] = grid[i][j];
            grid[i][j] = UsableColor.BLANK;
        } else if (left.getId() == 0 && nextL.getId() == 0) {
            grid[i - 1][j - 1] = grid[i][j];
            grid[i][j] = UsableColor.BLANK;
        }
    }

    public void zeroOut() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = UsableColor.BLANK;
            }
        }
    }

    private static void writeRectPixels(PixelWriter pw, int startX, int startY, int w, int h, Color c) {
        for (int x = startX; x < startX + w; x++) {
            for (int y = startY; y < startY + h; y++) {
                pw.setColor(x, y, c);
            }
        }
    }
}
