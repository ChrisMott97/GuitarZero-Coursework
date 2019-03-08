package org.gsep.mediator;

import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ModuleMediator implements Mediator{
    private List<SceneModule> modules;
    private SceneModule currentModule;
    private Stage view;

    public ModuleMediator(Stage view){
        this.view = view;
        this.modules = new ArrayList<>();
    }

    @Override
    public void addModule(SceneModule sceneModule) {
        this.modules.add(sceneModule);
    }

    @Override
    public List<SceneModule> getModules() {
        return modules;
    }

    public void setCurrentModule(SceneModule currentModule) {
        view.setScene(currentModule.scene);
        this.currentModule = currentModule;
    }

    public SceneModule getCurrentModule() {
        return currentModule;
    }
}
