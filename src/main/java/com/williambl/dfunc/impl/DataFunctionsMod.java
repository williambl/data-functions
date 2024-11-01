package com.williambl.dfunc.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.serialization.DataResult;
import com.williambl.dfunc.api.DFunctions;
import com.williambl.dfunc.api.DTypes;
import com.williambl.dfunc.api.functions.BlockInWorldDFunctions;
import com.williambl.dfunc.api.functions.EntityDFunctions;
import com.williambl.dfunc.api.functions.ItemStackDFunctions;
import com.williambl.dfunc.api.functions.LevelDFunctions;
import com.williambl.vampilang.lang.type.VType;
import com.williambl.vampilang.stdlib.ArithmeticVFunctions;
import com.williambl.vampilang.stdlib.LogicVFunctions;
import com.williambl.vampilang.stdlib.StandardVFunctions;
import com.williambl.vampilang.stdlib.StandardVTypes;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

public final class DataFunctionsMod implements ModInitializer {
	public static final String MODID = "dfunc";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	public static final ResourceKey<Registry<VType>> TYPE_REGISTRY_KEY = ResourceKey.createRegistryKey(id("vtypes"));
	public static final Registry<VType> TYPE_REGISTRY = FabricRegistryBuilder.createSimple(TYPE_REGISTRY_KEY).buildAndRegister();

	public static ResourceLocation id(String path) {
		return ResourceLocation.fromNamespaceAndPath(MODID, path);
	}

	@Override
	public void onInitialize() {
		StandardVTypes.register(DFunctions.ENV);
		StandardVFunctions.register(DFunctions.ENV);
		ArithmeticVFunctions.register(DFunctions.ENV);
		LogicVFunctions.register(DFunctions.ENV);
		DTypes.register(DFunctions.ENV);
		EntityDFunctions.register(DFunctions.ENV);
		BlockInWorldDFunctions.register(DFunctions.ENV);
		ItemStackDFunctions.register(DFunctions.ENV);
		LevelDFunctions.register(DFunctions.ENV);
	}

	private static <T> Function<JsonElement, DataResult<T>> maybeFailParseJson(Function<JsonElement, T> func) {
		return j -> {
			try {
				return DataResult.success(func.apply(j));
			} catch (JsonParseException e) {
				return DataResult.error(e::getLocalizedMessage);
			}
		};
	}
}