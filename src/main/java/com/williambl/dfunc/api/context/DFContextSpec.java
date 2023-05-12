package com.williambl.dfunc.api.context;

import com.google.common.collect.Sets;
import com.google.common.reflect.TypeToken;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Unmodifiable;

import java.util.*;

/**
 * Represents variables 'required' to exist in a {@link DFContext} for a function to be executed.
 */
public final class DFContextSpec {
    /**
     * The empty DFContextSpec, which requires no variables.
     */
    public static final DFContextSpec EMPTY = new DFContextSpec(Map.of());
    public static final DFContextSpec ENTITY = new DFContextSpec(Map.of("entity", TypeToken.of(Entity.class), "level", TypeToken.of(Level.class)));
    public static final DFContextSpec ENTITY_TARGET = new DFContextSpec(Map.of("entity", TypeToken.of(Entity.class), "level", TypeToken.of(Level.class), "target", TypeToken.of(Entity.class)));
    public static final DFContextSpec ENTITY_DAMAGE = new DFContextSpec(Map.of("entity", TypeToken.of(Entity.class), "level", TypeToken.of(Level.class), "damage_source", TypeToken.of(DamageSource.class), "damage_amount", TypeToken.of(Double.class), "attacker", new TypeToken<Optional<Entity>>() {}, "direct_attacker", new TypeToken<Optional<Entity>>() {}));

    private final @Unmodifiable Map<String, TypeToken<?>> requirements;

    /**
     * Create a new DFContextSpec with the given requirements.
     * @param requirements  the requirements
     */
    public DFContextSpec(Map<String, TypeToken<?>> requirements) {
        this.requirements = Map.copyOf(requirements);
    }

    /**
     * Create a new DFContextSpec, with the requirements derived from the ContextArgs provided.
     * @param ctxArgs   the ContextArgs
     */
    public DFContextSpec(List<ContextArg<?>> ctxArgs) {
        Map<String, TypeToken<?>> reqs = new HashMap<>();
        for (ContextArg<?> ctxArg : ctxArgs) {
            if (ctxArg.renameOrFunction().flatMap(e -> e.right()).isEmpty()) {
                reqs.put(ctxArg.renameOrFunction().flatMap(e -> e.left()).orElse(ctxArg.defaultName()), ctxArg.type());
            }
        }
        this.requirements = Map.copyOf(reqs);
    }

    /**
     * Merge this DFContextSpec with another, returning a new DFContextSpec with the requirements of both.
     * @param other the other DFContextSpec
     * @return      the merged DFContextSpec
     * @throws      IllegalArgumentException if the two DFContextSpecs have incompatible requirements (i.e. they both require a variable with the same name, but incompatible types)
     */
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

    /**
     * Check if this DFContextSpec satisfies another DFContextSpec.
     * @param requiredSpec  the other DFContextSpec
     * @return              whether this DFContextSpec satisfies the other DFContextSpec
     */
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
