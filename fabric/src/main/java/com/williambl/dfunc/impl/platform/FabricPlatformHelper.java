package com.williambl.dfunc.impl.platform;

import com.google.auto.service.AutoService;
import com.williambl.dfunc.impl.DataFunctionsModFabric;
import com.williambl.vampilang.lang.type.VType;
import net.fabricmc.fabric.api.gamerule.v1.rule.DoubleRule;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.GameRules;

import java.util.Map;
import java.util.Set;

@AutoService(IPlatformHelper.class)
public class FabricPlatformHelper implements IPlatformHelper {

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
    public VType getVType(ResourceLocation name) {
        return DataFunctionsModFabric.TYPE_REGISTRY.get(name);
    }

    @Override
    public Set<Map.Entry<ResourceKey<VType>, VType>> typeEntrySet() {
        return DataFunctionsModFabric.TYPE_REGISTRY.entrySet();
    }

    @Override
    public double mapNumberGameRule(GameRules.Value<?> v) {
        return v instanceof GameRules.IntegerValue i ? (double) i.get() : v instanceof DoubleRule d ? d.get() : 0.0;
    }
}