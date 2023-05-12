package com.williambl.dfunc.api.context;

import com.google.common.reflect.TypeToken;
import net.minecraft.Util;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Unmodifiable;

import java.util.*;

/**
 * Represents the context in which a {@link com.williambl.dfunc.api.DFunction} is being executed. This stores the arguments
 * that are available to the function.
 */
public class DFContext {
    private final @Unmodifiable Map<String, Object> argumentsByName;
    private final @Unmodifiable Map<TypeToken<?>, List<Object>> argumentsByType;

    /**
     * Create a new DFContext with the given arguments. In most cases, you'll want to use one of the static methods,
     * or a builder.
     * @param argumentsByName   the arguments, keyed by name
     * @param argumentsByType   the arguments, keyed by type
     */
    public DFContext(Map<String, Object> argumentsByName, Map<TypeToken<?>, List<Object>> argumentsByType) {
        this.argumentsByName = Map.copyOf(argumentsByName);
        this.argumentsByType = Map.copyOf(argumentsByType);
    }

    /**
     * Get an argument value by name, or by index if there are arguments of the given type but none with the given name.
     * If no argument is found, an exception is thrown.
     * @param name      the name of the argument
     * @param index     the index of the argument
     * @param typeToken the type of the argument
     * @return          the argument
     * @throws IllegalArgumentException if no argument with the given name or index exists
     */
    @SuppressWarnings("unchecked")
    public <T> T getArgumentWithNameOrIndex(String name, int index, TypeToken<T> typeToken) {
        Object resByName = this.argumentsByName.get(name);
        if (resByName != null && typeToken.isSupertypeOf(resByName.getClass())) {
            return (T) resByName;
        }

        List<Object> resByType = this.argumentsByType.get(typeToken);
        if (resByType != null && index < resByType.size()) {
            return (T) resByType.get(index);
        }

        throw new IllegalArgumentException("No argument with name " + name + " or index " + index + " with type " + typeToken + " exists");
    }

    /**
     * Create a new DFContext with the given arguments added.
     * @param name  the name of the argument
     * @param value the value of the argument
     * @return      the new DFContext
     */
    public <T> DFContext with(String name, T value) {
        return new DFContext(Util.make(new HashMap<>(this.argumentsByName), map -> map.put(name, value)),
                Util.make(new HashMap<>(this.argumentsByType), map -> map.computeIfAbsent(TypeToken.of(value.getClass()), $ -> new ArrayList<>()).add(value)));
    }

    /**
     * Create an empty DFContext
     * @return  the new DFContext
     */
    public static DFContext empty() {
        return new DFContext(Collections.emptyMap(), Collections.emptyMap());
    }

    /**
     * Create a DFContext with an entity (and its level).
     * @param entity    the entity
     * @return          the new DFContext
     */
    public static DFContext entity(Entity entity) {
        return builder()
                .addArgument("entity", entity)
                .addArgument("level", entity.getLevel())
                .build();
    }

    /**
     * Create a DFContext with an entity (and its level) and a second 'target' entity.
     * @param entity    the entity
     * @param target    the target entity
     * @return          the new DFContext
     */
    public static DFContext entityTarget(Entity entity, Entity target) {
        return builder()
                .addArgument("entity", entity)
                .addArgument("level", entity.getLevel())
                .addArgument("target", target)
                .build();
    }

    /**
     * Create a DFContext with an entity (and its level), a damage source (and the attacker + direct attacker), and a damage amount.
     * @param entity    the entity
     * @param source    the damage source
     * @param amount    the damage amount
     * @return          the new DFContext
     */
    public static DFContext entityDamage(Entity entity, DamageSource source, float amount) {
        return builder()
                .addArgument("entity", entity)
                .addArgument("level", entity.getLevel())
                .addArgument("damage_source", source)
                .addArgument("damage_amount", (double) amount)
                .addArgument("attacker", Optional.ofNullable(source.getEntity()), new TypeToken<>() {})
                .addArgument("direct_attacker", Optional.ofNullable(source.getDirectEntity()), new TypeToken<>() {})
                .build();
    }

    /**
     * Create a DFContext Builder.
     * @return  the Builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * A Builder for DFContexts.
     */
    public static class Builder {
        private final Map<String, Object> argumentsByName = new HashMap<>();
        private final Map<TypeToken<?>, List<Object>> argumentsByType = new HashMap<>();

        private Builder() {
        }

        /**
         * Add an argument to the DFContext.
         * @apiNote for complex types (i.e. anything generic), use {@link #addArgument(String, Object, TypeToken)} instead
         * @param name  the name of the argument
         * @param value the value of the argument
         * @return      the Builder
         */
        public <T> Builder addArgument(String name, T value) {
            this.argumentsByName.put(name, value);
            this.argumentsByType.computeIfAbsent(TypeToken.of(value.getClass()), $ -> new ArrayList<>()).add(value);
            return this;
        }

        /**
         * Add an argument to the DFContext.
         * @param name      the name of the argument
         * @param value     the value of the argument
         * @param typeToken the type of the argument
         * @return          the Builder
         */
        public <T> Builder addArgument(String name, T value, TypeToken<T> typeToken) {
            this.argumentsByName.put(name, value);
            this.argumentsByType.computeIfAbsent(typeToken, $ -> new ArrayList<>()).add(value);
            return this;
        }

        /**
         * Build the DFContext.
         * @return  the DFContext
         */
        public DFContext build() {
            return new DFContext(this.argumentsByName, this.argumentsByType);
        }
    }
}
