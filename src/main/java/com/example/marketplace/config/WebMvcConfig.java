package com.example.marketplace.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;




@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${app.config.allowedUrls}")
    String[] allowedUrls;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins(allowedUrls)
                .allowedMethods("*");
    }
}
