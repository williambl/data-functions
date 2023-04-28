package com.williambl.dfunc.number;

import com.mojang.datafixers.types.Func;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.williambl.dfunc.DFunction;
import com.williambl.dfunc.DFunctionType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.williambl.dfunc.DataFunctions.id;

public class NumberNumberFunctions {
    public static final DFunctionType<Double, Double, ? extends Function<Double, ? extends DFunction<Double, Double>>> CONSTANT = Registry.register(
            DFunction.NUMBER_TO_NUMBER_FUNCTION_TYPE_REGISTRY.registry(),
            id("constant"),
            DFunction.<Double, Double>create(Codec.DOUBLE));

    // binary operators - see BiNumberNumberDFunctions
    public static final DFunctionType<Double, Double, ? extends Function<Double, ? extends DFunction<Double, Double>>> ADD = fromBinaryOperator(id("add"), Double::sum);
    public static final DFunctionType<Double, Double, ? extends Function<Double, ? extends DFunction<Double, Double>>> SUBTRACT = fromBinaryOperator(id("subtract"), (a, b) -> a - b);
    public static final DFunctionType<Double, Double, ? extends Function<Double, ? extends DFunction<Double, Double>>> MULTIPLY = fromBinaryOperator(id("multiply"), (a, b) -> a * b);
    public static final DFunctionType<Double, Double, ? extends Function<Double, ? extends DFunction<Double, Double>>> DIVIDE = fromBinaryOperator(id("divide"), (a, b) -> a / b);
    public static final DFunctionType<Double, Double, ? extends Function<Double, ? extends DFunction<Double, Double>>> MODULO = fromBinaryOperator(id("modulo"), (a, b) -> a % b);
    public static final DFunctionType<Double, Double, ? extends Function<Double, ? extends DFunction<Double, Double>>> POWER = fromBinaryOperator(id("power"), Math::pow);
    public static final DFunctionType<Double, Double, ? extends Function<Double, ? extends DFunction<Double, Double>>> MAX = fromBinaryOperator(id("max"), Math::max);
    public static final DFunctionType<Double, Double, ? extends Function<Double, ? extends DFunction<Double, Double>>> MIN = fromBinaryOperator(id("min"), Math::min);

    // unary operators
    public static final DFunctionType<Double, Double, ? extends Supplier<? extends DFunction<Double, Double>>> ABSOLUTE = fromUnaryOperator(id("absolute"), Math::abs);
    public static final DFunctionType<Double, Double, ? extends Supplier<? extends DFunction<Double, Double>>> NEGATE = fromUnaryOperator(id("negate"), a -> -a);
    public static final DFunctionType<Double, Double, ? extends Supplier<? extends DFunction<Double, Double>>> SQUARE_ROOT = fromUnaryOperator(id("square_root"), Math::sqrt);


    public static DFunctionType<Double, Double, ? extends Function<Double, ? extends DFunction<Double, Double>>> fromBinaryOperator(ResourceLocation name, DoubleBinaryOperator operator) {
        return Registry.register(
                DFunction.NUMBER_TO_NUMBER_FUNCTION_TYPE_REGISTRY.registry(),
                name,
                DFunction.<Double, Double, Double>create(
                        Codec.DOUBLE.fieldOf("other"),
                        operator::applyAsDouble));
    }

    public static DFunctionType<Double, Double, ? extends Supplier<? extends DFunction<Double, Double>>> fromUnaryOperator(ResourceLocation name, DoubleUnaryOperator operator) {
        return Registry.register(
                DFunction.NUMBER_TO_NUMBER_FUNCTION_TYPE_REGISTRY.registry(),
                name,
                DFunction.<Double, Double>create(operator::applyAsDouble));
    }

    public static void init() {}
}
