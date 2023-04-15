package com.williambl.dpred;

import com.mojang.serialization.Codec;

public record DPredicateType<T, F>(Codec<? extends DPredicate<T>> codec, F factory) {
}
