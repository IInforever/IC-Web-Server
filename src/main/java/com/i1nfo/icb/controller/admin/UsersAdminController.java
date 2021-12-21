/*
 * Copyright (c) IInfo 2021.
 */

package com.i1nfo.icb.controller.admin;

import com.i1nfo.icb.model.User;
import com.i1nfo.icb.service.UserService;
import com.i1nfo.icb.validate.UserRegister;
import com.i1nfo.icb.validate.UserUpdate;
import com.i1nfo.icb.validate.UserUpdateAll;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody @Validated(UserRegister.class) User user) {
        if (userService.create(user))
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.badRequest().build();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Object> getUser(@PathVariable Long id) {
        User user = userService.getById(id);
        if (user == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable Long id, @RequestBody @Validated(UserUpdateAll.class) User user) {
        if (userService.updateById(id, user))
            return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> patchUser(@PathVariable Long id, @RequestBody @Validated(UserUpdate.class) @NotNull User user) {
        if (user.isEmpty())
            return ResponseEntity.badRequest().build();
        if (userService.updateById(id, user))
            return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
        if (userService.removeById(id))
            return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }

}
