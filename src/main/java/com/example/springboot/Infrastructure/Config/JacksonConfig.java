package com.example.springboot.Infrastructure.Config;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

//@Configuration
//public class JacksonConfig {
//    @Bean
//    public Jackson2ObjectMapperBuilderCustomizer jacksonCustomizer() {
//        return builder -> {
//            builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//            JavaTimeModule module = new JavaTimeModule();
//            builder.modules(module);
//        };
//    }
//}

//@Configuration
//public class JacksonConfig {
//    @Bean
//    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
//        return builder -> {
//            builder.simpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            builder.serializers(
//                    new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
//                    new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
//            );
//        };
//    }
//}

