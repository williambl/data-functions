package com.williambl.dfunc.impl.platform;

import com.google.auto.service.AutoService;
import com.williambl.dfunc.impl.DataFunctionsModNeoForge;
import com.williambl.vampilang.lang.type.VType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.GameRules;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;

import java.util.function.Supplier;

@AutoService(IDFuncPlatformHelper.class)
public class NeoForgeDFuncPlatformHelper implements IDFuncPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Forge";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.isProduction();
    }

    @Override
    public void registerVType(ResourceLocation name, Supplier<VType> vType) {
        DataFunctionsModNeoForge.setVTypeToRegister(name, vType);
    }

    @Override
    public Registry<VType> getVTypeRegistry() {
        return DataFunctionsModNeoForge.TYPE_REGISTRY;
    }

    @Override
    public double mapNumberGameRule(GameRules.Value<?> v) {
        return v instanceof GameRules.IntegerValue i ? (double) i.get() : 0.0;
    }
}