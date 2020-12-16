package pong;

import java.util.Random;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

// This is the root of the game
// Allows the game to preform scene initialization and the initial construction of the scene
public class Game extends GameScene{
    // Set up initial positions
    public Point2D posBall = Constants._ScreenSize.midpoint(Point2D.ZERO);
    public double ballSpeed;
    public Point2D ballDirVector;
    public Point2D posPlrPaddle = new Point2D(Constants._Padding.getX(),
            Constants._Y/2.0 - Constants._PaddleY/2.0);
    public Point2D posCompPaddle = new Point2D(Constants._X - Constants._Padding.getX() - Constants._PaddleX,
            Constants._Y/2.0 - Constants._PaddleY/2.0);
    //Scores
    public int playerScore = 0;
    public int computerScore = 0;

    public static final Random random = new Random();

    // Constructor
    public Game(Stage primaryStage, Orchestrator orchestrator) {
        super(primaryStage, orchestrator);
//        Group root = new Group();
//        scene = new Scene(root);
          scene.setOnKeyPressed(this::handleUserInput);
//        Canvas canvas = new Canvas(Constants._X, Constants._Y);
//
//        gc = canvas.getGraphicsContext2D();
//
//        root.getChildren().add(canvas);
//        primaryStage.setScene(scene);
//
//        new AnimationTimer()
//        {
//            @Override
//            public void handle(long currentNanoTime)
//            {
//                update();
//            }
//        }.start();
//        primaryStage.show();
//        start(true);
    }

    // Show the the game and start playing it
    public void start(){
        this.stage.show();
        this.animationTimer.start();
    }

    public void pause(){
        this.stage.hide();
        this.animationTimer.stop();
    }

    public void reset(){
        this.stage.hide();
        this.animationTimer.stop();
        this.launch(true);
    }

    // Draw the objects
    public void update(){
        // TODO pause state
        gc.clearRect(0, 0, Constants._X, Constants._Y);

        updateBall();
        drawObjects();
        checkWallCollision();
        checkPaddleCollision(posPlrPaddle);
        checkPaddleCollision(posCompPaddle);
        handicapAI();
    }

    // TODO Pretty Up the Graphics
    // Starts the positions on the canvas
    private void drawObjects() {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, Constants._X, Constants._Y);
        gc.setStroke(Color.WHITE);
        gc.setFill(Color.WHITE);

        // Draw the paddles on screen
        gc.fillRect(posPlrPaddle.getX(), posPlrPaddle.getY(),
                Constants._PaddleX, Constants._PaddleY);
        gc.strokeRect(posPlrPaddle.getX(), posPlrPaddle.getY(),
                Constants._PaddleX, Constants._PaddleY);
        gc.fillRect(posCompPaddle.getX(), posCompPaddle.getY(),
                Constants._PaddleX, Constants._PaddleY);
        gc.strokeRect(posCompPaddle.getX(), posCompPaddle.getY(),
                Constants._PaddleX, Constants._PaddleY);

        // Draw the ball on screen
        gc.fillOval(posBall.getX(), posBall.getY(),
                Constants._BallRadius, Constants._BallRadius);
        gc.strokeOval(posBall.getX(),
                posBall.getY(),
                Constants._BallRadius, Constants._BallRadius);

        // draw points
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText(
                Integer.toString(playerScore),
                200, 50
        );
        gc.fillText(
                Integer.toString(computerScore),
                600, 50
        );
    }

    // This is the listener for any time an event happens
    // Collision detection
    private void handleUserInput(KeyEvent e){
        KeyCode key = e.getCode();
        // Keyboard controls
        if (key == KeyCode.W && posPlrPaddle.getY() > 0 || key == KeyCode.UP && posPlrPaddle.getY() > 0 ){
            posPlrPaddle = posPlrPaddle.add(0, - Constants._PaddleSpeed);
        }
        if (key == KeyCode.S && posPlrPaddle.getY() < 450 || key == KeyCode.DOWN && posPlrPaddle.getY() < 450 ){
            posPlrPaddle = posPlrPaddle.add(0, Constants._PaddleSpeed);
        }
    }

    public void launch(boolean playerServe){
        // Launch the ball and reset the screen
        // Keep the starting angle between 15 and 45 degrees
        double ballAngle = Constants.degreesToRadians(random.nextDouble() * 30 + 15);
        if(random.nextBoolean()){
            // Randomly flip over x-axis
            ballAngle -= Math.PI/4;
        }
        if (!playerServe){
            // send ball towards opponent
            ballAngle -= Math.PI;
        }
        ballSpeed = Constants._BallStartSpeed;
        ballDirVector = new Point2D(Math.cos(ballAngle), Math.sin(ballAngle));
        posBall = Constants._ScreenSize.midpoint(Point2D.ZERO);
        System.out.println(ballAngle);
    }

    public void updateBall(){
        posBall = posBall.add(ballDirVector.multiply(ballSpeed));
    }

    public void checkWallCollision(){
        boolean ballHitBottom = posBall.getY() + Constants._BallRadius >= Constants._Y;
        boolean ballHitTop = posBall.getY() <= 0;
        boolean ballHitLeft = posBall.getX() <= 0;
        boolean ballHitRight = posBall.getX() + Constants._BallRadius >= Constants._X;

        if (ballHitTop || ballHitBottom){
            ballDirVector = new Point2D(ballDirVector.getX(), -1 * ballDirVector.getY());
        }
        if(ballHitLeft || ballHitRight){
            ballDirVector = new Point2D(-1 * ballDirVector.getX(), ballDirVector.getY());
        }
        if(ballHitRight){
            playerScore++;
            launch(false);
        }
        if(ballHitLeft){
            computerScore++;
            launch(true);
        }
    }

    public void checkPaddleCollision(Point2D paddle){

        // Check for ball being in contact with paddle side
        boolean ballOnPlrY = (posBall.getY() + Constants._BallRadius > paddle.getY() &&
                        posBall.getY() + Constants._BallRadius < paddle.getY() + Constants._PaddleY)
                || (posBall.getY() > paddle.getY() && posBall.getY() < paddle.getY() + Constants._PaddleY);

        boolean ballOnPlrX = (posBall.getX() + Constants._BallRadius > paddle.getX() &&
                        posBall.getX() + Constants._BallRadius < paddle.getX() + Constants._PaddleX)
                ||(posBall.getX() > paddle.getX() && posBall.getX() < paddle.getX() + Constants._PaddleX);
        // Paddle collides
        if (ballOnPlrX && ballOnPlrY) {
            ballDirVector = new Point2D(-1 * ballDirVector.getX(), ballDirVector.getY());
            ballSpeed += Constants._BallAcceleration;
            // if it's moving to the right
            if (ballDirVector.getX() > 0) {
                posBall = new Point2D(paddle.getX() - Constants._BallRadius, posBall.getY());
            }else{
                posBall = new Point2D(paddle.getX() + Constants._PaddleX, posBall.getY());
            }
        }
    }

    public void handicapAI() {
        // distance of the center of the paddle from the ball
        double compLatitude = posCompPaddle.getY() + Constants._PaddleY * 0.5;
        double ballLatitude = posBall.getY() + Constants._BallRadius / 2;
        double difference = compLatitude - ballLatitude;
        if (difference < -30){
            posCompPaddle = posCompPaddle.add(0, Constants._CompPaddleSpeed);
        }else if (difference > 30){
            posCompPaddle = posCompPaddle.add(0, -Constants._CompPaddleSpeed);
        }
    }

}
