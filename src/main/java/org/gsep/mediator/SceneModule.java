package org.gsep.mediator;

import javafx.scene.Scene;

/*
 * SceneModule.
 *
 * @author  Chris Mott.
 * @version 2.00, March 2019.
 */
public abstract class SceneModule {
    private String title;
    private Mediator mediator;
    private Scene scene;

    /**
     * Constructor.
     *
     * @param mediator the link back to the parent mediator.
     */
    public SceneModule(){ }

    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
        //TODO: Check is already exists in the module
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
     * @param module the new module to swap to.
     */
    public void swapTo(SceneModule module){
        mediator.setCurrentModule(module);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Scene getScene() {
        return scene;
    }

    public void init(){}
}
