package com.williambl.dfunc.predicate;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.williambl.dfunc.DFunction;
import com.williambl.dfunc.DFunctionType;
import com.williambl.dfunc.DPredicates;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.function.Function;

import static com.williambl.dfunc.DataFunctions.id;

public final class BiEntityDPredicates {
    public static final DFunctionType<Pair<Entity, Entity>, Boolean, ? extends Function<Boolean, ? extends DFunction<Pair<Entity, Entity>, Boolean>>> CONSTANT = DPredicates.constant(DFunction.BI_ENTITY_PREDICATE_TYPE_REGISTRY);
    public static final DFunctionType<Pair<Entity, Entity>, Boolean, ? extends Function<List<DFunction<Pair<Entity, Entity>, Boolean>>, ? extends DFunction<Pair<Entity, Entity>, Boolean>>> AND = DPredicates.and(DFunction.BI_ENTITY_PREDICATE_TYPE_REGISTRY);
    public static final DFunctionType<Pair<Entity, Entity>, Boolean, ? extends Function<List<DFunction<Pair<Entity, Entity>, Boolean>>, ? extends DFunction<Pair<Entity, Entity>, Boolean>>> OR = DPredicates.or(DFunction.BI_ENTITY_PREDICATE_TYPE_REGISTRY);
    public static final DFunctionType<Pair<Entity, Entity>, Boolean, ? extends Function<DFunction<Pair<Entity, Entity>, Boolean>, ? extends DFunction<Pair<Entity, Entity>, Boolean>>> NOT = DPredicates.not(DFunction.BI_ENTITY_PREDICATE_TYPE_REGISTRY);

    public static final DFunctionType<Pair<Entity, Entity>, Boolean, ? extends Function<DFunction<Entity, Boolean>, ? extends DFunction<Pair<Entity, Entity>, Boolean>>> ACTOR_CONDITION = Registry.register(
            DFunction.BI_ENTITY_PREDICATE_TYPE_REGISTRY.registry(),
            id("actor_condition"),
            DFunction.<DFunction<Entity, Boolean>, Pair<Entity, Entity>, Boolean>create(
                    DFunction.ENTITY_PREDICATE_TYPE_REGISTRY.codec().fieldOf("condition"),
                    (predicate, entities) -> !predicate.apply(entities.getFirst())));

    public static final DFunctionType<Pair<Entity, Entity>, Boolean, ? extends Function<DFunction<Entity, Boolean>, ? extends DFunction<Pair<Entity, Entity>, Boolean>>> TARGET_CONDITION = Registry.register(
            DFunction.BI_ENTITY_PREDICATE_TYPE_REGISTRY.registry(),
            id("target_condition"),
            DFunction.<DFunction<Entity, Boolean>, Pair<Entity, Entity>, Boolean>create(
                    DFunction.ENTITY_PREDICATE_TYPE_REGISTRY.codec().fieldOf("condition"),
                    (predicate, entities) -> !predicate.apply(entities.getSecond())));

    public static final DFunctionType<Pair<Entity, Entity>, Boolean, ? extends Function<DFunction<Entity, Boolean>, ? extends DFunction<Pair<Entity, Entity>, Boolean>>> EITHER = Registry.register(
            DFunction.BI_ENTITY_PREDICATE_TYPE_REGISTRY.registry(),
            id("either"),
            DFunction.<DFunction<Entity, Boolean>, Pair<Entity, Entity>, Boolean>create(
                    DFunction.ENTITY_PREDICATE_TYPE_REGISTRY.codec().fieldOf("predicate"),
                    (predicate, entities) -> predicate.apply(entities.getFirst()) || predicate.apply(entities.getSecond())));

    public static final DFunctionType<Pair<Entity, Entity>, Boolean, ? extends Function<DFunction<Entity, Boolean>, ? extends DFunction<Pair<Entity, Entity>, Boolean>>> BOTH = Registry.register(
            DFunction.BI_ENTITY_PREDICATE_TYPE_REGISTRY.registry(),
            id("both"),
            DFunction.<DFunction<Entity, Boolean>, Pair<Entity, Entity>, Boolean>create(
                    DFunction.ENTITY_PREDICATE_TYPE_REGISTRY.codec().fieldOf("predicate"),
                    (predicate, entities) -> predicate.apply(entities.getFirst()) && predicate.apply(entities.getSecond())));

    public static final DFunctionType<Pair<Entity, Entity>, Boolean, ? extends Function<DFunction<Pair<Entity, Entity>, Boolean>, ? extends DFunction<Pair<Entity, Entity>, Boolean>>> UNDIRECTED = Registry.register(
            DFunction.BI_ENTITY_PREDICATE_TYPE_REGISTRY.registry(),
            id("undirected"),
            DFunction.<DFunction<Pair<Entity, Entity>, Boolean>, Pair<Entity, Entity>, Boolean>create(
                    DFunction.BI_ENTITY_PREDICATE_TYPE_REGISTRY.codec().fieldOf("predicate"),
                    (predicate, entities) -> predicate.apply(entities) || predicate.apply(Pair.of(entities.getSecond(), entities.getFirst()))));

    public static final DFunctionType<Pair<Entity, Entity>, Boolean, ? extends Function<DFunction<Level, Boolean>, ? extends DFunction<Pair<Entity, Entity>, Boolean>>> LEVEL_PREDICATE = DPredicates.delegate(
            DFunction.BI_ENTITY_PREDICATE_TYPE_REGISTRY,
            DFunction.LEVEL_PREDICATE_TYPE_REGISTRY,
            p -> p.getFirst().getLevel());

    public static void init() {}
}
