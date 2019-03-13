package org.gsep.store;

import org.gsep.carousel.ItemContainerModel;
import org.gsep.carousel.ItemModel;
import org.gsep.mediator.SceneModule;

/*
 * SelectModule.
 *
 * @author  Chris Mott.
 * @version 2.00, March 2019.
 */
public class StoreModule extends SceneModule {
    private StoreController controller;
    private ItemModel itemModel;
    private ItemContainerModel itemContainerModel;

    private static StoreModule instance;

    private StoreModule(){
        itemModel = new ItemModel();
        itemContainerModel = new ItemContainerModel();
        controller = new StoreController(itemModel, itemContainerModel, this);
        try{
            setScene(controller.load());
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Store controller could not load.");
        }
        setTitle("Store Mode");
    }

    public static StoreModule getInstance(){
        if(instance == null){
            synchronized (StoreModule.class){
                if(instance == null){
                    instance = new StoreModule();
                }
            }
        }
        return instance;
    }

}
