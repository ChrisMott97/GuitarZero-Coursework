package org.gsep.play;

public class Point {
    private double x;
    private double y;

    Point(double x, double y){
        this.x = x;
        this.y = y;
    }

    Point(Point point){
        this.x = point.getX();
        this.y = point.getY();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void incrementX(double x) {
        this.x += x;
    }

    public void incrementY(double y) {
        this.y += y;
    }
}
