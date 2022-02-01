/*
 * Copyright (c) IInfo 2022.
 */

package com.i1nfo.ic.interceptor;

import com.i1nfo.ic.service.UserService;
import com.i1nfo.ic.utils.JWTUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.i1nfo.ic.interceptor.AuthInterceptor.CheckHeader;

@Component
public class PasteInterceptor implements HandlerInterceptor {

    private final JWTUtils jwtUtils;

    private final UserService userService;

    @Autowired
    public PasteInterceptor(JWTUtils jwtUtils, UserService userService) {
        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) {
        String jwt = request.getHeader("Authorization");
        if (jwt == null || jwt.length() == 0) {
            return true;
        }
        return CheckHeader(request, response, jwt, jwtUtils, userService);
    }

}
