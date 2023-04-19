package com.williambl.dfunc;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;

/**
 * Modified from <a href="https://github.com/apace100/apoli/blob/1.19/src/main/java/io/github/apace100/apoli/util/Comparison.java">Apoli (MIT License)</a>.
 */
public enum Comparison implements StringRepresentable {

    NONE("", (a, b) -> false),
    EQUAL("==", Double::equals),
    LESS_THAN("<", (a, b) -> a < b),
    GREATER_THAN(">", (a, b) -> a > b),
    LESS_THAN_OR_EQUAL("<=", (a, b) -> a <= b),
    GREATER_THAN_OR_EQUAL(">=", (a, b) -> a >= b),
    NOT_EQUAL("!=", (a, b) -> !a.equals(b));

    public static final Codec<Comparison> CODEC = StringRepresentable.fromEnum(Comparison::values);

    private final String name;
    private final BiFunction<Double, Double, Boolean> comparison;

    Comparison(String name, BiFunction<Double, Double, Boolean> comparison) {
        this.name = name;
        this.comparison = comparison;
    }

    public boolean compare(double a, double b) {
        return this.comparison.apply(a, b);
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name;
    }

    public static Comparison fromString(String comparisonString) {
        return switch (comparisonString) {
            case "==" -> EQUAL;
            case "<" -> LESS_THAN;
            case ">" -> GREATER_THAN;
            case "<=" -> LESS_THAN_OR_EQUAL;
            case ">=" -> GREATER_THAN_OR_EQUAL;
            case "!=" -> NOT_EQUAL;
            default -> NONE;
        };
    }
}