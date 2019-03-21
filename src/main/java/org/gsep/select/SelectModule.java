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

    private static SelectModule instance;
    private boolean guitarLinked = false;

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
        System.out.println("Select mode linking guitar");
        if (!guitarLinked) {
            linkGuitar(controller);
            guitarLinked = true;
        }
    }
}
