package org.gsep.slash;

import javafx.scene.Scene;
import org.gsep.carousel.ItemContainerModel;
import org.gsep.carousel.ItemModel;
import org.gsep.mediator.Mediator;
import org.gsep.mediator.SceneModule;

/*
 * SlashModule.
 *
 * @author  Chris Mott.
 * @version 2.00, March 2019.
 */
public class SlashModule extends SceneModule {
    private SlashController controller;
    private ItemModel itemModel;
    private ItemContainerModel itemContainerModel;

    private static SlashModule instance;

    private SlashModule(){
        itemModel = new ItemModel();
        itemContainerModel = new ItemContainerModel();
        controller = new SlashController(itemModel, itemContainerModel, this);
        try{
            setScene(controller.load());
        }catch(Exception e){
            System.out.println("Slash controller could not load.");
        }
        setTitle("Slash Mode");
    }

    public static SlashModule getInstance(){
        if(instance == null){
            synchronized (SlashModule.class){
                if(instance == null){
                    instance = new SlashModule();
                }
            }
        }
        return instance;
    }
}
