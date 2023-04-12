package com.williambl.datapred;

import com.mojang.datafixers.util.Function3;
import com.mojang.datafixers.util.Function4;
import com.mojang.datafixers.util.Function5;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.KeyDispatchDataCodec;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public class DPredicateImplementations {
    static class DPredicate4<A, B, C, D, T> implements DPredicate<T> {
        private final KeyDispatchDataCodec<? extends DPredicate<T>> codec;
        private final Function5<A, B, C, D, T, Boolean> function;
        private final A a;
        private final B b;
        private final C c;
        private final D d;

        public DPredicate4(KeyDispatchDataCodec<? extends DPredicate<T>> codec, Function5<A, B, C, D, T, Boolean> function, A a, B b, C c, D d) {
            this.codec = codec;
            this.function = function;
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
        }

        @Override
        public KeyDispatchDataCodec<? extends DPredicate<T>> codec() {
            return this.codec;
        }

        @Override
        public boolean test(T t) {
            return this.function.apply(this.a, this.b, this.c, this.d, t);
        }

        public static <A, B, C, D, T> Codec<DPredicate4<A, B, C, D, T>> create(MapCodec<A> codecA, MapCodec<B> codecB, MapCodec<C> codecC, MapCodec<D> codecD, Function5<A, B, C, D, T, Boolean> function) {
            AtomicReference<KeyDispatchDataCodec<DPredicate4<A, B, C, D, T>>> codecRef = new AtomicReference<>();
            var codec = RecordCodecBuilder.<DPredicate4<A, B, C, D, T>>create(instance -> instance.group(
                    codecA.forGetter(p -> p.a),
                    codecB.forGetter(p -> p.b),
                    codecC.forGetter(p -> p.c),
                    codecD.forGetter(p -> p.d)
            ).apply(instance, (a, b, c, d) -> new DPredicate4<>(codecRef.get(), function, a, b, c, d)));
            codecRef.set(KeyDispatchDataCodec.of(codec));
            return codec;
        }
    }

    static class DPredicate3<A, B, C, T> implements DPredicate<T> {
        private final KeyDispatchDataCodec<? extends DPredicate<T>> codec;
        private final Function4<A, B, C, T, Boolean> function;
        private final A a;
        private final B b;
        private final C c;

        public DPredicate3(KeyDispatchDataCodec<? extends DPredicate<T>> codec, Function4<A, B, C, T, Boolean> function, A a, B b, C c) {
            this.codec = codec;
            this.function = function;
            this.a = a;
            this.b = b;
            this.c = c;
        }

        @Override
        public KeyDispatchDataCodec<? extends DPredicate<T>> codec() {
            return this.codec;
        }

        @Override
        public boolean test(T t) {
            return this.function.apply(this.a, this.b, this.c, t);
        }

        public static <A, B, C, T> Codec<DPredicate3<A, B, C, T>> create(MapCodec<A> codecA, MapCodec<B> codecB, MapCodec<C> codecC, Function4<A, B, C, T, Boolean> function) {
            AtomicReference<KeyDispatchDataCodec<DPredicate3<A, B, C, T>>> codecRef = new AtomicReference<>();
            var codec = RecordCodecBuilder.<DPredicate3<A, B, C, T>>create(instance -> instance.group(
                    codecA.forGetter(p -> p.a),
                    codecB.forGetter(p -> p.b),
                    codecC.forGetter(p -> p.c)
            ).apply(instance, (a, b, c) -> new DPredicate3<>(codecRef.get(), function, a, b, c)));
            codecRef.set(KeyDispatchDataCodec.of(codec));
            return codec;
        }
    }

    static class DPredicate2<A, B, T> implements DPredicate<T> {
        private final KeyDispatchDataCodec<? extends DPredicate<T>> codec;
        private final Function3<A, B, T, Boolean> function;
        private final A a;
        private final B b;

        public DPredicate2(KeyDispatchDataCodec<? extends DPredicate<T>> codec, Function3<A, B, T, Boolean> function, A a, B b) {
            this.codec = codec;
            this.function = function;
            this.a = a;
            this.b = b;
        }

        @Override
        public KeyDispatchDataCodec<? extends DPredicate<T>> codec() {
            return this.codec;
        }

        @Override
        public boolean test(T t) {
            return this.function.apply(this.a, this.b, t);
        }

        public static <A, B, T> Codec<DPredicate2<A, B, T>> create(MapCodec<A> codecA, MapCodec<B> codecB, Function3<A, B, T, Boolean> function) {
            AtomicReference<KeyDispatchDataCodec<DPredicate2<A, B, T>>> codecRef = new AtomicReference<>();
            var codec = RecordCodecBuilder.<DPredicate2<A, B, T>>create(instance -> instance.group(
                    codecA.forGetter(p -> p.a),
                    codecB.forGetter(p -> p.b)
            ).apply(instance, (a, b) -> new DPredicate2<>(codecRef.get(), function, a, b)));
            codecRef.set(KeyDispatchDataCodec.of(codec));
            return codec;
        }
    }

    static class DPredicate1<A, T> implements DPredicate<T> {
        private final KeyDispatchDataCodec<? extends DPredicate<T>> codec;
        private final BiFunction<A, T, Boolean> function;
        private final A a;

        public DPredicate1(KeyDispatchDataCodec<? extends DPredicate<T>> codec, BiFunction<A, T, Boolean> function, A a) {
            this.codec = codec;
            this.function = function;
            this.a = a;
        }

        @Override
        public KeyDispatchDataCodec<? extends DPredicate<T>> codec() {
            return this.codec;
        }

        @Override
        public boolean test(T t) {
            return this.function.apply(this.a, t);
        }

        public static <A, T> Codec<DPredicate1<A, T>> create(MapCodec<A> codecA, BiFunction<A, T, Boolean> function) {
            AtomicReference<KeyDispatchDataCodec<DPredicate1<A, T>>> codecRef = new AtomicReference<>();
            var codec = RecordCodecBuilder.<DPredicate1<A, T>>create(instance -> instance.group(
                    codecA.forGetter(p -> p.a)
            ).apply(instance, (a) -> new DPredicate1<>(codecRef.get(), function, a)));
            codecRef.set(KeyDispatchDataCodec.of(codec));
            return codec;
        }
    }

    static class DPredicate0<T> implements DPredicate<T> {
        private final Predicate<T> function;
        private final KeyDispatchDataCodec<? extends DPredicate<T>> codec;

        public DPredicate0(Predicate<T> function) {
            this.function = function;
            this.codec = KeyDispatchDataCodec.of(Codec.unit(this));
        }

        @Override
        public KeyDispatchDataCodec<? extends DPredicate<T>> codec() {
            return this.codec;
        }

        @Override
        public boolean test(T t) {
            return this.function.test(t);
        }

        public static <T> Codec<DPredicate0<T>> create(Predicate<T> function) {
            return Codec.unit(new DPredicate0<>(function));
        }
    }
}
