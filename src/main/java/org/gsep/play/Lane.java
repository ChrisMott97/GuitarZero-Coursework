package org.gsep.play;


import javafx.geometry.Point2D;

/**
 * enum representing lanes on the note highway
 *
 * @author orsbarkanyi
 */
public enum Lane {
    LEFT(new Point2D(417, 0), new Point2D(284, 525)),
    MIDDLE(new Point2D(475, 0), new Point2D(475, 525)),
    RIGHT(new Point2D(532, 0), new Point2D(666, 525));

    private final Point2D spawnPoint;
    private final Point2D shieldPoint;

    Lane(Point2D spawnPoint, Point2D shieldPoint) {
        this.spawnPoint = spawnPoint;
        this.shieldPoint = shieldPoint;
    }

    public Point2D getSpawnPoint() {
        return spawnPoint;
    }

    public Point2D getShieldPoint() {
        return shieldPoint;
    }
}
