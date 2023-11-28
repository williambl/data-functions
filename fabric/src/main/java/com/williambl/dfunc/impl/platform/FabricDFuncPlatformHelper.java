package com.williambl.dfunc.impl.platform;

import com.google.auto.service.AutoService;
import com.williambl.dfunc.impl.DataFunctionsModFabric;
import com.williambl.vampilang.lang.type.VType;
import net.fabricmc.fabric.api.gamerule.v1.rule.DoubleRule;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.GameRules;

import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

@AutoService(IDFuncPlatformHelper.class)
public class FabricDFuncPlatformHelper implements IDFuncPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public void registerVType(ResourceLocation name, Supplier<VType> vType) {
        Registry.register(DataFunctionsModFabric.TYPE_REGISTRY, name, vType.get());
    }

    @Override
    public Registry<VType> getVTypeRegistry() {
        return DataFunctionsModFabric.TYPE_REGISTRY;
    }

    @Override
    public double mapNumberGameRule(GameRules.Value<?> v) {
        return v instanceof GameRules.IntegerValue i ? (double) i.get() : v instanceof DoubleRule d ? d.get() : 0.0;
    }
}