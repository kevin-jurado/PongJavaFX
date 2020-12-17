package pong;

import java.util.Random;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

// This is the root of the game
// Allows the game to preform scene initialization and the initial construction of the scene
public class Game extends GameScene{
    // Set up initial positions
    public Point2D posBall = Constants._ScreenSize.midpoint(Point2D.ZERO);
    public double ballSpeed;
    public Point2D ballDirVector;

    public Paddle playerPaddle = new Paddle(new Point2D(Constants._Padding.getX(),
            Constants._Y/2.0 - Constants._PaddleY/2.0), 24.0);
    public Paddle computerPaddle = new Paddle(new Point2D(Constants._X - Constants._Padding.getX() - Constants._PaddleX,
            Constants._Y/2.0 - Constants._PaddleY/2.0), 5);

    //Scores
    public int playerScore = 0;
    public int computerScore = 0;

    public static final Random random = new Random();

    // Constructor
    public Game(Stage primaryStage, Orchestrator orchestrator) {
        super(primaryStage, orchestrator);
          scene.setOnKeyPressed(this::handleUserInput);
          scene.setOnMouseMoved(this::handleMouseMovement);
    }

    // Show the the game and start playing it
    public void start(){
        this.stage.setScene(scene);
        this.stage.show();
        this.animationTimer.start();
    }

    // Freeze the game and show the pause menu
    public void pause(){
        this.stage.hide();
        this.animationTimer.stop();
    }

    // Reset paddle positions, ball positions, and scores
    public void reset(){
        this.stage.hide();
        this.animationTimer.stop();
        this.playerScore = 0;
        this.computerScore = 0;
        playerPaddle.pos = new Point2D(Constants._Padding.getX(),
                Constants._Y/2.0 - Constants._PaddleY/2.0);
        computerPaddle.pos = new Point2D(Constants._X - Constants._Padding.getX() - Constants._PaddleX,
                Constants._Y/2.0 - Constants._PaddleY/2.0);
        this.launch(true);
    }

    // Draw the objects
    public void update(){
        // Erases the rectangle
        gc.clearRect(0, 0, Constants._X, Constants._Y);
        updateBall();
        drawObjects();
        checkWallCollision();
        checkPaddleCollision(playerPaddle.pos);
        checkPaddleCollision(computerPaddle.pos);
        handicapAI();
    }

    // Starts the positions on the canvas
    private void drawObjects() {
        // draw the screen
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, Constants._X, Constants._Y);
        gc.setStroke(Color.WHITE);
        gc.setFill(Color.WHITE);
        int partitions = 50;
        double height = Constants._Y / partitions;
        for (int i = 0; i < partitions; i++) {
            if (i % 2 == 0) {
                gc.fillRect(
                        Constants._X / 2,
                        height * i + height / 2,
                        2,
                        height);
            }
        }

        // Draw the paddles on screen
        gc.fillRect(playerPaddle.pos.getX(), playerPaddle.pos.getY(),
                Constants._PaddleX, Constants._PaddleY);
        gc.strokeRect(playerPaddle.pos.getX(), playerPaddle.pos.getY(),
                Constants._PaddleX, Constants._PaddleY);
        gc.fillRect(computerPaddle.pos.getX(), computerPaddle.pos.getY(),
                Constants._PaddleX, Constants._PaddleY);
        gc.strokeRect(computerPaddle.pos.getX(), computerPaddle.pos.getY(),
                Constants._PaddleX, Constants._PaddleY);

        // Draw the ball on screen
        gc.fillOval(posBall.getX(), posBall.getY(),
                Constants._BallRadius, Constants._BallRadius);
        gc.strokeOval(posBall.getX(),
                posBall.getY(),
                Constants._BallRadius, Constants._BallRadius);

        // draw points
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(new Font(30));
        gc.fillText(
                Integer.toString(playerScore),
                200, 50
        );
        gc.fillText(
                Integer.toString(computerScore),
                600, 50
        );
    }

    private void handleMouseMovement(MouseEvent e){
        // The center of the paddle follows the cursor
        playerPaddle.setLatitude(e.getY() - Constants._PaddleY / 2);
    }

    // Collision detection
    private void handleUserInput(KeyEvent e){
        KeyCode key = e.getCode();
        // Keyboard controls
        if (key == KeyCode.W || key == KeyCode.UP){
            playerPaddle.moveUp();
        }
        if (key == KeyCode.S || key == KeyCode.DOWN){
            playerPaddle.moveDown();
        }
        if(key == KeyCode.ESCAPE){
            orchestrator.setState(GameState.PAUSE);
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
    }

    public void updateBall(){
        posBall = posBall.add(ballDirVector.multiply(ballSpeed));
    }

    public void checkWallCollision(){
        // Definitions for wall collision
        boolean ballHitBottom = posBall.getY() + Constants._BallRadius >= Constants._Y;
        boolean ballHitTop = posBall.getY() <= 0;
        boolean ballHitLeft = posBall.getX() <= 0;
        boolean ballHitRight = posBall.getX() + Constants._BallRadius >= Constants._X;

        if (ballHitTop || ballHitBottom) {
            ballDirVector = new Point2D(ballDirVector.getX(), -1 * ballDirVector.getY());
        }
        if (ballHitLeft || ballHitRight) {
            ballDirVector = new Point2D(-1 * ballDirVector.getX(), ballDirVector.getY());
        }
        if (ballHitRight) {
            playerScore++;
            if (playerScore >= Constants._Goal_Area) {
                this.reset();
                this.orchestrator.setState(GameState.WON);
            }
            launch(false);
        }
        if (ballHitLeft) {
            computerScore++;
            if (computerScore >= Constants._Goal_Area) {
                this.reset();
                this.orchestrator.setState(GameState.LOST);
            }
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
        double compLatitude = computerPaddle.pos.getY() + Constants._PaddleY * 0.5;
        double ballLatitude = posBall.getY() + Constants._BallRadius / 2;
        double difference = compLatitude - ballLatitude;
        if (difference < -30) {
            computerPaddle.moveDown();
        } else if (difference > 30) {
            computerPaddle.moveUp();
        }
    }

}
