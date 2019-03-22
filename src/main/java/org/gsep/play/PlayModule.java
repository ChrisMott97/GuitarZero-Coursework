package org.gsep.play;

import org.gsep.mediator.SceneModule;
import org.gsep.select.MusicItem;

/**
 * PlayModule. Wrapper class for {@link Play}
 *
 * @author  Chris Mott.
 * @version 2.00, March 2019.
 */
public class PlayModule extends SceneModule {

    private static PlayModule instance;

    private PlayModule(){
//        itemModel = new ItemModel();
//        itemContainerModel = new ItemContainerModel();
//        controller = new SelectController(itemModel, itemContainerModel, this);
//        try{
//            setScene(controller.load());
//        }catch(Exception e){
//            System.out.println("Select controller could not load.");
//        }
    }

    public static PlayModule getInstance(){
        if(instance == null){
            synchronized (PlayModule.class){
                if(instance == null){
                    System.out.println("Play Module instanced!");
                    instance = new PlayModule();
                }
            }
        }
        return instance;
    }

    public void init(){
        MusicItem item = getMediator().getIntendedItem();
        System.out.println(item);
//        Play play = new Play("/3.txt", "/3.mid");
        Play play = new Play(item, instance);
        setScene(play.getScene());
        setTitle("Play Mode");
        play.play();
    }

}
