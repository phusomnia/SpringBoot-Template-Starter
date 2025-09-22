package com.example.springboot.SharedKernel.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
//            builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//            JavaTimeModule module = new JavaTimeModule();
//            builder.modules(module);
            
//            builder.simpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            builder.serializers(
//                    new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
//                    new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
//            );
            
            // -- Omit null value from response -- 
            builder.serializationInclusion(JsonInclude.Include.NON_NULL);
        };
    }
}

