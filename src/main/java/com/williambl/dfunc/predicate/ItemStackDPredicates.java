package com.williambl.dfunc.predicate;

import com.mojang.serialization.Codec;
import com.williambl.dfunc.DFunction;
import com.williambl.dfunc.DFunctionType;
import com.williambl.dfunc.DPredicates;
import com.williambl.dfunc.DataFunctions;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.Registry;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.function.Function;

import static com.williambl.dfunc.DataFunctions.id;

public final class ItemStackDPredicates {
    public static final DFunctionType<ItemStack, Boolean, ? extends Function<Boolean, ? extends DFunction<ItemStack, Boolean>>> CONSTANT = DPredicates.constant(DFunction.ITEMSTACK_PREDICATE_TYPE_REGISTRY);
    public static final DFunctionType<ItemStack, Boolean, ? extends Function<List<DFunction<ItemStack, Boolean>>, ? extends DFunction<ItemStack, Boolean>>> AND = DPredicates.and(DFunction.ITEMSTACK_PREDICATE_TYPE_REGISTRY);
    public static final DFunctionType<ItemStack, Boolean, ? extends Function<List<DFunction<ItemStack, Boolean>>, ? extends DFunction<ItemStack, Boolean>>> OR = DPredicates.or(DFunction.ITEMSTACK_PREDICATE_TYPE_REGISTRY);
    public static final DFunctionType<ItemStack, Boolean, ? extends Function<DFunction<ItemStack, Boolean>, ? extends DFunction<ItemStack, Boolean>>> NOT = DPredicates.not(DFunction.ITEMSTACK_PREDICATE_TYPE_REGISTRY);

    public static final DFunctionType<ItemStack, Boolean, ? extends Function<ItemPredicate, ? extends DFunction<ItemStack, Boolean>>> ADVANCEMENT_PREDICATE = Registry.register(
            DFunction.ITEMSTACK_PREDICATE_TYPE_REGISTRY.registry(),
            id("advancement_predicate"),
            DFunction.<ItemPredicate, ItemStack, Boolean>create(
                    DataFunctions.ADVANCEMENT_ITEM_PREDICATE_CODEC.fieldOf("predicate"),
                    ItemPredicate::matches)
            );

    public static void init() {}
}
