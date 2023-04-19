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
    public static final DPredicateType<Level, ? extends Function<Boolean, ? extends DPredicate<Level>>> CONSTANT = Registry.register(
            DPredicate.LEVEL_PREDICATE_TYPE_REGISTRY.registry(),
            id("constant"),
            DPredicate.<Boolean, Level>create(
                    Codec.BOOL.fieldOf("value"),
                    (value, level) -> value
            )
    );

    public static final DPredicateType<Level, ? extends Function<List<DPredicate<Level>>, ? extends DPredicate<Level>>> AND = Registry.register(
            DPredicate.LEVEL_PREDICATE_TYPE_REGISTRY.registry(),
            id("and"),
            DPredicate.<List<DPredicate<Level>>, Level>create(
                    DPredicate.LEVEL_PREDICATE_TYPE_REGISTRY.codec().listOf().fieldOf("predicates"),
                    (predicates, level) -> predicates.stream().allMatch(p -> p.test(level))
            )
    );

    public static final DPredicateType<Level, ? extends Function<List<DPredicate<Level>>, ? extends DPredicate<Level>>> OR = Registry.register(
            DPredicate.LEVEL_PREDICATE_TYPE_REGISTRY.registry(),
            id("or"),
            DPredicate.<List<DPredicate<Level>>, Level>create(
                    DPredicate.LEVEL_PREDICATE_TYPE_REGISTRY.codec().listOf().fieldOf("predicates"),
                    (predicates, level) -> predicates.stream().anyMatch(p -> p.test(level))
            )
    );

    public static final DPredicateType<Level, ? extends Function<DPredicate<Level>, ? extends DPredicate<Level>>> NOT = Registry.register(
            DPredicate.LEVEL_PREDICATE_TYPE_REGISTRY.registry(),
            id("not"),
            DPredicate.<DPredicate<Level>, Level>create(
                    DPredicate.LEVEL_PREDICATE_TYPE_REGISTRY.codec().fieldOf("predicate"),
                    (predicate, level) -> !predicate.test(level)
            )
    );

    public static final DPredicateType<Level, ? extends Function<String, ? extends DPredicate<Level>>> BOOLEAN_GAME_RULE = Registry.register(
            DPredicate.LEVEL_PREDICATE_TYPE_REGISTRY.registry(),
            id("boolean_game_rule"),
            DPredicate.<String, Level>create(
                    Codec.STRING.fieldOf("rule"),
                    (rule, level) -> GameRulesAccessor.getGameRuleTypes().keySet().stream()
                            .filter(k -> k.getId().equals(rule))
                            .findFirst()
                            .map(level.getGameRules()::getRule)
                            .filter(GameRules.BooleanValue.class::isInstance)
                            .map(GameRules.BooleanValue.class::cast)
                            .map(GameRules.BooleanValue::get)
                            .orElse(false)));

    public static final DPredicateType<Level, ? extends BiFunction<String, DPredicate<Double>, ? extends DPredicate<Level>>> NUMBER_GAME_RULE = Registry.register(
            DPredicate.LEVEL_PREDICATE_TYPE_REGISTRY.registry(),
            id("number_game_rule"),
            DPredicate.<String, DPredicate<Double>, Level>create(
                    Codec.STRING.fieldOf("rule"),
                    DPredicate.NUMBER_PREDICATE_TYPE_REGISTRY.codec().fieldOf("predicate"),
                    (rule, predicate, level) -> GameRulesAccessor.getGameRuleTypes().keySet().stream()
                            .filter(k -> k.getId().equals(rule))
                            .findFirst()
                            .map(level.getGameRules()::getRule)
                            .map(v -> v instanceof GameRules.IntegerValue i ? (double) i.get() : v instanceof DoubleRule d ? d.get() : null)
                            .map(predicate::test)
                            .orElse(false)));

    public static final DPredicateType<Level, ? extends Supplier<? extends DPredicate<Level>>> IS_DAY = Registry.register(
            DPredicate.LEVEL_PREDICATE_TYPE_REGISTRY.registry(),
            id("is_day"),
            DPredicate.<Level>create(Level::isDay));

    public static final DPredicateType<Level, ? extends Supplier<? extends DPredicate<Level>>> IS_RAINING = Registry.register(
            DPredicate.LEVEL_PREDICATE_TYPE_REGISTRY.registry(),
            id("is_raining"),
            DPredicate.<Level>create(Level::isRaining));

    static void init() {}
}
