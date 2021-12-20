/*
 * Copyright (c) IInfo 2021.
 */

package com.i1nfo.icb.controller;

import com.i1nfo.icb.model.User;
import com.i1nfo.icb.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Object> getUser(@RequestAttribute @NotNull Long userID) {
        User user = userService.getBasicInfoByID(userID);
        if (user != null)
            return ResponseEntity.ok(user);
        else
            return ResponseEntity.notFound().build();
    }

}