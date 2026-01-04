package lab;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lab.events.EventDispatcher;
import lab.events.EventType;
import lab.events.InputHandler;
import lab.scenes.GameController;
import lab.scenes.MenuController;

import java.io.IOException;


public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    private GameController gameController;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        try {
            this.primaryStage = primaryStage;
            this.primaryStage.setTitle("SANDTRIS");
            this.primaryStage.setOnCloseRequest(this::exitProgram);
            this.primaryStage.setResizable(false);
            this.primaryStage.setMinHeight(681);
            this.primaryStage.setMinWidth(754);

            EventDispatcher.listen(EventType.SWITCH_SCENES, (x) ->
                Platform.runLater(() -> {
                    try {
                        cleanupGame();
                        showMainMenu();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                })
            );

            showMainMenu();
            this.primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showMainMenu() throws IOException {
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("MenuScene.fxml"));
        Parent root = menuLoader.load();
        MenuController menuController = menuLoader.getController();
        menuController.setApp(this);
        primaryStage.setScene(new Scene(root));
    }

    public void startGame() {
        try {
            cleanupGame();

            FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("GameScene.fxml"));
            Scene root = new Scene(gameLoader.load());

            gameController = gameLoader.getController();

            InputHandler.init(root);

            primaryStage.setScene(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cleanupGame() {
        if (gameController != null) {
            gameController.stop();
            gameController = null;
        }
    }

    @Override
    public void stop() throws Exception {
        cleanupGame();
        super.stop();
    }

    private void exitProgram(WindowEvent evt) {
        cleanupGame();
        Platform.exit();
        System.exit(0);
    }
}
