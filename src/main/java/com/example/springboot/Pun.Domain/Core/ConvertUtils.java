package com.example.springboot.Pun.Domain.Core;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Map;

public class ConvertUtils 
{
    private static final Gson gson = new Gson();

    public static <T> Map<String, Object> toMap(T value) {
        String json = gson.toJson(value);
        Type type = new TypeToken<Map<String, Object>>() {}.getType();
        return gson.fromJson(json, type);
    }
}
