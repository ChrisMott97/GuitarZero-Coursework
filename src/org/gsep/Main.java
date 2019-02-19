package org.gsep;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main extends Application {

    public void start (Stage stage) throws Exception{
        FXMLLoader carouselLoader = new FXMLLoader(getClass().getResource("SelectView.fxml"));
        Parent parent = carouselLoader.load();
        CarouselController carouselController = carouselLoader.getController();

        MusicItemModel model = new MusicItemModel();
        carouselController.linkModel(model);


        Scene scene = new Scene(parent);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        stage.setTitle("Select Mode");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
