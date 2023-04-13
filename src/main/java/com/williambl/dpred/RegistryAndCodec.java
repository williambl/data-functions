package com.williambl.dpred;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

record RegistryAndCodec<T>(Registry<Codec<? extends T>> registry, Codec<T> codec) {
    static <T extends DPredicate<?>> RegistryAndCodec<T> createRegistry(ResourceLocation name) {
        ResourceKey<Registry<Codec<? extends T>>> key = ResourceKey.createRegistryKey(name);
        MappedRegistry<Codec<? extends T>> registry = FabricRegistryBuilder.createSimple(key).buildAndRegister();
        @SuppressWarnings("unchecked")
        Codec<T> codec = registry.byNameCodec().dispatch(
                p -> (Codec<T>) p.codec().codec(),
                Function.identity()
        );

        return new RegistryAndCodec<>(registry, codec);
    }
}
