package pong;

import javafx.geometry.Point2D;

public class Constants {
    public static final int _X = 800;
    public static final int _Y = 500;

    public static final int _PaddleX = _X/40;
    public static final int _PaddleY = _Y/10;

    public static final int _BallRadius = _X/40;

    public static final Point2D _PaddleSize = new Point2D(_PaddleX, _PaddleY);

    public static final Point2D _ScreenSize = new Point2D(_X, _Y);

    public static final Point2D _Padding = new Point2D(50.0, 0.0);

    public static final Double _PaddleSpeed = 2.5;

}
