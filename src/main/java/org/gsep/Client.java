package org.gsep;

import javafx.application.Application;
import javafx.stage.Stage;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import org.gsep.controller.Button;
import org.gsep.controller.ButtonEvent;
import org.gsep.controller.ButtonListener;
import org.gsep.mediator.ModuleMediator;
import org.gsep.mediator.SceneModule;
import org.gsep.play.PlayModule;
import org.gsep.select.SelectModule;
import org.gsep.slash.SlashModule;
import org.gsep.store.StoreModule;
import org.gsep.play.GuitarEventHandler;

import java.io.IOException;
import java.util.Locale;

/*
 * Main.
 *
 * @author  Chris Mott.
 * @version 2.00, March 2019.
 */
public class Client extends Application {

    public void start (Stage view) throws Exception{
        ModuleMediator mediator = new ModuleMediator(view);

        SceneModule slashModule = SlashModule.getInstance();
        SceneModule selectModule = SelectModule.getInstance();
        SceneModule storeModule = StoreModule.getInstance();
        SceneModule playModule = PlayModule.getInstance();

        slashModule.setMediator(mediator);
        selectModule.setMediator(mediator);
        storeModule.setMediator(mediator);
        playModule.setMediator(mediator);

        mediator.setCurrentModule(slashModule);

        // defaults
        view.setTitle(slashModule.getTitle());
        view.setScene(slashModule.getScene());
        view.setResizable(false);
        view.show();
        //950x700
    }
}
