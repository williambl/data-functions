package com.williambl.dfunc;

import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.williambl.dfunc.functions.DPredicates;
import com.williambl.dfunc.functions.EntityDPredicates;
import net.fabricmc.api.ModInitializer;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public final class DataFunctions implements ModInitializer {
	public static final String MODID = "dfunc";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	public static final Codec<EntityPredicate> ADVANCEMENT_ENTITY_PREDICATE_CODEC = ExtraCodecs.JSON.xmap(EntityPredicate::fromJson, EntityPredicate::serializeToJson);
	public static final Codec<BlockPredicate> ADVANCEMENT_BLOCK_PREDICATE_CODEC = ExtraCodecs.JSON.xmap(BlockPredicate::fromJson, BlockPredicate::serializeToJson);
	public static final Codec<ItemPredicate> ADVANCEMENT_ITEM_PREDICATE_CODEC = ExtraCodecs.JSON.xmap(ItemPredicate::fromJson, ItemPredicate::serializeToJson);

	public static ResourceLocation id(String path) {
		return new ResourceLocation(MODID, path);
	}

	@Override
	public void onInitialize() {
	}
}