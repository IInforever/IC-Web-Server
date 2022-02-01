/*
 * Copyright (c) IInfo 2022.
 */

package com.i1nfo.ic.config;

import com.i1nfo.ic.interceptor.AdminAuthInterceptor;
import com.i1nfo.ic.interceptor.AuthInterceptor;
import com.i1nfo.ic.interceptor.PasteInterceptor;
import com.i1nfo.ic.interceptor.RecaptchaInterceptor;
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

    private final
    RecaptchaInterceptor recaptchaInterceptor;

    @Autowired
    public WebMvcConfig(AuthInterceptor authInterceptor, AdminAuthInterceptor adminAuthInterceptor, PasteInterceptor pasteInterceptor, RecaptchaInterceptor recaptchaInterceptor) {
        this.authInterceptor = authInterceptor;
        this.adminAuthInterceptor = adminAuthInterceptor;
        this.pasteInterceptor = pasteInterceptor;
        this.recaptchaInterceptor = recaptchaInterceptor;
    }

    @Override
    public void addInterceptors(@NotNull InterceptorRegistry registry) {
        // general user auth interceptor
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/api/users");

        // validate recaptcha response
        registry.addInterceptor(recaptchaInterceptor)
                .addPathPatterns("/api/pastes/anonymous")
                .addPathPatterns("/api/login")
                .addPathPatterns("/api/admin/login")
                .addPathPatterns("/api/register");

        // paste api auth check interceptor
        registry.addInterceptor(pasteInterceptor)
                .addPathPatterns("/api/pastes/**");

        // administrator auth interceptor
        registry.addInterceptor(adminAuthInterceptor)
                .addPathPatterns("/api/admin/users/**")
                .addPathPatterns("/api/admin/pastes/**");
    }
}
