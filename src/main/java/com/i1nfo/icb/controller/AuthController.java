/*
 * Copyright (c) IInfo 2021.
 */

package com.i1nfo.icb.controller;

import com.i1nfo.icb.model.User;
import com.i1nfo.icb.service.UserService;
import com.i1nfo.icb.utils.JWTUtils;
import com.i1nfo.icb.validate.UserLogin;
import com.i1nfo.icb.validate.UserRegister;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final UserService userService;

    private final JWTUtils jwt;

    @Autowired
    public AuthController(UserService userService, JWTUtils jwt) {
        this.userService = userService;
        this.jwt = jwt;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Validated(UserLogin.class) @NotNull User user) {
        Long id = userService.login(user.getName(), user.getPasswd());
        if (id == 0)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().header("Authorization", jwt.createToken(String.valueOf(id))).build();
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody @Validated(UserRegister.class) User user) {
        if (userService.register(user))
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.badRequest().build();
    }
}
