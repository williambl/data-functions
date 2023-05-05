package com.williambl.dfunc.functions;

import com.williambl.dfunc.ContextArg;
import com.williambl.dfunc.DFunction;
import com.williambl.dfunc.DFunctionType;
import com.williambl.dfunc.DataFunctions;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.material.Fluid;

import java.util.function.BiFunction;
import java.util.function.Function;

import static com.williambl.dfunc.DataFunctions.id;

public class BlockInWorldDPredicates {
    public static final DFunctionType<Boolean, ? extends BiFunction<BlockPredicate, ContextArg<BlockInWorld>, ? extends DFunction<Boolean>>> ADVANCEMENT_PREDICATE = Registry.register(
            DFunction.PREDICATE.registry(),
            id("block_advancement_predicate"),
            DFunction.<BlockPredicate, ContextArg<BlockInWorld>, Boolean>create(
                    DataFunctions.ADVANCEMENT_BLOCK_PREDICATE_CODEC.fieldOf("predicate"),
                    ContextArg.BLOCK,
                    (predicate, block, ctx) -> block.get(ctx).getLevel() instanceof ServerLevel sLevel && predicate.matches(sLevel, block.get(ctx).getPos())));

    public static void init() {}
}
