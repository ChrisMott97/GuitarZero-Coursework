package org.gsep.play;

import javafx.animation.AnimationTimer;
import javafx.scene.*;
import javafx.scene.image.Image;
import java.io.*;
import java.util.*;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.image.ImageView; 
import javafx.scene.layout.*;

public class Play extends Application{
	
	int backgroundHeight= 1350;
	int backgroundWidth= 1200;
	int backgroundYpos=-300;
	int backgroundXpos=-50;

    public static void main(String[] args) {
        launch(args);
        
    }

    public void start(Stage stage) throws FileNotFoundException{
        stage.setTitle("Play");


        //initialise scene
        Group root = new Group();
        Scene scene = new Scene(root, 1000, 750);
        stage.setScene(scene);

        NoteHighwayModel model = new NoteHighwayModel();
        NoteHighwayView view = new NoteHighwayView();
        NoteHighwayController controller = new NoteHighwayController(model, view);
        
        //hm- import image and assign to a image view, displaying it on the canvas
        Image image = new Image("Runway.png");
        ImageView iv = new ImageView();
        iv.setImage(image);
        iv.setFitHeight(backgroundHeight);
        iv.setFitWidth(backgroundWidth);
        iv.setY(backgroundYpos);
        iv.setX(backgroundXpos);
        root.getChildren().add(iv);
        
        
        root.getChildren().add(view.getCanvas());
        
    
   
        stage.show();
        controller.play();
    }
}