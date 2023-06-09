package com.williambl.dfunc.api.functions;

import com.mojang.datafixers.util.Function4;
import com.mojang.serialization.Codec;
import com.williambl.dfunc.api.DFunction;
import com.williambl.dfunc.api.context.ContextArg;
import com.williambl.dfunc.api.type.DFunctionType;
import com.williambl.dfunc.api.type.DFunctionTypeRegistry;
import com.williambl.dfunc.impl.DataFunctionsMod;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.Fluid;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;

import static com.williambl.dfunc.impl.DataFunctionsMod.id;

public class EntityDFunctions {
    public static final DFunctionType<Boolean, ? extends BiFunction<EntityPredicate, ContextArg<Entity>, ? extends DFunction<Boolean>>> ADVANCEMENT_PREDICATE = Registry.register(
            DFunction.PREDICATE.registry(),
            id("entity_advancement_predicate"),
            DFunction.<EntityPredicate, ContextArg<Entity>, Boolean>create(
                    DataFunctionsMod.ADVANCEMENT_ENTITY_PREDICATE_CODEC.fieldOf("predicate"),
                    ContextArg.ENTITY,
                    (predicate, e, ctx) -> e.get(ctx).getLevel() instanceof ServerLevel sLevel && predicate.matches(sLevel, null, e.get(ctx))));

    public static final DFunctionType<Boolean, ? extends Function<ContextArg<Entity>, ? extends DFunction<Boolean>>> DEAD_OR_DYING = Registry.register(
            DFunction.PREDICATE.registry(),
            id("dead_or_dying"),
            DFunction.<ContextArg<Entity>, Boolean>create(
                    ContextArg.ENTITY,
                    (e, ctx) -> e.get(ctx) instanceof LivingEntity l && l.isDeadOrDying()));

    public static final DFunctionType<Boolean, ? extends Function<ContextArg<Entity>, ? extends DFunction<Boolean>>> ON_FIRE = Registry.register(
            DFunction.PREDICATE.registry(),
            id("on_fire"),
            DFunction.<ContextArg<Entity>, Boolean>create(
                    ContextArg.ENTITY,
                    (e, ctx) -> e.get(ctx).isOnFire()));

    public static final DFunctionType<Boolean, ? extends Function<ContextArg<Entity>, ? extends DFunction<Boolean>>> SNEAKING = Registry.register(
            DFunction.PREDICATE.registry(),
            id("sneaking"),
            DFunction.<ContextArg<Entity>, Boolean>create(
                    ContextArg.ENTITY,
                    (e, ctx) -> e.get(ctx).isShiftKeyDown()));

    public static final DFunctionType<Boolean, ? extends Function<ContextArg<Entity>, ? extends DFunction<Boolean>>> SPRINTING = Registry.register(
            DFunction.PREDICATE.registry(),
            id("sprinting"),
            DFunction.<ContextArg<Entity>, Boolean>create(
                    ContextArg.ENTITY,
                    (e, ctx) -> e.get(ctx).isSprinting()));

    public static final DFunctionType<Boolean, ? extends Function<ContextArg<Entity>, ? extends DFunction<Boolean>>> SWIMMING = Registry.register(
            DFunction.PREDICATE.registry(),
            id("swimming"),
            DFunction.<ContextArg<Entity>, Boolean>create(
                    ContextArg.ENTITY,
                    (e, ctx) -> e.get(ctx).isSwimming()));

    public static final DFunctionType<Boolean, ? extends Function<ContextArg<Entity>, ? extends DFunction<Boolean>>> FALL_FLYING = Registry.register(
            DFunction.PREDICATE.registry(),
            id("fall_flying"),
            DFunction.<ContextArg<Entity>, Boolean>create(
                    ContextArg.ENTITY,
                    (e, ctx) -> e.get(ctx) instanceof LivingEntity l && l.isFallFlying()));

    public static final DFunctionType<Boolean, ? extends BiFunction<TagKey<Fluid>, ContextArg<Entity>, ? extends DFunction<Boolean>>> SUBMERGED_IN = Registry.register(
            DFunction.PREDICATE.registry(),
            id("submerged_in"),
            DFunction.<TagKey<Fluid>, ContextArg<Entity>, Boolean>create(
                    TagKey.codec(Registries.FLUID).fieldOf("fluid"),
                    ContextArg.ENTITY,
                    (f, e, ctx) -> e.get(ctx).isEyeInFluid(f)));

    public static final DFunctionType<Boolean, ? extends Function<ContextArg<Entity>, ? extends DFunction<Boolean>>> CAN_SEE_SKY = Registry.register(
            DFunction.PREDICATE.registry(),
            id("can_see_sky"),
            DFunction.<ContextArg<Entity>, Boolean>create(
                    ContextArg.ENTITY,
                    (e, ctx) -> e.get(ctx).getLevel().canSeeSky(e.get(ctx).blockPosition())));

    public static final DFunctionType<Boolean, ? extends Function<ContextArg<Entity>, ? extends DFunction<Boolean>>> IS_SURVIVAL_LIKE = Registry.register(
            DFunction.PREDICATE.registry(),
            id("is_survival_like"),
            DFunction.<ContextArg<Entity>, Boolean>create(
                    ContextArg.ENTITY,
                    (e, ctx) -> !e.get(ctx).isSpectator() && !(e.get(ctx) instanceof Player p && p.isCreative())));

    public static final DFunctionType<Boolean, ? extends BiFunction<MobEffect, ContextArg<Entity>, ? extends DFunction<Boolean>>> HAS_EFFECT = Registry.register(
            DFunction.PREDICATE.registry(),
            id("has_effect"),
            DFunction.<MobEffect, ContextArg<Entity>, Boolean>create(
                    BuiltInRegistries.MOB_EFFECT.byNameCodec().fieldOf("effect"),
                    ContextArg.ENTITY,
                    (effect, e, ctx) -> e.get(ctx) instanceof LivingEntity l && l.hasEffect(effect)));
    public static final DFunctionType<Double, ? extends Function<ContextArg<Entity>, ? extends DFunction<Double>>> AGE = createSimpleNumber(id("age"), e -> e.tickCount);
    public static final DFunctionType<Double, ? extends Function<ContextArg<Entity>, ? extends DFunction<Double>>> HEALTH = createSimpleNumber(id("health"), e -> e instanceof LivingEntity l ? l.getHealth() : 0.0);
    public static final DFunctionType<Double, ? extends BiFunction<Attribute, ContextArg<Entity>, ? extends DFunction<Double>>> ATTRIBUTE = Registry.register(
            DFunction.NUMBER_FUNCTION.registry(),
            id("attribute"),
            DFunction.<Attribute, ContextArg<Entity>, Double>create(
                    BuiltInRegistries.ATTRIBUTE.byNameCodec().fieldOf("attribute"),
                    ContextArg.ENTITY,
                    (attr, e, ctx) -> e.get(ctx) instanceof LivingEntity l ? Optional.ofNullable(l.getAttribute(attr)).map(AttributeInstance::getValue).orElse(0.0) : 0.0));
    public static final DFunctionType<Double, ? extends BiFunction<Attribute, ContextArg<Entity>, ? extends DFunction<Double>>> ATTRIBUTE_BASE = Registry.register(
            DFunction.NUMBER_FUNCTION.registry(),
            id("attribute_base"),
            DFunction.<Attribute, ContextArg<Entity>, Double>create(
                    BuiltInRegistries.ATTRIBUTE.byNameCodec().fieldOf("attribute"),
                    ContextArg.ENTITY,
                    (attr, e, ctx) -> e.get(ctx) instanceof LivingEntity l ? Optional.ofNullable(l.getAttribute(attr)).map(AttributeInstance::getBaseValue).orElse(0.0) : 0.0));

    public static final DFunctionType<Boolean, ? extends Function4<DFunction<Boolean>, String, DFunction<Boolean>, ContextArg<Optional<Entity>>, ? extends DFunction<Boolean>>> PREDICATE_WITH_UNWRAPPED_OPTIONAL = createWithUnwrappedOptional(DFunction.PREDICATE);
    public static final DFunctionType<Double, ? extends Function4<DFunction<Double>, String, DFunction<Double>, ContextArg<Optional<Entity>>, ? extends DFunction<Double>>> NUMBER_FUNCTION_WITH_UNWRAPPED_OPTIONAL = createWithUnwrappedOptional(DFunction.NUMBER_FUNCTION);

    public static void init() {}

    public static DFunctionType<Double, ? extends Function<ContextArg<Entity>, ? extends DFunction<Double>>> createSimpleNumber(ResourceLocation name, ToDoubleFunction<Entity> operator) {
        return Registry.register(
                DFunction.NUMBER_FUNCTION.registry(),
                name,
                DFunction.<ContextArg<Entity>, Double>create(
                        ContextArg.ENTITY,
                        (e, ctx) -> operator.applyAsDouble(e.get(ctx))));
    }

    public static <R> DFunctionType<R, ? extends Function4<DFunction<R>, String, DFunction<R>, ContextArg<Optional<Entity>>, ? extends DFunction<R>>> createWithUnwrappedOptional(
            DFunctionTypeRegistry<R> registry
    ) {
        return Registry.register(
                registry.registry(),
                id("with_unwrapped_optional"),
                DFunction.<DFunction<R>, String, DFunction<R>, ContextArg<Optional<Entity>>, R>create(
                        registry.codec().fieldOf("function"),
                        Codec.STRING.optionalFieldOf("name", "entity"),
                        registry.codec().fieldOf("fallback"),
                        ContextArg.OPTIONAL_ENTITY,
                        (pred, name, fallback, oe, ctx) -> oe.get(ctx).map(e -> pred.apply(ctx.with(name, e))).orElseGet(() -> fallback.apply(ctx))));
    }
}
