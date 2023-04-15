package com.williambl.dpred;

import com.mojang.datafixers.types.Func;
import com.mojang.datafixers.util.Function3;
import com.mojang.serialization.Codec;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.williambl.dpred.DatapackablePredicates.id;

public final class EntityDPredicates {
    public static final DPredicateType<Entity, ? extends Function<Boolean, ? extends DPredicate<Entity>>> CONSTANT = Registry.register(
            DPredicate.ENTITY_PREDICATE_TYPE_REGISTRY.registry(),
            id("constant"),
            DPredicate.<Boolean, Entity>create(
                    Codec.BOOL.fieldOf("value"),
                    (value, e) -> value
            )
    );

    public static final DPredicateType<Entity, ? extends Function<List<DPredicate<Entity>>, ? extends DPredicate<Entity>>> AND = Registry.register(
            DPredicate.ENTITY_PREDICATE_TYPE_REGISTRY.registry(),
            id("and"),
            DPredicate.<List<DPredicate<Entity>>, Entity>create(
                    DPredicate.ENTITY_PREDICATE_TYPE_REGISTRY.codec().listOf().fieldOf("predicates"),
                    (predicates, e) -> predicates.stream().allMatch(p -> p.test(e))
            )
    );

    public static final DPredicateType<Entity, ? extends Function<List<DPredicate<Entity>>, ? extends DPredicate<Entity>>> OR = Registry.register(
            DPredicate.ENTITY_PREDICATE_TYPE_REGISTRY.registry(),
            id("or"),
            DPredicate.<List<DPredicate<Entity>>, Entity>create(
                    DPredicate.ENTITY_PREDICATE_TYPE_REGISTRY.codec().listOf().fieldOf("predicates"),
                    (predicates, e) -> predicates.stream().anyMatch(p -> p.test(e))
            )
    );

    public static final DPredicateType<Entity, ? extends Function<DPredicate<Entity>, ? extends DPredicate<Entity>>> NOT = Registry.register(
            DPredicate.ENTITY_PREDICATE_TYPE_REGISTRY.registry(),
            id("not"),
            DPredicate.<DPredicate<Entity>, Entity>create(
                    DPredicate.ENTITY_PREDICATE_TYPE_REGISTRY.codec().fieldOf("predicate"),
                    (predicate, e) -> !predicate.test(e)
            )
    );

    public static final DPredicateType<Entity, ? extends Function<DPredicate<Level>, ? extends DPredicate<Entity>>> LEVEL_PREDICATE = Registry.register(
            DPredicate.ENTITY_PREDICATE_TYPE_REGISTRY.registry(),
            id("level_predicate"),
            DPredicate.<DPredicate<Level>, Entity>create(
                    DPredicate.LEVEL_PREDICATE_TYPE_REGISTRY.codec().fieldOf("predicate"),
                    (predicate, e) -> predicate.test(e.level)
            ));

    public static final DPredicateType<Entity, ? extends Function<EntityPredicate, ? extends DPredicate<Entity>>> ADVANCEMENT_PREDICATE = Registry.register(
            DPredicate.ENTITY_PREDICATE_TYPE_REGISTRY.registry(),
            id("advancement_predicate"),
            DPredicate.<EntityPredicate, Entity>create(
                    DatapackablePredicates.ADVANCEMENT_ENTITY_PREDICATE_CODEC.fieldOf("predicate"),
                    (predicate, e) -> e.level instanceof ServerLevel sLevel && predicate.matches(sLevel, null, e)
            ));

    public static final DPredicateType<Entity, ? extends Supplier<? extends DPredicate<Entity>>> DEAD_OR_DYING = Registry.register(
            DPredicate.ENTITY_PREDICATE_TYPE_REGISTRY.registry(),
            id("dead_or_dying"),
            DPredicate.create(
                    e -> e instanceof LivingEntity l && l.isDeadOrDying()
            ));

    public static final DPredicateType<Entity, ? extends Supplier<? extends DPredicate<Entity>>> ON_FIRE = Registry.register(
            DPredicate.ENTITY_PREDICATE_TYPE_REGISTRY.registry(),
            id("on_fire"),
            DPredicate.create(
                    Entity::isOnFire
            )
    );

    public static final DPredicateType<Entity, ? extends Supplier<? extends DPredicate<Entity>>> SNEAKING = Registry.register(
            DPredicate.ENTITY_PREDICATE_TYPE_REGISTRY.registry(),
            id("sneaking"),
            DPredicate.create(
                    Entity::isShiftKeyDown
            )
    );

    public static final DPredicateType<Entity, ? extends Supplier<? extends DPredicate<Entity>>> SPRINTING = Registry.register(
            DPredicate.ENTITY_PREDICATE_TYPE_REGISTRY.registry(),
            id("sprinting"),
            DPredicate.create(
                    Entity::isSprinting
            )
    );

    public static final DPredicateType<Entity, ? extends Supplier<? extends DPredicate<Entity>>> SWIMMING = Registry.register(
            DPredicate.ENTITY_PREDICATE_TYPE_REGISTRY.registry(),
            id("swimming"),
            DPredicate.create(
                    Entity::isSwimming
            )
    );

    public static final DPredicateType<Entity, ? extends Supplier<? extends DPredicate<Entity>>> FALL_FLYING = Registry.register(
            DPredicate.ENTITY_PREDICATE_TYPE_REGISTRY.registry(),
            id("fall_flying"),
            DPredicate.<Entity>create(
                    e -> e instanceof LivingEntity l && l.isFallFlying()
            )
    );

    public static final DPredicateType<Entity, ? extends Function<TagKey<Fluid>, ? extends DPredicate<Entity>>> SUBMERGED_IN = Registry.register(
            DPredicate.ENTITY_PREDICATE_TYPE_REGISTRY.registry(),
            id("submerged_in"),
            DPredicate.<TagKey<Fluid>, Entity>create(
                    TagKey.codec(Registries.FLUID).fieldOf("fluid"),
                    (f, e) -> e.isEyeInFluid(f)
            )
    );

    public static final DPredicateType<Entity, ? extends Function<DPredicate<Double>, ? extends DPredicate<Entity>>> AGE = Registry.register(
            DPredicate.ENTITY_PREDICATE_TYPE_REGISTRY.registry(),
            id("age"),
            DPredicate.<DPredicate<Double>, Entity>create(
                    DPredicate.NUMBER_PREDICATE_TYPE_REGISTRY.codec().fieldOf("predicate"),
                    (predicate, e) -> predicate.test((double) e.tickCount)
            ));

    public static final DPredicateType<Entity, ? extends Function3<Attribute, DPredicate<Double>, Boolean, ? extends DPredicate<Entity>>> ATTRIBUTE_VALUE = Registry.register(
            DPredicate.ENTITY_PREDICATE_TYPE_REGISTRY.registry(),
            id("attribute_value"),
            DPredicate.<Attribute, DPredicate<Double>, Boolean, Entity>create(
                    BuiltInRegistries.ATTRIBUTE.byNameCodec().fieldOf("attribute"),
                    DPredicate.NUMBER_PREDICATE_TYPE_REGISTRY.codec().fieldOf("predicate"),
                    Codec.BOOL.optionalFieldOf("relative_to_base", false),
                    (attr, predicate, relative, e) -> e instanceof LivingEntity l
                            && predicate.test(relative ? l.getAttributeValue(attr) - l.getAttributeBaseValue(attr) : l.getAttributeValue(attr))
            ));

    static void init() {}
}
