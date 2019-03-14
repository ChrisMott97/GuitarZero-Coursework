package org.gsep.play;

import javafx.geometry.Point2D;

public class NoteSprite extends Sprite{
    private Point2D source;
    private Point2D destination;
    private double spawnTime;
    private double initialSize = 45;
    private double finalSize = 109;

    public NoteSprite(Note noteType, Lane laneType){
        setImage(noteType.getImageSource());

        source = laneType.getStartPoint();
        destination = laneType.getShieldPoint();

        setWidthPreserveRatio(initialSize);

        this.spawnTime = System.currentTimeMillis();
    }

    public void updateProgress(double progress) {
        Point2D vector = destination.subtract(source);
        Point2D newpos = source.add(vector.multiply(progress));
        setPosition(newpos);

        setWidthPreserveRatio(initialSize + (finalSize-initialSize)*progress);
    }

    public double getSpawnTime(){
        return spawnTime;
    }
}
