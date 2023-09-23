package com.williambl.dfunc.api.functions;

import com.williambl.dfunc.api.DTypes;
import com.williambl.vampilang.lang.VEnvironment;
import com.williambl.vampilang.lang.VValue;
import com.williambl.vampilang.lang.function.VFunctionDefinition;
import com.williambl.vampilang.lang.function.VFunctionSignature;
import com.williambl.vampilang.stdlib.StandardVTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.WorldGenLevel;

import java.util.Map;

public class BlockInWorldDFunctions {
    public static final VFunctionDefinition ADVANCEMENT_PREDICATE = new VFunctionDefinition("block_advancement_predicate", new VFunctionSignature(Map.of(
            "predicate", DTypes.BLOCK_ADVANCEMENT_PREDICATE,
            "block", DTypes.BLOCK_IN_WORLD),
            StandardVTypes.BOOLEAN),
            (ctx, sig, arg) -> {
                var block = arg.get("block").get(DTypes.BLOCK_IN_WORLD);
                return new VValue(sig.outputType(), block.getLevel() instanceof ServerLevel level && arg.get("predicate").get(DTypes.BLOCK_ADVANCEMENT_PREDICATE).matches(level, block.getPos()));
            });

    // mojang why do you have THREE classes called BlockPredicate
    public static final VFunctionDefinition WORLDGEN_PREDICATE = new VFunctionDefinition("block_worldgen_predicate", new VFunctionSignature(Map.of(
            "predicate", DTypes.BLOCK_WORLDGEN_PREDICATE,
            "block", DTypes.BLOCK_IN_WORLD),
            StandardVTypes.BOOLEAN),
            (ctx, sig, arg) -> {
                var block = arg.get("block").get(DTypes.BLOCK_IN_WORLD);
                return new VValue(sig.outputType(), block.getLevel() instanceof WorldGenLevel level && arg.get("predicate").get(DTypes.BLOCK_WORLDGEN_PREDICATE).test(level, block.getPos()));
            });

    public static void register(VEnvironment env) {
        env.registerFunction(ADVANCEMENT_PREDICATE);
        env.registerFunction(WORLDGEN_PREDICATE);
    }
}
