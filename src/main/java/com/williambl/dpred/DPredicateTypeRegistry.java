package com.williambl.dpred;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

record DPredicateTypeRegistry<T>(Registry<DPredicateType<T, ?>> registry, Codec<DPredicate<T>> codec) {
    static <T> DPredicateTypeRegistry<T> createRegistry(ResourceLocation name) {
        ResourceKey<Registry<DPredicateType<T, ?>>> key = ResourceKey.createRegistryKey(name);
        Registry<DPredicateType<T, ?>> registry = FabricRegistryBuilder.createSimple(key).buildAndRegister();
        Codec<DPredicate<T>> codec = registry.byNameCodec().dispatch(
                DPredicate::type,
                DPredicateType::codec
        );

        return new DPredicateTypeRegistry<>(registry, codec);
    }
}
