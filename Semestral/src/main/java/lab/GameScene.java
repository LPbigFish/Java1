package lab;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class GameScene {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Canvas mainCanvas;

    @FXML
    private Label scoreLabel;

    @FXML
    private TableView<?> scoreTable;

    @FXML
    private Canvas shapeCanvas;

    @FXML
    void initialize() {
        assert anchorPane != null : "fx:id=\"anchorPane\" was not injected: check your FXML file 'GameScene.fxml'.";
        assert mainCanvas != null : "fx:id=\"mainCanvas\" was not injected: check your FXML file 'GameScene.fxml'.";
        assert scoreLabel != null : "fx:id=\"scoreLabel\" was not injected: check your FXML file 'GameScene.fxml'.";
        assert scoreTable != null : "fx:id=\"scoreTable\" was not injected: check your FXML file 'GameScene.fxml'.";
        assert shapeCanvas != null : "fx:id=\"shapeCanvas\" was not injected: check your FXML file 'GameScene.fxml'.";

    }

}
