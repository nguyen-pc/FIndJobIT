package com.example.FindJobIT.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class PermissionInterceptorConfiguration implements WebMvcConfigurer {
    @Bean
    PermissionInterceptor getPermissionInterceptor() {
        return new PermissionInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] whiteList = {
                "/", "/api/v1/auth/**", "/storage/**",
                "/api/v1/companies/**", "/api/v1/jobs/**", "/api/v1/skills", "/api/v1/files",
                "/api/v1/resumes/**", "/api/v1/subscribers/**",
                "/api/v1/positions/**", "/api/v1/questions/**", "/api/v1/skills/**",
                "/api/v1/jobs-company/**", "api/v1/jobs/follow/**", "api/v1/companies/follow/**",
                "/api/v1/jobs/recommend"
        };
        registry.addInterceptor(getPermissionInterceptor())
                .excludePathPatterns(whiteList);
    }
}
