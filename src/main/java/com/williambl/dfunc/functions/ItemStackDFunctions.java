package com.williambl.dfunc.functions;

import com.williambl.dfunc.ContextArg;
import com.williambl.dfunc.DFunction;
import com.williambl.dfunc.DFunctionType;
import com.williambl.dfunc.DataFunctions;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

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

    public static final DFunctionType<Double, ? extends BiFunction<Enchantment, ContextArg<ItemStack>, ? extends DFunction<Double>>> ENCHANTMENT_LEVEL = Registry.register(
            DFunction.NUMBER_FUNCTION.registry(),
            id("enchantment_level"),
            DFunction.<Enchantment, ContextArg<ItemStack>, Double>create(
                    BuiltInRegistries.ENCHANTMENT.byNameCodec().fieldOf("enchantment"),
                    ContextArg.ITEM,
                    (enchantment, stack, ctx) -> (double) EnchantmentHelper.getItemEnchantmentLevel(enchantment, stack.get(ctx))));

    public static final DFunctionType<Boolean, ? extends BiFunction<TagKey<Item>, ContextArg<ItemStack>, ? extends DFunction<Boolean>>> ITEM_TAG = Registry.register(
            DFunction.PREDICATE.registry(),
            id("item_tag"),
            DFunction.<TagKey<Item>, ContextArg<ItemStack>, Boolean>create(
                    TagKey.codec(Registries.ITEM).fieldOf("tag"),
                    ContextArg.ITEM,
                    (tag, stack, ctx) -> stack.get(ctx).is(tag)));

    public static void init() {}
}
