package com.williambl.datapred;

import com.mojang.datafixers.util.Function3;
import com.mojang.datafixers.util.Function4;
import com.mojang.datafixers.util.Function5;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.entity.Entity;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Predicate;

import static com.williambl.datapred.DatapackablePredicates.id;
import static com.williambl.datapred.RegistryAndCodec.createRegistry;

public interface DPredicate<T> extends Predicate<T> {
    KeyDispatchDataCodec<? extends DPredicate<T>> codec();

    RegistryAndCodec<DPredicate<Entity>> ENTITY_PREDICATE_CODEC_REGISTRY = createRegistry(id("entity_predicate"));

    static <T> Codec<? extends DPredicate<T>> create(Predicate<T> function) {
        return DPredicateImplementations.DPredicate0.create(function);
    }
    static <A, T> Codec<? extends DPredicate<T>> create(MapCodec<A> codecA, BiFunction<A, T, Boolean> function) {
        return DPredicateImplementations.DPredicate1.create(codecA, function);
    }
    static <A, B, T> Codec<? extends DPredicate<T>> create(MapCodec<A> codecA, MapCodec<B> codecB, Function3<A, B, T, Boolean> function) {
        return DPredicateImplementations.DPredicate2.create(codecA, codecB, function);
    }
    static <A, B, C, T> Codec<? extends DPredicate<T>> create(MapCodec<A> codecA, MapCodec<B> codecB, MapCodec<C> codecC, Function4<A, B, C, T, Boolean> function) {
        return DPredicateImplementations.DPredicate3.create(codecA, codecB, codecC, function);
    }
    static <A, B, C, D, T> Codec<? extends DPredicate<T>> create(MapCodec<A> codecA, MapCodec<B> codecB, MapCodec<C> codecC, MapCodec<D> codecD, Function5<A, B, C, D, T, Boolean> function) {
        return DPredicateImplementations.DPredicate4.create(codecA, codecB, codecC, codecD, function);
    }
}
