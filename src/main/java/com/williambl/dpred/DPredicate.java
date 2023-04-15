package com.williambl.dpred;

import com.mojang.datafixers.util.Function3;
import com.mojang.datafixers.util.Function4;
import com.mojang.datafixers.util.Function5;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static com.williambl.dpred.DatapackablePredicates.id;
import static com.williambl.dpred.DPredicateTypeRegistry.createRegistry;

/**
 * A data-defined predicate.
 * <p>
 * Create a new type of DPredicate using the many {@code create} methods in this class. If you need one with more than
 * four parameters, implement this interface yourself.
 * @param <T>   the type the predicate tests
 */
public interface DPredicate<T> extends Predicate<T> {
    /**
     * Get this DPredicate's type.
     * @return this DPredicate's type
     */
    DPredicateType<T, ?> type();

    DPredicateTypeRegistry<Entity> ENTITY_PREDICATE_TYPE_REGISTRY = createRegistry(id("entity_predicate"));
    DPredicateTypeRegistry<ItemStack> ITEMSTACK_PREDICATE_TYPE_REGISTRY = createRegistry(id("itemstack_predicate"));
    DPredicateTypeRegistry<BlockInWorld> BLOCK_IN_WORLD_PREDICATE_TYPE_REGISTRY = createRegistry(id("block_in_world_predicate"));
    DPredicateTypeRegistry<Pair<Entity, Entity>> BI_ENTITY_PREDICATE_TYPE_REGISTRY = createRegistry(id("bi_entity_predicate"));
    DPredicateTypeRegistry<Double> NUMBER_PREDICATE_TYPE_REGISTRY = createRegistry(id("number_predicate"));
    DPredicateTypeRegistry<Level> LEVEL_PREDICATE_TYPE_REGISTRY = createRegistry(id("level_predicate"));

    /**
     * Create a new DPredicate with no parameters.
     * @param function  the predicate function
     * @return          a DPredicate type
     */
    static <T> DPredicateType<T, ? extends Supplier<? extends DPredicate<T>>> create(Predicate<T> function) {
        return DPredicateImplementations.DPredicate0.create(function);
    }

    /**
     * Create a new DPredicate with one parameter.
     * @param codecA    the codec for the first parameter
     * @param function  the predicate function
     * @return          a DPredicate type
     */
    static <A, T> DPredicateType<T, ? extends Function<A, ? extends DPredicate<T>>> create(MapCodec<A> codecA, BiFunction<A, T, Boolean> function) {
        return DPredicateImplementations.DPredicate1.create(codecA, function);
    }

    /**
     * Create a new DPredicate with two parameters.
     * @param codecA    the codec for the first parameter
     * @param codecB    the codec for the second parameter
     * @param function  the predicate function
     * @return          a DPredicate type
     */
    static <A, B, T> DPredicateType<T, ? extends BiFunction<A, B, ? extends DPredicate<T>>> create(MapCodec<A> codecA, MapCodec<B> codecB, Function3<A, B, T, Boolean> function) {
        return DPredicateImplementations.DPredicate2.create(codecA, codecB, function);
    }

    /**
     * Create a new DPredicate with three parameters.
     * @param codecA    the codec for the first parameter
     * @param codecB    the codec for the second parameter
     * @param codecC    the codec for the third parameter
     * @param function  the predicate function
     * @return          a DPredicate type
     */
    static <A, B, C, T> DPredicateType<T, ? extends Function3<A, B, C, ? extends DPredicate<T>>> create(MapCodec<A> codecA, MapCodec<B> codecB, MapCodec<C> codecC, Function4<A, B, C, T, Boolean> function) {
        return DPredicateImplementations.DPredicate3.create(codecA, codecB, codecC, function);
    }

    /**
     * Create a new DPredicate with four parameters.
     * @param codecA    the codec for the first parameter
     * @param codecB    the codec for the second parameter
     * @param codecC    the codec for the third parameter
     * @param codecD    the codec for the fourth parameter
     * @param function  the predicate function
     * @return          a DPredicate type
     */
    static <A, B, C, D, T> DPredicateType<T, ? extends Function4<A, B, C, D, ? extends DPredicate<T>>> create(MapCodec<A> codecA, MapCodec<B> codecB, MapCodec<C> codecC, MapCodec<D> codecD, Function5<A, B, C, D, T, Boolean> function) {
        return DPredicateImplementations.DPredicate4.create(codecA, codecB, codecC, codecD, function);
    }
}
