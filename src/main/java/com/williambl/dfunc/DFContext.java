package com.williambl.dfunc;

import com.google.common.reflect.TypeToken;
import net.minecraft.Util;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Unmodifiable;

import java.util.*;

public class DFContext {
    private final @Unmodifiable Map<String, Object> argumentsByName;
    private final @Unmodifiable Map<TypeToken<?>, List<Object>> argumentsByType;

    public DFContext(Map<String, Object> argumentsByName, Map<TypeToken<?>, List<Object>> argumentsByType) {
        this.argumentsByName = Map.copyOf(argumentsByName);
        this.argumentsByType = Map.copyOf(argumentsByType);
    }

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

    public <T> DFContext with(String name, T value) {
        return new DFContext(Util.make(new HashMap<>(this.argumentsByName), map -> map.put(name, value)),
                Util.make(new HashMap<>(this.argumentsByType), map -> map.computeIfAbsent(TypeToken.of(value.getClass()), $ -> new ArrayList<>()).add(value)));
    }

    public static DFContext empty() {
        return new DFContext(Collections.emptyMap(), Collections.emptyMap());
    }

    public static DFContext entity(Entity entity) {
        return builder()
                .addArgument("entity", entity)
                .addArgument("level", entity.getLevel())
                .build();
    }

    public static DFContext entityTarget(Entity entity, Entity target) {
        return builder()
                .addArgument("entity", entity)
                .addArgument("level", entity.getLevel())
                .addArgument("target", target)
                .build();
    }

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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final Map<String, Object> argumentsByName = new HashMap<>();
        private final Map<TypeToken<?>, List<Object>> argumentsByType = new HashMap<>();

        private Builder() {
        }

        public <T> Builder addArgument(String name, T value) {
            this.argumentsByName.put(name, value);
            this.argumentsByType.computeIfAbsent(TypeToken.of(value.getClass()), $ -> new ArrayList<>()).add(value);
            return this;
        }

        public <T> Builder addArgument(String name, T value, TypeToken<T> typeToken) {
            this.argumentsByName.put(name, value);
            this.argumentsByType.computeIfAbsent(typeToken, $ -> new ArrayList<>()).add(value);
            return this;
        }

        public DFContext build() {
            return new DFContext(this.argumentsByName, this.argumentsByType);
        }
    }
}
