package com.williambl.dfunc.api.functions;

import com.williambl.dfunc.api.DTypes;
import com.williambl.vampilang.lang.VEnvironment;
import com.williambl.vampilang.lang.VValue;
import com.williambl.vampilang.lang.function.VFunctionDefinition;
import com.williambl.vampilang.lang.function.VFunctionSignature;
import com.williambl.vampilang.stdlib.StandardVTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;

import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;

public class EntityDFunctions {
    public static final VFunctionDefinition ADVANCEMENT_PREDICATE = new VFunctionDefinition("entity_advancement_predicate", new VFunctionSignature(Map.of(
            "predicate", DTypes.ENTITY_ADVANCEMENT_PREDICATE,
            "entity", DTypes.ENTITY),
            StandardVTypes.BOOLEAN),
            (ctx, sig, arg) -> {
                var entity = arg.get("entity").get(DTypes.ENTITY);
                return new VValue(sig.outputType(), entity.level() instanceof ServerLevel level && arg.get("predicate").get(DTypes.ENTITY_ADVANCEMENT_PREDICATE).matches(level, entity.position(), entity));
            });

    public static final VFunctionDefinition DEAD_OR_DYING = createFromPredicate("dead_or_dying", e -> e instanceof LivingEntity l && l.isDeadOrDying());

    public static final VFunctionDefinition ON_FIRE = createFromPredicate("on_fire", Entity::isOnFire);

    public static final VFunctionDefinition SNEAKING = createFromPredicate("sneaking", Entity::isShiftKeyDown);

    public static final VFunctionDefinition SPRINTING = createFromPredicate("sprinting", Entity::isSprinting);

    public static final VFunctionDefinition SWIMMING = createFromPredicate("swimming", Entity::isSwimming);

    public static final VFunctionDefinition FALL_FLYING = createFromPredicate("fall_flying", e -> e instanceof LivingEntity l && l.isFallFlying());

    public static final VFunctionDefinition SUBMERGED_IN = new VFunctionDefinition("submerged_in", new VFunctionSignature(Map.of(
            "fluid", DTypes.TAG.with(0, DTypes.FLUID),
            "entity", DTypes.ENTITY),
            StandardVTypes.BOOLEAN),
            (ctx, sig, args) -> new VValue(sig.outputType(), args.get("entity").get(DTypes.ENTITY).isEyeInFluid(args.get("fluid").getUnchecked())));

    public static final VFunctionDefinition CAN_SEE_SKY = createFromPredicate("can_see_sky", e -> e.level().canSeeSky(e.blockPosition()));

    public static final VFunctionDefinition IS_SURVIVAL_LIKE = createFromPredicate("is_survival_like", e -> !e.isSpectator() && !(e instanceof Player p && p.isCreative()));

    public static final VFunctionDefinition HAS_EFFECT = new VFunctionDefinition("has_effect", new VFunctionSignature(Map.of(
            "effect", DTypes.MOB_EFFECT,
            "entity", DTypes.ENTITY),
            StandardVTypes.BOOLEAN),
            (ctx, sig, args) -> new VValue(sig.outputType(), args.get("entity").get(DTypes.ENTITY) instanceof LivingEntity l && l.hasEffect(args.get("effect").get(DTypes.MOB_EFFECT))));

    public static final VFunctionDefinition AGE = createSimpleNumber("age", e -> e.tickCount);
    public static final VFunctionDefinition HEALTH = createSimpleNumber("health", e -> e instanceof LivingEntity l ? l.getHealth() : 0.0);
    public static final VFunctionDefinition ATTRIBUTE = new VFunctionDefinition("attribute", new VFunctionSignature(Map.of(
            "attribute", DTypes.ATTRIBUTE,
            "entity", DTypes.ENTITY),
            StandardVTypes.NUMBER),
            (ctx, sig, args) -> new VValue(sig.outputType(), args.get("entity").get(DTypes.ENTITY) instanceof LivingEntity l ? Optional.ofNullable(l.getAttribute(args.get("attribute").get(DTypes.ATTRIBUTE))).map(AttributeInstance::getValue).orElse(0.0) : 0.0));
    public static final VFunctionDefinition ATTRIBUTE_BASE = new VFunctionDefinition("attribute_base", new VFunctionSignature(Map.of(
            "attribute", DTypes.ATTRIBUTE,
            "entity", DTypes.ENTITY),
            StandardVTypes.NUMBER),
            (ctx, sig, args) -> new VValue(sig.outputType(), args.get("entity").get(DTypes.ENTITY) instanceof LivingEntity l ? Optional.ofNullable(l.getAttribute(args.get("attribute").get(DTypes.ATTRIBUTE))).map(AttributeInstance::getBaseValue).orElse(0.0) : 0.0));

    public static void register(VEnvironment env) {
        env.registerFunction(ADVANCEMENT_PREDICATE);
        env.registerFunction(DEAD_OR_DYING);
        env.registerFunction(ON_FIRE);
        env.registerFunction(SNEAKING);
        env.registerFunction(SPRINTING);
        env.registerFunction(SWIMMING);
        env.registerFunction(FALL_FLYING);
        env.registerFunction(SUBMERGED_IN);
        env.registerFunction(CAN_SEE_SKY);
        env.registerFunction(IS_SURVIVAL_LIKE);
        env.registerFunction(HAS_EFFECT);
        env.registerFunction(AGE);
        env.registerFunction(HEALTH);
        env.registerFunction(ATTRIBUTE);
        env.registerFunction(ATTRIBUTE_BASE);
    }

    public static VFunctionDefinition createFromPredicate(String name, Predicate<Entity> predicate) {
        return new VFunctionDefinition(name,
                new VFunctionSignature(Map.of("entity", DTypes.ENTITY), StandardVTypes.BOOLEAN),
                (ctx, sig, args) -> new VValue(sig.outputType(), predicate.test(args.get("entity").get(DTypes.ENTITY))));
    }

    public static VFunctionDefinition createSimpleNumber(String name, ToDoubleFunction<Entity> operator) {
        return new VFunctionDefinition(name,
                new VFunctionSignature(Map.of("entity", DTypes.ENTITY), StandardVTypes.NUMBER),
                (ctx, sig, args) -> new VValue(sig.outputType(), operator.applyAsDouble(args.get("entity").get(DTypes.ENTITY))));
    }
}
