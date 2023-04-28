package com.williambl.dfunc.number;

import com.mojang.datafixers.util.Function3;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.williambl.dfunc.DFunction;
import com.williambl.dfunc.DFunctionType;
import com.williambl.dfunc.DataFunctions;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.williambl.dfunc.DataFunctions.id;

public final class EntityNumberDFunctions {
    public static final DFunctionType<Entity, Double, ? extends Function<Double, ? extends DFunction<Entity, Double>>> CONSTANT = Registry.register(
            DFunction.ENTITY_TO_NUMBER_FUNCTION_TYPE_REGISTRY.registry(),
            id("constant"),
            DFunction.<Entity, Double>create(Codec.DOUBLE));

    public static final DFunctionType<Entity, Double, ? extends BiFunction<DFunction<Entity, Double>, DFunction<Double, Double>, ? extends DFunction<Entity, Double>>> TRANSFORMED = DFunction.transformed(
            DFunction.ENTITY_TO_NUMBER_FUNCTION_TYPE_REGISTRY,
            DFunction.NUMBER_TO_NUMBER_FUNCTION_TYPE_REGISTRY);

    public static final DFunctionType<Entity, Double, ? extends Function3<DFunction<Entity, Double>, DFunction<Entity, Double>, DFunction<Pair<Double, Double>, Double>, ? extends DFunction<Entity, Double>>> TRANSFORMED_WITH_ARGUMENT = DFunction.transformedWithArgument(
            DFunction.ENTITY_TO_NUMBER_FUNCTION_TYPE_REGISTRY,
            DFunction.BI_NUMBER_TO_NUMBER_FUNCTION_TYPE_REGISTRY);

    public static final DFunctionType<Entity, Double, ? extends Supplier<? extends DFunction<Entity, Double>>> AGE = Registry.register(
            DFunction.ENTITY_TO_NUMBER_FUNCTION_TYPE_REGISTRY.registry(),
            id("age"),
            DFunction.<Entity, Double>create(e -> (double) e.tickCount));

    public static final DFunctionType<Entity, Double, ? extends Supplier<? extends DFunction<Entity, Double>>> HEALTH = Registry.register(
            DFunction.ENTITY_TO_NUMBER_FUNCTION_TYPE_REGISTRY.registry(),
            id("health"),
            DFunction.<Entity, Double>create(e -> e instanceof LivingEntity l ? (double) l.getHealth() : 0.0));

    public static final DFunctionType<Entity, Double, ? extends Function<Attribute, ? extends DFunction<Entity, Double>>> ATTRIBUTE = Registry.register(
            DFunction.ENTITY_TO_NUMBER_FUNCTION_TYPE_REGISTRY.registry(),
            id("attribute"),
            DFunction.<Attribute, Entity, Double>create(
                    BuiltInRegistries.ATTRIBUTE.byNameCodec().fieldOf("attribute"),
                    (attribute, e) -> e instanceof LivingEntity l ? l.getAttributeValue(attribute) : 0.0));

    public static final DFunctionType<Entity, Double, ? extends Function<Attribute, ? extends DFunction<Entity, Double>>> ATTRIBUTE_BASE = Registry.register(
            DFunction.ENTITY_TO_NUMBER_FUNCTION_TYPE_REGISTRY.registry(),
            DataFunctions.id("attribute_base"),
            DFunction.<Attribute, Entity, Double>create(
                    BuiltInRegistries.ATTRIBUTE.byNameCodec().fieldOf("attribute"),
                    (attribute, e) -> e instanceof LivingEntity l ? l.getAttributeBaseValue(attribute) : 0.0));

    public static void init() {}
}
