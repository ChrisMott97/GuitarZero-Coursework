package org.gsep.select;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.gsep.carousel.Carousel;
import org.gsep.carousel.ItemContainerModel;
import org.gsep.carousel.ItemModel;

/*
 * Main.
 *
 * @author  Chris Mott.
 * @version 1.00, January 2019.
 */
public class Main extends Application {

    public void start (Stage stage) throws Exception{

        //load FXML
        FXMLLoader selectLoader = new FXMLLoader(getClass().getResource("/fxml/SelectView.fxml"));
        Parent parent = selectLoader.load();

        //load main controller
        SelectController selectController = selectLoader.getController();

        ItemModel iModel = new ItemModel<>();
        ItemContainerModel<MusicItem> icModel = new ItemContainerModel<>();

        //load carousel controller and link models
//        Carousel carousel = selectController.carousel;
//        carousel.linkModels(iModel, icModel);

        Scene scene = new Scene(parent);
        scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());

        stage.setTitle("Select Mode");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
