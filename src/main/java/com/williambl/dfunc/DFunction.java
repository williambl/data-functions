package com.williambl.dfunc;

import com.google.common.reflect.TypeToken;
import com.mojang.datafixers.util.*;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;

import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.williambl.dfunc.DataFunctions.id;
import static com.williambl.dfunc.DFunctionTypeRegistry.createRegistry;

/**
 * A data-defined predicate.
 * <p>
 * Create a new type of DPredicate using the many {@code create} methods in this class. If you need one with more than
 * four parameters, implement this interface yourself.
 */
public interface DFunction<R> extends Function<DFContext, R> {
    /**
     * Get this DPredicate's type.
     *
     * @return this DPredicate's type
     */
    DFunctionType<R, ?> type();

    DFContextSpec getSpec();

    DFunctionTypeRegistry<Boolean> PREDICATE = createRegistry(id("predicate"), Codec.BOOL);
    DFunctionTypeRegistry<Double> NUMBER_FUNCTION = createRegistry(id("number_function"), Codec.DOUBLE);

    /**
     * Create a DFunction that produces a value given by its codec.
     *
     * @param constantCodec the value codec
     * @return a DFunction type
     */
    static <R> DFunctionType<R, ? extends Function<R, ? extends DFunction<R>>> create(Codec<R> constantCodec) {
        return DFunctionImplementations.ConstantDFunction.create(constantCodec);
    }

    static <T1, R> DFunctionType<R, ? extends Function<T1, ? extends DFunction<R>>> create(MapCodec<T1> codec1, BiFunction<T1, DFContext, R> function) {
        return DFunctionImplementations.DFunction1.create(codec1, function);
    }
    static <T1, T2, R> DFunctionType<R, ? extends BiFunction<T1, T2, ? extends DFunction<R>>> create(MapCodec<T1> codec1, MapCodec<T2> codec2, Function3<T1, T2, DFContext, R> function) {
        return DFunctionImplementations.DFunction2.create(codec1, codec2, function);
    }
    static <T1, T2, T3, R> DFunctionType<R, ? extends Function3<T1, T2, T3, ? extends DFunction<R>>> create(MapCodec<T1> codec1, MapCodec<T2> codec2, MapCodec<T3> codec3, Function4<T1, T2, T3, DFContext, R> function) {
        return DFunctionImplementations.DFunction3.create(codec1, codec2, codec3, function);
    }
    static <T1, T2, T3, T4, R> DFunctionType<R, ? extends Function4<T1, T2, T3, T4, ? extends DFunction<R>>> create(MapCodec<T1> codec1, MapCodec<T2> codec2, MapCodec<T3> codec3, MapCodec<T4> codec4, Function5<T1, T2, T3, T4, DFContext, R> function) {
        return DFunctionImplementations.DFunction4.create(codec1, codec2, codec3, codec4, function);
    }
    static <T1, T2, T3, T4, T5, R> DFunctionType<R, ? extends Function5<T1, T2, T3, T4, T5, ? extends DFunction<R>>> create(MapCodec<T1> codec1, MapCodec<T2> codec2, MapCodec<T3> codec3, MapCodec<T4> codec4, MapCodec<T5> codec5, Function6<T1, T2, T3, T4, T5, DFContext, R> function) {
        return DFunctionImplementations.DFunction5.create(codec1, codec2, codec3, codec4, codec5, function);
    }
    static <T1, T2, T3, T4, T5, T6, R> DFunctionType<R, ? extends Function6<T1, T2, T3, T4, T5, T6, ? extends DFunction<R>>> create(MapCodec<T1> codec1, MapCodec<T2> codec2, MapCodec<T3> codec3, MapCodec<T4> codec4, MapCodec<T5> codec5, MapCodec<T6> codec6, Function7<T1, T2, T3, T4, T5, T6, DFContext, R> function) {
        return DFunctionImplementations.DFunction6.create(codec1, codec2, codec3, codec4, codec5, codec6, function);
    }
    static <T1, T2, T3, T4, T5, T6, T7, R> DFunctionType<R, ? extends Function7<T1, T2, T3, T4, T5, T6, T7, ? extends DFunction<R>>> create(MapCodec<T1> codec1, MapCodec<T2> codec2, MapCodec<T3> codec3, MapCodec<T4> codec4, MapCodec<T5> codec5, MapCodec<T6> codec6, MapCodec<T7> codec7, Function8<T1, T2, T3, T4, T5, T6, T7, DFContext, R> function) {
        return DFunctionImplementations.DFunction7.create(codec1, codec2, codec3, codec4, codec5, codec6, codec7, function);
    }
    static <T1, T2, T3, T4, T5, T6, T7, T8, R> DFunctionType<R, ? extends Function8<T1, T2, T3, T4, T5, T6, T7, T8, ? extends DFunction<R>>> create(MapCodec<T1> codec1, MapCodec<T2> codec2, MapCodec<T3> codec3, MapCodec<T4> codec4, MapCodec<T5> codec5, MapCodec<T6> codec6, MapCodec<T7> codec7, MapCodec<T8> codec8, Function9<T1, T2, T3, T4, T5, T6, T7, T8, DFContext, R> function) {
        return DFunctionImplementations.DFunction8.create(codec1, codec2, codec3, codec4, codec5, codec6, codec7, codec8, function);
    }
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> DFunctionType<R, ? extends Function9<T1, T2, T3, T4, T5, T6, T7, T8, T9, ? extends DFunction<R>>> create(MapCodec<T1> codec1, MapCodec<T2> codec2, MapCodec<T3> codec3, MapCodec<T4> codec4, MapCodec<T5> codec5, MapCodec<T6> codec6, MapCodec<T7> codec7, MapCodec<T8> codec8, MapCodec<T9> codec9, Function10<T1, T2, T3, T4, T5, T6, T7, T8, T9, DFContext, R> function) {
        return DFunctionImplementations.DFunction9.create(codec1, codec2, codec3, codec4, codec5, codec6, codec7, codec8, codec9, function);
    }
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R> DFunctionType<R, ? extends Function10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, ? extends DFunction<R>>> create(MapCodec<T1> codec1, MapCodec<T2> codec2, MapCodec<T3> codec3, MapCodec<T4> codec4, MapCodec<T5> codec5, MapCodec<T6> codec6, MapCodec<T7> codec7, MapCodec<T8> codec8, MapCodec<T9> codec9, MapCodec<T10> codec10, Function11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, DFContext, R> function) {
        return DFunctionImplementations.DFunction10.create(codec1, codec2, codec3, codec4, codec5, codec6, codec7, codec8, codec9, codec10, function);
    }
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, R> DFunctionType<R, ? extends Function11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, ? extends DFunction<R>>> create(MapCodec<T1> codec1, MapCodec<T2> codec2, MapCodec<T3> codec3, MapCodec<T4> codec4, MapCodec<T5> codec5, MapCodec<T6> codec6, MapCodec<T7> codec7, MapCodec<T8> codec8, MapCodec<T9> codec9, MapCodec<T10> codec10, MapCodec<T11> codec11, Function12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, DFContext, R> function) {
        return DFunctionImplementations.DFunction11.create(codec1, codec2, codec3, codec4, codec5, codec6, codec7, codec8, codec9, codec10, codec11, function);
    }
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, R> DFunctionType<R, ? extends Function12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, ? extends DFunction<R>>> create(MapCodec<T1> codec1, MapCodec<T2> codec2, MapCodec<T3> codec3, MapCodec<T4> codec4, MapCodec<T5> codec5, MapCodec<T6> codec6, MapCodec<T7> codec7, MapCodec<T8> codec8, MapCodec<T9> codec9, MapCodec<T10> codec10, MapCodec<T11> codec11, MapCodec<T12> codec12, Function13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, DFContext, R> function) {
        return DFunctionImplementations.DFunction12.create(codec1, codec2, codec3, codec4, codec5, codec6, codec7, codec8, codec9, codec10, codec11, codec12, function);
    }
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, R> DFunctionType<R, ? extends Function13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, ? extends DFunction<R>>> create(MapCodec<T1> codec1, MapCodec<T2> codec2, MapCodec<T3> codec3, MapCodec<T4> codec4, MapCodec<T5> codec5, MapCodec<T6> codec6, MapCodec<T7> codec7, MapCodec<T8> codec8, MapCodec<T9> codec9, MapCodec<T10> codec10, MapCodec<T11> codec11, MapCodec<T12> codec12, MapCodec<T13> codec13, Function14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, DFContext, R> function) {
        return DFunctionImplementations.DFunction13.create(codec1, codec2, codec3, codec4, codec5, codec6, codec7, codec8, codec9, codec10, codec11, codec12, codec13, function);
    }
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, R> DFunctionType<R, ? extends Function14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, ? extends DFunction<R>>> create(MapCodec<T1> codec1, MapCodec<T2> codec2, MapCodec<T3> codec3, MapCodec<T4> codec4, MapCodec<T5> codec5, MapCodec<T6> codec6, MapCodec<T7> codec7, MapCodec<T8> codec8, MapCodec<T9> codec9, MapCodec<T10> codec10, MapCodec<T11> codec11, MapCodec<T12> codec12, MapCodec<T13> codec13, MapCodec<T14> codec14, Function15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, DFContext, R> function) {
        return DFunctionImplementations.DFunction14.create(codec1, codec2, codec3, codec4, codec5, codec6, codec7, codec8, codec9, codec10, codec11, codec12, codec13, codec14, function);
    }
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, R> DFunctionType<R, ? extends Function15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, ? extends DFunction<R>>> create(MapCodec<T1> codec1, MapCodec<T2> codec2, MapCodec<T3> codec3, MapCodec<T4> codec4, MapCodec<T5> codec5, MapCodec<T6> codec6, MapCodec<T7> codec7, MapCodec<T8> codec8, MapCodec<T9> codec9, MapCodec<T10> codec10, MapCodec<T11> codec11, MapCodec<T12> codec12, MapCodec<T13> codec13, MapCodec<T14> codec14, MapCodec<T15> codec15, Function16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, DFContext, R> function) {
        return DFunctionImplementations.DFunction15.create(codec1, codec2, codec3, codec4, codec5, codec6, codec7, codec8, codec9, codec10, codec11, codec12, codec13, codec14, codec15, function);
    }

}
