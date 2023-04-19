package com.williambl.dfunc.predicate;

import com.mojang.serialization.Codec;
import com.williambl.dfunc.DFunction;
import com.williambl.dfunc.DFunctionType;
import com.williambl.dfunc.DataFunctions;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;

import java.util.List;
import java.util.function.Function;

import static com.williambl.dfunc.DataFunctions.id;

public final class BlockInWorldDPredicates {
    public static final DFunctionType<BlockInWorld, Boolean, ? extends Function<Boolean, ? extends DFunction<BlockInWorld, Boolean>>> CONSTANT = Registry.register(
            DFunction.BLOCK_IN_WORLD_PREDICATE_TYPE_REGISTRY.registry(),
            id("constant"),
            DFunction.<Boolean, BlockInWorld, Boolean>create(
                    Codec.BOOL.fieldOf("value"),
                    (value, block) -> value
            ));

    public static final DFunctionType<BlockInWorld, Boolean, ? extends Function<List<DFunction<BlockInWorld, Boolean>>, ? extends DFunction<BlockInWorld, Boolean>>> AND = Registry.register(
            DFunction.BLOCK_IN_WORLD_PREDICATE_TYPE_REGISTRY.registry(),
            id("and"),
            DFunction.<List<DFunction<BlockInWorld, Boolean>>, BlockInWorld, Boolean>create(
                    DFunction.BLOCK_IN_WORLD_PREDICATE_TYPE_REGISTRY.codec().listOf().fieldOf("predicates"),
                    (predicates, block) -> predicates.stream().allMatch(p -> p.apply(block))
            ));

    public static final DFunctionType<BlockInWorld, Boolean, ? extends Function<List<DFunction<BlockInWorld, Boolean>>, ? extends DFunction<BlockInWorld, Boolean>>> OR = Registry.register(
            DFunction.BLOCK_IN_WORLD_PREDICATE_TYPE_REGISTRY.registry(),
            id("or"),
            DFunction.<List<DFunction<BlockInWorld, Boolean>>, BlockInWorld, Boolean>create(
                    DFunction.BLOCK_IN_WORLD_PREDICATE_TYPE_REGISTRY.codec().listOf().fieldOf("predicates"),
                    (predicates, block) -> predicates.stream().anyMatch(p -> p.apply(block))
            ));

    public static final DFunctionType<BlockInWorld, Boolean, ? extends Function<DFunction<BlockInWorld, Boolean>, ? extends DFunction<BlockInWorld, Boolean>>> NOT = Registry.register(
            DFunction.BLOCK_IN_WORLD_PREDICATE_TYPE_REGISTRY.registry(),
            id("not"),
            DFunction.<DFunction<BlockInWorld, Boolean>, BlockInWorld, Boolean>create(
                    DFunction.BLOCK_IN_WORLD_PREDICATE_TYPE_REGISTRY.codec().fieldOf("predicate"),
                    (predicate, block) -> !predicate.apply(block)
            ));

    public static final DFunctionType<BlockInWorld, Boolean, ? extends Function<DFunction<Level, Boolean>, ? extends DFunction<BlockInWorld, Boolean>>> LEVEL_PREDICATE = Registry.register(
            DFunction.BLOCK_IN_WORLD_PREDICATE_TYPE_REGISTRY.registry(),
            id("level_predicate"),
            DFunction.<DFunction<Level, Boolean>, BlockInWorld, Boolean>create(
                    DFunction.LEVEL_PREDICATE_TYPE_REGISTRY.codec().fieldOf("predicate"),
                    (predicate, block) -> block.getLevel() instanceof Level level && predicate.apply(level)
            ));

    public static final DFunctionType<BlockInWorld, Boolean, ? extends Function<BlockPredicate, ? extends DFunction<BlockInWorld, Boolean>>> ADVANCEMENT_PREDICATE = Registry.register(
            DFunction.BLOCK_IN_WORLD_PREDICATE_TYPE_REGISTRY.registry(),
            id("advancement_predicate"),
            DFunction.<BlockPredicate, BlockInWorld, Boolean>create(
                    DataFunctions.ADVANCEMENT_BLOCK_PREDICATE_CODEC.fieldOf("predicate"),
                    (predicate, block) -> block.getLevel() instanceof ServerLevel sLevel && predicate.matches(sLevel, block.getPos())
            ));

    public static void init() {}
}
