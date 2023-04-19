package com.williambl.dfunc;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

/**
 * Holds a registry and a codec for DPredicateTypes.
 * @param registry  the registry
 * @param codec     the codec
 * @param <T>       the type the DPredicates test
 */
public record DFunctionTypeRegistry<T, R>(Registry<DFunctionType<T, R, ?>> registry, Codec<DFunction<T, R>> codec) {
    static <T, R> DFunctionTypeRegistry<T, R> createRegistry(ResourceLocation name) {
        ResourceKey<Registry<DFunctionType<T, R, ?>>> key = ResourceKey.createRegistryKey(name);
        Registry<DFunctionType<T, R, ?>> registry = FabricRegistryBuilder.createSimple(key).buildAndRegister();
        Codec<DFunction<T, R>> codec = registry.byNameCodec().dispatch(
                DFunction::type,
                DFunctionType::codec
        );

        return new DFunctionTypeRegistry<>(registry, codec);
    }
}
