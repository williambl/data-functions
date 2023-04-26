package com.williambl.dfunc;

import com.mojang.datafixers.util.Function3;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.Entity;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.williambl.dfunc.DataFunctions.id;

public final class DPredicates {
    public static <T> DFunctionType<T, Boolean, ? extends Function<Boolean, ? extends DFunction<T, Boolean>>> constant(DFunctionTypeRegistry<T, Boolean> functionType) {
        return Registry.register(
                functionType.registry(),
                id("constant"),
                DFunction.<Boolean, T, Boolean>create(
                        Codec.BOOL.fieldOf("value"),
                        (value, t) -> value));
    }

    public static <T> DFunctionType<T, Boolean, ? extends Function<List<DFunction<T, Boolean>>, ? extends DFunction<T, Boolean>>> and(DFunctionTypeRegistry<T, Boolean> functionType) {
        return Registry.register(
                functionType.registry(),
                id("and"),
                DFunction.<List<DFunction<T, Boolean>>, T, Boolean>create(
                        functionType.codec().listOf().fieldOf("predicates"),
                        (predicates, t) -> predicates.stream().allMatch(p -> p.apply(t))));
    }

    public static <T> DFunctionType<T, Boolean, ? extends Function<List<DFunction<T, Boolean>>, ? extends DFunction<T, Boolean>>> or(DFunctionTypeRegistry<T, Boolean> functionType) {
        return Registry.register(
                functionType.registry(),
                id("or"),
                DFunction.<List<DFunction<T, Boolean>>, T, Boolean>create(
                        functionType.codec().listOf().fieldOf("predicates"),
                        (predicates, t) -> predicates.stream().anyMatch(p -> p.apply(t))));
    }

    public static <T> DFunctionType<T, Boolean, ? extends Function<DFunction<T, Boolean>, ? extends DFunction<T, Boolean>>> not(DFunctionTypeRegistry<T, Boolean> functionType) {
        return Registry.register(
                functionType.registry(),
                id("not"),
                DFunction.<DFunction<T, Boolean>, T, Boolean>create(
                        functionType.codec().fieldOf("predicate"),
                        (predicate, t) -> !predicate.apply(t)));
    }

    public static <T1, T2> DFunctionType<T1, Boolean, ? extends Function<DFunction<T2, Boolean>, ? extends DFunction<T1, Boolean>>> delegate(DFunctionTypeRegistry<T1, Boolean> functionType, DFunctionTypeRegistry<T2, Boolean> functionType2, Function<T1, T2> transformer) {
        return Registry.register(
                functionType.registry(),
                functionType2.registry().key().location(),
                DFunction.<DFunction<T2, Boolean>, T1, Boolean>create(
                        functionType2.codec().fieldOf("predicate"),
                        (f2, t1) -> f2.apply(transformer.apply(t1))));
    }

    public static <T> DFunctionType<T, Boolean, ? extends BiFunction<DFunction<T, T>, DFunction<T, Boolean>, ? extends DFunction<T, Boolean>>> transformed(DFunctionTypeRegistry<T, Boolean> functionType, DFunctionTypeRegistry<T, T> transformerFunctionType) {
        return Registry.register(
                functionType.registry(),
                id("transformed"),
                DFunction.<DFunction<T, T>, DFunction<T, Boolean>, T, Boolean>create(
                        transformerFunctionType.codec().fieldOf("transformer"),
                        functionType.codec().fieldOf("predicate"),
                        (f1, f2, t1) -> f2.apply(f1.apply(t1))));
    }

    public static <T1, T2> DFunctionType<T1, Boolean, ? extends Function<DFunction<T2, Boolean>, ? extends DFunction<T1, Boolean>>> delegateOptional(DFunctionTypeRegistry<T1, Boolean> functionType, DFunctionTypeRegistry<T2, Boolean> functionType2, Function<T1, Optional<T2>> transformer) {
        return Registry.register(
                functionType.registry(),
                functionType2.registry().key().location(),
                DFunction.<DFunction<T2, Boolean>, T1, Boolean>create(
                        functionType2.codec().fieldOf("predicate"),
                        (f2, t1) -> transformer.apply(t1).filter(f2::apply).isPresent()));
    }

    public static <T1, T2> DFunctionType<T1, Boolean, ? extends BiFunction<DFunction<T1, T2>, DFunction<T2, Boolean>, ? extends DFunction<T1, Boolean>>> delegateWithDFunction(DFunctionTypeRegistry<T1, Boolean> functionType, DFunctionTypeRegistry<T2, Boolean> functionType2, DFunctionTypeRegistry<T1, T2> transformerFunctionType) {
        return Registry.register(
                functionType.registry(),
                functionType2.registry().key().location(),
                DFunction.<DFunction<T1, T2>, DFunction<T2, Boolean>, T1, Boolean>create(
                        transformerFunctionType.codec().fieldOf("argument"),
                        functionType2.codec().fieldOf("predicate"),
                        (transformer, f2, t1) -> f2.apply(transformer.apply(t1))));
    }

    public static <T1, T2> DFunctionType<T1, Boolean, ? extends Function3<DFunction<T1, T2>, DFunction<T1, T2>, DFunction<Pair<T2, T2>, Boolean>, ? extends DFunction<T1, Boolean>>> delegatePairWithDFunction(DFunctionTypeRegistry<T1, Boolean> functionType, DFunctionTypeRegistry<Pair<T2, T2>, Boolean> functionType2, DFunctionTypeRegistry<T1, T2> transformerFunctionType) {
        return Registry.register(
                functionType.registry(),
                functionType2.registry().key().location(),
                DFunction.<DFunction<T1, T2>, DFunction<T1, T2>, DFunction<Pair<T2, T2>, Boolean>, T1, Boolean>create(
                        transformerFunctionType.codec().fieldOf("argument_1"),
                        transformerFunctionType.codec().fieldOf("argument_2"),
                        functionType2.codec().fieldOf("predicate"),
                        (transformer1, transformer2, f2, t1) -> f2.apply(Pair.of(transformer1.apply(t1), transformer2.apply(t1)))));
    }
}
