package org.gsep.mediator;

import java.util.ArrayList;
import java.util.List;

public class ModuleMediator implements Mediator{
    private List<SceneModule> modules;

    public ModuleMediator(){
        this.modules = new ArrayList<>();
    }

    @Override
    public void addModule(SceneModule sceneModule) {
        this.modules.add(sceneModule);
    }

    public List<SceneModule> getModules() {
        return modules;
    }
}
