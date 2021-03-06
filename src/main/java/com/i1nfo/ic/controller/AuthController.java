/*
 * Copyright (c) IInfo 2022.
 */

package com.i1nfo.ic.controller;

import com.i1nfo.ic.model.User;
import com.i1nfo.ic.service.UserService;
import com.i1nfo.ic.utils.JWTUtils;
import com.i1nfo.ic.validate.UserLoginValidate;
import com.i1nfo.ic.validate.UserRegisterValidate;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

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
    public ResponseEntity<Object> login(@RequestBody @Validated(UserLoginValidate.class) @NotNull User user) {
        Long id = userService.getIdByNameAndPasswd(user.getName(), user.getPasswd());
        if (id == null)
            return ResponseEntity.notFound().build();
        userService.updateLoginTime(id);
        Map<String, String> claim = new HashMap<>();
        claim.put("uid", String.valueOf(id));
        return ResponseEntity.ok().header("Authorization", jwt.createToken("auth", claim)).build();
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody @Validated(UserRegisterValidate.class) User user) {
        if (userService.create(user))
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.badRequest().build();
    }
}
