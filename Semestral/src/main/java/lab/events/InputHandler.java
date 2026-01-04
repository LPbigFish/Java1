package lab.events;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import java.util.HashSet;
import java.util.Set;

public class InputHandler {
    private final Set<KeyCode> activeKeys = new HashSet<>();
    private long AFTER_DELAY = 10_000_000;
    private long lastLeftTime = 0;
    private long lastRightTime = 0;
    private final AnimationTimer timer;

    private static InputHandler instance;
    public static void init(Scene scene) {
        if (instance == null) {
            instance = new InputHandler(scene);
        }
    }
    public static InputHandler getInstance() {
        return instance;
    }
    public void updateSpeed(int speed) {
        AFTER_DELAY = 10_000_000 - (long)speed * 3_000;
    }
    public InputHandler(Scene scene) {
        scene.setOnKeyPressed(e -> onDown(e.getCode()));
        scene.setOnKeyReleased(e -> onUp(e.getCode()));

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update(now);
            }
        };
        timer.start();
    }
    private void update(long now) {
        if (activeKeys.contains(KeyCode.KP_LEFT) || activeKeys.contains(KeyCode.LEFT) || activeKeys.contains(KeyCode.A)) {
            if (now - lastLeftTime >= 0) {
                EventDispatcher.publish(EventType.MOVE_LEFT, new MoveEvent(EventType.MOVE_LEFT));
                lastLeftTime = now + AFTER_DELAY;
            }
        }

        if (activeKeys.contains(KeyCode.RIGHT) || activeKeys.contains(KeyCode.KP_RIGHT) || activeKeys.contains(KeyCode.D)) {
            if (now - lastRightTime >= 0) {
                EventDispatcher.publish(EventType.MOVE_RIGHT, new MoveEvent(EventType.MOVE_RIGHT));
                lastRightTime = now + AFTER_DELAY;
            }
        }
    }
    private void onDown(KeyCode code) {
        if (activeKeys.contains(code)) return;
        activeKeys.add(code);

        long FIRST_DELAY = 150_000_000;
        if (activeKeys.contains(KeyCode.KP_LEFT) || code == KeyCode.LEFT || code == KeyCode.A) {
            EventDispatcher.publish(EventType.MOVE_LEFT, new MoveEvent(EventType.MOVE_LEFT));
            lastLeftTime = System.nanoTime() + FIRST_DELAY;
        }
        else if (activeKeys.contains(KeyCode.KP_RIGHT) || code == KeyCode.RIGHT || code == KeyCode.D) {
            EventDispatcher.publish(EventType.MOVE_RIGHT, new MoveEvent(EventType.MOVE_RIGHT));
            lastRightTime = System.nanoTime() + FIRST_DELAY;
        }
    }
    private void onUp(KeyCode code) {
        activeKeys.remove(code);
    }

    public void stop() {
        timer.stop();
    }
}