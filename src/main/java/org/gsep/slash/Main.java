package org.gsep.slash;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.gsep.carousel.Carousel;
import org.gsep.select.SelectController;

/*
 * Main.
 *
 * @author  Chris Mott.
 * @version 1.00, January 2019.
 */
public class Main extends Application {

    public void start (Stage stage) throws Exception{

        //load main controller
        SlashController slashController = new SlashController(stage);
        Scene slashScene = slashController.load();

        SelectController selectController = new SelectController(stage);
        Scene selectScene = selectController.load();

        slashController.setNextScene(selectScene);

        stage.setTitle("Slash Mode");
        stage.setScene(slashScene);
        stage.setResizable(false);
        stage.show();
        //950x700
    }

    public static void main(String[] args) {
        launch(args);
    }
}
