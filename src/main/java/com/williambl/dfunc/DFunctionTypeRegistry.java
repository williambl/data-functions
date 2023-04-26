package com.williambl.dfunc;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

/**
 * Holds a registry and a codec for DPredicateTypes.
 * @param registry  the registry
 * @param codec     the codec
 * @param <T>       the type the DPredicates test
 */
public record DFunctionTypeRegistry<T, R>(Registry<DFunctionType<T, R, ?>> registry, Codec<DFunction<T, R>> codec) {
    static <T, R> DFunctionTypeRegistry<T, R> createRegistry(ResourceLocation name, @Nullable Codec<R> rCodec) {
        ResourceKey<Registry<DFunctionType<T, R, ?>>> key = ResourceKey.createRegistryKey(name);
        Registry<DFunctionType<T, R, ?>> registry = FabricRegistryBuilder.createSimple(key).buildAndRegister();
        Codec<DFunction<T, R>> dispatchCodec = registry.byNameCodec().dispatch(
                DFunction::type,
                DFunctionType::codec
        );

        Codec<DFunction<T, R>> codec = rCodec == null ? dispatchCodec : Codec.either(dispatchCodec, rCodec)
                .xmap(e -> e.map(Function.identity(), r -> DFunction.<T, R>create($ -> r).factory().get()),
                        df -> df instanceof DFunctionImplementations.ConstantDFunction<T, R> constantDFunction
                                ? Either.right(constantDFunction.apply(null))
                                : Either.left(df));

        return new DFunctionTypeRegistry<>(registry, codec);
    }

    static <T, R> DFunctionTypeRegistry<T, R> createRegistry(ResourceLocation name) {
        return createRegistry(name, null);
    }
}
