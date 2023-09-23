package com.williambl.dfunc.impl.platform;

import com.google.auto.service.AutoService;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

@AutoService(RegistrationProvider.Factory.class)
public class FabricRegistrationFactory implements RegistrationProvider.Factory {
    private final Map<ResourceKey<? extends Registry<?>>, Map<String, Provider<?>>> REGISTRIES = new ConcurrentHashMap<>();

    @Override
    public <T> RegistrationProvider<T> getOrCreate(ResourceKey<? extends Registry<T>> resourceKey, String modId) {
        if (REGISTRIES.containsKey(resourceKey) && REGISTRIES.get(resourceKey).containsKey(modId)) {
            REGISTRIES.get(resourceKey).get(modId);
        }
        Provider<T> provider = new Provider<>(modId, resourceKey);
        REGISTRIES.compute(resourceKey, (resourceKey1, stringProviderMap) -> {
            Map<String, Provider<?>> map = stringProviderMap != null ? stringProviderMap : new ConcurrentHashMap<>();
            map.put(modId, provider);
            return stringProviderMap;
        });
        return provider;
    }

    private static class Provider<T> implements RegistrationProvider<T> {
        private final String modId;
        private final Registry<T> registry;

        private final Set<RegistryObject<T>> entries = new HashSet<>();
        private final Set<RegistryObject<T>> entriesView = Collections.unmodifiableSet(entries);

        @SuppressWarnings({"unchecked"})
        private Provider(String modId, ResourceKey<? extends Registry<T>> key) {
            this.modId = modId;

            final var reg = BuiltInRegistries.REGISTRY.get(key.location());
            if(reg == null) {
                throw new RuntimeException("Registry with name " + key.location() + " was not found!");
            }
            registry = (Registry<T>) reg;
        }

        private Provider(String modId, Registry<T> registry) {
            this.modId = modId;
            this.registry = registry;
        }

        @Override
        @SuppressWarnings("unchecked")
        public RegistryObject<T> register(String name, Supplier<T> supplier) {
            final var rl = new ResourceLocation(modId, name);
            final var obj = Registry.register(registry, rl, supplier.get());
            final var ro = new RegistryObject<T>() {
                final ResourceKey<T> key = ResourceKey.create(registry.key(), rl);

                @Override
                public ResourceKey<T> getResourceKey() {
                    return key;
                }

                @Override
                public ResourceLocation getId() {
                    return rl;
                }

                @Override
                public T get() {
                    return obj;
                }

                @Override
                public Holder<T> asHolder() {
                    return registry.getHolderOrThrow(this.key);
                }
            };
            entries.add(ro);
            return ro;
        }

        @Override
        public Collection<RegistryObject<T>> getEntries() {
            return entriesView;
        }

        @Override
        public String getModId() {
            return modId;
        }
    }
}