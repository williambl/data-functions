package com.williambl.dpred;

import com.mojang.serialization.Codec;
import net.fabricmc.api.ModInitializer;

import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DatapackablePredicates implements ModInitializer {
	public static final String MODID = "dpred";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	public static final Codec<EntityPredicate> ADVANCEMENT_ENTITY_PREDICATE_CODEC = ExtraCodecs.JSON.xmap(EntityPredicate::fromJson, EntityPredicate::serializeToJson);
	public static final Codec<BlockPredicate> ADVANCEMENT_BLOCK_PREDICATE_CODEC = ExtraCodecs.JSON.xmap(BlockPredicate::fromJson, BlockPredicate::serializeToJson);
	public static final Codec<ItemPredicate> ADVANCEMENT_ITEM_PREDICATE_CODEC = ExtraCodecs.JSON.xmap(ItemPredicate::fromJson, ItemPredicate::serializeToJson);

	public static ResourceLocation id(String path) {
		return new ResourceLocation(MODID, path);
	}

	@Override
	public void onInitialize() {
		EntityDPredicates.init();
		BiEntityDPredicates.init();
		BlockInWorldDPredicates.init();
		NumberDPredicates.init();
		LevelDPredicates.init();
	}
}