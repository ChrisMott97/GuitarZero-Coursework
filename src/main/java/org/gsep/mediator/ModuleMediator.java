package org.gsep.mediator;

import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/*
 * ModuleMediator.
 *
 * @author  Chris Mott.
 * @version 2.00, March 2019.
 */
public class ModuleMediator implements Mediator{
    private List<SceneModule> modules;
    private SceneModule currentModule;
    private Stage view;

    /**
     * Constructer.
     *
     * @param view the global stage, allows changing scene.
     */
    public ModuleMediator(Stage view){
        this.view = view;
        this.modules = new ArrayList<>();
    }

    /**
     * Adds a module to the modules list.
     *
     * @param sceneModule the module to be added.
     */
    @Override
    public void addModule(SceneModule sceneModule) {
        this.modules.add(sceneModule);
    }

    /**
     * Gets all the modules.
     *
     * @return the list of modules.
     */
    @Override
    public List<SceneModule> getModules() {
        return modules;
    }

    /**
     * Sets the current module.
     *
     * @param currentModule the module to set as current.
     */
    public void setCurrentModule(SceneModule currentModule) {
        //TODO: Fade out scene, change, then fade in new to prevent stutter
        view.setScene(currentModule.getScene());
        view.setTitle(currentModule.getTitle());
        this.currentModule = currentModule;
    }

    /**
     * Retrieve the current module.
     *
     * @return the current module.
     */
    public SceneModule getCurrentModule() {
        return currentModule;
    }
}
