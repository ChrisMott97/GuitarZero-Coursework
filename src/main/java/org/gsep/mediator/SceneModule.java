package org.gsep.mediator;

import javafx.scene.Scene;

/*
 * SceneModule.
 *
 * @author  Chris Mott.
 * @version 2.00, March 2019.
 */
public abstract class SceneModule {
    private Mediator mediator;
    public Scene scene;

    /**
     * Constructor.
     *
     * @param mediator the link back to the parent mediator.
     */
    public SceneModule(Mediator mediator){
        this.mediator = mediator;
        mediator.addModule(this);
    }

    /**
     * Gets the parent mediator.
     *
     * @return the parent mediator.
     */
    public Mediator getMediator() {
        return mediator;
    }

    /**
     * Swaps the stage scene and current module to a given module and it's scene.
     *
     * @param sceneModule the new module to swap to.
     */
    public void swapTo(SceneModule sceneModule){
        getMediator().setCurrentModule(sceneModule);
    }
}
