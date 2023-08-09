package com.williambl.dfunc.impl;

import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.KeyDispatchCodec;
import com.williambl.vampilang.codec.*;
import com.williambl.vampilang.lang.*;
import com.williambl.vampilang.lang.function.VFunctionDefinition;
import com.williambl.vampilang.lang.type.SimpleVType;
import com.williambl.vampilang.lang.type.VParameterisedType;
import com.williambl.vampilang.lang.type.VTemplateType;
import com.williambl.vampilang.lang.type.VType;
import com.williambl.vampilang.stdlib.StandardVFunctions;
import com.williambl.vampilang.stdlib.StandardVTypes;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DataFunctionsEnvironment implements VEnvironment {
    private final Map<VType, Codec<?>> codecs = new HashMap<>();
    private final Map<VType, Function<VParameterisedType, Codec<?>>> parameterisedTypeCodecs = new HashMap<>();
    private final Map<String, VFunctionDefinition> functions = new HashMap<>();
    private final Map<TypeAndSpecCacheKey, Codec<VExpression>> cachedVExpressionCodecs = new HashMap<>();

    @Override
    public Codec<?> rawCodecForType(VType type) {
        var res = this.codecs.get(type);
        if (res == null && type instanceof VParameterisedType paramed && this.parameterisedTypeCodecs.containsKey(paramed.bareType)) {
            var codec = this.parameterisedTypeCodecs.get(paramed.bareType).apply(paramed);
            this.codecs.put(paramed, codec);
            return codec;
        }

        return res;
    }

    @Override
    public Map<VType, Codec<?>> codecsMatching(VType type) {
        return this.allTypesMatching(type).stream().map(t -> Optional.ofNullable(this.rawCodecForType(t)).map(v -> Map.entry(t, v))).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue));
    }

    public Set<VType> allTypesMatching(VType type) {
        Set<VType> set = new HashSet<>();
        if (type instanceof VTemplateType template) {
            if (template.bounds == null) {
                set.addAll(this.codecs.keySet());
            } else {
                set.addAll(template.bounds.stream().map(this::allTypesMatching).flatMap(Set::stream).toList());
            }
        } else if (type instanceof VParameterisedType paramed) {
            List<Set<VType>> typesMatchingEachParam = new ArrayList<>();
            for (int i = 0; i < paramed.parameters.size(); i++) {
                typesMatchingEachParam.add(this.allTypesMatching(paramed.parameters.get(i)));
            }
            Sets.cartesianProduct(typesMatchingEachParam).forEach(assignment -> set.add(paramed.with(assignment)));
        } else {
            set.add(type);
        }

        return set;
    }

    @Override
    public Codec<VExpression> expressionCodecForType(VType type, EvaluationContext.Spec spec) {
        return this.cachedVExpressionCodecs.computeIfAbsent(new TypeAndSpecCacheKey(type, spec), $ -> new VExpressionCodec(
                new ValueCodec(type, this),
                KeyDispatchCodec.<VFunctionDefinition, VExpression.FunctionApplication>unsafe("function", Codec.STRING.comapFlatMap(
                                        name -> Optional.ofNullable(this.functions.get(name)).map(DataResult::success).orElse(DataResult.error(() -> "No such function with name "+name)),
                                        VFunctionDefinition::name),
                                expr -> DataResult.success(expr.function()),
                                func -> DataResult.success(new FunctionApplicationCodec(func, this, spec)),
                                func -> DataResult.success(new FunctionApplicationCodec(func.function(), this, spec))).codec()
                        .xmap(f -> (VExpression.FunctionApplication) f.resolveTypes(this, spec), Function.identity())
                        .comapFlatMap(f -> f.resolvedSignature() != null && type.contains(f.resolvedSignature().outputType())
                                        ? DataResult.success(f)
                                        : DataResult.error(() -> "Unmatched type"),
                                Function.identity()),
                VariableRefCodec.CODEC,
                new ObjectConstructionCodec(this, spec).comapFlatMap(o -> type.contains(o.resolveTypes(this, spec).type()) ? DataResult.success(o) : DataResult.error(() -> "Unmatched type"), Function.identity()),
                new ListConstructionCodec(this, spec)));
    }

    @Override
    public void registerCodecForType(VType type, Codec<?> codec) {
        this.codecs.put(type, codec);
    }

    @Override
    public void registerCodecForParameterisedType(SimpleVType bareType, Function<VParameterisedType, Codec<?>> codecForType) {
        this.parameterisedTypeCodecs.put(bareType, codecForType);
    }

    @Override
    public void registerFunction(VFunctionDefinition function) {
        this.functions.put(function.name(), function);
    }

    @Override
    public void registerType(String s, VType vType) {
        ResourceLocation name = s.contains(":") ? ResourceLocation.tryParse(s) : ResourceLocation.tryBuild(DataFunctionsMod.MODID, s);
        if (name == null) {
            throw new IllegalArgumentException("invalid name %s".formatted(s));
        }

        Registry.register(DataFunctionsMod.TYPE_REGISTRY, name, vType);
    }

    @Override
    public VType getType(String s) {
        ResourceLocation name = s.contains(":") ? ResourceLocation.tryParse(s) : ResourceLocation.tryBuild(DataFunctionsMod.MODID, s);
        if (name == null) {
            throw new IllegalArgumentException("invalid name %s".formatted(s));
        }
        return DataFunctionsMod.TYPE_REGISTRY.get(name);
    }

    @Override
    public VParameterisedType listType() {
        return StandardVTypes.LIST;
    }

    @Override
    public Map<String, VType> allTypes() {
        return DataFunctionsMod.TYPE_REGISTRY.entrySet().stream().collect(Collectors.toMap( //TODO cache on registry freeze
                kv -> kv.getKey().location().toString(),
                Map.Entry::getValue));
    }

    @Override
    public TypeNamer createTypeNamer() {
        Map<VType, String> reversedMap = DataFunctionsMod.TYPE_REGISTRY.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, kv -> kv.getKey().location().toString()));
        return new TypeNamer(reversedMap);
    }

    @Override
    public void registerType(String name, VType type, Codec<?> codec) {
        VEnvironment.super.registerType(name, type, codec);
    }


    private record TypeAndSpecCacheKey(VType type, EvaluationContext.Spec spec) { }
}
