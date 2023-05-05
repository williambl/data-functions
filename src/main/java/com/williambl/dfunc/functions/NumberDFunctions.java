package com.williambl.dfunc.functions;

import com.mojang.serialization.Codec;
import com.williambl.dfunc.ContextArg;
import com.williambl.dfunc.DFunction;
import com.williambl.dfunc.DFunctionType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.function.*;

import static com.williambl.dfunc.DataFunctions.id;

public class NumberDFunctions {
    public static final DFunctionType<Double, ? extends Function<Double, ? extends DFunction<Double>>> CONSTANT = Registry.register(
            DFunction.NUMBER_FUNCTION.registry(),
            id("constant"),
            DFunction.<Double>create(Codec.DOUBLE));

    //binary operators
    public static final DFunctionType<Double, ? extends BiFunction<ContextArg<Double>, ContextArg<Double>, ? extends DFunction<Double>>> ADD = fromBinaryOperator(id("add"), Double::sum);
    public static final DFunctionType<Double, ? extends BiFunction<ContextArg<Double>, ContextArg<Double>, ? extends DFunction<Double>>> SUBTRACT = fromBinaryOperator(id("subtract"), (a, b) -> a - b);
    public static final DFunctionType<Double, ? extends BiFunction<ContextArg<Double>, ContextArg<Double>, ? extends DFunction<Double>>> MULTIPLY = fromBinaryOperator(id("multiply"), (a, b) -> a * b);
    public static final DFunctionType<Double, ? extends BiFunction<ContextArg<Double>, ContextArg<Double>, ? extends DFunction<Double>>> DIVIDE = fromBinaryOperator(id("divide"), (a, b) -> a / b);
    public static final DFunctionType<Double, ? extends BiFunction<ContextArg<Double>, ContextArg<Double>, ? extends DFunction<Double>>> MODULO = fromBinaryOperator(id("modulo"), (a, b) -> a % b);
    public static final DFunctionType<Double, ? extends BiFunction<ContextArg<Double>, ContextArg<Double>, ? extends DFunction<Double>>> POWER = fromBinaryOperator(id("power"), Math::pow);
    public static final DFunctionType<Double, ? extends BiFunction<ContextArg<Double>, ContextArg<Double>, ? extends DFunction<Double>>> MAX = fromBinaryOperator(id("max"), Math::max);
    public static final DFunctionType<Double, ? extends BiFunction<ContextArg<Double>, ContextArg<Double>, ? extends DFunction<Double>>> MIN = fromBinaryOperator(id("min"), Math::min);

    // unary operators
    public static final DFunctionType<Double, ? extends Function<ContextArg<Double>, ? extends DFunction<Double>>> ABSOLUTE = fromUnaryOperator(id("absolute"), Math::abs);
    public static final DFunctionType<Double, ? extends Function<ContextArg<Double>, ? extends DFunction<Double>>> NEGATE = fromUnaryOperator(id("negate"), a -> -a);
    public static final DFunctionType<Double, ? extends Function<ContextArg<Double>, ? extends DFunction<Double>>> SQUARE_ROOT = fromUnaryOperator(id("square_root"), Math::sqrt);

    // trigonometry
    public static final DFunctionType<Double, ? extends Function<ContextArg<Double>, ? extends DFunction<Double>>> SINE = fromUnaryOperator(id("sine"), Math::sin);
    public static final DFunctionType<Double, ? extends Function<ContextArg<Double>, ? extends DFunction<Double>>> COSINE = fromUnaryOperator(id("cosine"), Math::cos);
    public static final DFunctionType<Double, ? extends Function<ContextArg<Double>, ? extends DFunction<Double>>> TANGENT = fromUnaryOperator(id("tangent"), Math::tan);

    // polynomial
    public static final DFunctionType<Double, ? extends BiFunction<List<Double>, ContextArg<Double>, ? extends DFunction<Double>>> POLYNOMIAL = Registry.register(
            DFunction.NUMBER_FUNCTION.registry(),
            id("polynomial"),
            DFunction.create(
                    Codec.DOUBLE.listOf().fieldOf("coefficients"),
                    ContextArg.NUMBER_A,
                    (coefficients, x, ctx) -> {
                        double result = 0;
                        for (int i = 0; i < coefficients.size(); i++) {
                            result += coefficients.get(i) * Math.pow(x.get(ctx), i);
                        }
                        return result;
                    }
            ));

    public static DFunctionType<Double, ? extends BiFunction<ContextArg<Double>, ContextArg<Double>, ? extends DFunction<Double>>> fromBinaryOperator(ResourceLocation name, DoubleBinaryOperator operator) {
        return Registry.register(
                DFunction.NUMBER_FUNCTION.registry(),
                name,
                DFunction.<ContextArg<Double>, ContextArg<Double>, Double>create(
                        ContextArg.NUMBER_A,
                        ContextArg.NUMBER_B,
                        (a, b, ctx) -> operator.applyAsDouble(a.get(ctx), b.get(ctx))));
    }

    public static DFunctionType<Double, ? extends Function<ContextArg<Double>, ? extends DFunction<Double>>> fromUnaryOperator(ResourceLocation name, DoubleUnaryOperator operator) {
        return Registry.register(
                DFunction.NUMBER_FUNCTION.registry(),
                name,
                DFunction.<ContextArg<Double>, Double>create(
                        ContextArg.NUMBER_A,
                        (a, ctx) -> operator.applyAsDouble(a.get(ctx))));
    }

    public static void init() {}
}
