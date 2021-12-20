/*
 * Copyright (c) IInfo 2021.
 */

package com.i1nfo.icb.controller.admin;

import com.i1nfo.icb.model.User;
import com.i1nfo.icb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersAdminController {

    private final UserService userService;

    @Autowired
    public UsersAdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Object> getUsers() {
        List<User> users = userService.list();
        if (users == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(users);
    }


    @GetMapping("{id}")
    public ResponseEntity<Object> getUser(@PathVariable Long id) {
        User user = userService.getById(id);
        if (user == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(user);
    }

}
