package com.williambl.dfunc;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.function.Function;

import static com.williambl.dfunc.DataFunctions.id;

public final class BiEntityDPredicates {
    public static final DPredicateType<Pair<Entity, Entity>, ? extends Function<Boolean, ? extends DPredicate<Pair<Entity, Entity>>>> CONSTANT = Registry.register(
            DPredicate.BI_ENTITY_PREDICATE_TYPE_REGISTRY.registry(),
            id("constant"),
            DPredicate.<Boolean, Pair<Entity, Entity>>create(
                    Codec.BOOL.fieldOf("value"),
                    (value, entities) -> value
            )
    );

    public static final DPredicateType<Pair<Entity, Entity>, ? extends Function<List<DPredicate<Pair<Entity, Entity>>>, ? extends DPredicate<Pair<Entity, Entity>>>> AND = Registry.register(
            DPredicate.BI_ENTITY_PREDICATE_TYPE_REGISTRY.registry(),
            id("and"),
            DPredicate.<List<DPredicate<Pair<Entity, Entity>>>, Pair<Entity, Entity>>create(
                    DPredicate.BI_ENTITY_PREDICATE_TYPE_REGISTRY.codec().listOf().fieldOf("predicates"),
                    (predicates, entities) -> predicates.stream().allMatch(p -> p.test(entities))
            )
    );

    public static final DPredicateType<Pair<Entity, Entity>, ? extends Function<List<DPredicate<Pair<Entity, Entity>>>, ? extends DPredicate<Pair<Entity, Entity>>>> OR = Registry.register(
            DPredicate.BI_ENTITY_PREDICATE_TYPE_REGISTRY.registry(),
            id("or"),
            DPredicate.<List<DPredicate<Pair<Entity, Entity>>>, Pair<Entity, Entity>>create(
                    DPredicate.BI_ENTITY_PREDICATE_TYPE_REGISTRY.codec().listOf().fieldOf("predicates"),
                    (predicates, entities) -> predicates.stream().anyMatch(p -> p.test(entities))
            )
    );

    public static final DPredicateType<Pair<Entity, Entity>, ? extends Function<DPredicate<Pair<Entity, Entity>>, ? extends DPredicate<Pair<Entity, Entity>>>> NOT = Registry.register(
            DPredicate.BI_ENTITY_PREDICATE_TYPE_REGISTRY.registry(),
            id("not"),
            DPredicate.<DPredicate<Pair<Entity, Entity>>, Pair<Entity, Entity>>create(
                    DPredicate.BI_ENTITY_PREDICATE_TYPE_REGISTRY.codec().fieldOf("predicate"),
                    (predicate, entities) -> !predicate.test(entities)
            )
    );

    public static final DPredicateType<Pair<Entity, Entity>, ? extends Function<DPredicate<Entity>, ? extends DPredicate<Pair<Entity, Entity>>>> ACTOR_CONDITION = Registry.register(
            DPredicate.BI_ENTITY_PREDICATE_TYPE_REGISTRY.registry(),
            id("actor_condition"),
            DPredicate.<DPredicate<Entity>, Pair<Entity, Entity>>create(
                    DPredicate.ENTITY_PREDICATE_TYPE_REGISTRY.codec().fieldOf("condition"),
                    (predicate, entities) -> !predicate.test(entities.getFirst())
            )
    );

    public static final DPredicateType<Pair<Entity, Entity>, ? extends Function<DPredicate<Entity>, ? extends DPredicate<Pair<Entity, Entity>>>> TARGET_CONDITION = Registry.register(
            DPredicate.BI_ENTITY_PREDICATE_TYPE_REGISTRY.registry(),
            id("target_condition"),
            DPredicate.<DPredicate<Entity>, Pair<Entity, Entity>>create(
                    DPredicate.ENTITY_PREDICATE_TYPE_REGISTRY.codec().fieldOf("condition"),
                    (predicate, entities) -> !predicate.test(entities.getSecond())
            )
    );

    public static final DPredicateType<Pair<Entity, Entity>, ? extends Function<DPredicate<Entity>, ? extends DPredicate<Pair<Entity, Entity>>>> EITHER = Registry.register(
            DPredicate.BI_ENTITY_PREDICATE_TYPE_REGISTRY.registry(),
            id("either"),
            DPredicate.<DPredicate<Entity>, Pair<Entity, Entity>>create(
                    DPredicate.ENTITY_PREDICATE_TYPE_REGISTRY.codec().fieldOf("predicate"),
                    (predicate, entities) -> predicate.test(entities.getFirst()) || predicate.test(entities.getSecond())
            )
    );

    public static final DPredicateType<Pair<Entity, Entity>, ? extends Function<DPredicate<Entity>, ? extends DPredicate<Pair<Entity, Entity>>>> BOTH = Registry.register(
            DPredicate.BI_ENTITY_PREDICATE_TYPE_REGISTRY.registry(),
            id("both"),
            DPredicate.<DPredicate<Entity>, Pair<Entity, Entity>>create(
                    DPredicate.ENTITY_PREDICATE_TYPE_REGISTRY.codec().fieldOf("predicate"),
                    (predicate, entities) -> predicate.test(entities.getFirst()) && predicate.test(entities.getSecond())
            )
    );

    public static final DPredicateType<Pair<Entity, Entity>, ? extends Function<DPredicate<Pair<Entity, Entity>>, ? extends DPredicate<Pair<Entity, Entity>>>> UNDIRECTED = Registry.register(
            DPredicate.BI_ENTITY_PREDICATE_TYPE_REGISTRY.registry(),
            id("undirected"),
            DPredicate.<DPredicate<Pair<Entity, Entity>>, Pair<Entity, Entity>>create(
                    DPredicate.BI_ENTITY_PREDICATE_TYPE_REGISTRY.codec().fieldOf("predicate"),
                    (predicate, entities) -> predicate.test(entities) || predicate.test(Pair.of(entities.getSecond(), entities.getFirst()))
            )
    );

    public static final DPredicateType<Pair<Entity, Entity>, ? extends Function<DPredicate<Level>, ? extends DPredicate<Pair<Entity, Entity>>>> LEVEL_PREDICATE = Registry.register(
            DPredicate.BI_ENTITY_PREDICATE_TYPE_REGISTRY.registry(),
            id("level_predicate"),
            DPredicate.<DPredicate<Level>, Pair<Entity, Entity>>create(
                    DPredicate.LEVEL_PREDICATE_TYPE_REGISTRY.codec().fieldOf("predicate"),
                    (predicate, e) -> predicate.test(e.getFirst().level)
            ));

    static void init() {}
}
