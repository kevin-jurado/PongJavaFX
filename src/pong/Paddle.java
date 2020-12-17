package pong;

import javafx.geometry.Point2D;

public class Paddle {
    public Point2D pos;
    public double speed;

    public Paddle(Point2D pos, double speed){
        this.pos = pos;
        this.speed = speed;
    }

    public void moveUp() {
        pos = pos.add(0, -speed);
        if (pos.getY() <= 0) {
            pos = new Point2D(pos.getX(), 0);
        }
    }

    public void moveDown() {
        pos = pos.add(0, speed);
        if (pos.getY() + Constants._PaddleY >= Constants._Y) {
            pos = new Point2D(pos.getX(), Constants._Y - Constants._PaddleY);
        }
    }

    public void setLatitude(double latitude) {
        double lat;
        if (latitude < Constants._PaddleY / 2) {
            lat = 0;
        } else if (latitude > Constants._Y - Constants._PaddleY){
            lat = Constants._Y - Constants._PaddleY;
        } else
            lat = latitude;
        pos = new Point2D(pos.getX(), lat);
    }

    public void runAI(Point2D ballPos) {
        // distance of the center of the paddle from the ball
        double compLatitude = pos.getY() + Constants._PaddleY * 0.5;
        double ballLatitude = ballPos.getY() + Constants._BallRadius / 2;
        double difference = compLatitude - ballLatitude;
        if (difference < -30) {
            moveDown();
        } else if (difference > 30) {
            moveUp();
        }
    }
}
