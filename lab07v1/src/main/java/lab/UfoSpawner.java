package lab;

import javafx.scene.canvas.GraphicsContext;

import java.util.Random;

public class UfoSpawner implements DrawableSimulable {
    private static final Random random = new Random();

    long nextSpawn;

    final World world;

    public UfoSpawner(World world) {
        nextSpawn = System.currentTimeMillis();
        this.world = world;
    }

    @Override
    public void draw(GraphicsContext gc) {}

    @Override
    public void simulate(double deltaTime) {
        if (nextSpawn >= System.currentTimeMillis()) {
            return;
        }

        world.add(new Ufo(world));
        nextSpawn = System.currentTimeMillis() + random.nextLong(10000);
    }
}
