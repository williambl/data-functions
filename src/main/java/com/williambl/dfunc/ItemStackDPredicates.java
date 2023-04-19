package com.williambl.dfunc;

import com.mojang.serialization.Codec;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.Registry;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.function.Function;

import static com.williambl.dfunc.DataFunctions.id;

public final class ItemStackDPredicates {
    public static final DPredicateType<ItemStack, ? extends Function<Boolean, ? extends DPredicate<ItemStack>>> CONSTANT = Registry.register(
            DPredicate.ITEMSTACK_PREDICATE_TYPE_REGISTRY.registry(),
            id("constant"),
            DPredicate.<Boolean, ItemStack>create(
                    Codec.BOOL.fieldOf("value"),
                    (value, stack) -> value
            )
    );

    public static final DPredicateType<ItemStack, ? extends Function<List<DPredicate<ItemStack>>, ? extends DPredicate<ItemStack>>> AND = Registry.register(
            DPredicate.ITEMSTACK_PREDICATE_TYPE_REGISTRY.registry(),
            id("and"),
            DPredicate.<List<DPredicate<ItemStack>>, ItemStack>create(
                    DPredicate.ITEMSTACK_PREDICATE_TYPE_REGISTRY.codec().listOf().fieldOf("predicates"),
                    (predicates, stack) -> predicates.stream().allMatch(p -> p.test(stack))
            )
    );

    public static final DPredicateType<ItemStack, ? extends Function<List<DPredicate<ItemStack>>, ? extends DPredicate<ItemStack>>> OR = Registry.register(
            DPredicate.ITEMSTACK_PREDICATE_TYPE_REGISTRY.registry(),
            id("or"),
            DPredicate.<List<DPredicate<ItemStack>>, ItemStack>create(
                    DPredicate.ITEMSTACK_PREDICATE_TYPE_REGISTRY.codec().listOf().fieldOf("predicates"),
                    (predicates, stack) -> predicates.stream().anyMatch(p -> p.test(stack))
            )
    );

    public static final DPredicateType<ItemStack, ? extends Function<DPredicate<ItemStack>, ? extends DPredicate<ItemStack>>> NOT = Registry.register(
            DPredicate.ITEMSTACK_PREDICATE_TYPE_REGISTRY.registry(),
            id("not"),
            DPredicate.<DPredicate<ItemStack>, ItemStack>create(
                    DPredicate.ITEMSTACK_PREDICATE_TYPE_REGISTRY.codec().fieldOf("predicate"),
                    (predicate, stack) -> !predicate.test(stack)
            )
    );

    public static final DPredicateType<ItemStack, ? extends Function<ItemPredicate, ? extends DPredicate<ItemStack>>> ADVANCEMENT_PREDICATE = Registry.register(
            DPredicate.ITEMSTACK_PREDICATE_TYPE_REGISTRY.registry(),
            id("advancement_predicate"),
            DPredicate.<ItemPredicate, ItemStack>create(
                    DataFunctions.ADVANCEMENT_ITEM_PREDICATE_CODEC.fieldOf("predicate"),
                    ItemPredicate::matches)
            );

    static void init() {}
}
