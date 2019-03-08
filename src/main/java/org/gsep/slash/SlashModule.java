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

    /**
     * Constructor.
     *
     * @param mediator a link back to the parent mediator.
     * @throws Exception
     */
    public SlashModule(Mediator mediator) throws Exception{
        super(mediator);
        itemModel = new ItemModel();
        itemContainerModel = new ItemContainerModel();

        controller = new SlashController(itemModel, itemContainerModel, this);
        scene = controller.load();
    }
}
