package com.rest.vue.config;

import com.rest.vue.interceptors.GetInterceptorCategory;
import com.rest.vue.interceptors.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Config implements WebMvcConfigurer {
    @Autowired
    TokenInterceptor tokenInterceptor;

    @Autowired
    GetInterceptorCategory getInterceptorCategory;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080")
                .allowedMethods("*")
                .allowCredentials(true)
                .allowedHeaders("*");
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getInterceptorCategory)
                .addPathPatterns("/categories","categories/*");
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/getprofile", "/profile", "/profile/password");
    }
}
