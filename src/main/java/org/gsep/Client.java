package org.gsep;

import javafx.application.Application;
import javafx.stage.Stage;
import org.gsep.mediator.ModuleMediator;
import org.gsep.select.SelectModule;
import org.gsep.slash.SlashModule;

/*
 * Main.
 *
 * @author  Chris Mott.
 * @version 1.00, January 2019.
 */
public class Client extends Application {

    public void start (Stage view) throws Exception{
        ModuleMediator mediator = new ModuleMediator(view);

        SlashModule slashModule = new SlashModule(mediator);
        SelectModule selectModule = new SelectModule(mediator);

        mediator.setCurrentModule(slashModule);

        // defaults
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
