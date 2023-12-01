package com.williambl.dfunc.impl.platform;

import com.williambl.vampilang.lang.type.VType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.GameRules;

import java.util.function.Supplier;

public interface IDFuncPlatformHelper {

    public static final IDFuncPlatformHelper INSTANCE = DataFunctionsServiceUtil.load(IDFuncPlatformHelper.class);

    /**
     * Gets the name of the current platform
     *
     * @return The name of the current platform.
     */
    String getPlatformName();

    /**
     * Checks if a mod with the given id is loaded.
     *
     * @param modId The mod to check if it is loaded.
     * @return True if the mod is loaded, false otherwise.
     */
    boolean isModLoaded(String modId);

    /**
     * Check if the game is currently in a development environment.
     *
     * @return True if in a development environment, false otherwise.
     */
    boolean isDevelopmentEnvironment();

    void registerVType(ResourceLocation name, Supplier<VType> vType);

    /**
     * Gets the VType registry.
     *
     * @return The VTYpe registry.
     */
    Registry<VType> getVTypeRegistry();

    /**
     * Maps a number gamerule to a double.
     * This is platform specific as Fabric have their own implementation for
     * double values in their Gamerule API.
     *
     * @return The mapped double from the gamerule.
     */
    double mapNumberGameRule(GameRules.Value<?> v);

}
