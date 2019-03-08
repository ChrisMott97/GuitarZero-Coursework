package org.gsep.select;

import javafx.scene.Scene;
import org.gsep.carousel.ItemContainerModel;
import org.gsep.carousel.ItemModel;
import org.gsep.mediator.Mediator;
import org.gsep.mediator.SceneModule;

/*
 * SelectModule.
 *
 * @author  Chris Mott.
 * @version 2.00, March 2019.
 */
public class SelectModule extends SceneModule {
    private SelectController controller;
    private ItemModel itemModel;
    private ItemContainerModel itemContainerModel;

    /**
     * Constructor.
     *
     * @param mediator the link back to the parent mediator.
     * @throws Exception
     */
    public SelectModule(Mediator mediator) throws Exception{
        super(mediator);
        itemModel = new ItemModel();
        itemContainerModel = new ItemContainerModel();

        controller = new SelectController(itemModel, itemContainerModel, this);
        scene = controller.load();

    }
}
