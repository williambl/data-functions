package com.williambl.dfunc;

import com.google.common.reflect.TypeToken;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.stream.Stream;

public record ContextArg<T>(String defaultName, int index, TypeToken<T> type, Optional<Either<String, DFunction<T>>> renameOrFunction) {
    public T get(DFContext ctx) {
        return this.renameOrFunction().map(e -> e.map(
                        s -> ctx.getArgumentWithNameOrIndex(s, this.index(), this.type()),
                        d -> d.apply(ctx)))
                .orElseGet(() -> ctx.getArgumentWithNameOrIndex(this.defaultName(), this.index(), this.type()));
    }

    public static final class Type<T> extends MapCodec<ContextArg<T>> {
        private final String defaultName;
        private final int index;
        private final MapCodec<ContextArg<T>> codec;
        private final TypeToken<T> type;

        private Type(String defaultName, int index, @Nullable Codec<DFunction<T>> dfunctionCodec, TypeToken<T> type) {
            this.defaultName = defaultName;
            this.index = index;
            this.type = type;
            this.codec = createCodec(defaultName, index, dfunctionCodec, this.type);
        }

        public ContextArg<T> arg() {
            return new ContextArg<>(this.defaultName, this.index, this.type, Optional.empty());
        }

        public ContextArg<T> arg(String renamed) {
            return new ContextArg<>(this.defaultName, this.index, this.type, Optional.of(Either.left(renamed)));
        }

        public ContextArg<T> arg(DFunction<T> function) {
            return new ContextArg<>(this.defaultName, this.index, this.type, Optional.of(Either.right(function)));
        }

        @Override
        public <U> Stream<U> keys(DynamicOps<U> ops) {
            return this.codec.keys(ops);
        }

        @Override
        public <U> DataResult<ContextArg<T>> decode(DynamicOps<U> ops, MapLike<U> input) {
            return this.codec.decode(ops, input);
        }

        @Override
        public <U> RecordBuilder<U> encode(ContextArg<T> input, DynamicOps<U> ops, RecordBuilder<U> prefix) {
            return this.codec.encode(input, ops, prefix);
        }

        private static <T> MapCodec<ContextArg<T>> createCodec(String defaultName, int index, @Nullable Codec<DFunction<T>> dfunctionCodec, TypeToken<T> type) {
            if (dfunctionCodec != null) {
                return Codec.either(Codec.STRING, dfunctionCodec).optionalFieldOf(defaultName).xmap(
                        e -> new ContextArg<>(defaultName, index, type, e),
                        ContextArg::renameOrFunction
                );
            } else {
                return Codec.STRING.optionalFieldOf(defaultName).xmap(
                        s -> new ContextArg<>(defaultName, index, type, s.map(Either::left)),
                        c -> c.renameOrFunction().flatMap(e -> e.left())
                );
            }
        }
    }

    public static final Type<Entity> ENTITY = new Type<>("entity", 0, null, TypeToken.of(Entity.class));
    public static final Type<BlockInWorld> BLOCK = new Type<>("block", 0, null, TypeToken.of(BlockInWorld.class));
    public static final Type<ItemStack> ITEM = new Type<>("item", 0, null, TypeToken.of(ItemStack.class));
    public static final Type<DamageSource> DAMAGE_SOURCE = new Type<>("damage_source", 0, null, TypeToken.of(DamageSource.class));
    public static final Type<Level> LEVEL = new Type<>("level", 0, null, TypeToken.of(Level.class));
    public static final Type<Double> NUMBER_A = new Type<>("a", 0, DFunction.NUMBER_FUNCTION.codec(), TypeToken.of(Double.class));
    public static final Type<Double> NUMBER_B = new Type<>("b", 1, DFunction.NUMBER_FUNCTION.codec(), TypeToken.of(Double.class));
}
