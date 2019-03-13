package org.gsep.play;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.File;

abstract class Sprite {
    private Point2D position;
    private double size;
    private Image image;

    public void setImage(String url){
        File file = new File(getClass().getResource(url).getFile());
        this.image = new Image(file.toURI().toString());
    }

    /**
     * sets the position, compensating for the expected midpoint of the sprite
     * @param newpos
     */
    public void setPosition(Point2D newpos){
        position = new Point2D(newpos.getX()-size*0.5, newpos.getY()-size*0.9);
    }

    public void setSize(double size) {
        this.size = size;
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
