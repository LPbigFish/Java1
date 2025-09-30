package lab;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawingThread extends AnimationTimer {

	private final Canvas canvas;
	private final GraphicsContext gc;

    private double xPos = 0;

    private long lastFrame = System.currentTimeMillis();

	public DrawingThread(Canvas canvas) {
		this.canvas = canvas;
		this.gc = canvas.getGraphicsContext2D();
		
	}

	/**
	  * Draws objects into the canvas. Put you code here. 
	 */
	@Override
	public void handle(long now) {
        double currentFps = 1 / ((now -  lastFrame) / 1_000_000_000D);
        lastFrame = now;
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.AQUA);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.BLACK);
        drawPicture(20, 5);
        gc.fillText("Current FPS: " + currentFps, 0, 80);
        xPos += 0.1;
        if (xPos > canvas.getWidth()) {
            xPos = 0;
        }
	}

    private void drawPicture(double x, double y) {
        gc.fillRect(x + xPos, y,  xPos + 30, y + 30);
    }
}
