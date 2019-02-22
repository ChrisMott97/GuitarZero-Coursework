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

    public void setFill(Color fill) {
        this.fill = fill;
    }

    public void setStroke(Color stroke){
        this.stroke = stroke;
    }

    public void setPos(double posX, double posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public void setDim(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public void render(GraphicsContext gc) {
        gc.setLineWidth(2);
        gc.setStroke(stroke);
        gc.setFill(fill);
        gc.strokeRect(posX, posY, width, height);
        gc.fillRect(posX, posY, width, height);
    }
}
