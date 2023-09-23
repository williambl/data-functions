package com.williambl.dfunc.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.williambl.dfunc.api.DFunctions;
import com.williambl.dfunc.api.DTypes;
import com.williambl.dfunc.api.functions.BlockInWorldDFunctions;
import com.williambl.dfunc.api.functions.EntityDFunctions;
import com.williambl.dfunc.api.functions.ItemStackDFunctions;
import com.williambl.dfunc.api.functions.LevelDFunctions;
import com.williambl.dfunc.impl.platform.RegistrationProvider;
import com.williambl.vampilang.lang.type.VType;
import com.williambl.vampilang.stdlib.ArithmeticVFunctions;
import com.williambl.vampilang.stdlib.LogicVFunctions;
import com.williambl.vampilang.stdlib.StandardVFunctions;
import com.williambl.vampilang.stdlib.StandardVTypes;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

public final class DataFunctionsMod {
	public static final String MODID = "dfunc";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	public static final Codec<EntityPredicate> ADVANCEMENT_ENTITY_PREDICATE_CODEC = ExtraCodecs.JSON.comapFlatMap(maybeFailParseJson(EntityPredicate::fromJson), EntityPredicate::serializeToJson);
	public static final Codec<BlockPredicate> ADVANCEMENT_BLOCK_PREDICATE_CODEC = ExtraCodecs.JSON.comapFlatMap(maybeFailParseJson(BlockPredicate::fromJson), BlockPredicate::serializeToJson);
	public static final Codec<ItemPredicate> ADVANCEMENT_ITEM_PREDICATE_CODEC = ExtraCodecs.JSON.comapFlatMap(maybeFailParseJson(ItemPredicate::fromJson), ItemPredicate::serializeToJson);

	public static final ResourceKey<Registry<VType>> TYPE_REGISTRY_KEY = ResourceKey.createRegistryKey(id("vtypes"));

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