package com.williambl.dfunc.impl;

import com.williambl.vampilang.lang.TypeNamer;
import com.williambl.vampilang.lang.VEnvironmentImpl;
import com.williambl.vampilang.lang.type.VParameterisedType;
import com.williambl.vampilang.lang.type.VType;
import com.williambl.vampilang.stdlib.StandardVTypes;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;
import java.util.stream.Collectors;

public class DataFunctionsEnvironment extends VEnvironmentImpl {
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
}
