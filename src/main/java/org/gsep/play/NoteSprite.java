package org.gsep.play;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.File;

public class NoteSprite extends Sprite{
    private Point2D source;
    private Point2D destination;
    private double spawnTime;
    private double initialSize = 45;
    private double finalSize = 90;

    public NoteSprite(Note noteType, Lane laneType){
        switch (noteType){
            case BLACK:
                setImage("/play/black.png");
                break;
            case WHITE:
                setImage("/play/white.png");
                break;
        }
        switch (laneType){
            case LEFT:
                source = new Point2D(417, 0);
                destination = new Point2D(285, 526);
                break;
            case MIDDLE:
                source = new Point2D(475, 0);
                destination = new Point2D(476, 525);
                break;
            case RIGHT:
                source = new Point2D(532, 0);
                destination = new Point2D(665, 529);
                break;
        }
        setSize(initialSize);
        this.spawnTime = System.currentTimeMillis();
    }

    public void updateProgress(double progress) {
        Point2D vector = destination.subtract(source);
        Point2D newpos = source.add(vector.multiply(progress));
        setPosition(newpos);

        setSize(initialSize + (finalSize-initialSize)*progress);
    }

    public double getSpawnTime(){
        return spawnTime;
    }
}
