package pong;

import javafx.geometry.Point2D;

public class Ball {
    public Point2D pos;
    public double speed;

    public Ball(Point2D pos, double speed){
        this.pos = pos;
        this.speed = speed;
    }

    public void updateBall() {
        pos = pos.add()
    }
}
