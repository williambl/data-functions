package com.williambl.dfunc.functions;

import com.mojang.serialization.Codec;
import com.williambl.dfunc.*;
import com.williambl.dfunc.mixin.GameRulesAccessor;
import net.fabricmc.fabric.api.gamerule.v1.rule.DoubleRule;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.Registry;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;

import java.util.function.BiFunction;
import java.util.function.Function;

import static com.williambl.dfunc.DataFunctions.id;

public class LevelDPredicates {
    public static final DFunctionType<Boolean, ? extends BiFunction<String, ContextArg<Level>, ? extends DFunction<Boolean>>> BOOLEAN_GAME_RULE = Registry.register(
            DFunction.PREDICATE.registry(),
            id("boolean_game_rule"),
            DFunction.<String, ContextArg<Level>, Boolean>create(
                    Codec.STRING.fieldOf("rule"),
                    ContextArg.LEVEL,
                    (rule, level, ctx) -> GameRulesAccessor.getGameRuleTypes().keySet().stream()
                            .filter(k -> k.getId().equals(rule))
                            .findFirst()
                            .map(level.get(ctx).getGameRules()::getRule)
                            .filter(GameRules.BooleanValue.class::isInstance)
                            .map(GameRules.BooleanValue.class::cast)
                            .map(GameRules.BooleanValue::get)
                            .orElse(false)));

    public static final DFunctionType<Double, ? extends BiFunction<String, ContextArg<Level>, ? extends DFunction<Double>>> NUMBER_GAME_RULE = Registry.register(
            DFunction.NUMBER_FUNCTION.registry(),
            id("number_game_rule"),
            DFunction.<String, ContextArg<Level>, Double>create(
                    Codec.STRING.fieldOf("rule"),
                    ContextArg.LEVEL,
                    (rule, level, ctx) -> GameRulesAccessor.getGameRuleTypes().keySet().stream()
                            .filter(k -> k.getId().equals(rule))
                            .findFirst()
                            .map(level.get(ctx).getGameRules()::getRule)
                            .map(v -> v instanceof GameRules.IntegerValue i ? (double) i.get() : v instanceof DoubleRule d ? d.get() : null)
                            .orElse(0.0)));

    public static final DFunctionType<Boolean, ? extends Function<ContextArg<Level>, ? extends DFunction<Boolean>>> IS_DAY = Registry.register(
            DFunction.PREDICATE.registry(),
            id("is_day"),
            DFunction.<ContextArg<Level>, Boolean>create(
                    ContextArg.LEVEL,
                    (level, ctx) -> level.get(ctx).isDay()));

    public static final DFunctionType<Boolean, ? extends Function<ContextArg<Level>, ? extends DFunction<Boolean>>> IS_RAINING = Registry.register(
            DFunction.PREDICATE.registry(),
            id("is_raining"),
            DFunction.<ContextArg<Level>, Boolean>create(
                    ContextArg.LEVEL,
                    (level, ctx) -> level.get(ctx).isRaining()));

    public static void init() {}
}
