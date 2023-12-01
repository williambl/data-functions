package com.williambl.dfunc.impl;

import com.williambl.vampilang.lang.type.VType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.core.Registry;

public class DataFunctionsModFabric implements ModInitializer {
    public static final Registry<VType> TYPE_REGISTRY = FabricRegistryBuilder.createSimple(DataFunctionsMod.TYPE_REGISTRY_KEY).buildAndRegister();

    @Override
    public void onInitialize() {
        DataFunctionsMod.init();
    }
}
