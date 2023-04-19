package com.williambl.dfunc;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.williambl.dfunc.DataFunctions.id;

public final class NumberDPredicates {
    public static final DFunctionType<Double, Boolean, ? extends Function<Boolean, ? extends DFunction<Double, Boolean>>> CONSTANT = Registry.register(
            DFunction.NUMBER_PREDICATE_TYPE_REGISTRY.registry(),
            id("constant"),
            DFunction.<Boolean, Double, Boolean>create(
                    Codec.BOOL.fieldOf("value"),
                    (value, n) -> value));

    public static final DFunctionType<Double, Boolean, ? extends Function<List<DFunction<Double, Boolean>>, ? extends DFunction<Double, Boolean>>> AND = Registry.register(
            DFunction.NUMBER_PREDICATE_TYPE_REGISTRY.registry(),
            id("and"),
            DFunction.<List<DFunction<Double, Boolean>>, Double, Boolean>create(
                    DFunction.NUMBER_PREDICATE_TYPE_REGISTRY.codec().listOf().fieldOf("predicates"),
                    (predicates, n) -> predicates.stream().allMatch(p -> p.apply(n))));

    public static final DFunctionType<Double, Boolean, ? extends Function<List<DFunction<Double, Boolean>>, ? extends DFunction<Double, Boolean>>> OR = Registry.register(
            DFunction.NUMBER_PREDICATE_TYPE_REGISTRY.registry(),
            id("or"),
            DFunction.<List<DFunction<Double, Boolean>>, Double, Boolean>create(
                    DFunction.NUMBER_PREDICATE_TYPE_REGISTRY.codec().listOf().fieldOf("predicates"),
                    (predicates, n) -> predicates.stream().anyMatch(p -> p.apply(n))));

    public static final DFunctionType<Double, Boolean, ? extends Function<DFunction<Double, Boolean>, ? extends DFunction<Double, Boolean>>> NOT = Registry.register(
            DFunction.NUMBER_PREDICATE_TYPE_REGISTRY.registry(),
            id("not"),
            DFunction.<DFunction<Double, Boolean>, Double, Boolean>create(
                    DFunction.NUMBER_PREDICATE_TYPE_REGISTRY.codec().fieldOf("predicate"),
                    (predicate, n) -> !predicate.apply(n)));

    public static final DFunctionType<Double, Boolean, ? extends BiFunction<Comparison, Double, ? extends DFunction<Double, Boolean>>> COMPARISON = Registry.register(
            DFunction.NUMBER_PREDICATE_TYPE_REGISTRY.registry(),
            id("comparison"),
            DFunction.<Comparison, Double, Double, Boolean>create(
                    Comparison.CODEC.fieldOf("comparison"),
                    Codec.DOUBLE.fieldOf("other_value"),
                    (comparison, otherValue, n) -> comparison.compare(n, otherValue)));

    public static final DFunctionType<Double, Boolean, ? extends BiFunction<Double, DFunction<Double, Boolean>, ? extends DFunction<Double, Boolean>>> MODULO = Registry.register(
            DFunction.NUMBER_PREDICATE_TYPE_REGISTRY.registry(),
            id("modulo"),
            DFunction.<Double, DFunction<Double, Boolean>, Double, Boolean>create(
                    Codec.DOUBLE.fieldOf("modulo"),
                    DFunction.NUMBER_PREDICATE_TYPE_REGISTRY.codec().fieldOf("predicate"),
                    (modulo, predicate, n) -> predicate.apply(n % modulo)));

    static void init() {}
}
