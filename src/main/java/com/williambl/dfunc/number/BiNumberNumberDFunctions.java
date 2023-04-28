package com.williambl.dfunc.number;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.williambl.dfunc.DFunction;
import com.williambl.dfunc.DFunctionType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

import java.util.function.DoubleBinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.williambl.dfunc.DataFunctions.id;

public class BiNumberNumberDFunctions {
    public static final DFunctionType<Pair<Double, Double>, Double, ? extends Function<Double, ? extends DFunction<Pair<Double, Double>, Double>>> CONSTANT = Registry.register(
            DFunction.BI_NUMBER_TO_NUMBER_FUNCTION_TYPE_REGISTRY.registry(),
            id("constant"),
            DFunction.<Pair<Double, Double>, Double>create(Codec.DOUBLE));

    public static final DFunctionType<Pair<Double, Double>, Double, ? extends Supplier<? extends DFunction<Pair<Double, Double>, Double>>> ADD = fromBinaryOperator(id("add"), Double::sum);
    public static final DFunctionType<Pair<Double, Double>, Double, ? extends Supplier<? extends DFunction<Pair<Double, Double>, Double>>> SUBTRACT = fromBinaryOperator(id("subtract"), (a, b) -> a - b);
    public static final DFunctionType<Pair<Double, Double>, Double, ? extends Supplier<? extends DFunction<Pair<Double, Double>, Double>>> MULTIPLY = fromBinaryOperator(id("multiply"), (a, b) -> a * b);
    public static final DFunctionType<Pair<Double, Double>, Double, ? extends Supplier<? extends DFunction<Pair<Double, Double>, Double>>> DIVIDE = fromBinaryOperator(id("divide"), (a, b) -> a / b);
    public static final DFunctionType<Pair<Double, Double>, Double, ? extends Supplier<? extends DFunction<Pair<Double, Double>, Double>>> MODULO = fromBinaryOperator(id("modulo"), (a, b) -> a % b);
    public static final DFunctionType<Pair<Double, Double>, Double, ? extends Supplier<? extends DFunction<Pair<Double, Double>, Double>>> POWER = fromBinaryOperator(id("power"), Math::pow);
    public static final DFunctionType<Pair<Double, Double>, Double, ? extends Supplier<? extends DFunction<Pair<Double, Double>, Double>>> MAX = fromBinaryOperator(id("max"), Math::max);
    public static final DFunctionType<Pair<Double, Double>, Double, ? extends Supplier<? extends DFunction<Pair<Double, Double>, Double>>> MIN = fromBinaryOperator(id("min"), Math::min);

    public static DFunctionType<Pair<Double, Double>, Double, ? extends Supplier<? extends DFunction<Pair<Double, Double>, Double>>> fromBinaryOperator(ResourceLocation name, DoubleBinaryOperator operator) {
        return Registry.register(
                DFunction.BI_NUMBER_TO_NUMBER_FUNCTION_TYPE_REGISTRY.registry(),
                name,
                DFunction.<Pair<Double, Double>, Double>create((pair) -> operator.applyAsDouble(pair.getFirst(), pair.getSecond())));
    }

    public static void init() {}
}
