package org.gsep;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/*
 * Main.
 *
 * @author  Chris Mott.
 * @version 1.00, January 2019.
 */
public class Main extends Application {

    public void start (Stage stage) throws Exception{
        //load FXML
        FXMLLoader carouselLoader = new FXMLLoader(getClass().getResource("/fxml/SelectView.fxml"));
        Parent parent = carouselLoader.load();

        //load controller
        SelectController selectController = carouselLoader.getController();

        //link controller with models
        ItemModel<MusicItem> iModel = new ItemModel<>();
        ItemContainerModel icModel = new ItemContainerModel();
        selectController.linkModels(iModel, icModel);

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
