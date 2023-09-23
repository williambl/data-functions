package com.williambl.dfunc.api.functions;

import com.williambl.dfunc.api.DTypes;
import com.williambl.dfunc.impl.platform.DataFunctionsServices;
import com.williambl.dfunc.mixin.GameRulesAccessor;
import com.williambl.vampilang.lang.VEnvironment;
import com.williambl.vampilang.lang.VValue;
import com.williambl.vampilang.lang.function.VFunctionDefinition;
import com.williambl.vampilang.lang.function.VFunctionSignature;
import com.williambl.vampilang.stdlib.StandardVTypes;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;

import java.util.Map;
import java.util.function.Predicate;

public class LevelDFunctions {
    public static final VFunctionDefinition BOOLEAN_GAME_RULE = new VFunctionDefinition("boolean_game_rule",
            new VFunctionSignature(Map.of("rule", StandardVTypes.STRING, "level", DTypes.LEVEL), StandardVTypes.BOOLEAN),
            (ctx, sig, args) -> new VValue(sig.outputType(), GameRulesAccessor.getGameRuleTypes().keySet().stream()
                    .filter(k -> k.getId().equals(args.get("rule").get(StandardVTypes.STRING)))
                    .findFirst()
                    .map(args.get("level").get(DTypes.LEVEL).getGameRules()::getRule)
                    .filter(GameRules.BooleanValue.class::isInstance)
                    .map(GameRules.BooleanValue.class::cast)
                    .map(GameRules.BooleanValue::get)
                    .orElse(false)));

    public static final VFunctionDefinition NUMBER_GAME_RULE = new VFunctionDefinition("number_game_rule",
            new VFunctionSignature(Map.of("rule", StandardVTypes.STRING, "level", DTypes.LEVEL), StandardVTypes.NUMBER),
            (ctx, sig, args) -> new VValue(sig.outputType(), GameRulesAccessor.getGameRuleTypes().keySet().stream()
                    .filter(k -> k.getId().equals(args.get("rule").get(StandardVTypes.STRING)))
                    .findFirst()
                    .map(args.get("level").get(DTypes.LEVEL).getGameRules()::getRule)
                    .map(v -> DataFunctionsServices.PLATFORM.mapNumberGameRule(v))
                    .orElse(0.0)));

    public static final VFunctionDefinition IS_DAY = createFromPredicate("is_day", Level::isDay);

    public static final VFunctionDefinition IS_RAINING = createFromPredicate("is_raining", Level::isRaining);

    private static VFunctionDefinition createFromPredicate(String name, Predicate<Level> predicate) {
        return new VFunctionDefinition(name,
                new VFunctionSignature(Map.of("level", DTypes.LEVEL), StandardVTypes.BOOLEAN),
                (ctx, sig, args) -> new VValue(sig.outputType(), predicate.test(args.get("level").get(DTypes.LEVEL))));
    }

    public static void register(VEnvironment env) {
        env.registerFunction(BOOLEAN_GAME_RULE);
        env.registerFunction(NUMBER_GAME_RULE);
        env.registerFunction(IS_DAY);
        env.registerFunction(IS_RAINING);
    }
}
