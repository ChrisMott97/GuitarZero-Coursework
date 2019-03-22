package org.gsep.play;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.File;

/**
 * Base class for objects rendered on the game canvas
 *
 * @author orsbarkanyi
 */
abstract class GameObject {
    private Boolean visible = true;
    private Point2D position;
    private Point2D positionOffset;
    private double width;
    private double height;
    private Image image;
    private double opacity = 1;
    private int layer = 0;
    private Boolean active = true;

    public void setImage(String url){
        File file = new File(getClass().getResource(url).getFile());
        this.image = new Image(file.toURI().toString());
    }

    public void setImage(Image img){
        this.image = img;
    }

    /**
     * sets the position, compensating for the expected midpoint of the sprite
     * @param position
     */
    public void setPosition(Point2D position){
        this.position = new Point2D(position.getX(), position.getY());
    }

    public Point2D getPosition() {
        //apply offset factors if offset has been set
        if (positionOffset != null){
            Point2D offsetPosition = new Point2D(positionOffset.getX()*width, positionOffset.getY()*height);
            return position.subtract(offsetPosition);
        } else {
            return position;
        }
    }

    public void setPositionOffset(Point2D positionOffset) {
        this.positionOffset = positionOffset;
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

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public Boolean active(){
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setLayer(int layer) {
        this.layer = (layer > 0 && layer <= Renderer.LAYERS) ? layer : 0;
    }

    public int getLayer() {
        return layer;
    }

    public void setVisible(Boolean visible){
        this.visible = visible;
    }

    public Boolean getVisible() {
        return visible;
    }

    public double getOpacity() {
        return opacity;
    }

    public void setOpacity(double opacity) {
        this.opacity = opacity;
    }

    public void updatePosition(double currentTimeMillis){}

    public Image getImage() {
        return image;
    }

    /**
     * renders the sprite with the current settings for its appearance and location
     *
     * @param gc the GraphicsContext of the canvas to render the sprite to
     */
    public void render(GraphicsContext gc) {
        if (visible){
            gc.setGlobalAlpha(opacity);
            gc.drawImage(image, getPosition().getX(), getPosition().getY(), width, height);
        }
    }
}
