package com.williambl.dfunc.api.functions;

import com.mojang.serialization.Codec;
import com.williambl.dfunc.api.DFunction;
import com.williambl.dfunc.api.type.DFunctionType;
import net.minecraft.core.Registry;

import java.util.List;
import java.util.function.Function;

import static com.williambl.dfunc.DataFunctionsMod.id;

public class DPredicates {
    public static final DFunctionType<Boolean, ? extends Function<Boolean, ? extends DFunction<Boolean>>> CONSTANT = Registry.register(
            DFunction.PREDICATE.registry(),
            id("constant"),
            DFunction.<Boolean>create(Codec.BOOL));

    public static final DFunctionType<Boolean, ? extends Function<List<DFunction<Boolean>>, ? extends DFunction<Boolean>>> AND = Registry.register(
            DFunction.PREDICATE.registry(),
            id("and"),
            DFunction.<List<DFunction<Boolean>>, Boolean>create(
                    DFunction.PREDICATE.codec().listOf().fieldOf("predicates"),
                    (predicates, ctx) -> predicates.stream().allMatch(p -> p.apply(ctx))));

    public static final DFunctionType<Boolean, ? extends Function<List<DFunction<Boolean>>, ? extends DFunction<Boolean>>> OR = Registry.register(
            DFunction.PREDICATE.registry(),
            id("or"),
            DFunction.<List<DFunction<Boolean>>, Boolean>create(
                    DFunction.PREDICATE.codec().listOf().fieldOf("predicates"),
                    (predicates, ctx) -> predicates.stream().anyMatch(p -> p.apply(ctx))));

    public static final DFunctionType<Boolean, ? extends Function<DFunction<Boolean>, ? extends DFunction<Boolean>>> NOT = Registry.register(
            DFunction.PREDICATE.registry(),
            id("not"),
            DFunction.<DFunction<Boolean>, Boolean>create(
                    DFunction.PREDICATE.codec().fieldOf("predicate"),
                    (predicate, ctx) -> !predicate.apply(ctx)));

    public static void init() {}
}
