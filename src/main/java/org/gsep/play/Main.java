package org.gsep.play;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws IOException{
        stage.setTitle("Main");

        //initialise scene
        Group root = new Group();

        Play play = new Play(root, "/ComeOnEileen.txt", "/ComeOnEileen.mid");

        stage.setScene(play.getScene());
        stage.setResizable(false);

        stage.show();
        play.play();
    }
}