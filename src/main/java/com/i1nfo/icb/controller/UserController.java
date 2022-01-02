/*
 * Copyright (c) IInfo 2022.
 */

package com.i1nfo.icb.controller;

import com.i1nfo.icb.model.User;
import com.i1nfo.icb.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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