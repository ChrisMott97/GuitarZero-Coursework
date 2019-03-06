package org.gsep.play;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import java.io.*;
import java.util.*;
import javafx.application.Application;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.image.ImageView; 
import javafx.scene.layout.*;

public class Play extends Application{
    private final int CANVASWIDTH = 950;
    private final int CANVASHEIGHT = 700;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws FileNotFoundException{
        stage.setTitle("Play");

        //initialise scene
        Group root = new Group();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        Canvas canvas = new Canvas(CANVASWIDTH, CANVASHEIGHT);

        root.getChildren().add(createBackground());
        root.getChildren().add(canvas);

        //temporary to find point values
        scene.setOnMouseClicked(e -> {
            System.out.println(e.getSceneX());
            System.out.println(e.getSceneY());
        });

        NoteHighwayModel model = new NoteHighwayModel();
        NoteHighwayView view = new NoteHighwayView(canvas);
        NoteHighwayController controller = new NoteHighwayController(model, view);

        stage.show();
        view.startRender();
        controller.play();
    }

    /**
     * @author humzahmalik
     * Setting image view
     */
    public ImageView createBackground(){
        File file = new File(getClass().getResource("/play/highway.png").getFile());
        Image image = new Image(file.toURI().toString());

        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitWidth(CANVASWIDTH);
        imageView.setPreserveRatio(true);

        return imageView;
    }
}