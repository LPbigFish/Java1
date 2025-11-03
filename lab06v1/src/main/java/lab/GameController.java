package lab;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

public class GameController {

    @FXML
    private Slider angle;

    @FXML
    private Slider speed;

    @FXML
    private Label hitCountLabel;

    @FXML
    private Canvas canvas;

    private World world;
    private DrawingThread timer;
    private int hitCount = 0;

    @FXML
    void fire(ActionEvent event) {
        Bullet bullet = new BulletAnimated(world, world.getCannon().getPosition(), speed.getValue(), world.getCannon().getAngle(), World.GRAVITY);
        bullet.addHitListener(() -> System.out.println("FIREEEE"));
        bullet.addHitListener(this::increaseHits);
        world.add(bullet);
    }

    @FXML
    void initialize() {
        assert angle != null : "fx:id=\"angle\" was not injected: check your FXML file 'gameWindow.fxml'.";
        assert canvas != null : "fx:id=\"canvas\" was not injected: check your FXML file 'gameWindow.fxml'.";
        assert hitCountLabel != null : "fx:id=\"hitCountLabel\" was not injected: check your FXML file 'gameWindow.fxml'.";
        assert speed != null : "fx:id=\"speed\" was not injected: check your FXML file 'gameWindow.fxml'.";

        world = new World(canvas.getWidth(), canvas.getHeight());
        timer = new DrawingThread(canvas, world);
        timer.start();
        angle.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                world.getCannon().setAngle(newValue.doubleValue());
            }
        });
    }

    private void increaseHits() {
        hitCount++;
        hitCountLabel.setText(String.valueOf(hitCount));
    }

    public void stop() {
        timer.stop();
    }

}
