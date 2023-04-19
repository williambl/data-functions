package com.williambl.dfunc;

import com.mojang.serialization.Codec;
import com.williambl.dfunc.mixin.GameRulesAccessor;
import net.fabricmc.fabric.api.gamerule.v1.rule.DoubleRule;
import net.minecraft.core.Registry;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;


import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.williambl.dfunc.DataFunctions.id;

public final class LevelDPredicates {
    public static final DFunctionType<Level, Boolean, ? extends Function<Boolean, ? extends DFunction<Level, Boolean>>> CONSTANT = Registry.register(
            DFunction.LEVEL_PREDICATE_TYPE_REGISTRY.registry(),
            id("constant"),
            DFunction.<Boolean, Level, Boolean>create(
                    Codec.BOOL.fieldOf("value"),
                    (value, level) -> value));

    public static final DFunctionType<Level, Boolean, ? extends Function<List<DFunction<Level, Boolean>>, ? extends DFunction<Level, Boolean>>> AND = Registry.register(
            DFunction.LEVEL_PREDICATE_TYPE_REGISTRY.registry(),
            id("and"),
            DFunction.<List<DFunction<Level, Boolean>>, Level, Boolean>create(
                    DFunction.LEVEL_PREDICATE_TYPE_REGISTRY.codec().listOf().fieldOf("predicates"),
                    (predicates, level) -> predicates.stream().allMatch(p -> p.apply(level))));

    public static final DFunctionType<Level, Boolean, ? extends Function<List<DFunction<Level, Boolean>>, ? extends DFunction<Level, Boolean>>> OR = Registry.register(
            DFunction.LEVEL_PREDICATE_TYPE_REGISTRY.registry(),
            id("or"),
            DFunction.<List<DFunction<Level, Boolean>>, Level, Boolean>create(
                    DFunction.LEVEL_PREDICATE_TYPE_REGISTRY.codec().listOf().fieldOf("predicates"),
                    (predicates, level) -> predicates.stream().anyMatch(p -> p.apply(level))));

    public static final DFunctionType<Level, Boolean, ? extends Function<DFunction<Level, Boolean>, ? extends DFunction<Level, Boolean>>> NOT = Registry.register(
            DFunction.LEVEL_PREDICATE_TYPE_REGISTRY.registry(),
            id("not"),
            DFunction.<DFunction<Level, Boolean>, Level, Boolean>create(
                    DFunction.LEVEL_PREDICATE_TYPE_REGISTRY.codec().fieldOf("predicate"),
                    (predicate, level) -> !predicate.apply(level)));

    public static final DFunctionType<Level, Boolean, ? extends Function<String, ? extends DFunction<Level, Boolean>>> BOOLEAN_GAME_RULE = Registry.register(
            DFunction.LEVEL_PREDICATE_TYPE_REGISTRY.registry(),
            id("boolean_game_rule"),
            DFunction.<String, Level, Boolean>create(
                    Codec.STRING.fieldOf("rule"),
                    (rule, level) -> GameRulesAccessor.getGameRuleTypes().keySet().stream()
                            .filter(k -> k.getId().equals(rule))
                            .findFirst()
                            .map(level.getGameRules()::getRule)
                            .filter(GameRules.BooleanValue.class::isInstance)
                            .map(GameRules.BooleanValue.class::cast)
                            .map(GameRules.BooleanValue::get)
                            .orElse(false)));

    public static final DFunctionType<Level, Boolean, ? extends BiFunction<String, DFunction<Double, Boolean>, ? extends DFunction<Level, Boolean>>> NUMBER_GAME_RULE = Registry.register(
            DFunction.LEVEL_PREDICATE_TYPE_REGISTRY.registry(),
            id("number_game_rule"),
            DFunction.<String, DFunction<Double, Boolean>, Level, Boolean>create(
                    Codec.STRING.fieldOf("rule"),
                    DFunction.NUMBER_PREDICATE_TYPE_REGISTRY.codec().fieldOf("predicate"),
                    (rule, predicate, level) -> GameRulesAccessor.getGameRuleTypes().keySet().stream()
                            .filter(k -> k.getId().equals(rule))
                            .findFirst()
                            .map(level.getGameRules()::getRule)
                            .map(v -> v instanceof GameRules.IntegerValue i ? (double) i.get() : v instanceof DoubleRule d ? d.get() : null)
                            .map(predicate::apply)
                            .orElse(false)));

    public static final DFunctionType<Level, Boolean, ? extends Supplier<? extends DFunction<Level, Boolean>>> IS_DAY = Registry.register(
            DFunction.LEVEL_PREDICATE_TYPE_REGISTRY.registry(),
            id("is_day"),
            DFunction.<Level, Boolean>create(Level::isDay));

    public static final DFunctionType<Level, Boolean, ? extends Supplier<? extends DFunction<Level, Boolean>>> IS_RAINING = Registry.register(
            DFunction.LEVEL_PREDICATE_TYPE_REGISTRY.registry(),
            id("is_raining"),
            DFunction.<Level, Boolean>create(Level::isRaining));

    static void init() {}
}
