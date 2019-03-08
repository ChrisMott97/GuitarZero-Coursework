package org.gsep.select;

import javafx.scene.Scene;
import org.gsep.carousel.ItemContainerModel;
import org.gsep.carousel.ItemModel;
import org.gsep.mediator.Mediator;
import org.gsep.mediator.SceneModule;

public class SelectModule extends SceneModule {
    public SelectController controller;
    public ItemModel itemModel;
    public ItemContainerModel itemContainerModel;

    public SelectModule(Mediator mediator) throws Exception{
        super(mediator);
        itemModel = new ItemModel();
        itemContainerModel = new ItemContainerModel();

        controller = new SelectController(itemModel, itemContainerModel, this);
        scene = controller.load();

    }
}
