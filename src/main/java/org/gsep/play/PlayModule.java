package org.gsep.play;

import javafx.scene.Scene;
import org.gsep.carousel.ItemContainerModel;
import org.gsep.carousel.ItemModel;
import org.gsep.mediator.Mediator;
import org.gsep.mediator.SceneModule;

/*
 * PlayModule.
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
        Play play = new Play("/untitled2.txt", "/untitled2.mid");
        setScene(play.getScene());
        setTitle("Play Mode");
        play.play();
    }

}
