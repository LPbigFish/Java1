package lab;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;

public class GameController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button button;

    @FXML
    private Canvas canvas;

    @FXML
    private Slider speedSlider;

    @FXML
    private Slider angleSlider;

    private World world;

    private DrawingThread drawingThread;

    @FXML
    void initialize() {
        assert canvas != null : "fx:id=\"canvas\" was not injected: check your FXML file 'application.fxml'.";
        assert button != null : "fx:id=\"button\" was not injected: check your FXML file 'application.fxml'.";        this.world = new World(canvas.getWidth(), canvas.getHeight());
        this.drawingThread = new DrawingThread(canvas, world);
        drawingThread.start();
        angleSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            world.getCannon().setAngle(newVal.doubleValue());
        });
    }

    @FXML
    void fireButtonPressed(ActionEvent event) {
        world.reload(speedSlider.getValue());
    }

    public void stop() {
        drawingThread.stop();
    }
}
