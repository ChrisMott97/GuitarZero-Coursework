package org.gsep;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.gsep.carousel.Carousel;
/*
 * SceneController.
 *
 * Temporary parent controller.
 *
 * @author  Chris Mott.
 * @version 2.00, March 2019.
 */
public class SceneController extends StackPane {

    private Stage stage;

    public Scene load(FXMLLoader fxmlLoader, Carousel carousel) throws Exception{
        StackPane parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());
        return scene;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }
}
