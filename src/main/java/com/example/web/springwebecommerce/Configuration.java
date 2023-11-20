package com.example.web.springwebecommerce;

import com.example.web.springwebecommerce.interceptor.SessionInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@org.springframework.context.annotation.Configuration
public class Configuration implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SessionInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/login", "/css/**", "/js/**","/assets/**","/Register","/Ecommerce/**","/confirmar/**","/solicitar","/restablecer/**","/restablecer");;
    }
}

