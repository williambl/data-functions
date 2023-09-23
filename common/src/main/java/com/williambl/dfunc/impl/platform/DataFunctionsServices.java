package com.williambl.dfunc.impl.platform;

import com.williambl.dfunc.impl.DataFunctionsMod;

import java.util.ServiceLoader;

public class DataFunctionsServices {

    public static IPlatformHelper PLATFORM = load(IPlatformHelper.class);

    public static <T> T load(Class<T> clazz) {

        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        DataFunctionsMod.LOGGER.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }

}
