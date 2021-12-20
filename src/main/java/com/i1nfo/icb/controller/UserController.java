/*
 * Copyright (c) IInfo 2021.
 */

package com.i1nfo.icb.controller;

import com.i1nfo.icb.model.User;
import com.i1nfo.icb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUser(@PathVariable String id) {
        User user = userService.getById(id);
        if (user != null)
            return ResponseEntity.ok(user);
        else
            return ResponseEntity.notFound().build();
    }

}