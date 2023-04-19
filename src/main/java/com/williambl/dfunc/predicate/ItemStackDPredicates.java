package com.williambl.dfunc.predicate;

import com.mojang.serialization.Codec;
import com.williambl.dfunc.DFunction;
import com.williambl.dfunc.DFunctionType;
import com.williambl.dfunc.DataFunctions;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.Registry;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.function.Function;

import static com.williambl.dfunc.DataFunctions.id;

public final class ItemStackDPredicates {
    public static final DFunctionType<ItemStack, Boolean, ? extends Function<Boolean, ? extends DFunction<ItemStack, Boolean>>> CONSTANT = Registry.register(
            DFunction.ITEMSTACK_PREDICATE_TYPE_REGISTRY.registry(),
            id("constant"),
            DFunction.<Boolean, ItemStack, Boolean>create(
                    Codec.BOOL.fieldOf("value"),
                    (value, stack) -> value
            )
    );

    public static final DFunctionType<ItemStack, Boolean, ? extends Function<List<DFunction<ItemStack, Boolean>>, ? extends DFunction<ItemStack, Boolean>>> AND = Registry.register(
            DFunction.ITEMSTACK_PREDICATE_TYPE_REGISTRY.registry(),
            id("and"),
            DFunction.<List<DFunction<ItemStack, Boolean>>, ItemStack, Boolean>create(
                    DFunction.ITEMSTACK_PREDICATE_TYPE_REGISTRY.codec().listOf().fieldOf("predicates"),
                    (predicates, stack) -> predicates.stream().allMatch(p -> p.apply(stack))
            )
    );

    public static final DFunctionType<ItemStack, Boolean, ? extends Function<List<DFunction<ItemStack, Boolean>>, ? extends DFunction<ItemStack, Boolean>>> OR = Registry.register(
            DFunction.ITEMSTACK_PREDICATE_TYPE_REGISTRY.registry(),
            id("or"),
            DFunction.<List<DFunction<ItemStack, Boolean>>, ItemStack, Boolean>create(
                    DFunction.ITEMSTACK_PREDICATE_TYPE_REGISTRY.codec().listOf().fieldOf("predicates"),
                    (predicates, stack) -> predicates.stream().anyMatch(p -> p.apply(stack))
            )
    );

    public static final DFunctionType<ItemStack, Boolean, ? extends Function<DFunction<ItemStack, Boolean>, ? extends DFunction<ItemStack, Boolean>>> NOT = Registry.register(
            DFunction.ITEMSTACK_PREDICATE_TYPE_REGISTRY.registry(),
            id("not"),
            DFunction.<DFunction<ItemStack, Boolean>, ItemStack, Boolean>create(
                    DFunction.ITEMSTACK_PREDICATE_TYPE_REGISTRY.codec().fieldOf("predicate"),
                    (predicate, stack) -> !predicate.apply(stack)
            )
    );

    public static final DFunctionType<ItemStack, Boolean, ? extends Function<ItemPredicate, ? extends DFunction<ItemStack, Boolean>>> ADVANCEMENT_PREDICATE = Registry.register(
            DFunction.ITEMSTACK_PREDICATE_TYPE_REGISTRY.registry(),
            id("advancement_predicate"),
            DFunction.<ItemPredicate, ItemStack, Boolean>create(
                    DataFunctions.ADVANCEMENT_ITEM_PREDICATE_CODEC.fieldOf("predicate"),
                    ItemPredicate::matches)
            );

    static void init() {}
}
