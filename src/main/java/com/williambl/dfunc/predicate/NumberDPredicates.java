package com.williambl.dfunc.predicate;

import com.mojang.serialization.Codec;
import com.williambl.dfunc.Comparison;
import com.williambl.dfunc.DFunction;
import com.williambl.dfunc.DFunctionType;
import com.williambl.dfunc.DPredicates;
import net.minecraft.core.Registry;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.williambl.dfunc.DataFunctions.id;

public final class NumberDPredicates {
    public static final DFunctionType<Double, Boolean, ? extends Function<Boolean, ? extends DFunction<Double, Boolean>>> CONSTANT = DPredicates.constant(DFunction.NUMBER_PREDICATE_TYPE_REGISTRY);

    public static final DFunctionType<Double, Boolean, ? extends Function<List<DFunction<Double, Boolean>>, ? extends DFunction<Double, Boolean>>> AND = DPredicates.and(DFunction.NUMBER_PREDICATE_TYPE_REGISTRY);

    public static final DFunctionType<Double, Boolean, ? extends Function<List<DFunction<Double, Boolean>>, ? extends DFunction<Double, Boolean>>> OR = DPredicates.or(DFunction.NUMBER_PREDICATE_TYPE_REGISTRY);

    public static final DFunctionType<Double, Boolean, ? extends Function<DFunction<Double, Boolean>, ? extends DFunction<Double, Boolean>>> NOT = DPredicates.not(DFunction.NUMBER_PREDICATE_TYPE_REGISTRY);

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

    public static void init() {}
}
