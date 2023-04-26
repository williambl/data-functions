package com.williambl.dfunc.predicate;

import com.mojang.serialization.Codec;
import com.williambl.dfunc.DFunction;
import com.williambl.dfunc.DFunctionType;
import com.williambl.dfunc.DPredicates;
import com.williambl.dfunc.DataFunctions;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static com.williambl.dfunc.DataFunctions.id;

public final class BlockInWorldDPredicates {
    public static final DFunctionType<BlockInWorld, Boolean, ? extends Function<Boolean, ? extends DFunction<BlockInWorld, Boolean>>> CONSTANT = DPredicates.constant(DFunction.BLOCK_IN_WORLD_PREDICATE_TYPE_REGISTRY);
    public static final DFunctionType<BlockInWorld, Boolean, ? extends Function<List<DFunction<BlockInWorld, Boolean>>, ? extends DFunction<BlockInWorld, Boolean>>> AND = DPredicates.and(DFunction.BLOCK_IN_WORLD_PREDICATE_TYPE_REGISTRY);
    public static final DFunctionType<BlockInWorld, Boolean, ? extends Function<List<DFunction<BlockInWorld, Boolean>>, ? extends DFunction<BlockInWorld, Boolean>>> OR = DPredicates.or(DFunction.BLOCK_IN_WORLD_PREDICATE_TYPE_REGISTRY);
    public static final DFunctionType<BlockInWorld, Boolean, ? extends Function<DFunction<BlockInWorld, Boolean>, ? extends DFunction<BlockInWorld, Boolean>>> NOT = DPredicates.not(DFunction.BLOCK_IN_WORLD_PREDICATE_TYPE_REGISTRY);

    public static final DFunctionType<BlockInWorld, Boolean, ? extends Function<DFunction<Level, Boolean>, ? extends DFunction<BlockInWorld, Boolean>>> LEVEL_PREDICATE = DPredicates.delegateOptional(
            DFunction.BLOCK_IN_WORLD_PREDICATE_TYPE_REGISTRY,
            DFunction.LEVEL_PREDICATE_TYPE_REGISTRY,
            b -> b.getLevel() instanceof Level level ? Optional.of(level) : Optional.empty());

    public static final DFunctionType<BlockInWorld, Boolean, ? extends Function<BlockPredicate, ? extends DFunction<BlockInWorld, Boolean>>> ADVANCEMENT_PREDICATE = Registry.register(
            DFunction.BLOCK_IN_WORLD_PREDICATE_TYPE_REGISTRY.registry(),
            id("advancement_predicate"),
            DFunction.<BlockPredicate, BlockInWorld, Boolean>create(
                    DataFunctions.ADVANCEMENT_BLOCK_PREDICATE_CODEC.fieldOf("predicate"),
                    (predicate, block) -> block.getLevel() instanceof ServerLevel sLevel && predicate.matches(sLevel, block.getPos())
            ));

    public static void init() {}
}
