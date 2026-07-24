package com.wbs.wbs.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private RoleAuthenticationInterceptor roleAuthenticationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(roleAuthenticationInterceptor)
                .addPathPatterns("/user/**", "/admin/**")
                .excludePathPatterns("/user/login", "/user/register", "/admin/login");
    }
}
