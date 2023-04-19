package com.williambl.dfunc;

import com.mojang.datafixers.util.Function3;
import com.mojang.datafixers.util.Function4;
import com.mojang.datafixers.util.Function5;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static com.williambl.dfunc.DataFunctions.id;
import static com.williambl.dfunc.DFunctionTypeRegistry.createRegistry;

/**
 * A data-defined predicate.
 * <p>
 * Create a new type of DPredicate using the many {@code create} methods in this class. If you need one with more than
 * four parameters, implement this interface yourself.
 * @param <T>   the type the predicate tests
 */
public interface DFunction<T, R> extends Function<T, R> {
    /**
     * Get this DPredicate's type.
     * @return this DPredicate's type
     */
    DFunctionType<T, R, ?> type();

    DFunctionTypeRegistry<Entity, Boolean> ENTITY_PREDICATE_TYPE_REGISTRY = createRegistry(id("entity_predicate"), Codec.BOOL);
    DFunctionTypeRegistry<ItemStack, Boolean> ITEMSTACK_PREDICATE_TYPE_REGISTRY = createRegistry(id("itemstack_predicate"), Codec.BOOL);
    DFunctionTypeRegistry<BlockInWorld, Boolean> BLOCK_IN_WORLD_PREDICATE_TYPE_REGISTRY = createRegistry(id("block_in_world_predicate"), Codec.BOOL);
    DFunctionTypeRegistry<Pair<Entity, Entity>, Boolean> BI_ENTITY_PREDICATE_TYPE_REGISTRY = createRegistry(id("bi_entity_predicate"), Codec.BOOL);
    DFunctionTypeRegistry<Double, Boolean> NUMBER_PREDICATE_TYPE_REGISTRY = createRegistry(id("number_predicate"), Codec.BOOL);
    DFunctionTypeRegistry<Level, Boolean> LEVEL_PREDICATE_TYPE_REGISTRY = createRegistry(id("level_predicate"), Codec.BOOL);
    DFunctionTypeRegistry<Entity, Double> ENTITY_TO_NUMBER_FUNCTION_TYPE_REGISTRY = createRegistry(id("entity_to_number_function"), Codec.DOUBLE);

    /**
     * Create a new DPredicate with no parameters.
     * @param function  the predicate function
     * @return          a DPredicate type
     */
    static <T, R> DFunctionType<T, R, ? extends Supplier<? extends DFunction<T, R>>> create(Function<T, R> function) {
        return DFunctionImplementations.DFunction0.create(function);
    }

    /**
     * Create a new DPredicate with one parameter.
     * @param codecA    the codec for the first parameter
     * @param function  the predicate function
     * @return          a DPredicate type
     */
    static <A, T, R> DFunctionType<T, R, ? extends Function<A, ? extends DFunction<T, R>>> create(MapCodec<A> codecA, BiFunction<A, T, R> function) {
        return DFunctionImplementations.DFunction1.create(codecA, function);
    }

    /**
     * Create a new DPredicate with two parameters.
     * @param codecA    the codec for the first parameter
     * @param codecB    the codec for the second parameter
     * @param function  the predicate function
     * @return          a DPredicate type
     */
    static <A, B, T, R> DFunctionType<T, R, ? extends BiFunction<A, B, ? extends DFunction<T, R>>> create(MapCodec<A> codecA, MapCodec<B> codecB, Function3<A, B, T, R> function) {
        return DFunctionImplementations.DFunction2.create(codecA, codecB, function);
    }

    /**
     * Create a new DPredicate with three parameters.
     * @param codecA    the codec for the first parameter
     * @param codecB    the codec for the second parameter
     * @param codecC    the codec for the third parameter
     * @param function  the predicate function
     * @return          a DPredicate type
     */
    static <A, B, C, T, R> DFunctionType<T, R, ? extends Function3<A, B, C, ? extends DFunction<T, R>>> create(MapCodec<A> codecA, MapCodec<B> codecB, MapCodec<C> codecC, Function4<A, B, C, T, R> function) {
        return DFunctionImplementations.DFunction3.create(codecA, codecB, codecC, function);
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
    static <A, B, C, D, T, R> DFunctionType<T, R, ? extends Function4<A, B, C, D, ? extends DFunction<T, R>>> create(MapCodec<A> codecA, MapCodec<B> codecB, MapCodec<C> codecC, MapCodec<D> codecD, Function5<A, B, C, D, T, R> function) {
        return DFunctionImplementations.DFunction4.create(codecA, codecB, codecC, codecD, function);
    }
}
