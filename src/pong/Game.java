package pong;

import java.io.InputStream;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

// This is the root of the game
// Allows the game to preform scene initialization and the initial construction of the scene
public class Game extends GameScene {
    // Game objects
    public Ball ball = new Ball(Constants._ScreenSize.midpoint(Point2D.ZERO));
    public Paddle playerPaddle = new Paddle(new Point2D(Constants._Padding.getX(),
            Constants._Y/2.0 - Constants._PaddleY/2.0), 24.0);
    public Paddle computerPaddle = new Paddle(new Point2D(Constants._X - Constants._Padding.getX() - Constants._PaddleX,
            Constants._Y/2.0 - Constants._PaddleY/2.0), 5);

    //Scores
    public int playerScore = 0;
    public int computerScore = 0;

    // Constructor
    public Game(Stage primaryStage, Orchestrator orchestrator) {
        super(primaryStage, orchestrator);
        scene.setOnKeyPressed(this::handleUserInput);
        scene.setOnMouseMoved(this::handleMouseMovement);
    }

    // Show the the game and start playing it
    public void start() {
        stage.setScene(scene);
        stage.show();
        animationTimer.start();
    }

    // Freeze the game and show the pause menu
    public void pause() {
        stage.hide();
        animationTimer.stop();
    }

    // Reset paddle positions, ball positions, and scores
    public void reset() {
        stage.hide();
        animationTimer.stop();
        playerScore = 0;
        computerScore = 0;
        playerPaddle.pos = new Point2D(Constants._Padding.getX(),
                Constants._Y/2.0 - Constants._PaddleY/2.0);
        computerPaddle.pos = new Point2D(Constants._X - Constants._Padding.getX() - Constants._PaddleX,
                Constants._Y/2.0 - Constants._PaddleY/2.0);
        ball.launch(true);
    }

    // Draw the objects
    public void update() {
        gc.clearRect(0, 0, Constants._X, Constants._Y);
        ball.updatePosition();
        handleBallState(ball.checkCollisions());
        ball.checkPaddleCollision(playerPaddle.pos);
        ball.checkPaddleCollision(computerPaddle.pos);
        computerPaddle.runAI(ball.pos);
        drawObjects();
    }

    // Starts the positions on the canvas
    private void drawObjects() {
        // draw the screen
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, Constants._X, Constants._Y);
        gc.setStroke(Color.WHITE);
        gc.setFill(Color.WHITE);

        // Draw dotted line down the middle
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
        gc.fillOval(ball.pos.getX(), ball.pos.getY(),
                Constants._BallRadius, Constants._BallRadius);
        gc.strokeOval(ball.pos.getX(),
                ball.pos.getY(),
                Constants._BallRadius, Constants._BallRadius);

        // draw score
        gc.setFont(new Font(50));
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

    private void handleMouseMovement(MouseEvent e){
        // The center of the paddle follows the cursor
        playerPaddle.setLatitude(e.getY() - Constants._PaddleY / 2);
    }

    // Collision detection
    private void handleUserInput(KeyEvent e) {
        KeyCode key = e.getCode();
        // Keyboard controls
        if (key == KeyCode.W || key == KeyCode.UP) {
            playerPaddle.moveUp();
        }
        if (key == KeyCode.S || key == KeyCode.DOWN) {
            playerPaddle.moveDown();
        }
        if(key == KeyCode.ESCAPE) {
            orchestrator.setState(GameState.PAUSE);
        }
    }

    public void handleBallState(BallState state) {
        if (state == BallState.HIT_RIGHT_WALL) {
            playerScore++;
            if (playerScore >= Constants._WinningScore) {
                reset();
                orchestrator.setState(GameState.WON);
            }
            ball.launch(false);
        }
        if (state == BallState.HIT_LEFT_WALL) {
            computerScore++;
            if (computerScore >= Constants._WinningScore) {
                reset();
                orchestrator.setState(GameState.LOST);
            }
            ball.launch(true);
        }
    }

}
