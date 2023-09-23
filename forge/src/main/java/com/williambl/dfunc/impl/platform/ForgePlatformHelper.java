package com.williambl.dfunc.impl.platform;

import com.google.auto.service.AutoService;
import com.williambl.dfunc.impl.DataFunctionsMod;
import com.williambl.dfunc.impl.DataFunctionsModForge;
import com.williambl.vampilang.lang.type.VType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@AutoService(IPlatformHelper.class)
public class ForgePlatformHelper implements IPlatformHelper {

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
    public VType getVType(ResourceLocation name) {
        return RegistryObject.create(name, DataFunctionsModForge.TYPE_REGISTRY.get()).get();
    }

    @Override
    public Set<Map.Entry<ResourceKey<VType>, VType>> typeEntrySet() {
        return DataFunctionsModForge.TYPE_REGISTRY.get().getEntries();
    }

    @Override
    public double mapNumberGameRule(GameRules.Value<?> v) {
        return v instanceof GameRules.IntegerValue i ? (double) i.get() : 0.0;
    }
}