package org.gsep.play;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.File;

public class NoteSprite {
    private Point position;
    private Point source;
    private Point destination;
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
                source = new Point(413, 0);
                destination = new Point(318, 525);
                break;
            case MIDDLE:
                source = new Point(474, 0);
                destination = new Point(474, 525);
                break;
            case RIGHT:
                source = new Point(529, 0);
                destination = new Point(630, 525);
                break;
        }
        this.position = new Point(source);
        this.spawnTime = System.currentTimeMillis();
    }

    private void setImage(String url){
        File file = new File(getClass().getResource(url).getFile());
        this.image = new Image(file.toURI().toString());
    }

    private void setPosition(double x, double y){
        position.setX(x- size*0.5);
        position.setY(y- size*0.9);
    }

    public void updateProgress(double progress) {
        double newX = source.getX() + (destination.getX()-source.getX())*progress;
        double newY = source.getY() + (destination.getY()-source.getY())*progress;
        setPosition(newX, newY);

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
