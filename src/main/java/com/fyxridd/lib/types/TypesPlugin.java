package com.fyxridd.lib.types;

import com.fyxridd.lib.core.api.plugin.SimplePlugin;
import com.fyxridd.lib.types.manager.TypesManager;

public class TypesPlugin extends SimplePlugin{
    public static TypesPlugin instance;

    private TypesManager typesManager;
    
    @Override
    public void onEnable() {
        instance = this;

        typesManager = new TypesManager();
        
        super.onEnable();
    }

    public TypesManager getTypesManager() {
        return typesManager;
    }
}