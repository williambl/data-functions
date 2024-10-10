package com.williambl.dfunc.api.functions;

import com.williambl.dfunc.api.DTypes;
import com.williambl.vampilang.lang.VEnvironment;
import com.williambl.vampilang.lang.VValue;
import com.williambl.vampilang.lang.function.VFunctionDefinition;
import com.williambl.vampilang.lang.function.VFunctionSignature;
import com.williambl.vampilang.stdlib.StandardVTypes;
import net.minecraft.core.Holder;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.util.Map;

public class ItemStackDFunctions {
    public static final VFunctionDefinition ADVANCEMENT_PREDICATE = new VFunctionDefinition("item_advancement_predicate", new VFunctionSignature(Map.of(
            "predicate", DTypes.ITEM_ADVANCEMENT_PREDICATE,
            "item", DTypes.ITEM_STACK),
            StandardVTypes.BOOLEAN),
            (ctx, sig, arg) -> new VValue(sig.outputType(), arg.get("predicate").get(DTypes.ITEM_ADVANCEMENT_PREDICATE).test(arg.get("item").get(DTypes.ITEM_STACK))));

    public static final VFunctionDefinition ENCHANTMENT_LEVEL = new VFunctionDefinition("enchantment_level", new VFunctionSignature(Map.of(
            "enchantment", DTypes.ENCHANTMENT,
            "item", DTypes.ITEM_STACK),
            StandardVTypes.NUMBER),
            (ctx, sig, arg) -> new VValue(sig.outputType(), (double) EnchantmentHelper.getItemEnchantmentLevel(Holder.direct(arg.get("enchantment").get(DTypes.ENCHANTMENT)), arg.get("item").get(DTypes.ITEM_STACK))));

    public static final VFunctionDefinition TAG = new VFunctionDefinition("tag", new VFunctionSignature(Map.of(
            "tag", DTypes.TAG,
            "input", DTypes.TAGGABLE),
            StandardVTypes.BOOLEAN),
            (ctx, sig, args) -> new VValue(sig.outputType(), DTypes.tagFunctionForTaggable(args.get("input")).test(args.get("tag").getUnchecked())));

    public static final VFunctionDefinition ITEM_IS_EMPTY = new VFunctionDefinition("item_is_empty", new VFunctionSignature(Map.of(
            "item", DTypes.ITEM_STACK),
            StandardVTypes.BOOLEAN),
            (ctx, sig, args) -> new VValue(sig.outputType(), args.get("item").get(DTypes.ITEM_STACK).isEmpty()));

    public static void register(VEnvironment env) {
        env.registerFunction(ADVANCEMENT_PREDICATE);
        env.registerFunction(ENCHANTMENT_LEVEL);
        env.registerFunction(TAG);
        env.registerFunction(ITEM_IS_EMPTY);
    }
}
