package pong;

import javafx.geometry.Point2D;

import java.util.Random;

enum BallState {
    MOVING,
    HIT_LEFT_WALL,
    HIT_RIGHT_WALL
}

public class Ball {
    public Point2D pos;
    public double speed;
    public Point2D direction;
    public static final Random random = new Random();

    public Ball(Point2D pos){
        this.pos = pos;
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
        speed = Constants._BallStartSpeed;
        direction = new Point2D(Math.cos(ballAngle), Math.sin(ballAngle));
        pos = Constants._ScreenSize.midpoint(Point2D.ZERO);
    }
    
    public BallState checkCollisions() {
        // Definitions for wall collision
        boolean ballHitBottom = pos.getY() + Constants._BallRadius >= Constants._Y;
        boolean ballHitTop = pos.getY() <= 0;
        boolean ballHitLeft = pos.getX() <= 0;
        boolean ballHitRight = pos.getX() + Constants._BallRadius >= Constants._X;

        if (ballHitTop || ballHitBottom) {
            direction = new Point2D(direction.getX(), -1 * direction.getY());
        }
        if (ballHitLeft || ballHitRight) {
            direction = new Point2D(-1 * direction.getX(), direction.getY());
        }
        if (ballHitLeft) {
            return BallState.HIT_LEFT_WALL;
        } else if (ballHitRight) {
            return BallState.HIT_RIGHT_WALL;
        } else {
            return BallState.MOVING;
        }
    }

    public void checkPaddleCollision(Point2D paddle){
        // Check for ball being in contact with paddle side
        boolean ballOnPlrY = (pos.getY() + Constants._BallRadius > paddle.getY() &&
                pos.getY() + Constants._BallRadius < paddle.getY() + Constants._PaddleY)
                || (pos.getY() > paddle.getY() && pos.getY() < paddle.getY() + Constants._PaddleY);

        boolean ballOnPlrX = (pos.getX() + Constants._BallRadius > paddle.getX() &&
                pos.getX() + Constants._BallRadius < paddle.getX() + Constants._PaddleX)
                ||(pos.getX() > paddle.getX() && pos.getX() < paddle.getX() + Constants._PaddleX);
        // Paddle collides
        if (ballOnPlrX && ballOnPlrY) {
            direction = new Point2D(-1 * direction.getX(), direction.getY());
            speed += Constants._BallAcceleration;
            // if it's moving to the right
            if (direction.getX() > 0) {
                pos = new Point2D(paddle.getX() - Constants._BallRadius, pos.getY());
            }else{
                pos = new Point2D(paddle.getX() + Constants._PaddleX, pos.getY());
            }
        }
    }

    public void updatePosition(){
        pos = pos.add(direction.multiply(speed));
    }
}
