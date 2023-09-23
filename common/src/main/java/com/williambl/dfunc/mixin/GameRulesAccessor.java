package com.williambl.dfunc.mixin;

import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(GameRules.class)
public interface GameRulesAccessor {
    @Accessor("GAME_RULE_TYPES")
    static Map<GameRules.Key<?>, GameRules.Type<?>> getGameRuleTypes() {
        throw new UnsupportedOperationException();
    }
}
