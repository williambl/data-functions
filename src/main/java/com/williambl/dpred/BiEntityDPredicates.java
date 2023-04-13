package com.williambl.dpred;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.Fluid;

import java.util.List;

import static com.williambl.dpred.DatapackablePredicates.id;

public final class BiEntityDPredicates {
    public static final Codec<? extends DPredicate<Pair<Entity, Entity>>> CONSTANT = Registry.register(
            DPredicate.BI_ENTITY_PREDICATE_CODEC_REGISTRY.registry(),
            id("constant"),
            DPredicate.<Boolean, Pair<Entity, Entity>>create(
                    Codec.BOOL.fieldOf("value"),
                    (value, entities) -> value
            )
    );

    public static final Codec<? extends DPredicate<Pair<Entity, Entity>>> AND = Registry.register(
            DPredicate.BI_ENTITY_PREDICATE_CODEC_REGISTRY.registry(),
            id("and"),
            DPredicate.<List<DPredicate<Pair<Entity, Entity>>>, Pair<Entity, Entity>>create(
                    DPredicate.BI_ENTITY_PREDICATE_CODEC_REGISTRY.codec().listOf().fieldOf("predicates"),
                    (predicates, entities) -> predicates.stream().allMatch(p -> p.test(entities))
            )
    );

    public static final Codec<? extends DPredicate<Pair<Entity, Entity>>> OR = Registry.register(
            DPredicate.BI_ENTITY_PREDICATE_CODEC_REGISTRY.registry(),
            id("or"),
            DPredicate.<List<DPredicate<Pair<Entity, Entity>>>, Pair<Entity, Entity>>create(
                    DPredicate.BI_ENTITY_PREDICATE_CODEC_REGISTRY.codec().listOf().fieldOf("predicates"),
                    (predicates, entities) -> predicates.stream().anyMatch(p -> p.test(entities))
            )
    );

    public static final Codec<? extends DPredicate<Pair<Entity, Entity>>> NOT = Registry.register(
            DPredicate.BI_ENTITY_PREDICATE_CODEC_REGISTRY.registry(),
            id("not"),
            DPredicate.<DPredicate<Pair<Entity, Entity>>, Pair<Entity, Entity>>create(
                    DPredicate.BI_ENTITY_PREDICATE_CODEC_REGISTRY.codec().fieldOf("predicate"),
                    (predicate, entities) -> !predicate.test(entities)
            )
    );

    public static final Codec<? extends DPredicate<Pair<Entity, Entity>>> ACTOR_CONDITION = Registry.register(
            DPredicate.BI_ENTITY_PREDICATE_CODEC_REGISTRY.registry(),
            id("actor_condition"),
            DPredicate.<DPredicate<Entity>, Pair<Entity, Entity>>create(
                    DPredicate.ENTITY_PREDICATE_CODEC_REGISTRY.codec().fieldOf("condition"),
                    (predicate, entities) -> !predicate.test(entities.getFirst())
            )
    );

    public static final Codec<? extends DPredicate<Pair<Entity, Entity>>> TARGET_CONDITION = Registry.register(
            DPredicate.BI_ENTITY_PREDICATE_CODEC_REGISTRY.registry(),
            id("target_condition"),
            DPredicate.<DPredicate<Entity>, Pair<Entity, Entity>>create(
                    DPredicate.ENTITY_PREDICATE_CODEC_REGISTRY.codec().fieldOf("condition"),
                    (predicate, entities) -> !predicate.test(entities.getSecond())
            )
    );

    public static final Codec<? extends DPredicate<Pair<Entity, Entity>>> EITHER = Registry.register(
            DPredicate.BI_ENTITY_PREDICATE_CODEC_REGISTRY.registry(),
            id("either"),
            DPredicate.<DPredicate<Entity>, Pair<Entity, Entity>>create(
                    DPredicate.ENTITY_PREDICATE_CODEC_REGISTRY.codec().fieldOf("predicate"),
                    (predicate, entities) -> predicate.test(entities.getFirst()) || predicate.test(entities.getSecond())
            )
    );

    public static final Codec<? extends DPredicate<Pair<Entity, Entity>>> BOTH = Registry.register(
            DPredicate.BI_ENTITY_PREDICATE_CODEC_REGISTRY.registry(),
            id("both"),
            DPredicate.<DPredicate<Entity>, Pair<Entity, Entity>>create(
                    DPredicate.ENTITY_PREDICATE_CODEC_REGISTRY.codec().fieldOf("predicate"),
                    (predicate, entities) -> predicate.test(entities.getFirst()) && predicate.test(entities.getSecond())
            )
    );

    public static final Codec<? extends DPredicate<Pair<Entity, Entity>>> UNDIRECTED = Registry.register(
            DPredicate.BI_ENTITY_PREDICATE_CODEC_REGISTRY.registry(),
            id("undirected"),
            DPredicate.<DPredicate<Pair<Entity, Entity>>, Pair<Entity, Entity>>create(
                    DPredicate.BI_ENTITY_PREDICATE_CODEC_REGISTRY.codec().fieldOf("predicate"),
                    (predicate, entities) -> predicate.test(entities) || predicate.test(Pair.of(entities.getSecond(), entities.getFirst()))
            )
    );

    static void init() {}
}
