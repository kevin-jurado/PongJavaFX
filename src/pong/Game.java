package pong;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

// This is the root of the game
// Allows the game to preform scene initialization and the initial construction of the scene
public class Game{

    // Set up initial positions
    public Point2D posBall = Constants._ScreenSize.midpoint(Point2D.ZERO);
    // 50 is for padding, half of the screen height adjusted for the paddle size
    public Point2D posPlrPaddle = new Point2D(Constants._Padding.getX(),
            Constants._Y/2.0 - Constants._PaddleY/2.0);
    public Point2D posCompPaddle = new Point2D(Constants._X - Constants._Padding.getX() - Constants._PaddleX,
            Constants._Y/2.0 - Constants._PaddleY/2.0);

    public boolean isRunning = true;
    public Scene scene;
    public GraphicsContext gc;

    public Game(Stage primaryStage) {
        Group root = new Group();
        scene = new Scene(root);
        scene.setOnKeyPressed(this::handleUserInput);
        Canvas canvas = new Canvas(Constants._X, Constants._Y);

        gc = canvas.getGraphicsContext2D();

        root.getChildren().add(canvas);
        primaryStage.setScene(scene);

        new AnimationTimer()
        {
            // TODO Try using an override method
            @Override
            public void handle(long currentNanoTime)
            {
                //long startNanoTime;
                //double t = (currentNanoTime - startNanoTime) / 1000000000.0;

                update();
            }
        }.start();

        primaryStage.show();

    }

    // Draw the objects
    private void update(){
        System.out.println(posPlrPaddle.getY() + " Update");
        gc.clearRect(0, 0, Constants._X, Constants._Y);
        drawObjects();
    }

    // TODO Pretty Up the Graphics
    // Starts the positions on the canvas
    private void drawObjects() {
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLUE);

        // Draw the paddles on screen
        gc.strokeRect(posPlrPaddle.getX(), posPlrPaddle.getY(),
                Constants._PaddleX, Constants._PaddleY);
        gc.strokeRect(posCompPaddle.getX(), posCompPaddle.getY(),
                Constants._PaddleX, Constants._PaddleY);

        // Draw the ball on screen
        gc.strokeOval(posBall.getX(),
                posBall.getY() - Constants._BallRadius,
                Constants._BallRadius, Constants._BallRadius);
    }

    // This is the listener for any time an even happens
    // Collision detection
    private void handleUserInput(KeyEvent e){
        KeyCode key = e.getCode();

        if (key == KeyCode.W && posPlrPaddle.getY() > 0 || key == KeyCode.UP && posPlrPaddle.getY() > 0 ){
            posPlrPaddle = posPlrPaddle.add(0, - Constants._PaddleSpeed);
            System.out.println("UP");
        }
        if (key == KeyCode.S && posPlrPaddle.getY() < 450 || key == KeyCode.DOWN && posPlrPaddle.getY() < 450 ){
            posPlrPaddle = posPlrPaddle.add(0, Constants._PaddleSpeed);
            System.out.println("DOWN");
        }
    }


}
