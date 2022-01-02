/*
 * Copyright (c) IInfo 2022.
 */

package com.i1nfo.icb.config;

import com.i1nfo.icb.interceptor.AdminAuthInterceptor;
import com.i1nfo.icb.interceptor.AuthInterceptor;
import com.i1nfo.icb.interceptor.PasteInterceptor;
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

    private final
    PasteInterceptor pasteInterceptor;

    @Autowired
    public WebMvcConfig(AuthInterceptor authInterceptor, AdminAuthInterceptor adminAuthInterceptor, PasteInterceptor pasteInterceptor) {
        this.authInterceptor = authInterceptor;
        this.adminAuthInterceptor = adminAuthInterceptor;
        this.pasteInterceptor = pasteInterceptor;
    }

    @Override
    public void addInterceptors(@NotNull InterceptorRegistry registry) {
        // general user auth interceptor
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/api/user");

        // paste api auth check interceptor
        registry.addInterceptor(pasteInterceptor)
                .addPathPatterns("/api/paste/**");

        // administrator auth interceptor
        registry.addInterceptor(adminAuthInterceptor)
                .addPathPatterns("/api/users/**");
    }
}
