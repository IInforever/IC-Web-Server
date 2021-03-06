/*
 * Copyright (c) IInfo 2022.
 */

package com.i1nfo.ic.interceptor;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.i1nfo.ic.exception.NotAllowedException;
import com.i1nfo.ic.exception.UnauthorizedException;
import com.i1nfo.ic.utils.JWTUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AdminAuthInterceptor implements HandlerInterceptor {

    private final
    JWTUtils jwtUtils;

    public AdminAuthInterceptor(JWTUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) {
        String jwt = request.getHeader("Authorization");
        if (jwt == null || jwt.isBlank())
            throw new UnauthorizedException("no authorization token");
        DecodedJWT token = jwtUtils.verifyToken(jwt);
        if (!token.getSubject().equals("Admin"))
            throw new NotAllowedException("admin auth required", NotAllowedException.Type.USER);
        String newToken = jwtUtils.autoUpdateToken(token);
        if (newToken != null) {
            response.addHeader("Authorization", newToken);
        }
        return true;
    }
}
