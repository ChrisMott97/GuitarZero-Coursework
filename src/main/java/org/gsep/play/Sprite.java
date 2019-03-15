package org.gsep.play;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.File;

abstract class Sprite {
    private Point2D position;
    private double width;
    private double height;
    private Image image;
    private int layer = 0;

    public void setImage(String url){
        File file = new File(getClass().getResource(url).getFile());
        this.image = new Image(file.toURI().toString());
    }

    public void setImage(Image img){
        this.image = img;
    }

    /**
     * sets the position, compensating for the expected midpoint of the sprite
     * @param newpos
     */
    public void setPosition(Point2D newpos){
        //TODO variable offsets
        position = new Point2D(newpos.getX()-width*0.5, newpos.getY()-height*0.95);
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setWidthPreserveRatio(double width) {
        this.width = width;
        this.height = image.getHeight()/image.getWidth()*width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setHeightPreserveRatio(double height) {
        this.height = height;
        this.width = image.getWidth()/image.getHeight()*height;
    }

    public abstract Boolean active();

    public int getLayer() {
        return layer;
    }

    public void updatePosition(double currentTimeMillis){}

    /**
     * renders the sprite with the current settings for its appearance and location
     *
     * @param gc the GraphicsContext of the canvas to render the sprite to
     */
    public void render(GraphicsContext gc) {
        gc.drawImage(image, position.getX(), position.getY(), width, height);
    }
}
