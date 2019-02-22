import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Sprite {
    private Color fill;
    private Color stroke;
    private double posX;
    private double posY;
    private double width;
    private double height;

    /**
     * Sets fill colour of sprite
     *
     * @param fill fill colour
     */
    public void setFill(Color fill) {
        this.fill = fill;
    }

    /**
     * Sets stroke colour of sprite
     *
     * @param stroke stroke colour
     */
    public void setStroke(Color stroke){
        this.stroke = stroke;
    }

    /**
     * gets the x position of the top left corner of the sprite within the canvas
     *
     * @return X-position
     */
    public double getPosX() {
        return posX;
    }

    /**
     * gets the y position of the top left corner of the sprite within the canvas
     *
     * @return Y-position
     */
    public double getPosY() {
        return posY;
    }

    /**
     * sets the x and y positions of the top left corner of the sprite within the canvas
     *
     * @param posX X-position pixel value
     * @param posY Y-position pixel value
     */
    public void setPos(double posX, double posY) {
        this.posX = posX;
        this.posY = posY;
    }

    /**
     * sets the width and the height of the sprite within the canvas
     *
     * @param width width pixel value
     * @param height height pixel value
     */
    public void setDim(double width, double height) {
        this.width = width;
        this.height = height;
    }

    /**
     * renders the sprite with the current settings for its appearance and location
     *
     * @param gc the GraphicsContext of the canvas to render the sprite to
     */
    public void render(GraphicsContext gc) {
        gc.setLineWidth(2);
        gc.setStroke(stroke);
        gc.setFill(fill);
        gc.strokeRect(posX, posY, width, height);
        gc.fillRect(posX, posY, width, height);
    }
}
