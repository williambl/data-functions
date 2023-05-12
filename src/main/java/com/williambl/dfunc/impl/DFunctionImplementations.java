package com.williambl.dfunc.impl;

import com.mojang.datafixers.util.*;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.williambl.dfunc.api.DFunction;
import com.williambl.dfunc.api.context.ContextArg;
import com.williambl.dfunc.api.context.DFContext;
import com.williambl.dfunc.api.context.DFContextSpec;
import com.williambl.dfunc.api.type.DFunctionType;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * <h2>ABANDON ALL HOPE YE WHO ENTER HERE</h2>
 * <p>Sorry if the size of this file slows your IDE...</p>
 * <p>Implementations of DFunction for argument counts from 0 to 15 (plus for constant values).</p>
 * <p>Instead of using methods from this file directly, use the various DFunction#create methods.</p>
 */
public final class DFunctionImplementations {
    public static final class DFunction15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, R> implements DFunction<R> {
        private final Supplier<DFunctionType<R, ?>> typeSupplier;
        private final Function16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, DFContext, R> function;
        private final T1 t1;
        private final T2 t2;
        private final T3 t3;
        private final T4 t4;
        private final T5 t5;
        private final T6 t6;
        private final T7 t7;
        private final T8 t8;
        private final T9 t9;
        private final T10 t10;
        private final T11 t11;
        private final T12 t12;
        private final T13 t13;
        private final T14 t14;
        private final T15 t15;
        private final DFContextSpec spec;

        public DFunction15(Supplier<DFunctionType<R, ?>> typeSupplier, Function16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, DFContext, R> function, T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15) {
            this.typeSupplier = typeSupplier;
            this.function = function;
            this.t1 = t1;
            this.t2 = t2;
            this.t3 = t3;
            this.t4 = t4;
            this.t5 = t5;
            this.t6 = t6;
            this.t7 = t7;
            this.t8 = t8;
            this.t9 = t9;
            this.t10 = t10;
            this.t11 = t11;
            this.t12 = t12;
            this.t13 = t13;
            this.t14 = t14;
            this.t15 = t15;
            this.spec = createContextSpec(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
        }

        @Override
        public DFunctionType<R, ?> type() {
            return this.typeSupplier.get();
        }

        @Override
        public DFContextSpec getSpec() {
            return this.spec;
        }

        @Override
        public R apply(DFContext t) {
            return this.function.apply(this.t1, this.t2, this.t3, this.t4, this.t5, this.t6, this.t7, this.t8, this.t9, this.t10, this.t11, this.t12, this.t13, this.t14, this.t15, t);
        }

        public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, R> DFunctionType<R, ? extends Function15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, ? extends DFunction<R>>> create(
                MapCodec<T1> arg1,
                MapCodec<T2> arg2,
                MapCodec<T3> arg3,
                MapCodec<T4> arg4,
                MapCodec<T5> arg5,
                MapCodec<T6> arg6,
                MapCodec<T7> arg7,
                MapCodec<T8> arg8,
                MapCodec<T9> arg9,
                MapCodec<T10> arg10,
                MapCodec<T11> arg11,
                MapCodec<T12> arg12,
                MapCodec<T13> arg13,
                MapCodec<T14> arg14,
                MapCodec<T15> arg15,
                Function16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, DFContext, R> function
        ) {
            AtomicReference<DFunctionType<R, Function15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, DFunction15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, R>>>> typeRef = new AtomicReference<>();
            Function15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, DFunction15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, R>> factory = (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15) -> new DFunction15<>(
                    typeRef::get,
                    function,
                    t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
            var codec = RecordCodecBuilder.<DFunction15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, R>>create(instance -> instance.group(
                    arg1.forGetter(df -> df.t1),
                    arg2.forGetter(df -> df.t2),
                    arg3.forGetter(df -> df.t3),
                    arg4.forGetter(df -> df.t4),
                    arg5.forGetter(df -> df.t5),
                    arg6.forGetter(df -> df.t6),
                    arg7.forGetter(df -> df.t7),
                    arg8.forGetter(df -> df.t8),
                    arg9.forGetter(df -> df.t9),
                    arg10.forGetter(df -> df.t10),
                    arg11.forGetter(df -> df.t11),
                    arg12.forGetter(df -> df.t12),
                    arg13.forGetter(df -> df.t13),
                    arg14.forGetter(df -> df.t14),
                    arg15.forGetter(df -> df.t15)
            ).apply(instance, factory));
            typeRef.set(new DFunctionType<>(codec, factory));
            return typeRef.get();
        }
    }

    public static final class DFunction14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, R> implements DFunction<R> {
        private final Supplier<DFunctionType<R, ?>> typeSupplier;
        private final Function15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, DFContext, R> function;
        private final T1 t1;
        private final T2 t2;
        private final T3 t3;
        private final T4 t4;
        private final T5 t5;
        private final T6 t6;
        private final T7 t7;
        private final T8 t8;
        private final T9 t9;
        private final T10 t10;
        private final T11 t11;
        private final T12 t12;
        private final T13 t13;
        private final T14 t14;
        private final DFContextSpec spec;

        public DFunction14(Supplier<DFunctionType<R, ?>> typeSupplier, Function15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, DFContext, R> function, T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14) {
            this.typeSupplier = typeSupplier;
            this.function = function;
            this.t1 = t1;
            this.t2 = t2;
            this.t3 = t3;
            this.t4 = t4;
            this.t5 = t5;
            this.t6 = t6;
            this.t7 = t7;
            this.t8 = t8;
            this.t9 = t9;
            this.t10 = t10;
            this.t11 = t11;
            this.t12 = t12;
            this.t13 = t13;
            this.t14 = t14;
            this.spec = createContextSpec(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14);
        }

        @Override
        public DFunctionType<R, ?> type() {
            return this.typeSupplier.get();
        }

        @Override
        public DFContextSpec getSpec() {
            return this.spec;
        }

        @Override
        public R apply(DFContext ctx) {
            return this.function.apply(this.t1, this.t2, this.t3, this.t4, this.t5, this.t6, this.t7, this.t8, this.t9, this.t10, this.t11, this.t12, this.t13, this.t14, ctx);
        }

        public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, R> DFunctionType<R, ? extends Function14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, ? extends DFunction<R>>> create(
                MapCodec<T1> arg1,
                MapCodec<T2> arg2,
                MapCodec<T3> arg3,
                MapCodec<T4> arg4,
                MapCodec<T5> arg5,
                MapCodec<T6> arg6,
                MapCodec<T7> arg7,
                MapCodec<T8> arg8,
                MapCodec<T9> arg9,
                MapCodec<T10> arg10,
                MapCodec<T11> arg11,
                MapCodec<T12> arg12,
                MapCodec<T13> arg13,
                MapCodec<T14> arg14,
                Function15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, DFContext, R> function
        ) {
            AtomicReference<DFunctionType<R, Function14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, DFunction14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, R>>>> typeRef = new AtomicReference<>();
            Function14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, DFunction14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, R>> factory = (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14) -> new DFunction14<>(
                    typeRef::get,
                    function,
                    t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14);
            var codec = RecordCodecBuilder.<DFunction14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, R>>create(instance -> instance.group(
                    arg1.forGetter(df -> df.t1),
                    arg2.forGetter(df -> df.t2),
                    arg3.forGetter(df -> df.t3),
                    arg4.forGetter(df -> df.t4),
                    arg5.forGetter(df -> df.t5),
                    arg6.forGetter(df -> df.t6),
                    arg7.forGetter(df -> df.t7),
                    arg8.forGetter(df -> df.t8),
                    arg9.forGetter(df -> df.t9),
                    arg10.forGetter(df -> df.t10),
                    arg11.forGetter(df -> df.t11),
                    arg12.forGetter(df -> df.t12),
                    arg13.forGetter(df -> df.t13),
                    arg14.forGetter(df -> df.t14)
            ).apply(instance, factory));
            typeRef.set(new DFunctionType<>(codec, factory));
            return typeRef.get();
        }
    }

    public static final class DFunction13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, R> implements DFunction<R> {
        private final Supplier<DFunctionType<R, ?>> typeSupplier;
        private final Function14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, DFContext, R> function;
        private final T1 t1;
        private final T2 t2;
        private final T3 t3;
        private final T4 t4;
        private final T5 t5;
        private final T6 t6;
        private final T7 t7;
        private final T8 t8;
        private final T9 t9;
        private final T10 t10;
        private final T11 t11;
        private final T12 t12;
        private final T13 t13;
        private final DFContextSpec spec;

        public DFunction13(Supplier<DFunctionType<R, ?>> typeSupplier, Function14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, DFContext, R> function, T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13) {
            this.typeSupplier = typeSupplier;
            this.function = function;
            this.t1 = t1;
            this.t2 = t2;
            this.t3 = t3;
            this.t4 = t4;
            this.t5 = t5;
            this.t6 = t6;
            this.t7 = t7;
            this.t8 = t8;
            this.t9 = t9;
            this.t10 = t10;
            this.t11 = t11;
            this.t12 = t12;
            this.t13 = t13;
            this.spec = createContextSpec(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13);
        }

        @Override
        public DFunctionType<R, ?> type() {
            return this.typeSupplier.get();
        }

        @Override
        public DFContextSpec getSpec() {
            return this.spec;
        }

        @Override
        public R apply(DFContext ctx) {
            return this.function.apply(this.t1, this.t2, this.t3, this.t4, this.t5, this.t6, this.t7, this.t8, this.t9, this.t10, this.t11, this.t12, this.t13, ctx);
        }


        public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, R> DFunctionType<R, ? extends Function13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, ? extends DFunction<R>>> create(
                MapCodec<T1> arg1,
                MapCodec<T2> arg2,
                MapCodec<T3> arg3,
                MapCodec<T4> arg4,
                MapCodec<T5> arg5,
                MapCodec<T6> arg6,
                MapCodec<T7> arg7,
                MapCodec<T8> arg8,
                MapCodec<T9> arg9,
                MapCodec<T10> arg10,
                MapCodec<T11> arg11,
                MapCodec<T12> arg12,
                MapCodec<T13> arg13,
                Function14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, DFContext, R> function
        ) {
            AtomicReference<DFunctionType<R, Function13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, DFunction13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, R>>>> typeRef = new AtomicReference<>();
            Function13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, DFunction13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, R>> factory = (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13) -> new DFunction13<>(
                    typeRef::get,
                    function,
                    t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13);
            var codec = RecordCodecBuilder.<DFunction13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, R>>create(instance -> instance.group(
                    arg1.forGetter(df -> df.t1),
                    arg2.forGetter(df -> df.t2),
                    arg3.forGetter(df -> df.t3),
                    arg4.forGetter(df -> df.t4),
                    arg5.forGetter(df -> df.t5),
                    arg6.forGetter(df -> df.t6),
                    arg7.forGetter(df -> df.t7),
                    arg8.forGetter(df -> df.t8),
                    arg9.forGetter(df -> df.t9),
                    arg10.forGetter(df -> df.t10),
                    arg11.forGetter(df -> df.t11),
                    arg12.forGetter(df -> df.t12),
                    arg13.forGetter(df -> df.t13)
            ).apply(instance, factory));
            typeRef.set(new DFunctionType<>(codec, factory));
            return typeRef.get();
        }
    }

    public static final class DFunction12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, R> implements DFunction<R> {
        private final Supplier<DFunctionType<R, ?>> typeSupplier;
        private final Function13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, DFContext, R> function;
        private final T1 t1;
        private final T2 t2;
        private final T3 t3;
        private final T4 t4;
        private final T5 t5;
        private final T6 t6;
        private final T7 t7;
        private final T8 t8;
        private final T9 t9;
        private final T10 t10;
        private final T11 t11;
        private final T12 t12;
        private final DFContextSpec spec;

        public DFunction12(Supplier<DFunctionType<R, ?>> typeSupplier, Function13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, DFContext, R> function, T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12) {
            this.typeSupplier = typeSupplier;
            this.function = function;
            this.t1 = t1;
            this.t2 = t2;
            this.t3 = t3;
            this.t4 = t4;
            this.t5 = t5;
            this.t6 = t6;
            this.t7 = t7;
            this.t8 = t8;
            this.t9 = t9;
            this.t10 = t10;
            this.t11 = t11;
            this.t12 = t12;
            this.spec = createContextSpec(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
        }

        @Override
        public DFunctionType<R, ?> type() {
            return this.typeSupplier.get();
        }

        @Override
        public DFContextSpec getSpec() {
            return this.spec;
        }

        @Override
        public R apply(DFContext ctx) {
            return this.function.apply(this.t1, this.t2, this.t3, this.t4, this.t5, this.t6, this.t7, this.t8, this.t9, this.t10, this.t11, this.t12, ctx);
        }


        public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, R> DFunctionType<R, ? extends Function12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, ? extends DFunction<R>>> create(
                MapCodec<T1> arg1,
                MapCodec<T2> arg2,
                MapCodec<T3> arg3,
                MapCodec<T4> arg4,
                MapCodec<T5> arg5,
                MapCodec<T6> arg6,
                MapCodec<T7> arg7,
                MapCodec<T8> arg8,
                MapCodec<T9> arg9,
                MapCodec<T10> arg10,
                MapCodec<T11> arg11,
                MapCodec<T12> arg12,
                Function13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, DFContext, R> function
        ) {
            AtomicReference<DFunctionType<R, Function12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, DFunction12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, R>>>> typeRef = new AtomicReference<>();
            Function12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, DFunction12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, R>> factory = (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12) -> new DFunction12<>(
                    typeRef::get,
                    function,
                    t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
            var codec = RecordCodecBuilder.<DFunction12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, R>>create(instance -> instance.group(
                    arg1.forGetter(df -> df.t1),
                    arg2.forGetter(df -> df.t2),
                    arg3.forGetter(df -> df.t3),
                    arg4.forGetter(df -> df.t4),
                    arg5.forGetter(df -> df.t5),
                    arg6.forGetter(df -> df.t6),
                    arg7.forGetter(df -> df.t7),
                    arg8.forGetter(df -> df.t8),
                    arg9.forGetter(df -> df.t9),
                    arg10.forGetter(df -> df.t10),
                    arg11.forGetter(df -> df.t11),
                    arg12.forGetter(df -> df.t12)
            ).apply(instance, factory));
            typeRef.set(new DFunctionType<>(codec, factory));
            return typeRef.get();
        }
    }

    public static final class DFunction11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, R> implements DFunction<R> {
        private final Supplier<DFunctionType<R, ?>> typeSupplier;
        private final Function12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, DFContext, R> function;
        private final T1 t1;
        private final T2 t2;
        private final T3 t3;
        private final T4 t4;
        private final T5 t5;
        private final T6 t6;
        private final T7 t7;
        private final T8 t8;
        private final T9 t9;
        private final T10 t10;
        private final T11 t11;
        private final DFContextSpec spec;

        public DFunction11(Supplier<DFunctionType<R, ?>> typeSupplier, Function12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, DFContext, R> function, T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11) {
            this.typeSupplier = typeSupplier;
            this.function = function;
            this.t1 = t1;
            this.t2 = t2;
            this.t3 = t3;
            this.t4 = t4;
            this.t5 = t5;
            this.t6 = t6;
            this.t7 = t7;
            this.t8 = t8;
            this.t9 = t9;
            this.t10 = t10;
            this.t11 = t11;
            this.spec = createContextSpec(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11);
        }

        @Override
        public DFunctionType<R, ?> type() {
            return this.typeSupplier.get();
        }

        @Override
        public DFContextSpec getSpec() {
            return this.spec;
        }

        @Override
        public R apply(DFContext ctx) {
            return this.function.apply(this.t1, this.t2, this.t3, this.t4, this.t5, this.t6, this.t7, this.t8, this.t9, this.t10, this.t11, ctx);
        }

        public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, R> DFunctionType<R, ? extends Function11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, ? extends DFunction<R>>> create(
                MapCodec<T1> arg1,
                MapCodec<T2> arg2,
                MapCodec<T3> arg3,
                MapCodec<T4> arg4,
                MapCodec<T5> arg5,
                MapCodec<T6> arg6,
                MapCodec<T7> arg7,
                MapCodec<T8> arg8,
                MapCodec<T9> arg9,
                MapCodec<T10> arg10,
                MapCodec<T11> arg11,
                Function12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, DFContext, R> function
        ) {
            AtomicReference<DFunctionType<R, Function11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, DFunction11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, R>>>> typeRef = new AtomicReference<>();
            Function11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, DFunction11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, R>> factory = (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11) -> new DFunction11<>(
                    typeRef::get,
                    function,
                    t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11);
            var codec = RecordCodecBuilder.<DFunction11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, R>>create(instance -> instance.group(
                    arg1.forGetter(df -> df.t1),
                    arg2.forGetter(df -> df.t2),
                    arg3.forGetter(df -> df.t3),
                    arg4.forGetter(df -> df.t4),
                    arg5.forGetter(df -> df.t5),
                    arg6.forGetter(df -> df.t6),
                    arg7.forGetter(df -> df.t7),
                    arg8.forGetter(df -> df.t8),
                    arg9.forGetter(df -> df.t9),
                    arg10.forGetter(df -> df.t10),
                    arg11.forGetter(df -> df.t11)
            ).apply(instance, factory));
            typeRef.set(new DFunctionType<>(codec, factory));
            return typeRef.get();
        }
    }

    public static final class DFunction10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R> implements DFunction<R> {
        private final Supplier<DFunctionType<R, ?>> typeSupplier;
        private final Function11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, DFContext, R> function;
        private final T1 t1;
        private final T2 t2;
        private final T3 t3;
        private final T4 t4;
        private final T5 t5;
        private final T6 t6;
        private final T7 t7;
        private final T8 t8;
        private final T9 t9;
        private final T10 t10;
        private final DFContextSpec spec;

        public DFunction10(Supplier<DFunctionType<R, ?>> typeSupplier, Function11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, DFContext, R> function, T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10) {
            this.typeSupplier = typeSupplier;
            this.function = function;
            this.t1 = t1;
            this.t2 = t2;
            this.t3 = t3;
            this.t4 = t4;
            this.t5 = t5;
            this.t6 = t6;
            this.t7 = t7;
            this.t8 = t8;
            this.t9 = t9;
            this.t10 = t10;
            this.spec = createContextSpec(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
        }

        @Override
        public DFunctionType<R, ?> type() {
            return this.typeSupplier.get();
        }

        @Override
        public DFContextSpec getSpec() {
            return this.spec;
        }

        @Override
        public R apply(DFContext ctx) {
            return this.function.apply(this.t1, this.t2, this.t3, this.t4, this.t5, this.t6, this.t7, this.t8, this.t9, this.t10, ctx);
        }


        public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R> DFunctionType<R, ? extends Function10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, ? extends DFunction<R>>> create(
                MapCodec<T1> arg1,
                MapCodec<T2> arg2,
                MapCodec<T3> arg3,
                MapCodec<T4> arg4,
                MapCodec<T5> arg5,
                MapCodec<T6> arg6,
                MapCodec<T7> arg7,
                MapCodec<T8> arg8,
                MapCodec<T9> arg9,
                MapCodec<T10> arg10,
                Function11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, DFContext, R> function
        ) {
            AtomicReference<DFunctionType<R, Function10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, DFunction10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R>>>> typeRef = new AtomicReference<>();
            Function10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, DFunction10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R>> factory = (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10) -> new DFunction10<>(
                    typeRef::get,
                    function,
                    t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
            var codec = RecordCodecBuilder.<DFunction10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R>>create(instance -> instance.group(
                    arg1.forGetter(df -> df.t1),
                    arg2.forGetter(df -> df.t2),
                    arg3.forGetter(df -> df.t3),
                    arg4.forGetter(df -> df.t4),
                    arg5.forGetter(df -> df.t5),
                    arg6.forGetter(df -> df.t6),
                    arg7.forGetter(df -> df.t7),
                    arg8.forGetter(df -> df.t8),
                    arg9.forGetter(df -> df.t9),
                    arg10.forGetter(df -> df.t10)
            ).apply(instance, factory));
            typeRef.set(new DFunctionType<>(codec, factory));
            return typeRef.get();
        }
    }

    public static final class DFunction9<T1, T2, T3, T4, T5, T6, T7, T8, T9, R> implements DFunction<R> {
        private final Supplier<DFunctionType<R, ?>> typeSupplier;
        private final Function10<T1, T2, T3, T4, T5, T6, T7, T8, T9, DFContext, R> function;
        private final T1 t1;
        private final T2 t2;
        private final T3 t3;
        private final T4 t4;
        private final T5 t5;
        private final T6 t6;
        private final T7 t7;
        private final T8 t8;
        private final T9 t9;
        private final DFContextSpec spec;

        public DFunction9(Supplier<DFunctionType<R, ?>> typeSupplier, Function10<T1, T2, T3, T4, T5, T6, T7, T8, T9, DFContext, R> function, T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9) {
            this.typeSupplier = typeSupplier;
            this.function = function;
            this.t1 = t1;
            this.t2 = t2;
            this.t3 = t3;
            this.t4 = t4;
            this.t5 = t5;
            this.t6 = t6;
            this.t7 = t7;
            this.t8 = t8;
            this.t9 = t9;
            this.spec = createContextSpec(t1, t2, t3, t4, t5, t6, t7, t8, t9);
        }

        @Override
        public DFunctionType<R, ?> type() {
            return this.typeSupplier.get();
        }

        @Override
        public DFContextSpec getSpec() {
            return this.spec;
        }

        @Override
        public R apply(DFContext ctx) {
            return this.function.apply(this.t1, this.t2, this.t3, this.t4, this.t5, this.t6, this.t7, this.t8, this.t9, ctx);
        }

        public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> DFunctionType<R, ? extends Function9<T1, T2, T3, T4, T5, T6, T7, T8, T9, ? extends DFunction<R>>> create(
                MapCodec<T1> arg1,
                MapCodec<T2> arg2,
                MapCodec<T3> arg3,
                MapCodec<T4> arg4,
                MapCodec<T5> arg5,
                MapCodec<T6> arg6,
                MapCodec<T7> arg7,
                MapCodec<T8> arg8,
                MapCodec<T9> arg9,
                Function10<T1, T2, T3, T4, T5, T6, T7, T8, T9, DFContext, R> function
        ) {
            AtomicReference<DFunctionType<R, Function9<T1, T2, T3, T4, T5, T6, T7, T8, T9, DFunction9<T1, T2, T3, T4, T5, T6, T7, T8, T9, R>>>> typeRef = new AtomicReference<>();
            Function9<T1, T2, T3, T4, T5, T6, T7, T8, T9, DFunction9<T1, T2, T3, T4, T5, T6, T7, T8, T9, R>> factory = (t1, t2, t3, t4, t5, t6, t7, t8, t9) -> new DFunction9<>(
                    typeRef::get,
                    function,
                    t1, t2, t3, t4, t5, t6, t7, t8, t9);
            var codec = RecordCodecBuilder.<DFunction9<T1, T2, T3, T4, T5, T6, T7, T8, T9, R>>create(instance -> instance.group(
                    arg1.forGetter(df -> df.t1),
                    arg2.forGetter(df -> df.t2),
                    arg3.forGetter(df -> df.t3),
                    arg4.forGetter(df -> df.t4),
                    arg5.forGetter(df -> df.t5),
                    arg6.forGetter(df -> df.t6),
                    arg7.forGetter(df -> df.t7),
                    arg8.forGetter(df -> df.t8),
                    arg9.forGetter(df -> df.t9)
            ).apply(instance, factory));
            typeRef.set(new DFunctionType<>(codec, factory));
            return typeRef.get();
        }
    }

    public static final class DFunction8<T1, T2, T3, T4, T5, T6, T7, T8, R> implements DFunction<R> {
        private final Supplier<DFunctionType<R, ?>> typeSupplier;
        private final Function9<T1, T2, T3, T4, T5, T6, T7, T8, DFContext, R> function;
        private final T1 t1;
        private final T2 t2;
        private final T3 t3;
        private final T4 t4;
        private final T5 t5;
        private final T6 t6;
        private final T7 t7;
        private final T8 t8;
        private final DFContextSpec spec;

        public DFunction8(Supplier<DFunctionType<R, ?>> typeSupplier, Function9<T1, T2, T3, T4, T5, T6, T7, T8, DFContext, R> function, T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8) {
            this.typeSupplier = typeSupplier;
            this.function = function;
            this.t1 = t1;
            this.t2 = t2;
            this.t3 = t3;
            this.t4 = t4;
            this.t5 = t5;
            this.t6 = t6;
            this.t7 = t7;
            this.t8 = t8;
            this.spec = createContextSpec(t1, t2, t3, t4, t5, t6, t7, t8);
        }

        @Override
        public DFunctionType<R, ?> type() {
            return this.typeSupplier.get();
        }

        @Override
        public DFContextSpec getSpec() {
            return this.spec;
        }

        @Override
        public R apply(DFContext ctx) {
            return this.function.apply(this.t1, this.t2, this.t3, this.t4, this.t5, this.t6, this.t7, this.t8, ctx);
        }

        public static <T1, T2, T3, T4, T5, T6, T7, T8, R> DFunctionType<R, ? extends Function8<T1, T2, T3, T4, T5, T6, T7, T8, ? extends DFunction<R>>> create(
                MapCodec<T1> arg1,
                MapCodec<T2> arg2,
                MapCodec<T3> arg3,
                MapCodec<T4> arg4,
                MapCodec<T5> arg5,
                MapCodec<T6> arg6,
                MapCodec<T7> arg7,
                MapCodec<T8> arg8,
                Function9<T1, T2, T3, T4, T5, T6, T7, T8, DFContext, R> function
        ) {
            AtomicReference<DFunctionType<R, Function8<T1, T2, T3, T4, T5, T6, T7, T8, DFunction8<T1, T2, T3, T4, T5, T6, T7, T8, R>>>> typeRef = new AtomicReference<>();
            Function8<T1, T2, T3, T4, T5, T6, T7, T8, DFunction8<T1, T2, T3, T4, T5, T6, T7, T8, R>> factory = (t1, t2, t3, t4, t5, t6, t7, t8) -> new DFunction8<>(
                    typeRef::get,
                    function,
                    t1, t2, t3, t4, t5, t6, t7, t8);
            var codec = RecordCodecBuilder.<DFunction8<T1, T2, T3, T4, T5, T6, T7, T8, R>>create(instance -> instance.group(
                    arg1.forGetter(df -> df.t1),
                    arg2.forGetter(df -> df.t2),
                    arg3.forGetter(df -> df.t3),
                    arg4.forGetter(df -> df.t4),
                    arg5.forGetter(df -> df.t5),
                    arg6.forGetter(df -> df.t6),
                    arg7.forGetter(df -> df.t7),
                    arg8.forGetter(df -> df.t8)
            ).apply(instance, factory));
            typeRef.set(new DFunctionType<>(codec, factory));
            return typeRef.get();
        }
    }

    public static final class DFunction7<T1, T2, T3, T4, T5, T6, T7, R> implements DFunction<R> {
        private final Supplier<DFunctionType<R, ?>> typeSupplier;
        private final Function8<T1, T2, T3, T4, T5, T6, T7, DFContext, R> function;
        private final T1 t1;
        private final T2 t2;
        private final T3 t3;
        private final T4 t4;
        private final T5 t5;
        private final T6 t6;
        private final T7 t7;
        private final DFContextSpec spec;

        public DFunction7(Supplier<DFunctionType<R, ?>> typeSupplier, Function8<T1, T2, T3, T4, T5, T6, T7, DFContext, R> function, T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7) {
            this.typeSupplier = typeSupplier;
            this.function = function;
            this.t1 = t1;
            this.t2 = t2;
            this.t3 = t3;
            this.t4 = t4;
            this.t5 = t5;
            this.t6 = t6;
            this.t7 = t7;
            this.spec = createContextSpec(t1, t2, t3, t4, t5, t6, t7);
        }

        @Override
        public DFunctionType<R, ?> type() {
            return this.typeSupplier.get();
        }

        @Override
        public DFContextSpec getSpec() {
            return this.spec;
        }

        @Override
        public R apply(DFContext ctx) {
            return this.function.apply(this.t1, this.t2, this.t3, this.t4, this.t5, this.t6, this.t7, ctx);
        }

        public static <T1, T2, T3, T4, T5, T6, T7, R> DFunctionType<R, ? extends Function7<T1, T2, T3, T4, T5, T6, T7, ? extends DFunction<R>>> create(
                MapCodec<T1> arg1,
                MapCodec<T2> arg2,
                MapCodec<T3> arg3,
                MapCodec<T4> arg4,
                MapCodec<T5> arg5,
                MapCodec<T6> arg6,
                MapCodec<T7> arg7,
                Function8<T1, T2, T3, T4, T5, T6, T7, DFContext, R> function
        ) {
            AtomicReference<DFunctionType<R, Function7<T1, T2, T3, T4, T5, T6, T7, DFunction7<T1, T2, T3, T4, T5, T6, T7, R>>>> typeRef = new AtomicReference<>();
            Function7<T1, T2, T3, T4, T5, T6, T7, DFunction7<T1, T2, T3, T4, T5, T6, T7, R>> factory = (t1, t2, t3, t4, t5, t6, t7) -> new DFunction7<>(
                    typeRef::get,
                    function,
                    t1, t2, t3, t4, t5, t6, t7);
            var codec = RecordCodecBuilder.<DFunction7<T1, T2, T3, T4, T5, T6, T7, R>>create(instance -> instance.group(
                    arg1.forGetter(df -> df.t1),
                    arg2.forGetter(df -> df.t2),
                    arg3.forGetter(df -> df.t3),
                    arg4.forGetter(df -> df.t4),
                    arg5.forGetter(df -> df.t5),
                    arg6.forGetter(df -> df.t6),
                    arg7.forGetter(df -> df.t7)
            ).apply(instance, factory));
            typeRef.set(new DFunctionType<>(codec, factory));
            return typeRef.get();
        }
    }

    public static final class DFunction6<T1, T2, T3, T4, T5, T6, R> implements DFunction<R> {
        private final Supplier<DFunctionType<R, ?>> typeSupplier;
        private final Function7<T1, T2, T3, T4, T5, T6, DFContext, R> function;
        private final T1 t1;
        private final T2 t2;
        private final T3 t3;
        private final T4 t4;
        private final T5 t5;
        private final T6 t6;
        private final DFContextSpec spec;

        public DFunction6(Supplier<DFunctionType<R, ?>> typeSupplier, Function7<T1, T2, T3, T4, T5, T6, DFContext, R> function, T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6) {
            this.typeSupplier = typeSupplier;
            this.function = function;
            this.t1 = t1;
            this.t2 = t2;
            this.t3 = t3;
            this.t4 = t4;
            this.t5 = t5;
            this.t6 = t6;
            this.spec = createContextSpec(t1, t2, t3, t4, t5, t6);
        }

        @Override
        public DFunctionType<R, ?> type() {
            return this.typeSupplier.get();
        }

        @Override
        public DFContextSpec getSpec() {
            return this.spec;
        }

        @Override
        public R apply(DFContext ctx) {
            return this.function.apply(this.t1, this.t2, this.t3, this.t4, this.t5, this.t6, ctx);
        }

        public static <T1, T2, T3, T4, T5, T6, R> DFunctionType<R, ? extends Function6<T1, T2, T3, T4, T5, T6, ? extends DFunction<R>>> create(
                MapCodec<T1> arg1,
                MapCodec<T2> arg2,
                MapCodec<T3> arg3,
                MapCodec<T4> arg4,
                MapCodec<T5> arg5,
                MapCodec<T6> arg6,
                Function7<T1, T2, T3, T4, T5, T6, DFContext, R> function
        ) {
            AtomicReference<DFunctionType<R, Function6<T1, T2, T3, T4, T5, T6, DFunction6<T1, T2, T3, T4, T5, T6, R>>>> typeRef = new AtomicReference<>();
            Function6<T1, T2, T3, T4, T5, T6, DFunction6<T1, T2, T3, T4, T5, T6, R>> factory = (t1, t2, t3, t4, t5, t6) -> new DFunction6<>(
                    typeRef::get,
                    function,
                    t1, t2, t3, t4, t5, t6);
            var codec = RecordCodecBuilder.<DFunction6<T1, T2, T3, T4, T5, T6, R>>create(instance -> instance.group(
                    arg1.forGetter(df -> df.t1),
                    arg2.forGetter(df -> df.t2),
                    arg3.forGetter(df -> df.t3),
                    arg4.forGetter(df -> df.t4),
                    arg5.forGetter(df -> df.t5),
                    arg6.forGetter(df -> df.t6)
            ).apply(instance, factory));
            typeRef.set(new DFunctionType<>(codec, factory));
            return typeRef.get();
        }
    }

    public static final class DFunction5<T1, T2, T3, T4, T5, R> implements DFunction<R> {
        private final Supplier<DFunctionType<R, ?>> typeSupplier;
        private final Function6<T1, T2, T3, T4, T5, DFContext, R> function;
        private final T1 t1;
        private final T2 t2;
        private final T3 t3;
        private final T4 t4;
        private final T5 t5;
        private final DFContextSpec spec;

        public DFunction5(Supplier<DFunctionType<R, ?>> typeSupplier, Function6<T1, T2, T3, T4, T5, DFContext, R> function, T1 t1, T2 t2, T3 t3, T4 t4, T5 t5) {
            this.typeSupplier = typeSupplier;
            this.function = function;
            this.t1 = t1;
            this.t2 = t2;
            this.t3 = t3;
            this.t4 = t4;
            this.t5 = t5;
            this.spec = createContextSpec(t1, t2, t3, t4, t5);
        }

        @Override
        public DFunctionType<R, ?> type() {
            return this.typeSupplier.get();
        }

        @Override
        public DFContextSpec getSpec() {
            return this.spec;
        }

        @Override
        public R apply(DFContext ctx) {
            return this.function.apply(this.t1, this.t2, this.t3, this.t4, this.t5, ctx);
        }

        public static <T1, T2, T3, T4, T5, R> DFunctionType<R, ? extends Function5<T1, T2, T3, T4, T5, ? extends DFunction<R>>> create(
                MapCodec<T1> arg1,
                MapCodec<T2> arg2,
                MapCodec<T3> arg3,
                MapCodec<T4> arg4,
                MapCodec<T5> arg5,
                Function6<T1, T2, T3, T4, T5, DFContext, R> function
        ) {
            AtomicReference<DFunctionType<R, Function5<T1, T2, T3, T4, T5, DFunction5<T1, T2, T3, T4, T5, R>>>> typeRef = new AtomicReference<>();
            Function5<T1, T2, T3, T4, T5, DFunction5<T1, T2, T3, T4, T5, R>> factory = (t1, t2, t3, t4, t5) -> new DFunction5<>(
                    typeRef::get,
                    function,
                    t1, t2, t3, t4, t5);
            var codec = RecordCodecBuilder.<DFunction5<T1, T2, T3, T4, T5, R>>create(instance -> instance.group(
                    arg1.forGetter(df -> df.t1),
                    arg2.forGetter(df -> df.t2),
                    arg3.forGetter(df -> df.t3),
                    arg4.forGetter(df -> df.t4),
                    arg5.forGetter(df -> df.t5)
            ).apply(instance, factory));
            typeRef.set(new DFunctionType<>(codec, factory));
            return typeRef.get();
        }
    }

    public static final class DFunction4<T1, T2, T3, T4, R> implements DFunction<R> {
        private final Supplier<DFunctionType<R, ?>> typeSupplier;
        private final Function5<T1, T2, T3, T4, DFContext, R> function;
        private final T1 t1;
        private final T2 t2;
        private final T3 t3;
        private final T4 t4;
        private final DFContextSpec spec;

        public DFunction4(Supplier<DFunctionType<R, ?>> typeSupplier, Function5<T1, T2, T3, T4, DFContext, R> function, T1 t1, T2 t2, T3 t3, T4 t4) {
            this.typeSupplier = typeSupplier;
            this.function = function;
            this.t1 = t1;
            this.t2 = t2;
            this.t3 = t3;
            this.t4 = t4;
            this.spec = createContextSpec(t1, t2, t3, t4);
        }

        @Override
        public DFunctionType<R, ?> type() {
            return this.typeSupplier.get();
        }

        @Override
        public DFContextSpec getSpec() {
            return this.spec;
        }

        @Override
        public R apply(DFContext ctx) {
            return this.function.apply(this.t1, this.t2, this.t3, this.t4, ctx);
        }

        public static <T1, T2, T3, T4, R> DFunctionType<R, ? extends Function4<T1, T2, T3, T4, ? extends DFunction<R>>> create(
                MapCodec<T1> arg1,
                MapCodec<T2> arg2,
                MapCodec<T3> arg3,
                MapCodec<T4> arg4,
                Function5<T1, T2, T3, T4, DFContext, R> function
        ) {
            AtomicReference<DFunctionType<R, Function4<T1, T2, T3, T4, DFunction4<T1, T2, T3, T4, R>>>> typeRef = new AtomicReference<>();
            Function4<T1, T2, T3, T4, DFunction4<T1, T2, T3, T4, R>> factory = (t1, t2, t3, t4) -> new DFunction4<>(
                    typeRef::get,
                    function,
                    t1, t2, t3, t4);
            var codec = RecordCodecBuilder.<DFunction4<T1, T2, T3, T4, R>>create(instance -> instance.group(
                    arg1.forGetter(df -> df.t1),
                    arg2.forGetter(df -> df.t2),
                    arg3.forGetter(df -> df.t3),
                    arg4.forGetter(df -> df.t4)
            ).apply(instance, factory));
            typeRef.set(new DFunctionType<>(codec, factory));
            return typeRef.get();
        }
    }

    public static final class DFunction3<T1, T2, T3, R> implements DFunction<R> {
        private final Supplier<DFunctionType<R, ?>> typeSupplier;
        private final Function4<T1, T2, T3, DFContext, R> function;
        private final T1 t1;
        private final T2 t2;
        private final T3 t3;
        private final DFContextSpec spec;

        public DFunction3(Supplier<DFunctionType<R, ?>> typeSupplier, Function4<T1, T2, T3, DFContext, R> function, T1 t1, T2 t2, T3 t3) {
            this.typeSupplier = typeSupplier;
            this.function = function;
            this.t1 = t1;
            this.t2 = t2;
            this.t3 = t3;
            this.spec = createContextSpec(t1, t2, t3);
        }

        @Override
        public DFunctionType<R, ?> type() {
            return this.typeSupplier.get();
        }

        @Override
        public DFContextSpec getSpec() {
            return this.spec;
        }

        @Override
        public R apply(DFContext ctx) {
            return this.function.apply(this.t1, this.t2, this.t3, ctx);
        }


        public static <T1, T2, T3, R> DFunctionType<R, ? extends Function3<T1, T2, T3, ? extends DFunction<R>>> create(
                MapCodec<T1> arg1,
                MapCodec<T2> arg2,
                MapCodec<T3> arg3,
                Function4<T1, T2, T3, DFContext, R> function
        ) {
            AtomicReference<DFunctionType<R, Function3<T1, T2, T3, DFunction3<T1, T2, T3, R>>>> typeRef = new AtomicReference<>();
            Function3<T1, T2, T3, DFunction3<T1, T2, T3, R>> factory = (t1, t2, t3) -> new DFunction3<>(
                    typeRef::get,
                    function,
                    t1, t2, t3);
            var codec = RecordCodecBuilder.<DFunction3<T1, T2, T3, R>>create(instance -> instance.group(
                    arg1.forGetter(df -> df.t1),
                    arg2.forGetter(df -> df.t2),
                    arg3.forGetter(df -> df.t3)
            ).apply(instance, factory));
            typeRef.set(new DFunctionType<>(codec, factory));
            return typeRef.get();
        }
    }

    public static final class DFunction2<T1, T2, R> implements DFunction<R> {
        private final Supplier<DFunctionType<R, ?>> typeSupplier;
        private final Function3<T1, T2, DFContext, R> function;
        private final T1 t1;
        private final T2 t2;
        private final DFContextSpec spec;

        public DFunction2(Supplier<DFunctionType<R, ?>> typeSupplier, Function3<T1, T2, DFContext, R> function, T1 t1, T2 t2) {
            this.typeSupplier = typeSupplier;
            this.function = function;
            this.t1 = t1;
            this.t2 = t2;
            this.spec = createContextSpec(t1, t2);
        }

        @Override
        public DFunctionType<R, ?> type() {
            return this.typeSupplier.get();
        }

        @Override
        public DFContextSpec getSpec() {
            return this.spec;
        }

        @Override
        public R apply(DFContext ctx) {
            return this.function.apply(this.t1, this.t2, ctx);
        }


        public static <T1, T2, R> DFunctionType<R, ? extends BiFunction<T1, T2, ? extends DFunction<R>>> create(
                MapCodec<T1> arg1,
                MapCodec<T2> arg2,
                Function3<T1, T2, DFContext, R> function
        ) {
            AtomicReference<DFunctionType<R, BiFunction<T1, T2, DFunction2<T1, T2, R>>>> typeRef = new AtomicReference<>();
            BiFunction<T1, T2, DFunction2<T1, T2, R>> factory = (t1, t2) -> new DFunction2<>(
                    typeRef::get,
                    function,
                    t1, t2);
            var codec = RecordCodecBuilder.<DFunction2<T1, T2, R>>create(instance -> instance.group(
                    arg1.forGetter(df -> df.t1),
                    arg2.forGetter(df -> df.t2)
            ).apply(instance, factory));
            typeRef.set(new DFunctionType<>(codec, factory));
            return typeRef.get();
        }
    }

    public static final class DFunction1<T1, R> implements DFunction<R> {
        private final Supplier<DFunctionType<R, ?>> typeSupplier;
        private final BiFunction<T1, DFContext, R> function;
        private final T1 t1;
        private final DFContextSpec spec;

        public DFunction1(Supplier<DFunctionType<R, ?>> typeSupplier, BiFunction<T1, DFContext, R> function, T1 t1) {
            this.typeSupplier = typeSupplier;
            this.function = function;
            this.t1 = t1;
            this.spec = createContextSpec(t1);
        }

        @Override
        public DFunctionType<R, ?> type() {
            return this.typeSupplier.get();
        }

        @Override
        public DFContextSpec getSpec() {
            return this.spec;
        }

        @Override
        public R apply(DFContext ctx) {
            return this.function.apply(this.t1, ctx);
        }


        public static <T1, R> DFunctionType<R, ? extends Function<T1, ? extends DFunction<R>>> create(
                MapCodec<T1> arg1,
                BiFunction<T1, DFContext, R> function
        ) {
            AtomicReference<DFunctionType<R, Function<T1, DFunction1<T1, R>>>> typeRef = new AtomicReference<>();
            Function<T1, DFunction1<T1, R>> factory = (t1) -> new DFunction1<>(
                    typeRef::get,
                    function,
                    t1);
            var codec = RecordCodecBuilder.<DFunction1<T1, R>>create(instance -> instance.group(
                    arg1.forGetter(df -> df.t1)
            ).apply(instance, factory));
            typeRef.set(new DFunctionType<>(codec, factory));
            return typeRef.get();
        }
    }

    public static final class ConstantDFunction<R> implements DFunction<R> {

        private final Supplier<DFunctionType<R, ?>> typeSupplier;
        private final R constant;

        public ConstantDFunction(Supplier<DFunctionType<R, ?>> typeSupplier, R constant) {
            this.typeSupplier = typeSupplier;
            this.constant = constant;
        }

        @Override
        public DFunctionType<R, ?> type() {
            return this.typeSupplier.get();
        }

        @Override
        public DFContextSpec getSpec() {
            return DFContextSpec.EMPTY;
        }

        @Override
        public R apply(DFContext ctx) {
            return this.constant;
        }

        public static <R> DFunctionType<R, Function<R, ConstantDFunction<R>>> create(Codec<R> constantCodec) {
            AtomicReference<DFunctionType<R, Function<R, ConstantDFunction<R>>>> typeRef = new AtomicReference<>();
            Function<R, ConstantDFunction<R>> factory = a -> new ConstantDFunction<>(typeRef::get, a);
            var codec = RecordCodecBuilder.<ConstantDFunction<R>>create(instance -> instance.group(
                    constantCodec.fieldOf("value").forGetter(p -> p.constant)
            ).apply(instance, factory));
            typeRef.set(new DFunctionType<>(codec, factory));
            return typeRef.get();
        }
    }

    private static DFContextSpec createContextSpec(Object... args) {
        var spec = new DFContextSpec(Arrays.stream(args)
                .filter(ContextArg.class::isInstance)
                .<ContextArg<?>>map(ContextArg.class::cast)
                .toList());
        for (var arg : args) {
            if (arg instanceof DFunction<?> df) {
                spec = spec.merge(df.getSpec());
            }
        }
        return spec;
    }
}
