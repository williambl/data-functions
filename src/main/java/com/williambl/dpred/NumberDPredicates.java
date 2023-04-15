package com.williambl.dpred;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.williambl.dpred.DatapackablePredicates.id;

public final class NumberDPredicates {
    public static final DPredicateType<Double, ? extends Function<Boolean, ? extends DPredicate<Double>>> CONSTANT = Registry.register(
            DPredicate.NUMBER_PREDICATE_TYPE_REGISTRY.registry(),
            id("constant"),
            DPredicate.<Boolean, Double>create(
                    Codec.BOOL.fieldOf("value"),
                    (value, n) -> value
            )
    );

    public static final DPredicateType<Double, ? extends Function<List<DPredicate<Double>>, ? extends DPredicate<Double>>> AND = Registry.register(
            DPredicate.NUMBER_PREDICATE_TYPE_REGISTRY.registry(),
            id("and"),
            DPredicate.<List<DPredicate<Double>>, Double>create(
                    DPredicate.NUMBER_PREDICATE_TYPE_REGISTRY.codec().listOf().fieldOf("predicates"),
                    (predicates, n) -> predicates.stream().allMatch(p -> p.test(n))
            )
    );

    public static final DPredicateType<Double, ? extends Function<List<DPredicate<Double>>, ? extends DPredicate<Double>>> OR = Registry.register(
            DPredicate.NUMBER_PREDICATE_TYPE_REGISTRY.registry(),
            id("or"),
            DPredicate.<List<DPredicate<Double>>, Double>create(
                    DPredicate.NUMBER_PREDICATE_TYPE_REGISTRY.codec().listOf().fieldOf("predicates"),
                    (predicates, n) -> predicates.stream().anyMatch(p -> p.test(n))
            )
    );

    public static final DPredicateType<Double, ? extends Function<DPredicate<Double>, ? extends DPredicate<Double>>> NOT = Registry.register(
            DPredicate.NUMBER_PREDICATE_TYPE_REGISTRY.registry(),
            id("not"),
            DPredicate.<DPredicate<Double>, Double>create(
                    DPredicate.NUMBER_PREDICATE_TYPE_REGISTRY.codec().fieldOf("predicate"),
                    (predicate, n) -> !predicate.test(n)
            )
    );

    public static final DPredicateType<Double, ? extends BiFunction<Comparison, Double, ? extends DPredicate<Double>>> COMPARISON = Registry.register(
            DPredicate.NUMBER_PREDICATE_TYPE_REGISTRY.registry(),
            id("comparison"),
            DPredicate.<Comparison, Double, Double>create(
                    Comparison.CODEC.fieldOf("comparison"),
                    Codec.DOUBLE.fieldOf("other_value"),
                    (comparison, otherValue, n) -> comparison.compare(n, otherValue)));

    public static final DPredicateType<Double, ? extends BiFunction<Double, DPredicate<Double>, ? extends DPredicate<Double>>> MODULO = Registry.register(
            DPredicate.NUMBER_PREDICATE_TYPE_REGISTRY.registry(),
            id("modulo"),
            DPredicate.<Double, DPredicate<Double>, Double>create(
                    Codec.DOUBLE.fieldOf("modulo"),
                    DPredicate.NUMBER_PREDICATE_TYPE_REGISTRY.codec().fieldOf("predicate"),
                    (modulo, predicate, n) -> predicate.test(n % modulo)
            )
    );

    static void init() {}
}
