package com.williambl.dfunc.impl;

import com.williambl.dfunc.api.DFunctions;
import com.williambl.dfunc.api.DTypes;
import com.williambl.dfunc.api.functions.BlockInWorldDFunctions;
import com.williambl.dfunc.api.functions.EntityDFunctions;
import com.williambl.dfunc.api.functions.ItemStackDFunctions;
import com.williambl.dfunc.api.functions.LevelDFunctions;
import com.williambl.dfunc.impl.platform.IDFuncPlatformHelper;
import com.williambl.vampilang.lang.type.VType;
import com.williambl.vampilang.stdlib.ArithmeticVFunctions;
import com.williambl.vampilang.stdlib.LogicVFunctions;
import com.williambl.vampilang.stdlib.StandardVFunctions;
import com.williambl.vampilang.stdlib.StandardVTypes;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DataFunctionsMod {
	public static final String MODID = "dfunc";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
	public static final ResourceKey<Registry<VType>> TYPE_REGISTRY_KEY = ResourceKey.createRegistryKey(id("vtypes"));
	public static final Registry<VType> TYPE_REGISTRY = IDFuncPlatformHelper.INSTANCE.getVTypeRegistry();

	public static ResourceLocation id(String path) {
		return new ResourceLocation(MODID, path);
	}

	public static void init() {
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

}