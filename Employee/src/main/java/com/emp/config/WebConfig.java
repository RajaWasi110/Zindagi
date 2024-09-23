package com.emp.config;

import com.emp.jwt.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtRequestFilter)
                .addPathPatterns("/**")  // Apply to all endpoints
                .excludePathPatterns("/getToken");  // Exclude the getToken endpoint
    }
}
