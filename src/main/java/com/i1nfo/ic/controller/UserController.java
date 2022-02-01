/*
 * Copyright (c) IInfo 2022.
 */

package com.i1nfo.ic.controller;

import com.i1nfo.ic.model.User;
import com.i1nfo.ic.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Object> getUser(@RequestAttribute @NotNull Long userID) {
        User user = userService.getBasicInfoById(userID);
        if (user != null)
            return ResponseEntity.ok(user);
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserNameById(@PathVariable Long id) {
        User user = userService.getNameById(id);
        if (user == null)
            return ResponseEntity.notFound().build();
        Map<String, String> map = new HashMap<>();
        map.put("name", user.getName());
        return ResponseEntity.ok(map);
    }

    @PatchMapping
    public ResponseEntity<Object> patchUser(@RequestAttribute @NotNull Long userID,
                                            @RequestBody @NotNull User user) {
        if (user.isEmpty())
            return ResponseEntity.badRequest().build();
        if (userService.updateById(userID, user))
            return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }

}