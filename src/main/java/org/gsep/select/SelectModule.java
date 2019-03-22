package org.gsep.select;

import javafx.scene.Scene;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import org.gsep.carousel.ItemContainerModel;
import org.gsep.carousel.ItemModel;
import org.gsep.controller.Button;
import org.gsep.mediator.Mediator;
import org.gsep.mediator.SceneModule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

/**
 * SelectModule.
 *
 * @author  Chris Mott.
 * @author  Abigail Lilley
 * @version 2.00, March 2019.
 */
public class SelectModule extends SceneModule {
    private SelectController controller;
    private ItemModel itemModel;
    private ItemContainerModel itemContainerModel;
    private ButtonThreadMap map = new ButtonThreadMap();

    private static volatile SelectModule instance;

    private SelectModule(){

    }

    public static SelectModule getInstance(){
        if(instance == null){
            synchronized (SelectModule.class){
                if(instance == null){
                    instance = new SelectModule();
                }
            }
        }
        return instance;
    }

    @Override
    public void init(){
        itemModel = new ItemModel();
        itemContainerModel = new ItemContainerModel();
        controller = new SelectController(itemModel, itemContainerModel, this);
        try{
            setScene(controller.load());
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Select controller could not load.");
        }
        setTitle("Select Mode");

        if (map.getButtons() != null && map.getThreads() != null) {
            for (int i=0; i<map.getButtons().length; i++) {
                map.getButtons()[i].terminate();
                try {
                    map.getThreads()[i].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        map = linkGuitar(controller);
    }
}
