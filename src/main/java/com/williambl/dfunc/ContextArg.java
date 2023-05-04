package com.williambl.dfunc;

import com.google.common.reflect.TypeToken;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.Optional;

public record ContextArg<T>(Type<T> type, Optional<Either<String, DFunction<T>>> renameOrFunction) {
    public T get(DFContext ctx) {
        return this.renameOrFunction().map(e -> e.map(
                        s -> ctx.getArgumentWithNameOrIndex(s, this.type().index()),
                        d -> d.apply(ctx)))
                .orElseGet(() -> ctx.getArgumentWithNameOrIndex(this.type().defaultName(), this.type().index()));
    }

    public record Type<T>(String defaultName, int index, Codec<DFunction<T>> dfunctionCodec, TypeToken<T> type) {
        public MapCodec<ContextArg<T>> codec() {
            return Codec.either(Codec.STRING, this.dfunctionCodec()).optionalFieldOf(this.defaultName()).xmap(
                    e -> new ContextArg<>(this, e),
                    ContextArg::renameOrFunction
            );
        }
    }
}
