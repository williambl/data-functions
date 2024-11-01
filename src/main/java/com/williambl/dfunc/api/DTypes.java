package com.williambl.dfunc.api;

import com.google.common.reflect.TypeParameter;
import com.google.common.reflect.TypeToken;
import com.mojang.serialization.Codec;
import com.williambl.dfunc.impl.DataFunctionsMod;
import com.williambl.vampilang.lang.VEnvironment;
import com.williambl.vampilang.lang.VValue;
import com.williambl.vampilang.lang.type.*;
import com.williambl.vampilang.stdlib.StandardVTypes;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.Vec3;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class DTypes {
    private static final Map<ResourceKey<? extends Registry<?>>, TypedVType<? extends Holder<?>>> REGISTRY_KEY_2_VTYPE = new HashMap<>();
    private static final Map<VType, TaggableTypeInfo<?, ?>> TAGGABLE_TYPE_INFO = new HashMap<>();

    public static final VTemplateType TAGGABLE = VType.createDynamicTemplate(TAGGABLE_TYPE_INFO::containsKey);
    private static final SimpleVType BARE_TAG = VType.create();
    public static final VParameterisedType TAG = VType.createParameterised(BARE_TAG, TAGGABLE);

    public static final TypedVType<Level> LEVEL = VType.create(TypeToken.of(Level.class));

    public static final TypedVType<Entity> ENTITY = createTaggable(VType.create(TypeToken.of(Entity.class)), (e, t) -> e.getType().is(t), Registries.ENTITY_TYPE);
    public static final VType OPTIONAL_ENTITY = StandardVTypes.OPTIONAL.with(0, ENTITY); // don't register this, as it's just a parameterisation of another type

    public static final TypedVType<DamageSource> DAMAGE_SOURCE = createTaggable(VType.create(TypeToken.of(DamageSource.class)), DamageSource::is, Registries.DAMAGE_TYPE);

    public static final TypedVType<ItemStack> ITEM_STACK = createTaggable(VType.create(TypeToken.of(ItemStack.class)), ItemStack::is, Registries.ITEM);

    public static final TypedVType<Holder<Fluid>> FLUID = forRegistry(Registries.FLUID);
    public static final TypedVType<Holder<MobEffect>> MOB_EFFECT = forRegistry(Registries.MOB_EFFECT);
    public static final TypedVType<Holder<Enchantment>> ENCHANTMENT = forRegistry(Registries.ENCHANTMENT);
    public static final TypedVType<Holder<Attribute>> ATTRIBUTE = forRegistry(Registries.ATTRIBUTE);
    public static final TypedVType<Holder<SoundEvent>> SOUND_EVENT = forRegistry(Registries.SOUND_EVENT);

    public static final TypedVType<BlockInWorld> BLOCK_IN_WORLD = createTaggable(VType.create(TypeToken.of(BlockInWorld.class)), (b, t) -> b.getState().is(t), Registries.BLOCK);

    public static final TypedVType<ItemPredicate> ITEM_ADVANCEMENT_PREDICATE = VType.create(TypeToken.of(ItemPredicate.class));
    public static final TypedVType<BlockPredicate> BLOCK_ADVANCEMENT_PREDICATE = VType.create(TypeToken.of(BlockPredicate.class));
    public static final TypedVType<EntityPredicate> ENTITY_ADVANCEMENT_PREDICATE = VType.create(TypeToken.of(EntityPredicate.class));

    public static final TypedVType<Vec3> VEC3 = VType.create(TypeToken.of(Vec3.class));

    public static final TypedVType<ParticleOptions> PARTICLE = VType.create(TypeToken.of(ParticleOptions.class));

    public static final TypedVType<ResourceLocation> RESOURCE_LOCATION = VType.create(TypeToken.of(ResourceLocation.class));

    public static final TypedVType<net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate> BLOCK_WORLDGEN_PREDICATE = VType.create(TypeToken.of(net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate.class));

    public static <T> TypedVType<Holder<T>> forRegistry(ResourceKey<Registry<T>> registryKey) {
        if (REGISTRY_KEY_2_VTYPE.containsKey(registryKey)) {
            return (TypedVType<Holder<T>>) REGISTRY_KEY_2_VTYPE.get(registryKey);
        } else {
            return createFromRegistryKey(registryKey, new TypeToken<>() {});
        }
    }

    public static <T, R> TypedVType<T> createTaggable(TypedVType<T> vType, BiPredicate<T, TagKey<R>> isInTag, ResourceKey<Registry<R>> tagRegistryKey) {
        TAGGABLE_TYPE_INFO.put(vType, new TaggableTypeInfo<>(isInTag, tagRegistryKey));
        return vType;
    }

    public static <T, R> Predicate<TagKey<R>> tagFunctionForTaggable(VValue input) {
        var res = (TaggableTypeInfo<T, R>) TAGGABLE_TYPE_INFO.get(input.type());
        if (res == null) {
            throw new IllegalArgumentException("tagFunctionForTaggable called for non-taggable input type "+input.type());
        }

        return tag -> res.isInTag().test(input.getUnchecked(), tag);
    }

    private static <T> TypedVType<Holder<T>> createFromRegistryKey(ResourceKey<Registry<T>> registryKey, TypeToken<Holder<T>> typeToken) {
        var type = VType.create(typeToken);
        Codec<Holder<T>> codec = RegistryFixedCodec.create(registryKey);
        DFunctions.ENV.registerType(registryKey.location().toString(), type, codec);
        REGISTRY_KEY_2_VTYPE.put(registryKey, type);
        createTaggable(type, Holder::is, registryKey);
        return type;
    }

    private static void populateTypesFromRegistries() {
        for (Field f : Registries.class.getDeclaredFields()) {
            if (f.canAccess(null)
                    && f.getGenericType() instanceof ParameterizedType keyType
                    && keyType.getRawType() == ResourceKey.class
                    && keyType.getActualTypeArguments()[0] instanceof ParameterizedType regType
                    && regType.getRawType() == Registry.class) {
                makeFromRegistryKeyField(f, regType);
            }
        }
    }

    private static <T> void makeFromRegistryKeyField(Field f, Type regType) {
        try {
            var key = (ResourceKey<Registry<T>>) f.get(null);
            createFromRegistryKey(key, makeHolderTypeToken(regType));
        } catch (IllegalAccessException e) {
            DataFunctionsMod.LOGGER.error("Failed to create VType from Registry {}", f, e);
        }
    }

    private static <T> TypeToken<Holder<T>> makeHolderTypeToken(Type t) {
        return new TypeToken<Holder<T>>() {}
                .where(new TypeParameter<T>() {}, (TypeToken<T>) TypeToken.of(t));
    }

    public static void register(VEnvironment env) {
        populateTypesFromRegistries();

        env.registerType("level", LEVEL);
        env.registerType("entity", ENTITY);
        env.registerType("damage_source", DAMAGE_SOURCE);
        env.registerType("item_stack", ITEM_STACK, ItemStack.CODEC);
        env.registerType("block_in_world", BLOCK_IN_WORLD);
        env.registerType("item_advancement_predicate", ITEM_ADVANCEMENT_PREDICATE, ItemPredicate.CODEC);
        env.registerType("block_advancement_predicate", BLOCK_ADVANCEMENT_PREDICATE, BlockPredicate.CODEC);
        env.registerType("entity_advancement_predicate", ENTITY_ADVANCEMENT_PREDICATE, EntityPredicate.CODEC);
        env.registerType("block_worldgen_predicate", BLOCK_WORLDGEN_PREDICATE, net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate.CODEC);
        env.registerType("vec3", VEC3, Vec3.CODEC);
        env.registerType("sound_event", SOUND_EVENT, BuiltInRegistries.SOUND_EVENT.byNameCodec());
        env.registerType("particle", PARTICLE, ParticleTypes.CODEC);
        env.registerType("resource_location", RESOURCE_LOCATION, ResourceLocation.CODEC);
        env.registerType("tag", BARE_TAG);
        env.registerCodecForParameterisedType(BARE_TAG, type -> TagKey.codec(TAGGABLE_TYPE_INFO.get(type.parameters.getFirst()).registryKey()));
    }

    private record TaggableTypeInfo<T, R>(BiPredicate<T, TagKey<R>> isInTag, ResourceKey<Registry<R>> registryKey) {}
}
