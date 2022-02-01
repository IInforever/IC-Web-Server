/*
 * Copyright (c) IInfo 2022.
 */

package com.i1nfo.ic.interceptor;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.i1nfo.ic.exception.NotAllowedException;
import com.i1nfo.ic.exception.UnauthorizedException;
import com.i1nfo.ic.service.UserService;
import com.i1nfo.ic.utils.JWTUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final
    JWTUtils jwtUtils;

    private final
    UserService userService;

    @Autowired
    public AuthInterceptor(JWTUtils jwtUtils, UserService userService) {
        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }

    protected static boolean CheckHeader(HttpServletRequest request,
                                         HttpServletResponse response,
                                         String jwt, @NotNull JWTUtils jwtUtils,
                                         UserService userService) {
        DecodedJWT token = jwtUtils.verifyToken(jwt);
        if (token.getSubject() == null || token.getSubject().isBlank())
            throw new UnauthorizedException("no specific subject");
        if (token.getSubject().equals("Admin"))
            throw new NotAllowedException("not allowed for admin", NotAllowedException.Type.ADMIN);
        request.setAttribute("userID", Long.valueOf(token.getClaim("uid").asString()));
        String newToken = jwtUtils.autoUpdateToken(token);
        if (newToken != null) {
            response.addHeader("Authorization", newToken);
            userService.updateLoginTime(token.getClaim("uid").asLong());
        }
        return true;
    }

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request,
                             @NotNull HttpServletResponse response,
                             @NotNull Object handler) {
        String jwt = request.getHeader("Authorization");
        if (jwt == null || jwt.length() == 0)
            throw new UnauthorizedException("no authorization token");
        return CheckHeader(request, response, jwt, jwtUtils, userService);
    }
}
