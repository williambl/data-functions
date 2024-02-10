package com.williambl.dfunc.api;

import com.google.common.reflect.TypeToken;
import com.williambl.dfunc.impl.DataFunctionsMod;
import com.williambl.vampilang.lang.VEnvironment;
import com.williambl.vampilang.lang.VValue;
import com.williambl.vampilang.lang.type.*;
import com.williambl.vampilang.stdlib.StandardVTypes;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.Vec3;
import org.checkerframework.checker.units.qual.A;

import java.util.function.Predicate;

public class DTypes {
    public static final TypedVType<Level> LEVEL = VType.create(TypeToken.of(Level.class));
    public static final TypedVType<Entity> ENTITY = VType.create(TypeToken.of(Entity.class));
    public static final VType OPTIONAL_ENTITY = StandardVTypes.OPTIONAL.with(0, ENTITY); // don't register this, as it's just a parameterisation of another type
    public static final TypedVType<DamageSource> DAMAGE_SOURCE = VType.create(TypeToken.of(DamageSource.class));
    public static final TypedVType<ItemStack> ITEM_STACK = VType.create(TypeToken.of(ItemStack.class));
    public static final TypedVType<Fluid> FLUID = VType.create(TypeToken.of(Fluid.class));
    public static final TypedVType<MobEffect> MOB_EFFECT = VType.create(TypeToken.of(MobEffect.class));
    public static final TypedVType<BlockInWorld> BLOCK_IN_WORLD = VType.create(TypeToken.of(BlockInWorld.class));
    public static final TypedVType<Enchantment> ENCHANTMENT = VType.create(TypeToken.of(Enchantment.class));
    public static final TypedVType<Attribute> ATTRIBUTE = VType.create(TypeToken.of(Attribute.class));
    public static final TypedVType<ItemPredicate> ITEM_ADVANCEMENT_PREDICATE = VType.create(TypeToken.of(ItemPredicate.class));
    public static final TypedVType<BlockPredicate> BLOCK_ADVANCEMENT_PREDICATE = VType.create(TypeToken.of(BlockPredicate.class));
    public static final TypedVType<EntityPredicate> ENTITY_ADVANCEMENT_PREDICATE = VType.create(TypeToken.of(EntityPredicate.class));
    public static final TypedVType<Vec3> VEC3 = VType.create(TypeToken.of(Vec3.class));
    public static final TypedVType<SoundEvent> SOUND_EVENT = VType.create(TypeToken.of(SoundEvent.class));
    public static final TypedVType<ParticleOptions> PARTICLE = VType.create(TypeToken.of(ParticleOptions.class));
    public static final TypedVType<ResourceLocation> RESOURCE_LOCATION = VType.create(TypeToken.of(ResourceLocation.class));
    public static final TypedVType<net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate> BLOCK_WORLDGEN_PREDICATE = VType.create(TypeToken.of(net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate.class));
    public static final VTemplateType TAGGABLE = VType.createTemplate(ENTITY, DAMAGE_SOURCE, ITEM_STACK, BLOCK_IN_WORLD, FLUID);
    private static final SimpleVType BARE_TAG = VType.create();
    public static final VParameterisedType TAG = VType.createParameterised(BARE_TAG, TAGGABLE);
    @SuppressWarnings("unchecked")
    public static Predicate<TagKey<?>> tagFunctionForTaggable(VValue taggable) {
        if (taggable.value() instanceof ItemStack stack) {
            return tag -> stack.is((TagKey<Item>) tag);
        } else if (taggable.value() instanceof DamageSource source) {
            return tag -> source.is((TagKey<DamageType>) tag);
        } else if (taggable.value() instanceof BlockInWorld block) {
            return tag -> block.getState().is((TagKey<Block>) tag);
        } else if (taggable.value() instanceof Fluid fluid) {
            return tag -> fluid.is((TagKey<Fluid>) tag);
        }

        return $ -> false;
    }
    //TODO use a map for these
    public static ResourceKey<? extends Registry<?>> registryKeyForTaggable(VType taggableType) {
        if (taggableType.equals(ENTITY)) {
            return Registries.ENTITY_TYPE;
        } else if (taggableType.equals(DAMAGE_SOURCE)) {
            return Registries.DAMAGE_TYPE;
        } else if (taggableType.equals(ITEM_STACK)) {
            return Registries.ITEM;
        } else if (taggableType.equals(BLOCK_IN_WORLD)) {
            return Registries.BLOCK;
        } else if (taggableType.equals(FLUID)) {
            return Registries.FLUID;
        }

        throw new IllegalArgumentException("Type %s is not taggable".formatted(taggableType));
    }

    public static void register(VEnvironment env) {
        env.registerType("level", LEVEL);
        env.registerType("entity", ENTITY);
        env.registerType("damage_source", DAMAGE_SOURCE);
        env.registerType("item_stack", ITEM_STACK, ItemStack.CODEC);
        env.registerType("fluid", FLUID, BuiltInRegistries.FLUID.byNameCodec());
        env.registerType("mob_effect", MOB_EFFECT, BuiltInRegistries.MOB_EFFECT.byNameCodec());
        env.registerType("block_in_world", BLOCK_IN_WORLD);
        env.registerType("enchantment", ENCHANTMENT, BuiltInRegistries.ENCHANTMENT.byNameCodec());
        env.registerType("attribute", ATTRIBUTE, BuiltInRegistries.ATTRIBUTE.byNameCodec());
        env.registerType("item_advancement_predicate", ITEM_ADVANCEMENT_PREDICATE, ItemPredicate.CODEC);
        env.registerType("block_advancement_predicate", BLOCK_ADVANCEMENT_PREDICATE, BlockPredicate.CODEC);
        env.registerType("entity_advancement_predicate", ENTITY_ADVANCEMENT_PREDICATE, EntityPredicate.CODEC);
        env.registerType("block_worldgen_predicate", BLOCK_WORLDGEN_PREDICATE, net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate.CODEC);
        env.registerType("vec3", VEC3, Vec3.CODEC);
        env.registerType("sound_event", SOUND_EVENT, BuiltInRegistries.SOUND_EVENT.byNameCodec());
        env.registerType("particle", PARTICLE, ParticleTypes.CODEC);
        env.registerType("resource_location", RESOURCE_LOCATION, ResourceLocation.CODEC);
        env.registerType("tag", BARE_TAG);
        //noinspection unchecked
        env.registerCodecForParameterisedType(BARE_TAG, type -> TagKey.codec((ResourceKey<? extends Registry<Object>>) registryKeyForTaggable(type.parameters.get(0))));
    }
}
