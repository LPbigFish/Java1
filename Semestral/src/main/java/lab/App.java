package lab;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lab.objects.GameStore;

import java.util.Objects;
import java.util.logging.Logger;

/**
 * Class <b>App</b> - extends class Application and it is an entry point of the program
 *
 * @author Java I
 */
public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private GameStore store;
    private Canvas canvas;
    private AnimationTimer timer;
    Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public void start(Stage primaryStage) {
        try {
            //Construct a main window with a canvas.
            Group root = new Group();
            canvas = new Canvas(1280, 960);
            root.getChildren().add(canvas);
            Scene scene = new Scene(root, 1280, 960);
            scene.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
                logger.info(String.valueOf(e.getCode()));
                switch (e.getCode()) {
                    case W: store.velX = 10;
                        break;
                    case S: store.velX = -10;
                        break;
                    case A: store.rotation -= 0.5;
                        break;
                    case D: store.rotation += 0.5;
                        break;
                    case SPACE: store.measureDistanceFromKey();
                        break;
                    case ESCAPE: return;
                    default: e.consume();
                        break;
                }
            });
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("application.css")).toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.resizableProperty().set(false);
            primaryStage.setTitle("Adventure");
            primaryStage.show();
            //Exit program when main window is closed
            primaryStage.setOnCloseRequest(this::exitProgram);
            store =  new GameStore();
            timer = new DrawingThread(canvas, store);
            timer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() throws Exception {
        timer.stop();
        super.stop();
    }

    private void exitProgram(WindowEvent evt) {
        System.exit(0);
    }
}
