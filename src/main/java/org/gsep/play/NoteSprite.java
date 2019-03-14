package org.gsep.play;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.File;

public class NoteSprite {
    private Point2D position;
    private Point2D source;
    private Point2D destination;
    private double spawnTime;
    private double initialSize = 40;
    private double finalSize = 80;
    private double size = initialSize;
    private Image image;

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
                source = new Point2D(413, 0);
                destination = new Point2D(318, 525);
                break;
            case MIDDLE:
                source = new Point2D(474, 0);
                destination = new Point2D(474, 525);
                break;
            case RIGHT:
                source = new Point2D(529, 0);
                destination = new Point2D(630, 525);
                break;
        }
        this.spawnTime = System.currentTimeMillis();
    }

    private void setImage(String url){
        File file = new File(getClass().getResource(url).getFile());
        this.image = new Image(file.toURI().toString());
    }

    /**
     * sets the position, compensating for the expected midpoint of the sprite
     * @param newpos
     */
    private void setPosition(Point2D newpos){
        position = new Point2D(newpos.getX()-size*0.5, newpos.getY()-size*0.9);
    }

    public void updateProgress(double progress) {
        Point2D vector = destination.subtract(source);
        Point2D newpos = source.add(vector.multiply(progress));
        setPosition(newpos);

        size = initialSize + (finalSize-initialSize)*progress;
    }

    public double getSpawnTime(){
        return spawnTime;
    }


    /**
     * renders the sprite with the current settings for its appearance and location
     *
     * @param gc the GraphicsContext of the canvas to render the sprite to
     */
    public void render(GraphicsContext gc) {
        gc.drawImage(image, position.getX(), position.getY(), size, size);
    }
}
