package lab;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Objects;

/**
 * Class <b>App</b> - extends class Application and it is an entry point of the program
 *
 * @author Java I
 */
public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    GameController gameController;

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("/lab/application.fxml"));
            Parent root = gameLoader.load();
            gameController = gameLoader.getController();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("application.css")).toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.resizableProperty().set(false);
            primaryStage.setTitle("Java 1 - 5th laboratory");
            primaryStage.show();
            // Exit program when main window is closed
            primaryStage.setOnCloseRequest(this::exitProgram);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    private void exitProgram(WindowEvent evt) {
        System.exit(0);
    }
}
