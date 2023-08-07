package com.williambl.dfunc.api;

import com.williambl.dfunc.impl.DataFunctionsEnvironment;
import com.williambl.vampilang.lang.EvaluationContext;
import com.williambl.vampilang.lang.VEnvironment;
import com.williambl.vampilang.lang.VExpression;
import com.williambl.vampilang.lang.VValue;
import com.williambl.vampilang.lang.type.TypedVType;
import com.williambl.vampilang.stdlib.StandardVTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;

import java.util.Map;

public class DFunctions {
    public static final VEnvironment ENV = new DataFunctionsEnvironment();
    public static final EvaluationContext.Spec EMPTY = new EvaluationContext.Spec();
    public static final EvaluationContext.Spec ENTITY = new EvaluationContext.Spec(Map.of("entity", DTypes.ENTITY, "level", DTypes.LEVEL));
    public static final EvaluationContext.Spec ENTITY_TARGET = new EvaluationContext.Spec(Map.of("entity", DTypes.ENTITY, "level", DTypes.LEVEL, "target", DTypes.ENTITY));
    public static final EvaluationContext.Spec ENTITY_DAMAGE = new EvaluationContext.Spec(Map.of("entity", DTypes.ENTITY, "level", DTypes.LEVEL, "damage_source", DTypes.DAMAGE_SOURCE, "damage_amount", StandardVTypes.NUMBER)); //TODO optionals !

    public static EvaluationContext createEntityContext(Entity entity) {
        return EvaluationContext.builder(ENTITY).addVariable("entity", new VValue(DTypes.ENTITY, entity)).addVariable("level", new VValue(DTypes.LEVEL, entity.level())).build();
    }

    public static EvaluationContext createEntityTargetContext(Entity entity, Entity target) {
        return EvaluationContext.builder(ENTITY_TARGET).addVariable("entity", new VValue(DTypes.ENTITY, entity)).addVariable("level", new VValue(DTypes.LEVEL, entity.level())).addVariable("target", new VValue(DTypes.ENTITY, target)).build();
    }

    public static EvaluationContext createEntityDamageContext(Entity entity, DamageSource damage, float amount) {
        return EvaluationContext.builder(ENTITY_TARGET).addVariable("entity", new VValue(DTypes.ENTITY, entity)).addVariable("level", new VValue(DTypes.LEVEL, entity.level())).addVariable("damage_source", new VValue(DTypes.DAMAGE_SOURCE, damage)).addVariable("damage_amount", new VValue(StandardVTypes.NUMBER, (double) amount)).build();
    }

    public static <T> T evaluate(VExpression expr, EvaluationContext ctx, TypedVType<T> expectedType) {
        return expr.evaluate(ctx).get(expectedType);
    }

    public static <T> T evaluate(VExpression expr, EvaluationContext ctx) {
        return expr.evaluate(ctx).getUnchecked();
    }
}
