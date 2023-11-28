package com.williambl.dfunc.impl;

import com.williambl.vampilang.lang.type.VType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Mod(DataFunctionsMod.MODID)
public class DataFunctionsModNeoForge {
    protected static final Map<ResourceLocation, Supplier<VType>> VTYPES_TO_REGISTER = new HashMap<>();
    protected static boolean hasRegisteredVTypes = false;

    public static final Registry<VType> TYPE_REGISTRY = new RegistryBuilder<>(DataFunctionsMod.TYPE_REGISTRY_KEY).create();
    public DataFunctionsModNeoForge() {
        DataFunctionsMod.init();
    }

    public static void setVTypeToRegister(ResourceLocation name, Supplier<VType> vType) {
        if (hasRegisteredVTypes) {
            throw new UnsupportedOperationException("Could not register VType " + name + " after the RegisterEvent stage.");
        }
        VTYPES_TO_REGISTER.put(name, vType);
    }

    @Mod.EventBusSubscriber(modid = DataFunctionsMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class DataFunctionsModEvents {

        @SubscribeEvent
        public static void registerTypeRegistry(NewRegistryEvent event) {
            event.register(TYPE_REGISTRY);
        }

        @SubscribeEvent
        public static void registerVTypes(RegisterEvent event) {
            if (!event.getRegistryKey().equals(DataFunctionsMod.TYPE_REGISTRY_KEY)) return;

            VTYPES_TO_REGISTER.forEach((name, vType) -> {
                event.register(DataFunctionsMod.TYPE_REGISTRY_KEY, name, vType);
            });
            VTYPES_TO_REGISTER.clear();
            hasRegisteredVTypes = true;
        }

    }
}
