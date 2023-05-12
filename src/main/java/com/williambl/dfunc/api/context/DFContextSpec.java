package com.williambl.dfunc.api.context;

import com.google.common.collect.Sets;
import com.google.common.reflect.TypeToken;
import org.jetbrains.annotations.Unmodifiable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class DFContextSpec {
    public static final DFContextSpec EMPTY = new DFContextSpec(Map.of());

    private final @Unmodifiable Map<String, TypeToken<?>> requirements;

    public DFContextSpec(Map<String, TypeToken<?>> requirements) {
        this.requirements = Map.copyOf(requirements);
    }

    public DFContextSpec(List<ContextArg<?>> ctxArgs) {
        Map<String, TypeToken<?>> reqs = new HashMap<>();
        for (ContextArg<?> ctxArg : ctxArgs) {
            if (ctxArg.renameOrFunction().flatMap(e -> e.right()).isEmpty()) {
                reqs.put(ctxArg.renameOrFunction().flatMap(e -> e.left()).orElse(ctxArg.defaultName()), ctxArg.type());
            }
        }
        this.requirements = Map.copyOf(reqs);
    }

    public DFContextSpec merge(DFContextSpec other) {
        Map<String, TypeToken<?>> reqs = new HashMap<>();
        // for each entry, if just one of this.requirements and other.requirements has it, put it in as-is.
        // if it's needed by both, put the strictest type required for it. if the two types are incompatible, throw an exception
        for (String key : Sets.union(this.requirements.keySet(), other.requirements.keySet())) {
            TypeToken<?> thisType = this.requirements.get(key);
            TypeToken<?> otherType = other.requirements.get(key);
            if (thisType == null) {
                reqs.put(key, otherType);
            } else if (otherType == null) {
                reqs.put(key, thisType);
            } else {
                if (thisType.isSupertypeOf(otherType)) {
                    reqs.put(key, thisType);
                } else if (otherType.isSupertypeOf(thisType)) {
                    reqs.put(key, otherType);
                } else {
                    throw new IllegalArgumentException("DFContextSpec merge failed: incompatible types for key " + key + ": " + thisType + " and " + otherType);
                }
            }
        }

        return new DFContextSpec(reqs);
    }

    public boolean satisfies(DFContextSpec requiredSpec) {
        return this.requirements.entrySet().stream().allMatch(entry -> {
            TypeToken<?> otherType = requiredSpec.requirements.get(entry.getKey());
            return otherType != null && otherType.isSupertypeOf(entry.getValue());
        });
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (DFContextSpec) obj;
        return Objects.equals(this.requirements, that.requirements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.requirements);
    }

    @Override
    public String toString() {
        return "DFContextSpec[" +
                "requirements=" + this.requirements + ']';
    }
}
