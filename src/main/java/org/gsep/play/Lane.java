package org.gsep.play;


import javafx.geometry.Point2D;

public enum Lane {
    LEFT(
            new Point2D(417, 0),
            new Point2D(284, 525),
            new Point2D(246, 700)
    ),
    MIDDLE(
            new Point2D(475, 0),
            new Point2D(475, 525),
            new Point2D(475, 700)
    ),
    RIGHT(
            new Point2D(532, 0),
            new Point2D(666, 525),
            new Point2D(705, 700)
    );

    private final Point2D startPoint;
    private final Point2D shieldPoint;
    private final Point2D endPoint;

    Lane(Point2D startPoint, Point2D shieldPoint, Point2D endPoint) {
        this.startPoint = startPoint;
        this.shieldPoint = shieldPoint;
        this.endPoint = endPoint;
    }

    public Point2D getStartPoint() {
        return startPoint;
    }

    public Point2D getShieldPoint() {
        return shieldPoint;
    }

    public Point2D getEndPoint() {
        return endPoint;
    }
}
