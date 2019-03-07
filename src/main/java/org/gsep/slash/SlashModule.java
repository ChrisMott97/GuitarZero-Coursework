package org.gsep.slash;

import javafx.scene.Scene;
import org.gsep.carousel.ItemContainerModel;
import org.gsep.carousel.ItemModel;
import org.gsep.mediator.Mediator;
import org.gsep.mediator.SceneModule;

public class SlashModule extends SceneModule {
    public SlashController controller;
    public Scene scene;
    public ItemModel itemModel;
    public ItemContainerModel itemContainerModel;

    public SlashModule(Mediator mediator) throws Exception{
        super(mediator);
        itemModel = new ItemModel();
        itemContainerModel = new ItemContainerModel();

        controller = new SlashController(itemModel, itemContainerModel);
        scene = controller.load();
    }
}
