package org.gsep;

import org.gsep.mediator.SceneModule;
import org.gsep.select.SelectModule;
import org.gsep.slash.SlashModule;
import org.gsep.store.StoreModule;

public enum Modules {
    SLASH (SlashModule.getInstance()),
    SELECT (SelectModule.getInstance()),
    STORE (StoreModule.getInstance());

    SceneModule module;

    Modules(SceneModule module){
        this.module = module;
    }

    public SceneModule getModule() {
        return module;
    }
}
