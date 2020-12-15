package pong;

import java.util.Random;
import javafx.animation.AnimationTimer;
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
    public double ballSpeed;
    public Point2D ballDirVector;

    // 50 is for padding, half of the screen height adjusted for the paddle size
    public Point2D posPlrPaddle = new Point2D(Constants._Padding.getX(),
            Constants._Y/2.0 - Constants._PaddleY/2.0);
    public Point2D posCompPaddle = new Point2D(Constants._X - Constants._Padding.getX() - Constants._PaddleX,
            Constants._Y/2.0 - Constants._PaddleY/2.0);

    public static final Random random = new Random();

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
            @Override
            public void handle(long currentNanoTime)
            {
                update();
            }
        }.start();

        primaryStage.show();

        launchBall();
    }

    // Draw the objects
    private void update(){
        // TODO pause state
        System.out.println(posPlrPaddle.getY() + " Update");
        gc.clearRect(0, 0, Constants._X, Constants._Y);

        updateBall();
        drawObjects();
        checkWallCollision();
        checkPaddleCollision(posPlrPaddle);
    }

    // TODO Pretty Up the Graphics
    // Starts the positions on the canvas
    private void drawObjects() {
        gc.setFill(Color.GREEN);
        gc.fillRect(0, 0, Constants._X, Constants._Y);
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

    // This is the listener for any time an event happens
    // Collision detection
    private void handleUserInput(KeyEvent e){
        KeyCode key = e.getCode();

        // Keyboard controls
        if (key == KeyCode.W && posPlrPaddle.getY() > 0 || key == KeyCode.UP && posPlrPaddle.getY() > 0 ){
            posPlrPaddle = posPlrPaddle.add(0, - Constants._PaddleSpeed);
            System.out.println("UP");
        }
        if (key == KeyCode.S && posPlrPaddle.getY() < 450 || key == KeyCode.DOWN && posPlrPaddle.getY() < 450 ){
            posPlrPaddle = posPlrPaddle.add(0, Constants._PaddleSpeed);
            System.out.println("DOWN");
        }

    }

    public void launchBall(){
        // Launch the ball
        boolean ballDirection = random.nextBoolean();
        // bound to an acute angle on start
        double ballAngle = Constants._PADDLE_ANGLES[random.nextInt(5) + 1];
        if (ballDirection){
            ballAngle *= -1;
        }
        ballSpeed = Constants._BallStartSpeed;
        ballDirVector = new Point2D(Math.cos(ballAngle), Math.sin(ballAngle));
    }

    public void updateBall(){
        // We're adding to the position of the gc based
        // on the angle of the bounce and the distance traveled
        posBall = posBall.add(ballDirVector.multiply(ballSpeed));
    }

    public void checkWallCollision(){
        boolean ballHitBottom = posBall.getY() > 500;
        boolean ballHitTop = posBall.getY() < + Constants._X/40;
        boolean ballHitLeft = posBall.getX() < 0;
        boolean ballHitRight = posBall.getX() > (800 - Constants._X/40);

        if (ballHitTop || ballHitBottom){
            ballDirVector = new Point2D(ballDirVector.getX(), -1 * ballDirVector.getY());
            //ballSpeed += Constants._BallAcceleration;
        }
        if(ballHitLeft || ballHitRight){
            ballDirVector = new Point2D(-1 * ballDirVector.getX(), ballDirVector.getY());
            //ballSpeed += Constants._BallAcceleration;
        }
    }

    // TODO Get ball colliding with paddles
    public void checkPaddleCollision(Point2D paddle){

        boolean ballHit;
        boolean ballOnPlrY = posBall.getY() + Constants._BallRadius > posPlrPaddle.getY()
                && posBall.getY() < posBall.getY() + Constants._PaddleY;
        boolean ballOnPlrX = posBall.getX() + Constants._BallRadius > posPlrPaddle.getX()
                && posBall.getX() < posBall.getX() + Constants._PaddleX;
        if (ballOnPlrX && ballOnPlrY){
            System.out.println("Ball hit paddle");
        }
    }

    // Get and set the pause state
    public enum State{
        PLAYING, PAUSED, ENDED;
    }
    private State state; // TODO set state
    public State getState() {
        return state;
    }

}
