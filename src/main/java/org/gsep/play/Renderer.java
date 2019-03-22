package org.gsep.play;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Renderer extends AnimationTimer{
    public static int LAYERS = 3;
    private List<GameObject> gameObjects = new CopyOnWriteArrayList<>();
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

            if (gameObjects.size() > 0){
                for (GameObject gameObject : gameObjects){
                    gameObject.updatePosition(System.currentTimeMillis());

                    if (gameObject.active() && gameObject.getLayer() == layers.indexOf(layer)) {
                        gameObject.render(layer.getGraphicsContext2D());
                    } else if (!gameObject.active()){
                        gameObjects.remove(gameObject);
                    }
                }
            }
        }
    }

    public void add(GameObject gameObject){
        gameObjects.add(0, gameObject); //add to first position to achieve first-on-top layering within layer
    }
}
