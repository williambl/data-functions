package com.williambl.dfunc;

import com.google.common.reflect.TypeToken;
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
    public <T> List<T> getArgumentsOfType() {
        TypeToken<T> typeToken = new TypeToken<>() {};
        List<Object> result = this.argumentsByType.get(typeToken);
        if (result == null) {
            throw new IllegalArgumentException("No argument of type " + typeToken + " exists");
        }
        return new ArrayList<T>((Collection<? extends T>) result);
    }

    @SuppressWarnings("unchecked")
    public <T> T getArgumentWithNameOrIndex(String name, int index) {
        TypeToken<T> typeToken = new TypeToken<>() {};
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
            this.argumentsByType.computeIfAbsent(new TypeToken<T>() {}, $ -> new ArrayList<>()).add(value);
            return this;
        }

        public DFContext build() {
            return new DFContext(this.argumentsByName, this.argumentsByType);
        }
    }
}
