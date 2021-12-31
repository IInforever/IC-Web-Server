/*
 * Copyright (c) IInfo 2022.
 */

package com.i1nfo.icb.config;

import com.i1nfo.icb.interceptor.AdminAuthInterceptor;
import com.i1nfo.icb.interceptor.AuthInterceptor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final
    AuthInterceptor authInterceptor;

    private final
    AdminAuthInterceptor adminAuthInterceptor;

    @Autowired
    public WebMvcConfig(AuthInterceptor authInterceptor, AdminAuthInterceptor adminAuthInterceptor) {
        this.authInterceptor = authInterceptor;
        this.adminAuthInterceptor = adminAuthInterceptor;
    }

    @Override
    public void addInterceptors(@NotNull InterceptorRegistry registry) {
        // general user auth interceptor
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/api/user")
                .addPathPatterns("/api/paste");

        // administrator auth interceptor
        registry.addInterceptor(adminAuthInterceptor)
                .addPathPatterns("/api/users/**");
    }
}
