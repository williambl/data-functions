package com.williambl.dfunc.predicate;

import com.mojang.datafixers.util.Pair;
import com.williambl.dfunc.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.williambl.dfunc.DataFunctions.id;

public final class DamageDPredicates {
    public static final DFunctionType<DamageInstance, Boolean, ? extends Function<Boolean, ? extends DFunction<DamageInstance, Boolean>>> CONSTANT = DPredicates.constant(DFunction.DAMAGE_PREDICATE_TYPE_REGISTRY);
    public static final DFunctionType<DamageInstance, Boolean, ? extends Function<List<DFunction<DamageInstance, Boolean>>, ? extends DFunction<DamageInstance, Boolean>>> AND = DPredicates.and(DFunction.DAMAGE_PREDICATE_TYPE_REGISTRY);
    public static final DFunctionType<DamageInstance, Boolean, ? extends Function<List<DFunction<DamageInstance, Boolean>>, ? extends DFunction<DamageInstance, Boolean>>> OR = DPredicates.or(DFunction.DAMAGE_PREDICATE_TYPE_REGISTRY);
    public static final DFunctionType<DamageInstance, Boolean, ? extends Function<DFunction<DamageInstance, Boolean>, ? extends DFunction<DamageInstance, Boolean>>> NOT = DPredicates.not(DFunction.DAMAGE_PREDICATE_TYPE_REGISTRY);

    public static final DFunctionType<DamageInstance, Boolean, ? extends Function<DFunction<Level, Boolean>, ? extends DFunction<DamageInstance, Boolean>>> LEVEL_PREDICATE = DPredicates.delegate(
            DFunction.DAMAGE_PREDICATE_TYPE_REGISTRY,
            DFunction.LEVEL_PREDICATE_TYPE_REGISTRY,
            d -> d.affected().getLevel());

    public static final DFunctionType<DamageInstance, Boolean, ? extends Function<DFunction<Double, Boolean>, ? extends DFunction<DamageInstance, Boolean>>> AMOUNT_PREDICATE = DPredicates.delegate(
            id("amount_predicate"),
            DFunction.DAMAGE_PREDICATE_TYPE_REGISTRY,
            DFunction.NUMBER_PREDICATE_TYPE_REGISTRY,
            d -> (double) d.amount());

    public static final DFunctionType<DamageInstance, Boolean, ? extends Function<DFunction<Entity, Boolean>, ? extends DFunction<DamageInstance, Boolean>>> VICTIM_PREDICATE = DPredicates.delegate(
            id("victim_predicate"),
            DFunction.DAMAGE_PREDICATE_TYPE_REGISTRY,
            DFunction.ENTITY_PREDICATE_TYPE_REGISTRY,
            DamageInstance::affected);

    public static final DFunctionType<DamageInstance, Boolean, ? extends Function<DFunction<Entity, Boolean>, ? extends DFunction<DamageInstance, Boolean>>> ATTACKER_PREDICATE = DPredicates.delegateOptional(
            id("attacker_predicate"),
            DFunction.DAMAGE_PREDICATE_TYPE_REGISTRY,
            DFunction.ENTITY_PREDICATE_TYPE_REGISTRY,
            d -> Optional.ofNullable(d.source().getEntity()));

    public static final DFunctionType<DamageInstance, Boolean, ? extends Function<DFunction<Entity, Boolean>, ? extends DFunction<DamageInstance, Boolean>>> DIRECT_ATTACKER_PREDICATE = DPredicates.delegateOptional(
            id("direct_attacker_predicate"),
            DFunction.DAMAGE_PREDICATE_TYPE_REGISTRY,
            DFunction.ENTITY_PREDICATE_TYPE_REGISTRY,
            d -> Optional.ofNullable(d.source().getDirectEntity()));

    public static final DFunctionType<DamageInstance, Boolean, ? extends Function<DFunction<Pair<Entity, Entity>, Boolean>, ? extends DFunction<DamageInstance, Boolean>>> ATTACKER_AND_VICTIM_PREDICATE = DPredicates.delegateOptional(
            id("attacker_and_victim_predicate"),
            DFunction.DAMAGE_PREDICATE_TYPE_REGISTRY,
            DFunction.BI_ENTITY_PREDICATE_TYPE_REGISTRY,
            d -> Optional.ofNullable(d.source().getEntity()).map(e -> Pair.of(e, d.affected())));

    public static final DFunctionType<DamageInstance, Boolean, ? extends Function<TagKey<DamageType>, ? extends DFunction<DamageInstance, Boolean>>> DAMAGE_TYPE_TAG = Registry.register(
            DFunction.DAMAGE_PREDICATE_TYPE_REGISTRY.registry(),
            id("damage_type_tag"),
            DFunction.<TagKey<DamageType>, DamageInstance, Boolean>create(
                    TagKey.codec(Registries.DAMAGE_TYPE).fieldOf("tag"),
                    (tag, d) -> d.source().is(tag)));

    public static void init() {}
}
