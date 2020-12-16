package pong;
import javafx.application.Application;
import javafx.stage.Stage;

// The orchestrator controls the scene
public class Orchestrator extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("PONG");
        Game pong = new Game(primaryStage, this);
        //StartScreen startScreen = new StartScreen(primaryStage, this);
        //startScreen.start();
        pong.reset();
        pong.start();
    }

}
