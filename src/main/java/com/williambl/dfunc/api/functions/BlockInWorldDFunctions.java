package com.williambl.dfunc.api.functions;

import com.williambl.dfunc.api.context.ContextArg;
import com.williambl.dfunc.api.DFunction;
import com.williambl.dfunc.api.type.DFunctionType;
import com.williambl.dfunc.impl.DataFunctionsMod;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;

import java.util.function.BiFunction;

import static com.williambl.dfunc.impl.DataFunctionsMod.id;

public class BlockInWorldDFunctions {
    public static final DFunctionType<Boolean, ? extends BiFunction<BlockPredicate, ContextArg<BlockInWorld>, ? extends DFunction<Boolean>>> ADVANCEMENT_PREDICATE = Registry.register(
            DFunction.PREDICATE.registry(),
            id("block_advancement_predicate"),
            DFunction.<BlockPredicate, ContextArg<BlockInWorld>, Boolean>create(
                    DataFunctionsMod.ADVANCEMENT_BLOCK_PREDICATE_CODEC.fieldOf("predicate"),
                    ContextArg.BLOCK,
                    (predicate, block, ctx) -> block.get(ctx).getLevel() instanceof ServerLevel sLevel && predicate.matches(sLevel, block.get(ctx).getPos())));

    // mojang why do you have THREE classes called BlockPredicate
    public static final DFunctionType<Boolean, ? extends BiFunction<net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate, ContextArg<BlockInWorld>, ? extends DFunction<Boolean>>> BLOCK_PREDICATE = Registry.register(
            DFunction.PREDICATE.registry(),
            id("block_predicate"),
            DFunction.<net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate, ContextArg<BlockInWorld>, Boolean>create(
                    net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate.CODEC.fieldOf("predicate"),
                    ContextArg.BLOCK,
                    (predicate, block, ctx) -> block.get(ctx).getLevel() instanceof WorldGenLevel level && predicate.test(level, block.get(ctx).getPos())));

    public static void init() {}
}
