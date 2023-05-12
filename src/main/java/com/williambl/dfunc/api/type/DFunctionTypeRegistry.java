package com.williambl.dfunc.api.type;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.williambl.dfunc.DFunctionImplementations;
import com.williambl.dfunc.api.DFunction;
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
 */
public record DFunctionTypeRegistry<R>(Registry<DFunctionType<R, ?>> registry, Codec<DFunction<R>> codec) {
    static <T, R> DFunctionTypeRegistry<R> createRegistry(ResourceLocation name, @Nullable Codec<R> rCodec) {
        ResourceKey<Registry<DFunctionType<R, ?>>> key = ResourceKey.createRegistryKey(name);
        Registry<DFunctionType<R, ?>> registry = FabricRegistryBuilder.createSimple(key).buildAndRegister();
        Codec<DFunction<R>> dispatchCodec = registry.byNameCodec().dispatch(
                DFunction::type,
                DFunctionType::codec
        );

        Codec<DFunction<R>> codec = rCodec == null ? dispatchCodec : Codec.either(dispatchCodec, rCodec)
                .xmap(e -> e.map(Function.identity(), r -> DFunction.create(rCodec).factory().apply(r)),
                        df -> df instanceof DFunctionImplementations.ConstantDFunction<R> constantDFunction
                                ? Either.right(constantDFunction.apply(null))
                                : Either.left(df));

        return new DFunctionTypeRegistry<>(registry, codec);
    }

    static <T, R> DFunctionTypeRegistry<R> createRegistry(ResourceLocation name) {
        return createRegistry(name, null);
    }
}
