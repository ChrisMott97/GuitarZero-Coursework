package org.gsep.slash;


import org.gsep.carousel.ItemContainerModel;
import org.gsep.carousel.ItemModel;
import org.gsep.mediator.SceneModule;


/**
 * SlashModule.
 *
 * @author  Chris Mott.
 * @author  Abigail Lilley
 * @version 2.00, March 2019.
 */
public class SlashModule extends SceneModule {
    private SlashController controller;
    private ItemModel itemModel;
    private ItemContainerModel itemContainerModel;

    private static SlashModule instance;
    private boolean guitarLinked = false;

    private SlashModule(){
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

    @Override
    public void init(){
        itemModel = new ItemModel();
        itemContainerModel = new ItemContainerModel();
        controller = new SlashController(itemModel, itemContainerModel, this);
        try{
            setScene(controller.load());
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Slash controller could not load.");
        }
        setTitle("Slash Mode");
        System.out.println("Slash mode linking guitar");

        if (!guitarLinked) {
            linkGuitar(controller);
            guitarLinked = true;
        }
    }
}
