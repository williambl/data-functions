package com.williambl.dpred;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.Fluid;

import java.util.List;

import static com.williambl.dpred.DatapackablePredicates.id;

public final class EntityDPredicates {
    public static final Codec<? extends DPredicate<Entity>> CONSTANT = Registry.register(
            DPredicate.ENTITY_PREDICATE_CODEC_REGISTRY.registry(),
            id("constant"),
            DPredicate.<Boolean, Entity>create(
                    Codec.BOOL.fieldOf("value"),
                    (value, e) -> value
            )
    );

    public static final Codec<? extends DPredicate<Entity>> AND = Registry.register(
            DPredicate.ENTITY_PREDICATE_CODEC_REGISTRY.registry(),
            id("and"),
            DPredicate.<List<DPredicate<Entity>>, Entity>create(
                    DPredicate.ENTITY_PREDICATE_CODEC_REGISTRY.codec().listOf().fieldOf("predicates"),
                    (predicates, e) -> predicates.stream().allMatch(p -> p.test(e))
            )
    );

    public static final Codec<? extends DPredicate<Entity>> OR = Registry.register(
            DPredicate.ENTITY_PREDICATE_CODEC_REGISTRY.registry(),
            id("or"),
            DPredicate.<List<DPredicate<Entity>>, Entity>create(
                    DPredicate.ENTITY_PREDICATE_CODEC_REGISTRY.codec().listOf().fieldOf("predicates"),
                    (predicates, e) -> predicates.stream().anyMatch(p -> p.test(e))
            )
    );

    public static final Codec<? extends DPredicate<Entity>> NOT = Registry.register(
            DPredicate.ENTITY_PREDICATE_CODEC_REGISTRY.registry(),
            id("not"),
            DPredicate.<DPredicate<Entity>, Entity>create(
                    DPredicate.ENTITY_PREDICATE_CODEC_REGISTRY.codec().fieldOf("predicate"),
                    (predicate, e) -> !predicate.test(e)
            )
    );

    public static final Codec<? extends DPredicate<Entity>> ON_FIRE = Registry.register(
            DPredicate.ENTITY_PREDICATE_CODEC_REGISTRY.registry(),
            id("on_fire"),
            DPredicate.create(
                    Entity::isOnFire
            )
    );

    public static final Codec<? extends DPredicate<Entity>> SNEAKING = Registry.register(
            DPredicate.ENTITY_PREDICATE_CODEC_REGISTRY.registry(),
            id("sneaking"),
            DPredicate.create(
                    Entity::isShiftKeyDown
            )
    );

    public static final Codec<? extends DPredicate<Entity>> SPRINTING = Registry.register(
            DPredicate.ENTITY_PREDICATE_CODEC_REGISTRY.registry(),
            id("sprinting"),
            DPredicate.create(
                    Entity::isSprinting
            )
    );

    public static final Codec<? extends DPredicate<Entity>> SWIMMING = Registry.register(
            DPredicate.ENTITY_PREDICATE_CODEC_REGISTRY.registry(),
            id("swimming"),
            DPredicate.create(
                    Entity::isSwimming
            )
    );

    public static final Codec<? extends DPredicate<Entity>> FALL_FLYING = Registry.register(
            DPredicate.ENTITY_PREDICATE_CODEC_REGISTRY.registry(),
            id("fall_flying"),
            DPredicate.<Entity>create(
                    e -> e instanceof LivingEntity l && l.isFallFlying()
            )
    );

    public static final Codec<? extends DPredicate<Entity>> SUBMERGED_IN = Registry.register(
            DPredicate.ENTITY_PREDICATE_CODEC_REGISTRY.registry(),
            id("submerged_in"),
            DPredicate.<TagKey<Fluid>, Entity>create(
                    TagKey.codec(Registries.FLUID).fieldOf("fluid"),
                    (f, e) -> e.isEyeInFluid(f)
            )
    );

    static void init() {}
}