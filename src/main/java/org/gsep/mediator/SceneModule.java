package org.gsep.mediator;

public abstract class SceneModule {
    private Mediator mediator;

    public SceneModule(Mediator mediator){
        this.mediator = mediator;
        mediator.addModule(this);
    }

}
