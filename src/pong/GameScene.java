package pong;


import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class GameScene{
    // Variable Declaration
    public Scene scene;
    public GraphicsContext gc;
    public Orchestrator orchestrator;
    public AnimationTimer animationTimer;
    public Stage stage;

    // Default constructor
    public GameScene() {}

    // Constructor
    public GameScene(Stage stage, Orchestrator orchestrator){
        Group root = new Group();
        scene = new Scene(root);
        Canvas canvas = new Canvas(Constants._X, Constants._Y);
        gc = canvas.getGraphicsContext2D();
        this.orchestrator = orchestrator;
        this.stage = stage;
        root.getChildren().add(canvas);
        stage.setScene(scene);
        animationTimer = new AnimationTimer()
        {
            @Override
            public void handle(long currentNanoTime)
            {
                update();
            }
        };
    }

    public void start(){}
    public void pause(){}
    public void reset(){}
    public void update(){}
}
