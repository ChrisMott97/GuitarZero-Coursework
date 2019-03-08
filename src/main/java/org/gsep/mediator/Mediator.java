package org.gsep.mediator;

import java.util.List;

public interface Mediator {
    void addModule(SceneModule sceneModule);
    void setCurrentModule(SceneModule sceneModule);
    List<SceneModule> getModules();
}
