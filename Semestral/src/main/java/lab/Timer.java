package lab;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;

import java.time.Duration;

public class Timer extends RenderableEntity {
    private long startTime;

    public Timer(Point2D pos) {
        super(pos);
        startTime = System.currentTimeMillis();
    }

    @Override
    protected void internalDraw(GraphicsContext gc) {
        gc.setFont(Font.font(5));
        Duration duration = Duration.ofMillis(System.currentTimeMillis() - startTime);
        int minutes = (int) (duration.toMinutes() % 60);
        int seconds = (int) (duration.toSeconds() % 60);
        gc.fillText(String.format("%02d:%02d.%03d", minutes, seconds, duration.toMillis() % 1000), pos.getX(), pos.getY());

    }
}
