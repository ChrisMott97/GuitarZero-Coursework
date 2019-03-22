package org.gsep.select;

import org.gsep.carousel.ItemContainerModel;
import org.gsep.carousel.ItemModel;
import org.gsep.mediator.SceneModule;

/**
 * SelectModule.
 *
 * @author  Chris Mott.
 * @author  Abigail Lilley
 * @version 2.00, March 2019.
 */
public class SelectModule extends SceneModule {
    private ButtonThreadHolder holder = new ButtonThreadHolder();

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
        ItemModel itemModel = new ItemModel();
        ItemContainerModel itemContainerModel = new ItemContainerModel();
        SelectController controller = new SelectController(itemModel, itemContainerModel, this);
        try{
            setScene(controller.load());
        }catch(Exception e){
            e.printStackTrace();
        }
        setTitle("Select Mode");

        if (holder.getButtons() != null && holder.getThreads() != null) {
            for (int i=0; i<holder.getButtons().length; i++) {
                holder.getButtons()[i].terminate();
                try {
                    holder.getThreads()[i].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        holder = linkGuitar(controller);
    }
}
