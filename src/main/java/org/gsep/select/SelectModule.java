package org.gsep.select;

import javafx.scene.Scene;
import org.gsep.carousel.ItemContainerModel;
import org.gsep.carousel.ItemModel;
import org.gsep.mediator.Mediator;
import org.gsep.mediator.SceneModule;

/*
 * SelectModule.
 *
 * @author  Chris Mott.
 * @version 2.00, March 2019.
 */
public class SelectModule extends SceneModule {
    private SelectController controller;
    private ItemModel itemModel;
    private ItemContainerModel itemContainerModel;

    private static SelectModule instance;

    private SelectModule(){
        itemModel = new ItemModel();
        itemContainerModel = new ItemContainerModel();
        controller = new SelectController(itemModel, itemContainerModel, this);
        try{
            setScene(controller.load());
        }catch(Exception e){
            System.out.println("Select controller could not load.");
        }
        setTitle("Select Mode");
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

}
