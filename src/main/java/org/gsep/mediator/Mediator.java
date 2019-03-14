package org.gsep.mediator;

import java.util.List;

/*
 * Mediator.
 *
 * @author  Chris Mott.
 * @version 2.00, March 2019.
 */
public interface Mediator {
    /**
     * Adds a module to the list of modules.
     *
     * @param sceneModule the module to be added.
     */
    void addModule(SceneModule sceneModule);

    /**
     * Sets the currently used module for the program.
     *
     * @param sceneModule the module to set.
     */
    void setCurrentModule(SceneModule sceneModule);

    /**
     * Returns the modules.
     *
     * @return list of the modules.
     */
    List<SceneModule> getModules();
}
