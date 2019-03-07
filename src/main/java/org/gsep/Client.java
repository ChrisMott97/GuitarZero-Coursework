package org.gsep;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.gsep.carousel.Carousel;
import org.gsep.carousel.ItemContainerModel;
import org.gsep.carousel.ItemModel;
import org.gsep.mediator.ModuleMediator;
import org.gsep.mediator.SceneModule;
import org.gsep.select.SelectController;
import org.gsep.select.SelectModule;
import org.gsep.slash.SlashController;
import org.gsep.slash.SlashModule;

/*
 * Main.
 *
 * @author  Chris Mott.
 * @version 1.00, January 2019.
 */
public class Client extends Application {

    public void start (Stage view) throws Exception{
        ModuleMediator mediator = new ModuleMediator();

        SlashModule slashModule = new SlashModule(mediator);
        SelectModule selectModule = new SelectModule(mediator);

        for (SceneModule module: mediator.getModules()) {
            System.out.println(module);
        }
//        ItemModel iModel = new ItemModel();
//        ItemContainerModel icModel = new ItemContainerModel();
//
//        //load main controller
//        SlashController slashController = new SlashController(view);
//        Scene slashScene = slashController.load();
//
//        SelectController selectController = new SelectController(view);
//        Scene selectScene = selectController.load();
//
//        slashController.setNextScene(selectScene);
//
        view.setTitle("Slash Mode");
        view.setScene(slashModule.scene);
        view.setResizable(false);
        view.show();
        //950x700
    }

    public static void main(String[] args) {
        launch(args);
    }
}
