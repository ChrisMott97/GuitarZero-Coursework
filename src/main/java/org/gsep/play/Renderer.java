package org.gsep.play;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Renderer extends AnimationTimer{
    public static int LAYERS = 1;
    private List<Sprite> sprites = new CopyOnWriteArrayList<>();
    private final List<Canvas> layers = new ArrayList<>();

    public Renderer(Group root) {
        for (int i = 0; i < LAYERS; i++){
            Canvas canvas = new Canvas(Play.CANVASWIDTH, Play.CANVASHEIGHT);
            root.getChildren().add(canvas);
            layers.add(canvas);
        }
    }

    public void handle(long currentNanoTime) {
        for (Canvas layer : layers){
            layer.getGraphicsContext2D().clearRect(0,0, layer.getWidth(), layer.getHeight()); //clear the canvas

            if (sprites.size() > 0){
                for (Sprite sprite : sprites){
                    sprite.updatePosition(System.currentTimeMillis());

                    if (sprite.active() && sprite.getLayer() == layers.indexOf(layer)) {
                            sprite.render(layer.getGraphicsContext2D());
                    } else if (!sprite.active()){
                        sprites.remove(sprite);
                    }
                }
            }
        }
    }

    public void add(Sprite sprite){
        sprites.add(0, sprite); //add to first position to achieve first-on-top layering within layer
    }
}
