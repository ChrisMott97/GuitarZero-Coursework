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
	//constants!
	final int SCENEWIDHT = 1000;
	final int SCENEHEIGHT = 750;

    public static void main(String[] args) {
        launch(args);
        
    }

    public void start(Stage stage) throws FileNotFoundException{
        stage.setTitle("Play");

        //initialise scene
        Group root = new Group();
        //constants!
        Scene scene = new Scene(root, SCENEWIDHT, SCENEHEIGHT);
        stage.setScene(scene);

        NoteHighwayModel model = new NoteHighwayModel();
        NoteHighwayView view = new NoteHighwayView();
        NoteHighwayController controller = new NoteHighwayController(model, view);
        
        
        //hm- import image and assign to a image view, displaying it on the canvas
        root.getChildren().add(view.setBackground());
        root.getChildren().add(view.getCanvas());
        
    
   
        stage.show();
        controller.play();
    }
}