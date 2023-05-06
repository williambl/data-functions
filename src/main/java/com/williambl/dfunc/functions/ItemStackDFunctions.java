package com.williambl.dfunc.functions;

import com.williambl.dfunc.ContextArg;
import com.williambl.dfunc.DFunction;
import com.williambl.dfunc.DFunctionType;
import com.williambl.dfunc.DataFunctions;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.Registry;
import net.minecraft.world.item.ItemStack;

import java.util.function.BiFunction;

import static com.williambl.dfunc.DataFunctions.id;

public class ItemStackDFunctions {
    public static final DFunctionType<Boolean, ? extends BiFunction<ItemPredicate, ContextArg<ItemStack>, ? extends DFunction<Boolean>>> ADVANCEMENT_PREDICATE = Registry.register(
            DFunction.PREDICATE.registry(),
            id("item_advancement_predicate"),
            DFunction.<ItemPredicate, ContextArg<ItemStack>, Boolean>create(
                    DataFunctions.ADVANCEMENT_ITEM_PREDICATE_CODEC.fieldOf("predicate"),
                    ContextArg.ITEM,
                    (predicate, stack, ctx) -> predicate.matches(stack.get(ctx))));

    public static void init() {}
}
