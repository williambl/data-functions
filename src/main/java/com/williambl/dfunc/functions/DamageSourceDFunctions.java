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
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.util.function.BiFunction;

import static com.williambl.dfunc.DataFunctions.id;

public class DamageSourceDFunctions {
    public static final DFunctionType<Boolean, ? extends BiFunction<TagKey<DamageType>, ContextArg<DamageSource>, ? extends DFunction<Boolean>>> DAMAGE_TAG = Registry.register(
            DFunction.PREDICATE.registry(),
            id("damage_tag"),
            DFunction.<TagKey<DamageType>, ContextArg<DamageSource>, Boolean>create(
                    TagKey.codec(Registries.DAMAGE_TYPE).fieldOf("tag"),
                    ContextArg.DAMAGE_SOURCE,
                    (tag, dmg, ctx) -> dmg.get(ctx).is(tag)));

    public static void init() {}
}
