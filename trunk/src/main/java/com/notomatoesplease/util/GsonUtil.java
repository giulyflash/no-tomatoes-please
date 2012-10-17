package com.notomatoesplease.util;

import java.lang.reflect.Type;

import com.google.gson.Gson;

public final class GsonUtil {
    private static final Gson GSON = new Gson();

    private GsonUtil() {
    }

    public static String toJson(final Object src) {
        return GSON.toJson(src);
    }

    public static Object toObject(final String json, final Class<Object> classOfT) {
        return GSON.fromJson(json, classOfT);
    }

    public static <T> T toObject(final String json, final Type type) {
        return GSON.fromJson(json, type);
    }
}
