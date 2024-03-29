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
    private final Map<TypeAndSpecCacheKey, Codec<List<VExpression>>> cachedVExpressionMultiCodecs = new HashMap<>();

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
        return this.cachedVExpressionCodecs.computeIfAbsent(new TypeAndSpecCacheKey(type, spec), $ ->
                this.expressionMultiCodecForType(type, spec).comapFlatMap(
                        exprs -> exprs.stream()
                                .map(expr -> expr.resolveTypes(this, spec))
                                .map(DataResult::result)
                                .filter(Optional::isPresent)
                                .map(Optional::get)
                                .filter(expr -> type.contains(expr.type()))
                                .map(DataResult::success)
                                .findFirst()
                                .orElse(DataResult.error(() -> "Unmatched type")),
                        List::of)
        );
    }

    @Override
    public Codec<List<VExpression>> expressionMultiCodecForType(VType type, EvaluationContext.Spec spec) {
        return this.cachedVExpressionMultiCodecs.computeIfAbsent(new TypeAndSpecCacheKey(type, spec), $ -> new VExpressionCodec(
                ValueDecoder.createCodec(this, spec, type),
                KeyDispatchCodec.<VFunctionDefinition, List<VExpression.FunctionApplication>>unsafe("function", Codec.STRING.comapFlatMap(
                                        name -> Optional.ofNullable(this.functions.get(name)).map(DataResult::success).orElse(DataResult.error(() -> "No such function with name " + name)),
                                        VFunctionDefinition::name),
                                exprs -> exprs.stream().map(expr -> DataResult.success(expr.function())).findFirst().orElse(DataResult.error(() -> "No entry in list!")),
                                func -> DataResult.success(FunctionApplicationDecoder.createCodec(func, this, spec)),
                                funcs -> funcs.stream().map(func -> DataResult.success(FunctionApplicationDecoder.createCodec(func.function(), this, spec))).findFirst().orElse(DataResult.error(() -> "No entry in list!"))).codec()
                        .comapFlatMap(fs -> Optional.of(fs.stream()
                                                .map(f -> f.resolveTypes(this, spec)
                                                        .flatMap(fr -> type.contains(((VExpression.FunctionApplication) fr).resolvedSignature().outputType())
                                                                ? DataResult.success((VExpression.FunctionApplication) fr)
                                                                : DataResult.error(() -> "Unmatched type")))
                                                .map(DataResult::result)
                                                .filter(Optional::isPresent)
                                                .map(Optional::get)
                                                .toList())
                                        .filter(l -> !l.isEmpty())
                                        .map(DataResult::success)
                                        .orElse(DataResult.error(() -> "Unmatched type")),
                                Function.identity()),
                VariableRefCodec.CODEC,
                ObjectConstructionDecoder.createCodec(this, spec).comapFlatMap(os ->
                                Optional.of(os.stream()
                                                .map(o -> o.resolveTypes(this, spec)
                                                        .flatMap(or -> type.contains(or.type())
                                                                ? DataResult.success((VExpression.ObjectConstruction) or)
                                                                : DataResult.error(() -> "Unmatched type")))
                                                .map(DataResult::result)
                                                .filter(Optional::isPresent)
                                                .map(Optional::get)
                                                .toList())
                                        .filter(l -> !l.isEmpty())
                                        .map(DataResult::success)
                                        .orElse(DataResult.error(() -> "Unmatched type!")),
                        Function.identity()),
                ListConstructionDecoder.createCodec(this, spec, type),
                LambdaDecoder.createCodec(this, spec, type)));
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
