package com.williambl.dfunc;

import com.mojang.serialization.Codec;

/**
 * A DFunctionType holds a Codec and a factory function for a DFunction. In this way it holds everything needed to
 * create new instances of a DFunction, whether through code or data.
 * @param codec     the codec for the DFunction
 * @param factory   the factory function for the DFunction
 * @param <T>       the input type of the DFunction
 * @param <R>       the result type of the DFunction
 * @param <F>       the type of the factory function
 */
public record DFunctionType<T, R, F>(Codec<? extends DFunction<T, R>> codec, F factory) {
}
