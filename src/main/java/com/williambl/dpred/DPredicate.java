package com.williambl.dpred;

import com.mojang.datafixers.util.Function3;
import com.mojang.datafixers.util.Function4;
import com.mojang.datafixers.util.Function5;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;

import java.util.function.BiFunction;
import java.util.function.Predicate;

import static com.williambl.dpred.DatapackablePredicates.id;
import static com.williambl.dpred.RegistryAndCodec.createRegistry;

public interface DPredicate<T> extends Predicate<T> {
    KeyDispatchDataCodec<? extends DPredicate<T>> codec();

    RegistryAndCodec<DPredicate<Entity>> ENTITY_PREDICATE_CODEC_REGISTRY = createRegistry(id("entity_predicate"));
    RegistryAndCodec<DPredicate<ItemStack>> ITEMSTACK_PREDICATE_CODEC_REGISTRY = createRegistry(id("itemstack_predicate"));
    RegistryAndCodec<DPredicate<BlockInWorld>> BLOCK_IN_WORLD_PREDICATE_CODEC_REGISTRY = createRegistry(id("block_in_world_predicate"));
    RegistryAndCodec<DPredicate<Pair<Entity, Entity>>> BI_ENTITY_PREDICATE_CODEC_REGISTRY = createRegistry(id("bi_entity_predicate"));
    RegistryAndCodec<DPredicate<Pair<DamageSource, Float>>> DAMAGE_PREDICATE_CODEC_REGISTRY = createRegistry(id("damage_predicate"));

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
