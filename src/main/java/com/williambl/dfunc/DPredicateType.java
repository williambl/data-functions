package com.williambl.dfunc;

import com.mojang.serialization.Codec;

/**
 * A DPredicateType holds a Codec and a factory function for a DPredicate. In this way it holds everything needed to
 * create new instances of a DPredicate, whether through code or data.
 * @param codec     the codec for the DPredicate
 * @param factory   the factory function for the DPredicate
 * @param <T>       the type the DPredicate tests
 * @param <F>       the type of the factory function
 */
public record DPredicateType<T, F>(Codec<? extends DPredicate<T>> codec, F factory) {
}
