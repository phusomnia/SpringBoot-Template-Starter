package com.example.springboot.Pun.Domain.Core;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

public class CustomJson 
{
    private static final ObjectMapper mapper = new ObjectMapper();
    
    @SneakyThrows
    public static String json(Object value, CustomJsonOptions options) {
        switch (options) {
            case WRITE_INDENTED:
                return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(value);
            case NONE:
                return mapper.writeValueAsString(value);
            default:
                throw new IllegalArgumentException("Invalid option: " + options);
        }
    }
}
