package org.gsep.mediator;

import javafx.scene.Scene;

public abstract class SceneModule {
    private Mediator mediator;
    public Scene scene;

    public SceneModule(Mediator mediator){
        this.mediator = mediator;
        mediator.addModule(this);
    }

    public Mediator getMediator() {
        return mediator;
    }

    public void swapTo(SceneModule sceneModule){
        getMediator().setCurrentModule(sceneModule);
    }
}
