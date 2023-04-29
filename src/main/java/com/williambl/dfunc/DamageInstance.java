package com.williambl.dfunc;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;

public record DamageInstance(DamageSource source, float amount, Entity affected) {
}
