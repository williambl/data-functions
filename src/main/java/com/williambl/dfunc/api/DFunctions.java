package com.williambl.dfunc.api;

import com.mojang.serialization.Codec;
import com.williambl.dfunc.impl.DataFunctionsEnvironment;
import com.williambl.vampilang.lang.EvaluationContext;
import com.williambl.vampilang.lang.VEnvironment;
import com.williambl.vampilang.lang.VExpression;
import com.williambl.vampilang.lang.VValue;
import com.williambl.vampilang.lang.type.TypedVType;
import com.williambl.vampilang.lang.type.VType;
import com.williambl.vampilang.stdlib.StandardVTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class DFunctions {
    public static final VEnvironment ENV = new DataFunctionsEnvironment();
    public static final EvaluationContext.Spec EMPTY = new EvaluationContext.Spec();
    public static final EvaluationContext.Spec ENTITY = new EvaluationContext.Spec(Map.of("entity", DTypes.ENTITY, "level", DTypes.LEVEL));
    public static final EvaluationContext.Spec ENTITY_TARGET = new EvaluationContext.Spec(Map.of("entity", DTypes.ENTITY, "level", DTypes.LEVEL, "target", DTypes.ENTITY));
    public static final EvaluationContext.Spec ENTITY_DAMAGE = new EvaluationContext.Spec(Map.of("entity", DTypes.ENTITY, "level", DTypes.LEVEL, "damage_source", DTypes.DAMAGE_SOURCE, "damage_amount", StandardVTypes.NUMBER, "attacker", DTypes.OPTIONAL_ENTITY, "direct_attacker", DTypes.OPTIONAL_ENTITY));
    public static final EvaluationContext.Spec ENTITY_DAMAGE_WITH_WEAPON = new EvaluationContext.Spec(Map.of("entity", DTypes.ENTITY, "level", DTypes.LEVEL, "damage_source", DTypes.DAMAGE_SOURCE, "damage_amount", StandardVTypes.NUMBER, "attacker", DTypes.OPTIONAL_ENTITY, "direct_attacker", DTypes.OPTIONAL_ENTITY, "weapon", DTypes.ITEM_STACK));
    public static final EvaluationContext.Spec BLOCK_IN_WORLD = new EvaluationContext.Spec(Map.of("block", DTypes.BLOCK_IN_WORLD, "level", DTypes.LEVEL));
    public static final EvaluationContext.Spec ENTITY_INTERACT_WITH_BLOCK = new EvaluationContext.Spec(Map.of("entity", DTypes.ENTITY, "level", DTypes.LEVEL, "item", DTypes.ITEM_STACK, "block", DTypes.BLOCK_IN_WORLD));

    public static EvaluationContext createEntityContext(Entity entity) {
        return EvaluationContext.builder(ENTITY)
                .addVariable("entity", new VValue(DTypes.ENTITY, entity))
                .addVariable("level", new VValue(DTypes.LEVEL, entity.level()))
                .build(ENV);
    }

    public static EvaluationContext createEntityTargetContext(Entity entity, Entity target) {
        return EvaluationContext.builder(ENTITY_TARGET)
                .addVariable("entity", new VValue(DTypes.ENTITY, entity))
                .addVariable("level", new VValue(DTypes.LEVEL, entity.level()))
                .addVariable("target", new VValue(DTypes.ENTITY, target))
                .build(ENV);
    }

    public static EvaluationContext createEntityDamageContext(Entity entity, DamageSource damage, float amount) {
        return EvaluationContext.builder(ENTITY_DAMAGE)
                .addVariable("entity", new VValue(DTypes.ENTITY, entity))
                .addVariable("level", new VValue(DTypes.LEVEL, entity.level()))
                .addVariable("damage_source", new VValue(DTypes.DAMAGE_SOURCE, damage))
                .addVariable("damage_amount", new VValue(StandardVTypes.NUMBER, (double) amount))
                .addVariable("attacker", new VValue(DTypes.OPTIONAL_ENTITY, Optional.ofNullable(damage.getEntity())))
                .addVariable("direct_attacker", new VValue(DTypes.OPTIONAL_ENTITY, Optional.ofNullable(damage.getDirectEntity())))
                .build(ENV);
    }

    public static EvaluationContext entityDamageWithWeapon(Entity entity, DamageSource source, float amount, ItemStack weapon) {
        return EvaluationContext.builder(ENTITY_DAMAGE_WITH_WEAPON)
                .addVariable("entity", new VValue(DTypes.ENTITY, entity))
                .addVariable("level", new VValue(DTypes.LEVEL, entity.level()))
                .addVariable("damage_source", new VValue(DTypes.DAMAGE_SOURCE, source))
                .addVariable("damage_amount", new VValue(StandardVTypes.NUMBER, (double) amount))
                .addVariable("attacker", new VValue(DTypes.OPTIONAL_ENTITY, Optional.ofNullable(source.getEntity())))
                .addVariable("direct_attacker", new VValue(DTypes.OPTIONAL_ENTITY, Optional.ofNullable(source.getDirectEntity())))
                .addVariable("weapon", new VValue(DTypes.ITEM_STACK, weapon))
                .build(ENV);
    }

    public static EvaluationContext createBlockInWorldContext(BlockInWorld blockInWorld) {
        return EvaluationContext.builder(BLOCK_IN_WORLD)
                .addVariable("block", new VValue(DTypes.BLOCK_IN_WORLD, blockInWorld))
                .addVariable("level", new VValue(DTypes.LEVEL, blockInWorld.getLevel()))
                .build(ENV);
    }

    public static EvaluationContext createEntityInteractWithBlockContext(Entity entity, ItemStack item, BlockInWorld block) {
        return EvaluationContext.builder(ENTITY_INTERACT_WITH_BLOCK)
                .addVariable("entity", new VValue(DTypes.ENTITY, entity))
                .addVariable("level", new VValue(DTypes.LEVEL, entity.level()))
                .addVariable("item", new VValue(DTypes.ITEM_STACK, item))
                .addVariable("block", new VValue(DTypes.BLOCK_IN_WORLD, block))
                .build(ENV);
    }

    public static <T> T evaluate(VExpression expr, EvaluationContext ctx, TypedVType<T> expectedType) {
        return expr.evaluate(ctx).get(expectedType);
    }

    public static <T> T evaluate(VExpression expr, EvaluationContext ctx) {
        return expr.evaluate(ctx).getUnchecked();
    }

    public static Codec<VExpression> resolvedExpressionCodec(VType outputType, EvaluationContext.Spec spec) {
        // types are already resolved in the decoding direction, we just need to do the same in the encoding direction
        return ENV.expressionCodecForType(outputType, spec).flatComapMap(Function.identity(), v -> v.resolveTypes(ENV, spec));
    }
}
