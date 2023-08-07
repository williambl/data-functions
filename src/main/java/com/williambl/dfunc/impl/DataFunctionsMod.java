package com.williambl.dfunc.impl;

import com.mojang.serialization.Codec;
import com.williambl.dfunc.api.DFunctions;
import com.williambl.dfunc.api.DTypes;
import com.williambl.dfunc.api.functions.*;
import com.williambl.vampilang.lang.type.VType;
import com.williambl.vampilang.stdlib.ArithmeticVFunctions;
import com.williambl.vampilang.stdlib.LogicVFunctions;
import com.williambl.vampilang.stdlib.StandardVFunctions;
import com.williambl.vampilang.stdlib.StandardVTypes;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DataFunctionsMod implements ModInitializer {
	public static final String MODID = "dfunc";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	public static final Codec<EntityPredicate> ADVANCEMENT_ENTITY_PREDICATE_CODEC = ExtraCodecs.JSON.xmap(EntityPredicate::fromJson, EntityPredicate::serializeToJson);
	public static final Codec<BlockPredicate> ADVANCEMENT_BLOCK_PREDICATE_CODEC = ExtraCodecs.JSON.xmap(BlockPredicate::fromJson, BlockPredicate::serializeToJson);
	public static final Codec<ItemPredicate> ADVANCEMENT_ITEM_PREDICATE_CODEC = ExtraCodecs.JSON.xmap(ItemPredicate::fromJson, ItemPredicate::serializeToJson);

	public static final ResourceKey<Registry<VType>> TYPE_REGISTRY_KEY = ResourceKey.createRegistryKey(id("vtypes"));
	public static final Registry<VType> TYPE_REGISTRY = FabricRegistryBuilder.createSimple(TYPE_REGISTRY_KEY).buildAndRegister();

	public static ResourceLocation id(String path) {
		return new ResourceLocation(MODID, path);
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
}