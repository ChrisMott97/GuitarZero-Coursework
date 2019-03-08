package org.gsep.play;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class Main extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws IOException{
        stage.setTitle("Main");

        //initialise scene
        Group root = new Group();

        Play play = new Play(root, "/nf.txt", "");

        stage.setScene(scene);
        stage.setResizable(false);

        stage.show();
        //start render
        //play song
    }
}