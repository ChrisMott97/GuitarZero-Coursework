package org.gsep;

import javafx.application.Application;
import javafx.stage.Stage;
import org.gsep.mediator.ModuleMediator;
import org.gsep.mediator.SceneModule;
import org.gsep.select.SelectModule;
import org.gsep.slash.SlashModule;

/*
 * Main.
 *
 * @author  Chris Mott.
 * @version 2.00, March 2019.
 */
public class Client extends Application {

    public void start (Stage view) throws Exception{
        ModuleMediator mediator = new ModuleMediator(view);

        SceneModule slashModule = Modules.SLASH.getModule();
        SceneModule selectModule = Modules.SELECT.getModule();

        slashModule.setMediator(mediator);
        selectModule.setMediator(mediator);

        mediator.setCurrentModule(slashModule);

        // defaults
        view.setTitle(slashModule.getTitle());
        view.setScene(slashModule.getScene());
        view.setResizable(false);
        view.show();
        //950x700
    }

    public static void main(String[] args) {
        launch(args);
    }
}
