package com.fyxridd.lib.types;

import com.fyxridd.lib.core.api.plugin.SimplePlugin;

public class TypesPlugin extends SimplePlugin{
    public static MsgPlugin instance;

    @Override
    public void onEnable() {
        instance = this;

        super.onEnable();
    }
}