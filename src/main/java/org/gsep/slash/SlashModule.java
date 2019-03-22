package org.gsep.slash;

import javafx.scene.Scene;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import org.gsep.carousel.ItemContainerModel;
import org.gsep.carousel.ItemModel;
import org.gsep.controller.Button;
import org.gsep.mediator.Mediator;
import org.gsep.mediator.SceneModule;
import org.gsep.play.GuitarEventHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

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
    private ButtonThreadMap map = new ButtonThreadMap();

    private static volatile SlashModule instance;

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
