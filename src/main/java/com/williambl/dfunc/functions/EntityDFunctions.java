package com.williambl.dfunc.functions;

import com.williambl.dfunc.ContextArg;
import com.williambl.dfunc.DFunction;
import com.williambl.dfunc.DFunctionType;
import com.williambl.dfunc.DataFunctions;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
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

import static com.williambl.dfunc.DataFunctions.id;

public class EntityDFunctions {
    public static final DFunctionType<Boolean, ? extends BiFunction<EntityPredicate, ContextArg<Entity>, ? extends DFunction<Boolean>>> ADVANCEMENT_PREDICATE = Registry.register(
            DFunction.PREDICATE.registry(),
            id("entity_advancement_predicate"),
            DFunction.<EntityPredicate, ContextArg<Entity>, Boolean>create(
                    DataFunctions.ADVANCEMENT_ENTITY_PREDICATE_CODEC.fieldOf("predicate"),
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

    public static void init() {}

    public static DFunctionType<Double, ? extends Function<ContextArg<Entity>, ? extends DFunction<Double>>> createSimpleNumber(ResourceLocation name, ToDoubleFunction<Entity> operator) {
        return Registry.register(
                DFunction.NUMBER_FUNCTION.registry(),
                name,
                DFunction.<ContextArg<Entity>, Double>create(
                        ContextArg.ENTITY,
                        (e, ctx) -> operator.applyAsDouble(e.get(ctx))));
    }
}
