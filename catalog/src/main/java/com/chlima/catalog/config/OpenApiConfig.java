package com.chlima.catalog.config;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;

public class OpenApiConfig {

    @Bean
    public OpenApiCustomizer openApiCustomizer() {
        return openApi -> {
        };
    }
}
