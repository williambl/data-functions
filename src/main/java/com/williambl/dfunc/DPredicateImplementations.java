package com.williambl.dfunc;

import com.mojang.datafixers.util.Function3;
import com.mojang.datafixers.util.Function4;
import com.mojang.datafixers.util.Function5;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class DPredicateImplementations {
    static final class DPredicate4<A, B, C, D, T> implements DPredicate<T> {
        private final Supplier<DPredicateType<T, ?>> typeSupplier;
        private final Function5<A, B, C, D, T, Boolean> function;
        private final A a;
        private final B b;
        private final C c;
        private final D d;

        public DPredicate4(Supplier<DPredicateType<T, ?>> typeSupplier, Function5<A, B, C, D, T, Boolean> function, A a, B b, C c, D d) {
            this.typeSupplier = typeSupplier;
            this.function = function;
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
        }

        @Override
        public DPredicateType<T, ?> type() {
            return this.typeSupplier.get();
        }

        @Override
        public boolean test(T t) {
            return this.function.apply(this.a, this.b, this.c, this.d, t);
        }

        public static <A, B, C, D, T> DPredicateType<T, Function4<A, B, C, D, DPredicate4<A, B, C, D, T>>> create(MapCodec<A> codecA, MapCodec<B> codecB, MapCodec<C> codecC, MapCodec<D> codecD, Function5<A, B, C, D, T, Boolean> function) {
            AtomicReference<DPredicateType<T, Function4<A, B, C, D, DPredicate4<A, B, C, D, T>>>> typeRef = new AtomicReference<>();
            Function4<A, B, C, D, DPredicate4<A, B, C, D, T>> factory = (a, b, c, d) -> new DPredicate4<>(typeRef::get, function, a, b, c, d);
            var codec = RecordCodecBuilder.<DPredicate4<A, B, C, D, T>>create(instance -> instance.group(
                    codecA.forGetter(p -> p.a),
                    codecB.forGetter(p -> p.b),
                    codecC.forGetter(p -> p.c),
                    codecD.forGetter(p -> p.d)
            ).apply(instance, factory));
            typeRef.set(new DPredicateType<>(codec, factory));
            return typeRef.get();
        }
    }

    static final class DPredicate3<A, B, C, T> implements DPredicate<T> {
        private final Supplier<DPredicateType<T, ?>> typeSupplier;
        private final Function4<A, B, C, T, Boolean> function;
        private final A a;
        private final B b;
        private final C c;

        public DPredicate3(Supplier<DPredicateType<T, ?>> typeSupplier, Function4<A, B, C, T, Boolean> function, A a, B b, C c) {
            this.typeSupplier = typeSupplier;
            this.function = function;
            this.a = a;
            this.b = b;
            this.c = c;
        }

        @Override
        public DPredicateType<T, ?> type() {
            return this.typeSupplier.get();
        }

        @Override
        public boolean test(T t) {
            return this.function.apply(this.a, this.b, this.c, t);
        }

        public static <A, B, C, T> DPredicateType<T, Function3<A, B, C, DPredicate3<A, B, C, T>>> create(MapCodec<A> codecA, MapCodec<B> codecB, MapCodec<C> codecC, Function4<A, B, C, T, Boolean> function) {
            AtomicReference<DPredicateType<T, Function3<A, B, C, DPredicate3<A, B, C, T>>>> typeRef = new AtomicReference<>();
            Function3<A, B, C, DPredicate3<A, B, C, T>> factory = (a, b, c) -> new DPredicate3<>(typeRef::get, function, a, b, c);
            var codec = RecordCodecBuilder.<DPredicate3<A, B, C, T>>create(instance -> instance.group(
                    codecA.forGetter(p -> p.a),
                    codecB.forGetter(p -> p.b),
                    codecC.forGetter(p -> p.c)
            ).apply(instance, factory));
            typeRef.set(new DPredicateType<>(codec, factory));
            return typeRef.get();
        }
    }

    static final class DPredicate2<A, B, T> implements DPredicate<T> {
        private final Supplier<DPredicateType<T, ?>> typeSupplier;
        private final Function3<A, B, T, Boolean> function;
        private final A a;
        private final B b;

        public DPredicate2(Supplier<DPredicateType<T, ?>> typeSupplier, Function3<A, B, T, Boolean> function, A a, B b) {
            this.typeSupplier = typeSupplier;
            this.function = function;
            this.a = a;
            this.b = b;
        }

        @Override
        public DPredicateType<T, ?> type() {
            return this.typeSupplier.get();
        }

        @Override
        public boolean test(T t) {
            return this.function.apply(this.a, this.b, t);
        }

        public static <A, B, T> DPredicateType<T, BiFunction<A, B, DPredicate2<A, B, T>>> create(MapCodec<A> codecA, MapCodec<B> codecB, Function3<A, B, T, Boolean> function) {
            AtomicReference<DPredicateType<T, BiFunction<A, B, DPredicate2<A, B, T>>>> typeRef = new AtomicReference<>();
            BiFunction<A, B, DPredicate2<A, B, T>> factory = (a, b) -> new DPredicate2<>(typeRef::get, function, a, b);
            var codec = RecordCodecBuilder.<DPredicate2<A, B, T>>create(instance -> instance.group(
                    codecA.forGetter(p -> p.a),
                    codecB.forGetter(p -> p.b)
            ).apply(instance, factory));
            typeRef.set(new DPredicateType<>(codec, factory));
            return typeRef.get();
        }
    }

    static final class DPredicate1<A, T> implements DPredicate<T> {
        private final Supplier<DPredicateType<T, ?>> typeSupplier;
        private final BiFunction<A, T, Boolean> function;
        private final A a;

        public DPredicate1(Supplier<DPredicateType<T, ?>> typeSupplier, BiFunction<A, T, Boolean> function, A a) {
            this.typeSupplier = typeSupplier;
            this.function = function;
            this.a = a;
        }

        @Override
        public DPredicateType<T, ?> type() {
            return this.typeSupplier.get();
        }

        @Override
        public boolean test(T t) {
            return this.function.apply(this.a, t);
        }

        public static <A, T> DPredicateType<T, Function<A, DPredicate1<A, T>>> create(MapCodec<A> codecA, BiFunction<A, T, Boolean> function) {
            AtomicReference<DPredicateType<T, Function<A, DPredicate1<A, T>>>> typeRef = new AtomicReference<>();
            Function<A, DPredicate1<A, T>> factory = a -> new DPredicate1<>(typeRef::get, function, a);
            var codec = RecordCodecBuilder.<DPredicate1<A, T>>create(instance -> instance.group(
                    codecA.forGetter(p -> p.a)
            ).apply(instance, factory));
            typeRef.set(new DPredicateType<>(codec, factory));
            return typeRef.get();
        }
    }

    static final class DPredicate0<T> implements DPredicate<T> {
        private final Predicate<T> function;
        private final Supplier<DPredicateType<T, ?>> typeSupplier;

        public DPredicate0(Supplier<DPredicateType<T, ?>> typeSupplier, Predicate<T> function) {
            this.function = function;
            this.typeSupplier = typeSupplier;
        }

        @Override
        public DPredicateType<T, ?> type() {
            return this.typeSupplier.get();
        }

        @Override
        public boolean test(T t) {
            return this.function.test(t);
        }

        public static <T> DPredicateType<T, Supplier<DPredicate0<T>>> create(Predicate<T> function) {
            AtomicReference<DPredicateType<T, Supplier<DPredicate0<T>>>> typeRef = new AtomicReference<>();
            var predicate = new DPredicate0<>(typeRef::get, function);
            typeRef.set(new DPredicateType<>(Codec.unit(predicate), () -> predicate));
            return typeRef.get();
        }
    }
}
