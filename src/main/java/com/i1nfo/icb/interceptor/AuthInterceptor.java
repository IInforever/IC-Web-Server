/*
 * Copyright (c)  IInfo 2021.
 */

package com.i1nfo.icb.interceptor;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.i1nfo.icb.exception.UnauthorizedException;
import com.i1nfo.icb.utils.JWTUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component

public class AuthInterceptor implements HandlerInterceptor {

    private final
    JWTUtils jwtUtils;

    @Autowired
    public AuthInterceptor(JWTUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws IOException {
        String jwt = request.getHeader("Authorization");
        if (jwt == null || jwt.length() == 0)
            throw new UnauthorizedException("no authorization token");
        DecodedJWT token = jwtUtils.verifyToken(jwt);
        if (token.getSubject() == null || token.getSubject().length() == 0)
            throw new UnauthorizedException("no specific subject");
        String newToken = jwtUtils.autoUpdateToken(token);
        if (newToken != null)
            response.addHeader("Authorization", newToken);
        return true;
    }
}
