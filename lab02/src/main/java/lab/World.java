package lab;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class World {
    private double width;
    private double height;
    private Bullet bullet;
    private Bullet bullet2;
    private BulletAnimated bulletAnimated;

    private Cannon cannon;

    public World(double width, double height) {
        this.width = width;
        this.height = height;

        bullet = new Bullet();
        bullet2 = new Bullet(new Point2D(10, 300), new Point2D(100, 50), new Point2D(0, -9.81));
        bulletAnimated = new BulletAnimated(new Point2D(10, 100), new Point2D(100, 50), new Point2D(0, -9.81));
        cannon = new Cannon(new Point2D(20, 20), 45);
    }

    public void draw(GraphicsContext gc) {
        gc.save();
        // Change coordinate system to human like
        gc.scale(1, -1);
        gc.translate(0, -height);
        bullet.draw(gc);
        bullet2.draw(gc);
        bulletAnimated.draw(gc);
        cannon.draw(gc);

        gc.restore();
    }

    public void simulate(double deltaTime) {
        bullet.simulate(deltaTime);
        bullet2.simulate(deltaTime);
        bulletAnimated.simulate(deltaTime);
        cannon.simulate(deltaTime);
    }
}
