package com.williambl.dfunc.functions;

import com.williambl.dfunc.ContextArg;
import com.williambl.dfunc.DFunction;
import com.williambl.dfunc.DFunctionType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import org.w3c.dom.Attr;

import java.util.Optional;
import java.util.function.*;

import static com.williambl.dfunc.DataFunctions.id;

public class EntityNumberDFunctions {

    public static final DFunctionType<Double, ? extends Function<ContextArg<Entity>, ? extends DFunction<Double>>> AGE = createSimple(id("age"), e -> e.tickCount);
    public static final DFunctionType<Double, ? extends Function<ContextArg<Entity>, ? extends DFunction<Double>>> HEALTH = createSimple(id("health"), e -> e instanceof LivingEntity l ? l.getHealth() : 0.0);
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

    public static DFunctionType<Double, ? extends Function<ContextArg<Entity>, ? extends DFunction<Double>>> createSimple(ResourceLocation name, ToDoubleFunction<Entity> operator) {
        return Registry.register(
                DFunction.NUMBER_FUNCTION.registry(),
                name,
                DFunction.<ContextArg<Entity>, Double>create(
                        ContextArg.ENTITY,
                        (e, ctx) -> operator.applyAsDouble(e.get(ctx))));
    }

    public static void init() {}
}
