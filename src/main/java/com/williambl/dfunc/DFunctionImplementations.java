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

public final class DFunctionImplementations {
    static final class DFunction4<A, B, C, D, T, R> implements DFunction<T, R> {
        private final Supplier<DFunctionType<T, R, ?>> typeSupplier;
        private final Function5<A, B, C, D, T, R> function;
        private final A a;
        private final B b;
        private final C c;
        private final D d;

        public DFunction4(Supplier<DFunctionType<T, R, ?>> typeSupplier, Function5<A, B, C, D, T, R> function, A a, B b, C c, D d) {
            this.typeSupplier = typeSupplier;
            this.function = function;
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
        }

        @Override
        public DFunctionType<T, R, ?> type() {
            return this.typeSupplier.get();
        }

        @Override
        public R apply(T t) {
            return this.function.apply(this.a, this.b, this.c, this.d, t);
        }

        public static <A, B, C, D, T, R> DFunctionType<T, R, Function4<A, B, C, D, DFunction4<A, B, C, D, T, R>>> create(MapCodec<A> codecA, MapCodec<B> codecB, MapCodec<C> codecC, MapCodec<D> codecD, Function5<A, B, C, D, T, R> function) {
            AtomicReference<DFunctionType<T, R, Function4<A, B, C, D, DFunction4<A, B, C, D, T, R>>>> typeRef = new AtomicReference<>();
            Function4<A, B, C, D, DFunction4<A, B, C, D, T, R>> factory = (a, b, c, d) -> new DFunction4<>(typeRef::get, function, a, b, c, d);
            var codec = RecordCodecBuilder.<DFunction4<A, B, C, D, T, R>>create(instance -> instance.group(
                    codecA.forGetter(p -> p.a),
                    codecB.forGetter(p -> p.b),
                    codecC.forGetter(p -> p.c),
                    codecD.forGetter(p -> p.d)
            ).apply(instance, factory));
            typeRef.set(new DFunctionType<>(codec, factory));
            return typeRef.get();
        }
    }

    static final class DFunction3<A, B, C, T, R> implements DFunction<T, R> {
        private final Supplier<DFunctionType<T, R, ?>> typeSupplier;
        private final Function4<A, B, C, T, R> function;
        private final A a;
        private final B b;
        private final C c;

        public DFunction3(Supplier<DFunctionType<T, R, ?>> typeSupplier, Function4<A, B, C, T, R> function, A a, B b, C c) {
            this.typeSupplier = typeSupplier;
            this.function = function;
            this.a = a;
            this.b = b;
            this.c = c;
        }

        @Override
        public DFunctionType<T, R, ?> type() {
            return this.typeSupplier.get();
        }

        @Override
        public R apply(T t) {
            return this.function.apply(this.a, this.b, this.c, t);
        }

        public static <A, B, C, T, R> DFunctionType<T, R, Function3<A, B, C, DFunction3<A, B, C, T, R>>> create(MapCodec<A> codecA, MapCodec<B> codecB, MapCodec<C> codecC, Function4<A, B, C, T, R> function) {
            AtomicReference<DFunctionType<T, R, Function3<A, B, C, DFunction3<A, B, C, T, R>>>> typeRef = new AtomicReference<>();
            Function3<A, B, C, DFunction3<A, B, C, T, R>> factory = (a, b, c) -> new DFunction3<>(typeRef::get, function, a, b, c);
            var codec = RecordCodecBuilder.<DFunction3<A, B, C, T, R>>create(instance -> instance.group(
                    codecA.forGetter(p -> p.a),
                    codecB.forGetter(p -> p.b),
                    codecC.forGetter(p -> p.c)
            ).apply(instance, factory));
            typeRef.set(new DFunctionType<>(codec, factory));
            return typeRef.get();
        }
    }

    static final class DFunction2<A, B, T, R> implements DFunction<T, R> {
        private final Supplier<DFunctionType<T, R, ?>> typeSupplier;
        private final Function3<A, B, T, R> function;
        private final A a;
        private final B b;

        public DFunction2(Supplier<DFunctionType<T, R, ?>> typeSupplier, Function3<A, B, T, R> function, A a, B b) {
            this.typeSupplier = typeSupplier;
            this.function = function;
            this.a = a;
            this.b = b;
        }

        @Override
        public DFunctionType<T, R, ?> type() {
            return this.typeSupplier.get();
        }

        @Override
        public R apply(T t) {
            return this.function.apply(this.a, this.b, t);
        }

        public static <A, B, T, R> DFunctionType<T, R, BiFunction<A, B, DFunction2<A, B, T, R>>> create(MapCodec<A> codecA, MapCodec<B> codecB, Function3<A, B, T, R> function) {
            AtomicReference<DFunctionType<T, R, BiFunction<A, B, DFunction2<A, B, T, R>>>> typeRef = new AtomicReference<>();
            BiFunction<A, B, DFunction2<A, B, T, R>> factory = (a, b) -> new DFunction2<>(typeRef::get, function, a, b);
            var codec = RecordCodecBuilder.<DFunction2<A, B, T, R>>create(instance -> instance.group(
                    codecA.forGetter(p -> p.a),
                    codecB.forGetter(p -> p.b)
            ).apply(instance, factory));
            typeRef.set(new DFunctionType<>(codec, factory));
            return typeRef.get();
        }
    }

    static final class DFunction1<A, T, R> implements DFunction<T, R> {
        private final Supplier<DFunctionType<T, R, ?>> typeSupplier;
        private final BiFunction<A, T, R> function;
        private final A a;

        public DFunction1(Supplier<DFunctionType<T, R, ?>> typeSupplier, BiFunction<A, T, R> function, A a) {
            this.typeSupplier = typeSupplier;
            this.function = function;
            this.a = a;
        }

        @Override
        public DFunctionType<T, R, ?> type() {
            return this.typeSupplier.get();
        }

        @Override
        public R apply(T t) {
            return this.function.apply(this.a, t);
        }

        public static <A, T, R> DFunctionType<T, R, Function<A, DFunction1<A, T, R>>> create(MapCodec<A> codecA, BiFunction<A, T, R> function) {
            AtomicReference<DFunctionType<T, R, Function<A, DFunction1<A, T, R>>>> typeRef = new AtomicReference<>();
            Function<A, DFunction1<A, T, R>> factory = a -> new DFunction1<>(typeRef::get, function, a);
            var codec = RecordCodecBuilder.<DFunction1<A, T, R>>create(instance -> instance.group(
                    codecA.forGetter(p -> p.a)
            ).apply(instance, factory));
            typeRef.set(new DFunctionType<>(codec, factory));
            return typeRef.get();
        }
    }

    static final class DFunction0<T, R> implements DFunction<T, R> {
        private final Function<T, R> function;
        private final Supplier<DFunctionType<T, R, ?>> typeSupplier;

        public DFunction0(Supplier<DFunctionType<T, R, ?>> typeSupplier, Function<T, R> function) {
            this.function = function;
            this.typeSupplier = typeSupplier;
        }

        @Override
        public DFunctionType<T, R, ?> type() {
            return this.typeSupplier.get();
        }

        @Override
        public R apply(T t) {
            return this.function.apply(t);
        }

        public static <T, R> DFunctionType<T, R, Supplier<DFunction0<T, R>>> create(Function<T, R> function) {
            AtomicReference<DFunctionType<T, R, Supplier<DFunction0<T, R>>>> typeRef = new AtomicReference<>();
            var predicate = new DFunction0<>(typeRef::get, function);
            typeRef.set(new DFunctionType<>(Codec.unit(predicate), () -> predicate));
            return typeRef.get();
        }
    }
}
