package com.williambl.dfunc.impl;

import com.williambl.vampilang.lang.type.VType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Supplier;

@Mod(DataFunctionsMod.MODID)
public class DataFunctionsModForge {
    public static final DeferredRegister<VType> TYPE_DEFERRED_REGISTER = DeferredRegister.create(DataFunctionsMod.TYPE_REGISTRY_KEY, DataFunctionsMod.MODID);
    public static Supplier<IForgeRegistry<VType>> TYPE_REGISTRY;
    public DataFunctionsModForge() {
        TYPE_REGISTRY = TYPE_DEFERRED_REGISTER.makeRegistry(RegistryBuilder::new);
        TYPE_DEFERRED_REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
        DataFunctionsMod.init();
    }
}
