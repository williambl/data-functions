package com.williambl.dfunc.impl.platform;

import com.google.auto.service.AutoService;
import com.williambl.dfunc.impl.platform.RegistrationProvider;
import com.williambl.dfunc.impl.platform.RegistryObject;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.javafmlmod.FMLModContainer;
import net.minecraftforge.registries.DeferredRegister;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

@AutoService(RegistrationProvider.Factory.class)
public class ForgeRegistrationFactory implements RegistrationProvider.Factory {
    private final Map<ResourceKey<? extends Registry<?>>, Map<String, Provider<?>>> REGISTRIES = new ConcurrentHashMap<>();

    @Override
    public <T> RegistrationProvider<T> getOrCreate(ResourceKey<? extends Registry<T>> resourceKey, String modId) {
        if (REGISTRIES.containsKey(resourceKey) && REGISTRIES.get(resourceKey).containsKey(modId)) {
            REGISTRIES.get(resourceKey).get(modId);
        }
        final var containerOpt = ModList.get().getModContainerById(modId);
        if(containerOpt.isEmpty())
            throw new NullPointerException("Cannot find mod container for id " + modId);
        final var cont = containerOpt.get();
        if(cont instanceof FMLModContainer fmlModContainer) {
            final var register = DeferredRegister.create(resourceKey, modId);
            register.register(fmlModContainer.getEventBus());
            Provider<T> provider = new Provider<>(modId, register);
            REGISTRIES.compute(resourceKey, (resourceKey1, stringProviderMap) -> {
                Map<String, Provider<?>> map = stringProviderMap != null ? stringProviderMap : new ConcurrentHashMap<>();
                map.put(modId, provider);
                return stringProviderMap;
            });
            return provider;
        } else {
            throw new ClassCastException("The container of the mod " + modId + " is not a FML one!");
        }
    }

    private class Provider<T> implements RegistrationProvider<T> {
        private final String modId;
        private final DeferredRegister<T> registry;

        private final Set<RegistryObject<T>> entries = new HashSet<>();
        private final Set<RegistryObject<T>> entriesView = Collections.unmodifiableSet(entries);

        private Provider(String modId, DeferredRegister<T> registry) {
            this.modId = modId;
            this.registry = registry;
        }

        @Override
        public String getModId() {
            return modId;
        }

        @Override
        @SuppressWarnings("unchecked")
        public RegistryObject<T> register(String name, Supplier<T> supplier) {
            final var obj = registry.register(name, supplier);
            final var ro = new RegistryObject<T>() {

                @Override
                public ResourceKey<T> getResourceKey() {
                    return obj.getKey();
                }

                @Override
                public ResourceLocation getId() {
                    return obj.getId();
                }

                @Override
                public T get() {
                    return obj.get();
                }

                @Override
                public Holder<T> asHolder() {
                    return obj.getHolder().orElseThrow();
                }
            };
            entries.add(ro);
            return ro;
        }

        @Override
        public Set<RegistryObject<T>> getEntries() {
            return entriesView;
        }
    }
}