package lab.scenes;

import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import lab.DrawingThread;
import lab.components.Grid;
import lab.components.Timer;
import lab.events.*;
import lab.score.ScoreData;
import lab.score.ScoreRepository;

public class GameController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Text finalScoreText;

    @FXML
    private Pane gameOverPane;

    @FXML
    private Canvas mainCanvas;

    @FXML
    private TextField nameInputBox;

    @FXML
    private Button exitBtn;

    @FXML
    private Label scoreLabel;

    @FXML
    private TableView<ScoreData> scoreTable;
    @FXML
    private TableColumn<ScoreData, String> columnName;
    @FXML
    private TableColumn<ScoreData, Integer> columnScore;

    @FXML
    private Button submitScoreBtn;

    private DrawingThread drawingThread;

    private List<ScoreData> scoreDataList;
    int score = 0;
    Integer speed = 80;
    boolean validName = false;
    private void startNewGame() {
        score = 0;
        speed = 80;
        scoreLabel.setText("Score: 0");

        if (drawingThread != null) {
            drawingThread.stop();
        }

        drawingThread = new DrawingThread(mainCanvas,
                List.of(
                        new Grid(new Point2D(mainCanvas.getWidth() / 2 - 100 * 2 - 25,20), new Point2D(200, 320)),
                        new Timer(new Point2D(2, 10))
                ));
        drawingThread.start();
    }

    @FXML
    void initialize() {
        assert anchorPane != null : "fx:id=\"anchorPane\" was not injected: check your FXML file 'GameScene.fxml'.";
        assert finalScoreText != null : "fx:id=\"finalScoreText\" was not injected: check your FXML file 'GameScene.fxml'.";
        assert gameOverPane != null : "fx:id=\"gameOverPane\" was not injected: check your FXML file 'GameScene.fxml'.";
        assert mainCanvas != null : "fx:id=\"mainCanvas\" was not injected: check your FXML file 'GameScene.fxml'.";
        assert nameInputBox != null : "fx:id=\"nameInputBox\" was not injected: check your FXML file 'GameScene.fxml'.";
        assert scoreLabel != null : "fx:id=\"scoreLabel\" was not injected: check your FXML file 'GameScene.fxml'.";
        assert scoreTable != null : "fx:id=\"scoreTable\" was not injected: check your FXML file 'GameScene.fxml'.";
        assert submitScoreBtn != null : "fx:id=\"submitScoreBtn\" was not injected: check your FXML file 'GameScene.fxml'.";

        Platform.runLater(() -> {
            gameOverPane.setVisible(false);
            gameOverPane.setDisable(true);
        });

        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnScore.setCellValueFactory(new PropertyValueFactory<>("score"));

        try {
            scoreDataList = ScoreRepository.load();
            scoreTable.getItems().clear();
            scoreTable.getItems().addAll(scoreDataList);
        } catch (Exception e) {
            Alert a = new  Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.getDialogPane().setContentText("Failed to save score!");
            a.show();
            return;
        }

        startEventListeners();
        startNewGame();
    }

    @FXML
    void onSubmit(ActionEvent event) {
        scoreDataList.add(new ScoreData(nameInputBox.getText(), score));
        scoreDataList = scoreDataList.stream()
                .collect(Collectors.toMap(
                        ScoreData::getName,
                        score -> score,
                        (existing, replacement) -> existing.getScore() >= replacement.getScore() ? existing : replacement
                ))
                .values()
                .stream()
                .sorted(Comparator.comparingInt(ScoreData::getScore).reversed())
                .collect(Collectors.toList());
        scoreTable.getItems().clear();
        scoreTable.getItems().addAll(scoreDataList);
        try {
            ScoreRepository.save(scoreDataList);
        } catch (Exception e) {
            Alert a = new  Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.getDialogPane().setContentText("Failed to save score!");
            a.show();
        }
        Platform.runLater(() -> {
            gameOverPane.setVisible(false);
            gameOverPane.setDisable(true);
            nameInputBox.clear();
        });
        EventDispatcher.publish(EventType.SWITCH_SCENES, new SwitchScenesEvent());
    }

    @FXML
    void onExit(ActionEvent event) {
        EventDispatcher.publish(EventType.SWITCH_SCENES, new SwitchScenesEvent());
    }
    private void startEventListeners() {
        EventDispatcher.listen(EventType.UPDATE_SCORE, (x) -> {
            if (x instanceof ScoreUpdateEvent s) {
                Platform.runLater(() -> {
                    this.score += s.getScore();
                    this.speed = 80 + this.score / 750;
                    InputHandler.getInstance().updateSpeed(this.speed);
                    drawingThread.updateSpeed(this.speed);
                    scoreLabel.setText("Score: " + this.score);
                });
            }
        });
        nameInputBox.textProperty().addListener((observable, oldValue, newValue) -> {
            String name = newValue.trim();
            if (name.length() < 2) {
                validName = false;
            }
            if (name.length() >= 2) {
                validName = true;
            }
            if (name.length() > 5) {
                validName = false;
            }
            submitScoreBtn.setDisable(!validName);
        });
        EventDispatcher.listen(EventType.GAME_OVER, (event) -> Platform.runLater(() -> {
                if (drawingThread != null) {
                    drawingThread.stop();
                }

                finalScoreText.setText(score + "");
                nameInputBox.setText("");
                nameInputBox.setPromptText("Enter your name");
                gameOverPane.setDisable(false);
                gameOverPane.setVisible(true);
                submitScoreBtn.setDisable(true);

                gameOverPane.toFront();
        }));
    }
    public void stop() {
        drawingThread.stop();
        Platform.runLater(() -> {
            gameOverPane.setVisible(false);
            gameOverPane.setDisable(true);
        });
    }
}
