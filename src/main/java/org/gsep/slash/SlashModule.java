package org.gsep.slash;

import org.gsep.carousel.ItemContainerModel;
import org.gsep.carousel.ItemModel;
import org.gsep.mediator.SceneModule;

/**
 * SlashModule.
 *
 * @author  Chris Mott.
 * @author  Abigail Lilley.
 * @version 2.00, March 2019.
 */
public class SlashModule extends SceneModule {

    private ButtonThreadHolder holder = new ButtonThreadHolder();
    private static volatile SlashModule instance;

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

    /**
     * Initialise Slash Mode each time the mode is swapped to
     */
    @Override
    public void init(){
        ItemModel itemModel = new ItemModel();
        ItemContainerModel itemContainerModel = new ItemContainerModel();
        SlashController controller = new SlashController(itemModel, itemContainerModel, this);
        try{
            setScene(controller.load());
        }catch(Exception e){
            e.printStackTrace();
        }
        setTitle("Slash Mode");

        if (holder.getButtons() != null && holder.getThreads() != null) {
            for (int i=0; i<holder.getButtons().length; i++) {
                holder.getButtons()[i].terminate();        /* End threads associated with old instance of SlashModule */
                try {
                    holder.getThreads()[i].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        holder = linkGuitar(controller);                                                         /* Start new threads */
        }
}
