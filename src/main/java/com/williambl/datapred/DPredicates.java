package com.williambl.datapred;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.material.Fluid;

import static com.williambl.datapred.DatapackablePredicates.id;

public class DPredicates {
    public static final Codec<? extends DPredicate<Entity>> IS_SUBMERGED_IN = Registry.register(
            DPredicate.ENTITY_PREDICATE_CODEC_REGISTRY.registry(),
            id("submerged_in"),
            DPredicate.<TagKey<Fluid>, Entity>create(
                    TagKey.codec(Registries.FLUID).fieldOf("fluid"),
                    (f, e) -> e.isEyeInFluid(f)
            ));
}
