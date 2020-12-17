package pong;
import javafx.application.Application;
import javafx.stage.Stage;

enum GameState{
    START, PLAYING, PAUSE, WON, LOST
}

// The orchestrator controls the scenes
public class Orchestrator extends Application {

    public Game pong;
    private StartScreen startScreen;
    private PauseScreen pauseScreen;
    public GameScene currentScene;
    public VictoryScreen victoryScreen;
    public LoseScreen loseScreen;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage){
        primaryStage.setTitle("PONG");
        pong = new Game(primaryStage, this);
        startScreen = new StartScreen(primaryStage, this);
        pauseScreen = new PauseScreen(primaryStage, this);
        victoryScreen = new VictoryScreen(primaryStage, this);
        loseScreen = new LoseScreen(primaryStage, this);
        pong.reset();
        currentScene = startScreen;
        setState(GameState.START);
    }

    public void setState(GameState state){
        // Temporarily store the new game scene
        GameScene scene = new GameScene();
        currentScene.pause();

        if (state == GameState.START) {
            scene = startScreen;
        } else if (state == GameState.PLAYING) {
            scene = pong;
        } else if (state == GameState.PAUSE) {
            scene = pauseScreen;
        } else if (state == GameState.WON) {
            scene = victoryScreen;
        } else if (state == GameState.LOST) {
            scene = loseScreen;
        }
        currentScene = scene;
        currentScene.start();
    }
}
