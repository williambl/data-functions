package com.williambl.dfunc.api.functions;

import com.williambl.dfunc.api.context.ContextArg;
import com.williambl.dfunc.api.DFunction;
import com.williambl.dfunc.api.type.DFunctionType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;

import java.util.function.BiFunction;

import static com.williambl.dfunc.impl.DataFunctionsMod.id;

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
