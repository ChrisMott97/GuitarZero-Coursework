package org.gsep.select;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.gsep.carousel.Carousel;
import org.gsep.carousel.ItemContainerModel;
import org.gsep.carousel.ItemModel;

import java.util.Stack;

/*
 * Main.
 *
 * @author  Chris Mott.
 * @version 1.00, January 2019.
 */
public class Main extends Application {

    public void start (Stage stage) throws Exception{

        //load main controller
        SelectController selectController = new SelectController();
        StackPane parent = selectController.fxmlLoader.load();

        ItemModel iModel = new ItemModel();
        ItemContainerModel icModel = new ItemContainerModel();

        //load carousel controller and link models
        Carousel carousel = selectController.carousel;
        carousel.linkModels(iModel, icModel);

        selectController.loadData();

        //TODO: Turn controller into Scene so input can be inside controller
        Scene scene = new Scene(parent);
        scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());

        //temporary keyboard input
        scene.setOnKeyPressed(keyEvent -> {
            switch(keyEvent.getCode()){
                case RIGHT:
                    carousel.next();
                    break;
                case LEFT:
                    carousel.previous();
                    break;
            }
        });

        stage.setTitle("Select Mode");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        //950x700
    }

    public static void main(String[] args) {
        launch(args);
    }
}
