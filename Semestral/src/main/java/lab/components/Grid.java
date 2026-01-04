package lab.components;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import lab.enums.ColorGroup;
import lab.enums.Shapes;
import lab.enums.UsableColor;
import lab.events.EventDispatcher;
import lab.events.EventType;
import lab.events.GameOverEvent;
import lab.events.ScoreUpdateEvent;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Grid extends RenderableEntity {
    private final int rows;
    private final int cols;
    private final ShortPoint gridPos;
    private final UsableColor[][] grid;
    private final WritableImage image;
    private final WritableImage staticBorderImage;
    private Shape currentShape;
    private boolean simSide = true;

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

        writeRectPixels(borderWriter, 0, 0, BORDER_THICKNESS, totalHeight, borderColor);
        writeRectPixels(borderWriter, cols + BORDER_THICKNESS, 0, BORDER_THICKNESS, totalHeight, borderColor);
        writeRectPixels(borderWriter, 0, rows, totalWidth, BORDER_THICKNESS, borderColor);

        this.image = new WritableImage(cols, rows);

        for (int i = 0; i < rows; i++) {
            Arrays.fill(grid[i], UsableColor.BLANK);
        }

        EventDispatcher.listen(EventType.MOVE_LEFT, e -> tryMove(-1));
        EventDispatcher.listen(EventType.MOVE_RIGHT, e -> tryMove(1));

        spawnNewShape();
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

        if (currentShape != null) {
            currentShape.draw(pw, new ShortPoint(cols, rows));
        }

        gc.drawImage(image, gridPos.getX() + 0.5, gridPos.getY() - 2);

        gc.drawImage(staticBorderImage, pos.getX(), pos.getY());
    }
    @Override
    public void simulate(long time) {
        if (currentShape == null) {
            spawnNewShape();
        } else {
                ShortPoint nextPos = new ShortPoint(currentShape.getPos().getX() - 1, currentShape.getPos().getY());

                if (checkCollision(currentShape, nextPos)) {
                    currentShape.setPos(nextPos);
                } else {
                    solidifyShape();
                }
        }

        for (int i = 1; i < rows; i++) {
            if (simSide) {
                for (int j = 0; j < cols; j++) {
                    classify(i, j);
                }
            } else {
                for (int j = cols - 1; j >= 0; j--) {
                    classify(i, j);
                }
            }
        }

        simSide = !simSide;

        if (!simSide) {
            classifyDeletion();
        }
        if (getSolidHeight() >= rows - 5) {
            EventDispatcher.publish(EventType.GAME_OVER, new GameOverEvent());
        }
    }
    private void spawnNewShape() {
        Shapes[] allShapes = Shapes.values();
        Shape newShape = allShapes[new Random().nextInt(allShapes.length)].getShape();

        newShape.setPos(new ShortPoint(rows, cols / 2 - 2));

        int rot = (int) (Math.random() * 10) % 4;
        for (int i = 0; i < rot; i++) {
            for (Block b : newShape.getBlocks()) {
                int oldRow = b.pos.getX();
                int oldCol = b.pos.getY();

                int newRow = -oldCol;

                b.pos = new ShortPoint(newRow, oldRow);
            }
        }

        newShape.recalculateBounds();

        this.currentShape = newShape;

        if (!checkCollision(newShape, newShape.getPos())) {
            EventDispatcher.publish(EventType.GAME_OVER, new GameOverEvent());
        }
    }
    private boolean checkCollision(Shape shape, ShortPoint targetPos) {
        for (Block block : shape.getBlocks()) {
            int blockRowStart = targetPos.getX() + (block.pos.getX() * Block.BLOCK_SIZE);
            int blockColStart = targetPos.getY() + (block.pos.getY() * Block.BLOCK_SIZE);

            for (int r = 0; r < Block.BLOCK_SIZE; r++) {
                for (int c = 0; c < Block.BLOCK_SIZE; c++) {
                    int absRow = blockRowStart + r;
                    int absCol = blockColStart + c;

                    if (absCol < 0 || absCol >= cols) return false;
                    if (absRow < 0) return false;

                    if (absRow < rows) {
                        if (grid[absRow][absCol] != UsableColor.BLANK) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
    private void tryMove(int dy) {
        if (currentShape == null) return;
        ShortPoint nextPos = new ShortPoint(
                currentShape.getPos().getX(),
                currentShape.getPos().getY() + dy
        );
        if (checkCollision(currentShape, nextPos)) {
            currentShape.setPos(nextPos);
        }
    }
    private void solidifyShape() {
        if (currentShape == null) return;

        for (Block block : currentShape.getBlocks()) {
            int blockRowStart = currentShape.getPos().getX() + (block.pos.getX() * Block.BLOCK_SIZE);
            int blockColStart = currentShape.getPos().getY() + (block.pos.getY() * Block.BLOCK_SIZE);

            for (int r = 0; r < Block.BLOCK_SIZE; r++) {
                for (int c = 0; c < Block.BLOCK_SIZE; c++) {
                    int absRow = blockRowStart + r;
                    int absCol = blockColStart + c;

                    if (absRow >= 0 && absRow < rows && absCol >= 0 && absCol < cols) {
                        grid[absRow][absCol] = block.definition[r][c];
                    }
                }
            }
        }

        this.currentShape = null;
    }
    public int getSolidHeight() {
        for (int y = rows - 1; y >= 0; y--) {
            for (int x = 0; x < cols; x++) {
                if (grid[y][x] != UsableColor.BLANK) {
                    return y + 1;
                }
            }
        }
        return 0;
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
            UsableColor[] current = {grid[i][0], grid[i][boundY]};
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

        l = l.stream().filter(
                x -> x.getValue() != UsableColor.BLANK.getGroup()
        ).collect(Collectors.toCollection(LinkedList::new));
        r = r.stream().filter(
                x -> x.getValue() != UsableColor.BLANK.getGroup()
        ).collect(Collectors.toCollection(LinkedList::new));

        if (l.isEmpty() && r.isEmpty())
            return;

        for (Pair<ShortPoint, ColorGroup> pair : l) {
            if (r.stream().anyMatch(p -> p.getValue().equals(pair.getValue()))) {
                int score = findPath(pair.getKey(), pair.getValue());
                if (score != -1) {
                    ScoreUpdateEvent event = new ScoreUpdateEvent(score);
                    EventDispatcher.publish(EventType.UPDATE_SCORE, event);
                }
            }
        }
    }
    private int findPath(ShortPoint start, ColorGroup color) {
        boolean[][] visited = new boolean[(int) rows][(int) cols];
        boolean reachedRight = false;
        Queue<ShortPoint> queue = new LinkedList<>();
        queue.add(start);
        visited[start.getX()][start.getY()] = true;
        List<ShortPoint> cluster = new ArrayList<>();

        while (!queue.isEmpty()) {
            ShortPoint current = queue.poll();
            int x = current.getX();
            int y = current.getY();

            cluster.add(current);
            if (y == cols - 1) {
                reachedRight = true;
            }
            if (!color.equals(grid[x][y].getGroup())) {
                continue;
            }

            final int[] dx = {-1, 1, 0, 0};
            final int[] dy = {0, 0, -1, 1};

            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];
                if (nx >= 0 && nx < rows && ny >= 0 && ny < cols) {
                    UsableColor c = grid[nx][ny];
                    if (c.getGroup().equals(color) && !visited[nx][ny]) {
                        visited[nx][ny] = true;
                        queue.add(new ShortPoint(nx, ny));
                    }
                }
            }
        }

        if (!reachedRight) {
            return -1;
        }

        AtomicInteger score = new AtomicInteger(0);
        cluster.forEach(p -> {
            grid[p.getX()][p.getY()] = UsableColor.BLANK;
            score.incrementAndGet();
        });
        return score.get();
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

        if (down == UsableColor.BLANK) {
            move(i, j, 0);
            return;
        }

        boolean nextL = (j > 0) && grid[i - 1][j - 1] == UsableColor.BLANK;
        boolean nextR = (j < cols - 1) && grid[i - 1][j + 1] == UsableColor.BLANK;

        if (nextL && nextR) {
            move(i, j, Math.random() > 0.5 ? 1 : -1);
        } else if (nextL) {
            move(i, j, -1);
        } else if (nextR) {
            move(i, j, 1);
        }
    }
    private void leftExtreme(int i, int j) {
        UsableColor down = grid[i - 1][j];
        UsableColor right = grid[i - 1][j + 1];
        UsableColor nextR = grid[i][j + 1];
        if (down == UsableColor.BLANK) {
            move(i, j, 0);
            return;
        }
        if (nextR == UsableColor.BLANK && right == UsableColor.BLANK) {
            move(i, j, 1);
        }
    }
    private void rightExtreme(int i, int j) {
        UsableColor down = grid[i - 1][j];
        UsableColor left = grid[i - 1][j - 1];
        UsableColor nextL = grid[i][j - 1];
        if (down == UsableColor.BLANK) {
            move(i, j, 0);
        } else if (left == UsableColor.BLANK && nextL == UsableColor.BLANK) {
            move(i, j, -1);
        }
    }
    private void move(int row, int col, int dx) {
        grid[row - 1][col + dx] = grid[row][col];
        grid[row][col] = UsableColor.BLANK;
    }
    private static void writeRectPixels(PixelWriter pw, int startX, int startY, int w, int h, Color c) {
        for (int x = startX; x < startX + w; x++) {
            for (int y = startY; y < startY + h; y++) {
                pw.setColor(x, y, c);
            }
        }
    }
}
